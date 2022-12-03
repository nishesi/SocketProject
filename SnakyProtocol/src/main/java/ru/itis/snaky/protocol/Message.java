package ru.itis.snaky.protocol;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Message implements SnakySerializable {
    private final MessageType messageType;
    private final Object[] parameters;

    public Object getParameter(int index) {
        return parameters[index];
    }
}
