package ru.itis.snaky.protocol.message.parameters;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationParams extends MessageParams {
    private String nickname;
    private boolean success;
}

