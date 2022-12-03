package ru.itis.snaky.protocol.request;

import lombok.EqualsAndHashCode;
import ru.itis.snaky.protocol.SnakySerializable;

@EqualsAndHashCode
public abstract class Request implements SnakySerializable {
    private final RequestType requestType;
    private final Object[] parameters;

    protected Request(RequestType requestType, Object[] parameters) {
        this.requestType = requestType;
        this.parameters = parameters;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public Object getParameter(int index) {
        return parameters[index];
    }
}
