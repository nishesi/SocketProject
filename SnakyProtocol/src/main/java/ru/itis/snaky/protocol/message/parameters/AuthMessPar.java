package ru.itis.snaky.protocol.message.parameters;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class AuthMessPar extends MessageParameter {
    private String nickname;
}
