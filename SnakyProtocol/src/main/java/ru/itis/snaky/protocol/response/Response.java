package ru.itis.snaky.protocol.response;

import lombok.EqualsAndHashCode;
import ru.itis.snaky.protocol.SnakySerializable;

@EqualsAndHashCode
public abstract class Response implements SnakySerializable {
    private final ResponseType responseType;
    private final Object[] parameters;

    protected Response(ResponseType responseType, Object[] parameters) {
        this.responseType = responseType;
        this.parameters = parameters;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public Object getParameter(int index) {
        return parameters[index];
    }
}
