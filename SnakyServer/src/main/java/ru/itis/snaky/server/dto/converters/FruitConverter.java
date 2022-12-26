package ru.itis.snaky.server.dto.converters;

import ru.itis.snaky.protocol.dto.TransferColor;
import ru.itis.snaky.protocol.dto.TransferFruit;
import ru.itis.snaky.server.dto.Fruit;

public class FruitConverter {
    public static TransferFruit from(Fruit fruit) {
        return new TransferFruit(fruit.getX(), fruit.getY(), ColorConverter.from(fruit.getColor()));
    }
}
