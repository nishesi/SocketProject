package ru.itis.snaky.protocol.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferFruit {
    private int x;
    private int y;
    private TransferColor color;
}
