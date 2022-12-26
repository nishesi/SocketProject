package ru.itis.snaky.protocol;

import org.junit.jupiter.api.Test;
import ru.itis.snaky.protocol.io.ProtocolInputStream;
import ru.itis.snaky.protocol.io.ProtocolOutputStream;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.AuthenticationParams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProtocolOutputStreamTest {
    @Test
    void testInputStream() {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ProtocolOutputStream out = new ProtocolOutputStream(byteOut);
        Message<AuthenticationParams> message = new Message<>(MessageType.AUTHORIZATION,
                new AuthenticationParams("NishEsI", false));

        out.writeMessage(message);
        try {
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Arrays.toString(byteOut.toByteArray()));

        ProtocolInputStream protocolIn = new ProtocolInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
        Message message1 = protocolIn.readMessage();
        System.out.println(message1);
        assertEquals(message, message1);
    }
}
