package ru.itis.snaky.protocol.serializer;

import ru.itis.snaky.protocol.exceptions.ProtocolSerializerException;
import ru.itis.snaky.protocol.request.Request;
import ru.itis.snaky.protocol.response.Response;

import java.io.*;

public class SerializerImpl implements Serializer{
    @Override
    public byte[] encode(Request request) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(out)){
            outputStream.writeObject(request);

        } catch (IOException e) {
            throw new ProtocolSerializerException(e);
        }
        return out.toByteArray();
    }

    @Override
    public byte[] encode(Response response) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(out)){
            outputStream.writeObject(response);

        } catch (IOException e) {
            throw new ProtocolSerializerException(e);
        }
        return out.toByteArray();
    }

    @Override
    public Request decodeRequest(byte[] request) throws ProtocolSerializerException {
        InputStream in = new ByteArrayInputStream(request);

        try (ObjectInputStream inputStream = new ObjectInputStream(in)){

            return (Request) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new ProtocolSerializerException(e);
        }
    }

    @Override
    public Response decodeResponse(byte[] response) throws ProtocolSerializerException {
        InputStream in = new ByteArrayInputStream(response);

        try (ObjectInputStream inputStream = new ObjectInputStream(in)){

            return (Response) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new ProtocolSerializerException(e);
        }
    }
}
