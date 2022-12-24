package ru.itis.snaky.protocol.serializer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializerTest {
    @Test
    public void serializeTest() {
        for (int i = 0; i < 100; i++) {

            Random random = new Random();
            Test1.NestedTest1[] nestedTest1s = new Test1.NestedTest1[random.nextInt(20000)];

            for (int j = 0; j < nestedTest1s.length; j++) {
                nestedTest1s[j] = new Test1.NestedTest1(random.nextInt());
            }

            Test1 test1 = new Test1(
                    random.nextBoolean(),
                    (byte) random.nextInt(256),
                    (short) random.nextInt(65537),
                    (int) random.nextLong(),
                    UUID.randomUUID().toString(),
                    new Test1.NestedTest1(random.nextInt()),
                    nestedTest1s
            );

            byte[] bytes = Serializer.serialize(test1);
            Test1 test11 = Serializer.deserialize(bytes, Test1.class);

            assertEquals(test1, test11);
        }
    }

    // testing classes

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    static class Test1 {
        private boolean aBoolean;
        private byte aByte;
        private short aShort;
        private int aInt;
        private String aString;
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
