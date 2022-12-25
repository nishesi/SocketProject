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
            writeElement(out, element, element.getClass());
        } catch (IOException e) {
            throw new ProtocolSerializationException("serialization failed", e);
        }
    }

    private static <T> void writeElement(OutputStream out, T element, Class<?> elementClass) throws IOException {
        validate(out, element);

        if (elementClass.isPrimitive()) {
            writePrimitive(out, element, elementClass);
        } else if (elementClass.isArray()) {
            writeArray(out, element);
        } else {
            writeObject(out, element);
        }
    }

    private static <T> void writePrimitive(OutputStream out, T element, Class<?> tClass) throws IOException {
        if (tClass.equals(Boolean.TYPE)) {
            out.write((byte) ((boolean)element ? 1 : 0));

        } else if (tClass.equals(Byte.TYPE)) {
            out.write((byte)element);

        } else if (tClass.equals(Short.TYPE)) {
            ByteBuffer buffer = ByteBuffer.allocate(2).putShort((short)element);
            out.write(buffer.array());

        } else if (tClass.equals(Integer.TYPE)) {
            ByteBuffer buffer = ByteBuffer.allocate(4).putInt((int)element);
            out.write(buffer.array());

        } else {
            throw new ProtocolSerializationException("Unsupported type: " + tClass);
        }
    }

    private static <T> void writeArray(OutputStream out, T array) throws IOException {
        int length = Array.getLength(array);
        ByteBuffer buffer = ByteBuffer.allocate(4).putInt(length);
        out.write(buffer.array());

        for (int i = 0; i < length; i++) {
            writeElement(out, Array.get(array, i), array.getClass().getComponentType());
        }
    }

    private static <T> void writeObject(OutputStream out, T element) throws IOException {
        if (element.getClass().equals(String.class)) {
            String str = (String)element;
            byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
            ByteBuffer lenBuffer = ByteBuffer.allocate(4).putInt(strBytes.length);

            out.write(lenBuffer.array());
            out.write(strBytes);
            return;
        }
        Field[] fields = getFilteredSortedFields(element.getClass().getDeclaredFields());

        for (Field field : fields) {
            writeField(out, element, field);
        }
    }


    private static <T> void validate(OutputStream out, T element) {
        String mess = null;
        if (out == null) {
            mess = "outputStream is null";
        }
        if (element == null) {
            mess += " element is null";
        }
        if (mess != null) {
            throw new NullPointerException("Can't serialize: " + mess);
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

    private static <T> void writeField(OutputStream out, T element, Field field) throws IOException {
        try {
            field.setAccessible(true);
            validateField(field.get(element));
            writeElement(out, field.get(element), field.getType());
            field.setAccessible(false);

        } catch (IllegalAccessException ex) {
            throw new ProtocolSerializationException("access failed", ex);
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
        try {
            validateVersion(in);
            return readElement(in, tClass);
        } catch (IOException e) {
            throw new ProtocolSerializationException("deserialization failed", e);
        }
    }

    private static void validateVersion(InputStream in) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);

        byte version = dataInputStream.readByte();
        if (version != SERIALIZATION_VERSION) {
            throw new ProtocolSerializationException("Unsupported serialization version: " +
                    "current: " + SERIALIZATION_VERSION +
                    ", required: " + version);
        }
    }

    private static <T> T readElement(InputStream in, Class<T> tClass) throws IOException {
        if (tClass.isPrimitive()) {
            return readPrimitive(in, tClass);

        } else if (tClass.isArray()) {
            return readArray(in, tClass);

        } else {
            return readObject(in, tClass);
        }
    }

    private static <T> T readPrimitive(InputStream in, Class<T> primitiveClass) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);

        if (primitiveClass.equals(Boolean.TYPE)) {
            return (T) Boolean.valueOf(dataIn.readBoolean());

        } else if (primitiveClass.equals(Byte.TYPE)) {
            return (T) Byte.valueOf(dataIn.readByte());

        } else if (primitiveClass.equals(Short.TYPE)) {
            return (T) Short.valueOf(dataIn.readShort());

        } else if (primitiveClass.equals(Integer.TYPE)) {
            return (T) Integer.valueOf(dataIn.readInt());

        } else {
            throw new ProtocolSerializationException("Unsupported type: " + primitiveClass);
        }
    }

    private static <T> T readArray(InputStream in, Class<T> arrayClass) throws IOException {
        int length = in.read() << 24 |
                in.read() << 16 |
                in.read() << 8 |
                in.read();

        Object array = Array.newInstance(arrayClass.getComponentType(), length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, readElement(in, arrayClass.getComponentType()));
        }
        return (T) array;
    }

    private static <T> T readObject(InputStream in, Class<T> objectClass) throws IOException {
        if (objectClass.equals(String.class)) {
            int length = in.read() << 24 |
                    in.read() << 16 |
                    in.read() << 8 |
                    in.read();
            byte[] strBytes = new byte[length];
            in.read(strBytes);
            String str = new String(strBytes, StandardCharsets.UTF_8);
            return (T)str;
        }

        T obj = createEmptyObject(objectClass);
        Field[] fields = getFilteredSortedFields(objectClass.getDeclaredFields());

        for (Field field : fields) {
            injectField(in, field, obj);
        }
        return obj;
    }

    private static <T> T createEmptyObject(Class<T> tClass) {
        if (tClass.isArray() || tClass.isPrimitive()) {
            throw new IllegalArgumentException("illegal type");
        }
        Constructor<?>[] constructors = tClass.getConstructors();
        Constructor<T> constr = (Constructor<T>) constructors[0];
        List<Object> params = new ArrayList<>();
        for (Class<?> pType : constr.getParameterTypes()) {
            if (pType.equals(Boolean.TYPE)) {
                params.add(false);
            } else {
                params.add((pType.isPrimitive()) ? (byte) 0 : null);
            }
        }

        try {
            constr.setAccessible(true);
            return constr.newInstance(params.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ProtocolSerializationException("serialization failed: can't create instance", e);
        }
    }

    private static <T> void injectField(InputStream in, Field field, T element) throws IOException {
        try {
            field.setAccessible(true);
            Object fieldValue = readElement(in, field.getType());
            field.set(element, fieldValue);
            field.setAccessible(false);

        } catch (IllegalAccessException ex) {
            throw new ProtocolSerializationException("something go wrong");
        }
    }
}
