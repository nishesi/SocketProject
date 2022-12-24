package ru.itis.snaky.protocol.io;

import lombok.AllArgsConstructor;
import ru.itis.snaky.protocol.exceptions.ProtocolSerializationException;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.serializer.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static ru.itis.snaky.protocol.Properties.PROTOCOL_VERSION;


@AllArgsConstructor
public class ProtocolInputStream extends InputStream {
    private final InputStream inputStream;

    public Message<?> readMessage() throws ProtocolSerializationException {
        try {
            validateVersion();

            MessageType messageType = MessageType.fromByte((byte) inputStream.read());

            int paramsLength = inputStream.read() << 24 |
                    inputStream.read() << 16 |
                    inputStream.read() << 8 |
                    inputStream.read();
            byte[] params = new byte[paramsLength];

            int readLength = inputStream.read(params);
            if (readLength != paramsLength) {
                throw new ProtocolSerializationException("params length mismatch: " +
                        "expected " + paramsLength + ", found " + readLength);
            }

            return new Message<>(
                    messageType,
                    Serializer.deserialize(params, messageType.getParameterClass()));

        } catch (IOException e) {
            throw new ProtocolSerializationException("read failed", e);
        }
    }

    private void validateVersion() throws IOException {
        byte version = (byte) inputStream.read();

        if (version != PROTOCOL_VERSION) {
            throw new ProtocolSerializationException("unsupported protocol version: " +
                    "expected " + PROTOCOL_VERSION + ", found " + version);
        }
    }


    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return inputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read(b, off, len);
    }

    @Override
    public byte[] readAllBytes() throws IOException {
        return inputStream.readAllBytes();
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        return inputStream.readNBytes(len);
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        return inputStream.readNBytes(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return inputStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public void mark(int readlimit) {
        inputStream.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        inputStream.reset();
    }

    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }

    @Override
    public long transferTo(OutputStream out) throws IOException {
        return inputStream.transferTo(out);
    }
}
