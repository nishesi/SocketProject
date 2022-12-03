package ru.itis.snaky.protocol.response;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class SnakyResponse extends Response {
    public SnakyResponse(ResponseType responseType, Object[] parameters) {
        super(responseType, parameters);
    }
}
