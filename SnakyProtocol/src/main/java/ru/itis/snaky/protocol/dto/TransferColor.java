package ru.itis.snaky.protocol.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class TransferColor {
    private short[] rgb = new short[3];

    /**
     * @param r value should be between 0 and 255
     * @param g value should be between 0 and 255
     * @param b value should be between 0 and 255
     */

    public TransferColor(short r, short g, short b) {
        validateValue(r);
        this.rgb[0] = r;
        validateValue(g);
        this.rgb[1] = g;
        validateValue(b);
        this.rgb[2] = b;
    }

    /**
     * @param r value should be between 0 and 255
     * @param g value should be between 0 and 255
     * @param b value should be between 0 and 255
     */

    public TransferColor(int r, int g, int b) {
        validateValue(r);
        this.rgb[0] = (short) r;
        validateValue(g);
        this.rgb[1] = (short) g;
        validateValue(b);
        this.rgb[2] = (short) b;
    }

    /**
     * @param r value should be between 0.0 and 1.0
     * @param g value should be between 0.0 and 1.0
     * @param b value should be between 0.0 and 1.0
     */

    public TransferColor(double r, double g, double b) {
        validateDoubleValue(r);
        this.rgb[0] = (short) (r * 255);
        validateDoubleValue(g);
        this.rgb[1] = (short) (g * 255);
        validateDoubleValue(b);
        this.rgb[2] = (short) (b * 255);
    }

    private void validateValue(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Invalid value: " + value + ", expected 0 <= value <= 255");
        }
    }

    private void validateDoubleValue(double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Invalid value: " + value + ", expected 0 <= value <= 1");
        }
    }

    public static int byteLength() {
        return 3*2;
    }

    public void serialize(int[] arr, int offset) {
        if (arr.length < offset + byteLength()) {
            throw new IllegalArgumentException("Not enough length to serialize");
        }

        arr[offset] = (byte) (rgb[0] >> 8);
        arr[offset + 1] = (byte) (rgb[0]);

        arr[offset + 2] = (byte) (rgb[1] >> 8);
        arr[offset + 3] = (byte) (rgb[1]);

        arr[offset + 4] = (byte) (rgb[2] >> 8);
        arr[offset + 5] = (byte) (rgb[2]);
    }

    public int[] serialize() {
        int[] arr = new int[byteLength()];

        serialize(arr, 0);

        return arr;
    }

    public static TransferColor deserialize(int[] arr, int offset) {
        if (arr.length < offset + byteLength()) {
            throw new IllegalArgumentException("invalid length = " + arr.length + " expected = " + byteLength());
        }

        return new TransferColor(
                arr[offset] << 8 | arr[offset + 1],
                arr[offset + 2] << 8 | arr[offset + 3],
                arr[offset + 4] << 8 | arr[offset + 5]);
    }
}
