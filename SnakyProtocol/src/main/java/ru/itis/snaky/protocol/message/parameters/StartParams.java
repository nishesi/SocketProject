package ru.itis.snaky.protocol.message.parameters;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StartParams extends MessageParams {
    private boolean success;
}
