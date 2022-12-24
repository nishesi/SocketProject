package ru.itis.snaky.protocol.message;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.itis.snaky.protocol.message.parameters.MessageParams;
import ru.itis.snaky.protocol.serializer.SnakySerializable;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Message<T extends MessageParams> implements SnakySerializable {
    private final MessageType messageType;
    private final T params;
}
