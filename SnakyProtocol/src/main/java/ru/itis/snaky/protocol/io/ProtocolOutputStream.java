package ru.itis.snaky.protocol.io;

import lombok.AllArgsConstructor;
import ru.itis.snaky.protocol.exceptions.ProtocolSerializationException;
import ru.itis.snaky.protocol.message.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

@AllArgsConstructor
public class ProtocolOutputStream extends OutputStream {
    private final OutputStream outputStream;

    public void writeMessage(Message message) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(buffer)) {

            objectOutputStream.writeObject(message);
            byte[] serializedMessage = buffer.toByteArray();

            outputStream.write(serializedMessage.length >> 24);
            outputStream.write(serializedMessage.length >> 16);
            outputStream.write(serializedMessage.length >> 8);
            outputStream.write(serializedMessage.length);
            outputStream.write(serializedMessage);
            outputStream.flush();

        } catch (IOException e) {
            throw new ProtocolSerializationException(e);
        }

    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
