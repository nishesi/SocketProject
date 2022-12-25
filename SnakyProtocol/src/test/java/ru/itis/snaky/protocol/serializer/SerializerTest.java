package ru.itis.snaky.protocol.serializer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import ru.itis.snaky.protocol.dto.TransferColor;
import ru.itis.snaky.protocol.dto.TransferFruit;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.dto.TransferSnake;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializerTest {
    @Test
    public void serializeTest() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {

            Test1.NestedTest1[] nestedTest1s = new Test1.NestedTest1[random.nextInt(20000)];

            for (int j = 0; j < nestedTest1s.length; j++) {
                nestedTest1s[j] = new Test1.NestedTest1(random.nextInt());
            }

            short[] shorts = new short[random.nextInt(3)];

            for (int j = 0; j < shorts.length; j++) {
                shorts[j] = (short) random.nextInt(256);
            }

            Test1 test1 = new Test1(
                    random.nextBoolean(),
                    (byte) random.nextInt(256),
                    (short) random.nextInt(65537),
                    (int) random.nextLong(),
                    UUID.randomUUID().toString(),
                    shorts,
                    new Test1.NestedTest1(random.nextInt()),
                    nestedTest1s
            );

            byte[] bytes = Serializer.serialize(test1);
            Test1 test11 = Serializer.deserialize(bytes, Test1.class);

            assertEquals(test1, test11);
        }
    }

    @Test
    public void transferObjectsTest() {
        Random random = new Random();
        TransferColor[] transferColors = new TransferColor[random.nextInt(2)];
        for (int i = 0; i < transferColors.length; i++) {
            transferColors[i] = new TransferColor(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256));
        }
        TransferRoom transferRoom = new TransferRoom(random.nextInt(), UUID.randomUUID().toString(), random.nextInt(), random.nextInt(), transferColors);
        assertEquals(transferRoom, Serializer.deserialize(Serializer.serialize(transferRoom), transferRoom.getClass()));
    }

    @Test
    public void testTest2() {
        Random random = new Random();
        short[] shorts = new short[random.nextInt(3) + 1];

        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = (short) random.nextInt(256);
        }

        Test2 test2 = new Test2(shorts);
        assertEquals(test2, Serializer.deserialize(Serializer.serialize(test2), Test2.class));
        System.out.println(test2);
    }

    @Test
    public void testTransferClasses() {
        testTransferColor();
        testTransferFruit();
        testTransferRoom();
        testTransferSnake();
    }

    private void testTransferColor() {
        Random random = new Random();
        TransferColor transferColor = generateTransferColor(random);

        TransferColor transferColor1 = Serializer.deserialize(Serializer.serialize(transferColor), TransferColor.class);
        assertEquals(transferColor, transferColor1);
    }

    private TransferColor generateTransferColor(Random random) {
        return new TransferColor(
                (short) random.nextInt(256),
                (short) random.nextInt(256),
                (short) random.nextInt(256)
        );
    }

    private void testTransferFruit() {
        Random random = new Random();
        TransferFruit transferFruit = new TransferFruit(
                random.nextInt(),
                random.nextInt(),
                generateTransferColor(random)
        );

        TransferFruit transferFruit1 = Serializer.deserialize(Serializer.serialize(transferFruit), TransferFruit.class);
        assertEquals(transferFruit, transferFruit1);
    }

    private void testTransferSnake() {
        Random random = new Random();

        TransferSnake.Cube[] cubes = new TransferSnake.Cube[random.nextInt(100)];

        for (int i = 0; i < cubes.length; i++) {
            cubes[i] = new TransferSnake.Cube(
                    random.nextInt(),
                    random.nextInt()
            );
        }

        TransferSnake transferSnake = new TransferSnake(
                cubes,
                UUID.randomUUID().toString(),
                generateTransferColor(random)
        );

        TransferSnake transferSnake1 = Serializer.deserialize(Serializer.serialize(transferSnake), TransferSnake.class);
        assertEquals(transferSnake, transferSnake1);
    }

    private void testTransferRoom() {
        Random random = new Random();
        TransferColor[] colorArray = new TransferColor[random.nextInt(200)];

        for (int i = 0; i < colorArray.length; i++) {
            colorArray[i] = generateTransferColor(random);
        }

        TransferRoom transferRoom = new TransferRoom(
                random.nextInt(),
                UUID.randomUUID().toString(),
                random.nextInt(),
                random.nextInt(),
                colorArray
        );

        TransferRoom transferRoom1 = Serializer.deserialize(Serializer.serialize(transferRoom), TransferRoom.class);
        assertEquals(transferRoom, transferRoom1);
    }

    // testing classes

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    static class Test2 {
        private short[] shorts;
    }

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    static class Test1 {
        private boolean aBoolean;
        private byte aByte;
        private short aShort;
        private int aInt;
        private String aString;
        private short[] shorts;
        private NestedTest1 nestedTest1;
        private NestedTest1[] nestedTestArr;

        @ToString
        @EqualsAndHashCode
        @AllArgsConstructor
        static class NestedTest1 {
            private int i;
        }
    }
}
