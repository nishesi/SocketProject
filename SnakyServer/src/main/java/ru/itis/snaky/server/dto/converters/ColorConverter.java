package ru.itis.snaky.server.dto.converters;

import ru.itis.snaky.protocol.dto.TransferColor;
import ru.itis.snaky.server.dto.Color;

public class ColorConverter {
    public static TransferColor from(Color color) {
        return new TransferColor(color.getRgb()[0], color.getRgb()[1], color.getRgb()[2]);
    }
}
