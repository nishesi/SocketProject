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


    public static int byteLength() {
        return 8 + TransferColor.byteLength();
    }

    public void serialize(int[] arr, int offset) {
        if (arr.length < offset + byteLength()) {
            throw new IllegalArgumentException("Not enough length to serialize");
        }

        arr[offset + 0] = (x >>> 24);
        arr[offset + 1] = (x >>> 16);
        arr[offset + 2] = (x >>> 8);
        arr[offset + 3] = (x >>> 0);

        arr[offset + 4] = (y >> 24);
        arr[offset + 5] = (y >> 16);
        arr[offset + 6] = (y >> 8);
        arr[offset + 7] = (y);

        color.serialize(arr, offset + 8);
    }

    public int[] serialize() {
        int[] arr = new int[byteLength()];

        serialize(arr, 0);
        return arr;
    }

    public static TransferFruit deserialize(int[] arr, int offset) {
        if (arr.length < offset + byteLength()) {
            throw new IllegalArgumentException("invalid length = " + arr.length + " expected = " + byteLength());
        }

        int x = (arr[offset] << 24) +
                (arr[offset + 1] << 16) +
                (arr[offset + 2] << 8) +
                arr[offset + 3];

        int y = arr[offset + 4] << 24 |
                arr[offset + 5] << 16 |
                arr[offset + 6] << 8 |
                arr[offset + 7];

        return new TransferFruit(x, y, TransferColor.deserialize(arr, offset + 8));
    }
}
