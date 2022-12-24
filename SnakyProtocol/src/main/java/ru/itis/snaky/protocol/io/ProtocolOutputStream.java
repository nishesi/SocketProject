package ru.itis.snaky.protocol.io;

import lombok.AllArgsConstructor;
import ru.itis.snaky.protocol.exceptions.ProtocolSerializationException;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.serializer.Serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import static ru.itis.snaky.protocol.Properties.PROTOCOL_VERSION;

@AllArgsConstructor
public class ProtocolOutputStream extends OutputStream {
    private final OutputStream outputStream;

    public void writeMessage(Message<?> message) throws ProtocolSerializationException {

        byte[] params = Serializer.serialize(message.getParameters());
        ByteBuffer length = ByteBuffer.allocate(4).putInt(params.length);

        try {
            outputStream.write(PROTOCOL_VERSION);
            outputStream.write(message.getMessageType().getValue());
            outputStream.write(length.array());
            outputStream.write(params);

            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
