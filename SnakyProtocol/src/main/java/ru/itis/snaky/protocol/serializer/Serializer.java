package ru.itis.snaky.protocol.serializer;

import ru.itis.snaky.protocol.exceptions.ProtocolSerializationException;

import java.io.*;
import java.lang.reflect.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static ru.itis.snaky.protocol.Properties.SERIALIZATION_VERSION;

/**
 * Serializer is a class that have static methods for serialization and deserialization of object.
 * This class can be used if you definitely know what object serialized in these bytes.
 *
 * @author Nurislam Zaripov
 */

public class Serializer {
    public static <T> byte[] serialize(T element) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        serialize(byteArrayOutputStream, element);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Serializes fields of element if it has private, non-static fields.
     * Supported serialization types: boolean, byte, short, int, String,
     * Object (if it has only supported types as fields), array of supported types
     *
     * @param out     - OutputStream where object will be serialized
     * @param element - object to be serialized
     * @param <T>     - type of element
     * @throws NullPointerException           if OutputStream, element or its field is null
     * @throws ProtocolSerializationException if element is unsupported type or its field is unsupported type
     */

    public static <T> void serialize(OutputStream out, T element) {
        try {
            out.write(SERIALIZATION_VERSION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeElement(out, element);
    }

    private static <T> void writeElement(OutputStream out, T element) {
        validate(out, element);
        validate(element.getClass());

        Field[] fields = getFilteredSortedFields(element.getClass().getDeclaredFields());

        try {
            for (Field field : fields) {
                writeField(out, element, field);
            }
        } catch (IOException | IllegalAccessException e) {
            throw new ProtocolSerializationException("serialization failed", e);
        }
    }


    private static <T> void validate(OutputStream out, T element) {
        if (out == null || element == null) {
            throw new NullPointerException("Can't serialize: out = " + out + ", element = " + element);
        }
    }

    private static void validateField(Object fieldValue) {
        if (fieldValue == null) {
            throw new IllegalArgumentException("unsupported serialization type: null");
        }
    }

    private static Field[] getFilteredSortedFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> {
                    int modifiers = field.getModifiers();
                    return Modifier.isPrivate(modifiers) & !Modifier.isStatic(modifiers);
                })
                .sorted(Comparator.comparing(Field::getName))
                .toArray(Field[]::new);
    }

    private static <T> void writeField(OutputStream out, T element, Field field) throws IOException, IllegalAccessException {
        field.setAccessible(true);
        validateField(field.get(element));

        if (field.getType().isPrimitive()) {
            writePrimitive(out, element, field);

        } else if (field.getType().isArray()) {
            writeArray(out, element, field);

        } else {
            writeObject(out, element, field);
        }
        field.setAccessible(false);
    }

    private static <T> void writePrimitive(OutputStream out, T element, Field field)
            throws IllegalAccessException, IOException {
        Class<?> type = field.getType();

        if (type.equals(Boolean.TYPE)) {
            out.write((byte) (field.getBoolean(element) ? 1 : 0));

        } else if (type.equals(Byte.TYPE)) {
            out.write(field.getByte(element));

        } else if (type.equals(Short.TYPE)) {

            short s = field.getShort(element);
            ByteBuffer buffer = ByteBuffer.allocate(2).putShort(s);
            out.write(buffer.array());

        } else if (type.equals(Integer.TYPE)) {

            int i = field.getInt(element);
            ByteBuffer buffer = ByteBuffer.allocate(4).putInt(i);
            out.write(buffer.array());

        } else if (type.equals(Double.TYPE) || type.equals(Float.TYPE) ||
                type.equals(Character.TYPE) || type.equals(Long.TYPE)) {
            throw new ProtocolSerializationException("Unsupported type: " + type);
        }
    }

    private static <T> void writeArray(OutputStream out, T element, Field field) throws IllegalAccessException, IOException {
        Object arr = field.get(element);
        int len = Array.getLength(arr);

        ByteBuffer lenBuffer = ByteBuffer.allocate(4).putInt(len);
        out.write(lenBuffer.array());

        for (int i = 0; i < len; i++) {
            writeElement(out, Array.get(arr, i));
        }
    }

    private static <T> void writeObject(OutputStream out, T element, Field field) throws IllegalAccessException, IOException {
        if (field.getType().equals(String.class)) {

            String str = (String) field.get(element);
            ByteBuffer lenBuffer = ByteBuffer.allocate(4).putInt(str.length());
            out.write(lenBuffer.array());
            out.write(str.getBytes());

        } else {
            writeElement(out, field.get(element));
        }
    }

    public static <T> T deserialize(byte[] arr, Class<T> tClass) {
        ByteArrayInputStream in = new ByteArrayInputStream(arr);
        return deserialize(in, tClass);
    }

    /**
     * Method for deserialization definitely object from a byte stream into object.
     * if another object serialized into byte stream, not guarantied that method throws exception.
     *
     * @param in     InputStream from which object will be deserialized
     * @param tClass type instance of you want to deserialize
     * @return instance of tClass
     * @throws ProtocolSerializationException if version of serialized object not supported with
     *                                        current realization, or if tClass has an unsupported field type.
     */

    public static <T> T deserialize(InputStream in, Class<T> tClass) {
        validateVersion(in);
        return readElement(in, tClass);
    }

    private static void validateVersion(InputStream in) {
        DataInputStream dataInputStream = new DataInputStream(in);
        try {
            byte version = dataInputStream.readByte();
            if (version != SERIALIZATION_VERSION) {
                throw new ProtocolSerializationException("Unsupported serialization version: " +
                        "current: " + SERIALIZATION_VERSION +
                        ", required: " + version);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static <T> T readElement(InputStream in, Class<T> tClass) {
        validate(tClass);
        Field[] fields = getFilteredSortedFields(tClass.getDeclaredFields());
        T obj = createEmptyObject(tClass);

        for (Field field : fields) {
            injectField(in, field, obj);
        }
        return obj;
    }

    private static <T> T createEmptyObject(Class<T> tClass) {
        //todo ватапук
        if (tClass.isArray()) {
            return null;
        }
        final Constructor<T> constr = (Constructor<T>) tClass.getConstructors()[0];
        final List<Object> params = new ArrayList<>();
        for (Class<?> pType : constr.getParameterTypes()) {
            if (pType.equals(Boolean.TYPE)) {
                params.add(false);
            } else {
                params.add((pType.isPrimitive()) ? 0 : null);
            }
        }

        try {
            constr.setAccessible(true);
            return constr.newInstance(params.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ProtocolSerializationException("serialization failed: can't create instance", e);
        }
    }

    private static <T> void injectField(InputStream in, Field field, T element) {
        try {

            field.setAccessible(true);
            if (field.getType().isPrimitive()) {
                injectPrimitive(in, field, element);

            } else if (field.getType().isArray()) {
                injectArrayElements(in, field, element);

            } else {
                injectObject(in, field, element);
            }
            field.setAccessible(false);

        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void injectPrimitive(InputStream in, Field field, T element) throws IOException, IllegalAccessException {
        Type type = field.getType();
        DataInputStream dataInputStream = new DataInputStream(in);


        if (type.equals(Boolean.TYPE)) {
            field.set(element, dataInputStream.readBoolean());

        } else if (type.equals(Byte.TYPE)) {
            field.set(element, dataInputStream.readByte());

        } else if (type.equals(Short.TYPE)) {
            field.set(element, dataInputStream.readShort());

        } else if (type.equals(Integer.TYPE)) {
            field.set(element, dataInputStream.readInt());

        } else if (type.equals(Double.TYPE) || type.equals(Float.TYPE) ||
                type.equals(Character.TYPE) || type.equals(Long.TYPE)) {
            throw new ProtocolSerializationException("Unsupported type: " + type);
        }
    }

    private static <T> void injectArrayElements(InputStream in, Field field, T element) throws IOException, IllegalAccessException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int len = dataInputStream.readInt();

        Object arr = Array.newInstance(field.getType().getComponentType(), len);

        for (int i = 0; i < len; i++) {
            Array.set(arr, i, readElement(in, field.getType().getComponentType()));
        }

        field.set(element, arr);
    }

    private static <T> void injectObject(InputStream in, Field field, T element) throws IOException, IllegalAccessException {
        if (field.getType().equals(String.class)) {
            int len = in.read() << 24 |
                    in.read() << 16 |
                    in.read() << 8 |
                    in.read();
            byte[] arr = new byte[len];

            if (in.read(arr) != len) {
                throw new ProtocolSerializationException("Bytes ended");
            }

            String str = new String(arr, StandardCharsets.UTF_8);

            field.set(element, str);
        } else {

            Object fieldObj = readElement(in, field.getType());
            field.set(element, fieldObj);
        }
    }

    private static <T> void validate(Class<T> clazz) {
        if (clazz.isArray()) {
            throw new IllegalArgumentException("unsupported type: array");
        }
    }
}
