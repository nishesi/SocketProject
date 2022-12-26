package ru.itis.snaky.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class Cube {
    @Getter
    private int x;
    @Getter
    private int y;

}
