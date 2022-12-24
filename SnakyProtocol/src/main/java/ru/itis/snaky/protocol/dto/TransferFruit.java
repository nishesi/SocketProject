package ru.itis.snaky.protocol.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TransferFruit {
    private int x;
    private int y;
    private TransferColor color;
}
