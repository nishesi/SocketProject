package ru.itis.snaky.server.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Color {
    private final short[] rgb = new short[3];

    public Color(short r, short g, short b) {
        validateValue(r);
        this.rgb[0] = r;
        validateValue(g);
        this.rgb[1] = g;
        validateValue(b);
        this.rgb[2] = b;
    }

    public Color(int r, int g, int b) {
        validateValue(r);
        this.rgb[0] = (short) r;
        validateValue(g);
        this.rgb[1] = (short) g;
        validateValue(b);
        this.rgb[2] = (short) b;
    }

    public Color(double r, double g, double b) {
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
}
