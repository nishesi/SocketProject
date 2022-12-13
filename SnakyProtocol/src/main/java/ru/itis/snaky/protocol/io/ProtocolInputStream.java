package ru.itis.snaky.protocol.io;

import lombok.AllArgsConstructor;
import ru.itis.snaky.protocol.exceptions.ProtocolSerializationException;
import ru.itis.snaky.protocol.message.Message;

import java.io.*;


@AllArgsConstructor
public class ProtocolInputStream extends InputStream {
    private final InputStream inputStream;

    public Message readMessage() throws ProtocolSerializationException {
        try {
            int length = inputStream.read() << 24 |
                    inputStream.read() << 16 |
                    inputStream.read() << 8 |
                    inputStream.read();

            byte[] bytes = new byte[length];
            int realLength = inputStream.read(bytes);

            if (realLength != length) {
                throw new ProtocolSerializationException("expected " + length + " bytes, found " + realLength);
            }

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (Message) objectInputStream.readObject();

        } catch (ClassNotFoundException | IOException e) {
            throw new ProtocolSerializationException(e);
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
