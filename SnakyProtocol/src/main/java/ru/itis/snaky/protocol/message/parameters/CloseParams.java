package ru.itis.snaky.protocol.message.parameters;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CloseParams extends MessageParams {
}
