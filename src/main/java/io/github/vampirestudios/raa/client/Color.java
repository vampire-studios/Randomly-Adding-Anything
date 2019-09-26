package io.github.vampirestudios.raa.client;

public class Color {

    private final int color;

    public Color(int r, int g, int b, int a) {
        color = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(float r, float g, float b) {
        this((int) (r * 255 + 0.5), (int) (g * 255 + 0.5), (int) (b * 255 + 0.5));
    }

    public int getColor() {
        return color;
    }

    public int getRed() {
        return (getColor() >> 16) & 0xFF;
    }

    public int getGreen() {
        return (getColor() >> 8) & 0xFF;
    }

    public int getBlue() {
        return getColor() & 0xFF;
    }

    public int getAlpha() {
        return (getColor() >> 24) & 0xff;
    }

    public static int[] intToRgb(int color) {
        int r = (color>>16)&0xFF;
        int g = (color>>8)&0xFF;
        int b = (color>>0)&0xFF;
        return new int[] {
                r, g, b
        };
    }

}
