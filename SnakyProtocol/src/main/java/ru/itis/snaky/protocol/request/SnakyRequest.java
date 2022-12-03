package ru.itis.snaky.protocol.request;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class SnakyRequest extends Request {
    public SnakyRequest(RequestType requestType, Object[] parameters) {
        super(requestType, parameters);
    }


}
