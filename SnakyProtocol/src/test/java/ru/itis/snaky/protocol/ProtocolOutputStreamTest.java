package ru.itis.snaky.protocol;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProtocolOutputStreamTest {
    @Test
    void testInputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ProtocolOutputStream outputStream = new ProtocolOutputStream(baos);

        Message message = new Message(MessageType.AUTHORIZATION, new Object[]{"username", 256});

        outputStream.writeMessage(message);

        try {
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ProtocolInputStream inputStream = new ProtocolInputStream(bais);
        Message readed = inputStream.readMessage();

        assertEquals(message, readed);
    }
}
