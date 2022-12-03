package ru.itis.snaky.protocol.serializer;

import org.junit.jupiter.api.Test;
import ru.itis.snaky.protocol.request.Request;
import ru.itis.snaky.protocol.request.RequestType;
import ru.itis.snaky.protocol.request.SnakyRequest;
import ru.itis.snaky.protocol.response.Response;
import ru.itis.snaky.protocol.response.ResponseType;
import ru.itis.snaky.protocol.response.SnakyResponse;

import static org.junit.jupiter.api.Assertions.*;

class SerializerImplTest {

    @Test
    void testRequest() {
        SnakyRequest request = new SnakyRequest(RequestType.AUTHORIZATION, new Object[]{"username", "256"});
        Serializer serializer = new SerializerImpl();
        byte[] bytes = serializer.encode(request);
        Request decoded = serializer.decodeRequest(bytes);

        assertEquals(request, decoded);
    }

    @Test
    void testResponse() {
        SnakyResponse request = new SnakyResponse(ResponseType.AUTHORIZATION, new Object[]{"username", "256"});
        Serializer serializer = new SerializerImpl();
        byte[] bytes = serializer.encode(request);
        Response decoded = serializer.decodeResponse(bytes);
        assertEquals(request, decoded);
    }

}
