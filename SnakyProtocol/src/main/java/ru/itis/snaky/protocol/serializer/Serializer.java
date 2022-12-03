package ru.itis.snaky.protocol.serializer;

import ru.itis.snaky.protocol.exceptions.ProtocolSerializerException;
import ru.itis.snaky.protocol.request.Request;
import ru.itis.snaky.protocol.response.Response;

public interface Serializer {
    byte[] encode(Request request);

    byte[] encode(Response response);

    Request decodeRequest(byte[] request) throws ProtocolSerializerException;

    Response decodeResponse(byte[] response) throws ProtocolSerializerException;
}
