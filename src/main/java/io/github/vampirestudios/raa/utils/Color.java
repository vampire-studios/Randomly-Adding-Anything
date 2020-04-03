//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.raa.utils;

public class Color {
    public static final Color WHITE = new Color(255, 255, 255);
    private static final double FACTOR = 0.7D;
    private final int color;

    public Color(int r, int g, int b, int a) {
        this.color = (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | b & 255;
    }

    public Color(int rgb) {
        this.color = -16777216 | rgb;
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(float r, float g, float b) {
        this((int)((double)(r * 255.0F) + 0.5D), (int)((double)(g * 255.0F) + 0.5D), (int)((double)(b * 255.0F) + 0.5D));
    }

    public static int[] intToRgb(int color) {
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        return new int[]{r, g, b};
    }

    public static float[] floatToRgb(float color) {
        float r = (int) color >> 16 & 255;
        float g = (int) color >> 8 & 255;
        float b = (int) color & 255;
        return new float[]{r / 255.0F, g / 255.0F, b / 255.0F};
    }

    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0;
        int g = 0;
        int b = 0;
        if (saturation == 0.0F) {
            r = g = b = (int)(brightness * 255.0F + 1.0F);
        } else {
            float h = (hue - (float)Math.floor((double)hue)) * 6.0F;
            float f = h - (float)Math.floor((double)h);
            float p = brightness * (1.0F - saturation);
            float q = brightness * (1.0F - saturation * f);
            float t = brightness * (1.0F - saturation * (1.0F - f));
            switch((int)h) {
            case 0:
                r = (int)(brightness * 255.0F + 1.0F);
                g = (int)(t * 255.0F + 1.0F);
                b = (int)(p * 255.0F + 1.0F);
                break;
            case 1:
                r = (int)(q * 255.0F + 1.0F);
                g = (int)(brightness * 255.0F + 1.0F);
                b = (int)(p * 255.0F + 1.0F);
                break;
            case 2:
                r = (int)(p * 255.0F + 1.0F);
                g = (int)(brightness * 255.0F + 1.0F);
                b = (int)(t * 255.0F + 1.0F);
                break;
            case 3:
                r = (int)(p * 255.0F + 1.0F);
                g = (int)(q * 255.0F + 1.0F);
                b = (int)(brightness * 255.0F + 1.0F);
                break;
            case 4:
                r = (int)(t * 255.0F + 1.0F);
                g = (int)(p * 255.0F + 1.0F);
                b = (int)(brightness * 255.0F + 1.0F);
                break;
            case 5:
                r = (int)(brightness * 255.0F + 1.0F);
                g = (int)(p * 255.0F + 1.0F);
                b = (int)(q * 255.0F + 1.0F);
            }
        }

        return -16777216 | r << 16 | g << 8 | b;
    }

    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        if (hsbvals == null) {
            hsbvals = new float[3];
        }

        int cmax = Math.max(r, g);
        if (b > cmax) {
            cmax = b;
        }

        int cmin = Math.min(r, g);
        if (b < cmin) {
            cmin = b;
        }

        float brightness = (float)cmax / 255.0F;
        float saturation;
        if (cmax != 0) {
            saturation = (float)(cmax - cmin) / (float)cmax;
        } else {
            saturation = 0.0F;
        }

        float hue;
        if (saturation == 0.0F) {
            hue = 0.0F;
        } else {
            float redc = (float)(cmax - r) / (float)(cmax - cmin);
            float greenc = (float)(cmax - g) / (float)(cmax - cmin);
            float bluec = (float)(cmax - b) / (float)(cmax - cmin);
            if (r == cmax) {
                hue = bluec - greenc;
            } else if (g == cmax) {
                hue = 2.0F + redc - bluec;
            } else {
                hue = 4.0F + greenc - redc;
            }

            hue /= 6.0F;
            if (hue < 0.0F) {
                ++hue;
            }
        }

        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }

    public static Color getHSBColor(float h, float s, float b) {
        return new Color(HSBtoRGB(h, s, b));
    }

    public int getColor() {
        return this.color;
    }

    public int getRed() {
        return this.getColor() >> 16 & 255;
    }

    public int getGreen() {
        return this.getColor() >> 8 & 255;
    }

    public int getBlue() {
        return this.getColor() & 255;
    }

    public int getAlpha() {
        return this.getColor() >> 24 & 255;
    }

    public int getChroma() {
        int r = this.getRed();
        int g = this.getGreen();
        int b = this.getBlue();
        int max = Math.max(Math.max(r, g), b);
        int min = Math.min(Math.min(r, g), b);
        return max - min;
    }

    public int getHue() {
        float r = (float)this.getRed() / 255.0F;
        float g = (float)this.getGreen() / 255.0F;
        float b = (float)this.getBlue() / 255.0F;
        float max = Math.max(Math.max(r, g), b);
        float min = Math.min(Math.min(r, g), b);
        float chroma = max - min;
        if (chroma == 0.0F) {
            return 0;
        } else if (max >= r) {
            return (int)((g - b) / chroma % 6.0F * 60.0F);
        } else if (max >= g) {
            return (int)(((b - r) / chroma + 2.0F) * 60.0F);
        } else {
            return max >= b ? (int)(((r - g) / chroma + 4.0F) * 60.0F) : 0;
        }
    }

    public int getLightness() {
        int r = this.getRed();
        int g = this.getGreen();
        int b = this.getBlue();
        int max = Math.max(Math.max(r, g), b);
        int min = Math.min(Math.min(r, g), b);
        return (max + min) / 2;
    }

    public int getLuma() {
        float r = (float)this.getRed() / 255.0F;
        float g = (float)this.getGreen() / 255.0F;
        float b = (float)this.getBlue() / 255.0F;
        return (int)((0.2126F * r + 0.7152F * g + 0.0722F * b) * 255.0F);
    }

    public int getValue() {
        int r = this.getRed();
        int g = this.getGreen();
        int b = this.getBlue();
        return Math.max(Math.max(r, g), b);
    }

    public float getHSVSaturation() {
        float v = (float)this.getValue();
        if (v == 0.0F) {
            return 0.0F;
        } else {
            float c = (float)this.getChroma();
            return c / v;
        }
    }

    public float getHSLSaturation() {
        float l = (float)this.getLuma() / 255.0F;
        if (l != 0.0F && l != 1.0F) {
            float c = (float)this.getChroma() / 255.0F;
            return c / (1.0F - Math.abs(2.0F * l - 1.0F));
        } else {
            return 0.0F;
        }
    }

    public Color brighter() {
        int r = this.getRed();
        int g = this.getGreen();
        int b = this.getBlue();
        int alpha = this.getAlpha();
        int i = 3;
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        } else {
            if (r > 0 && r < i) {
                r = i;
            }

            if (g > 0 && g < i) {
                g = i;
            }

            if (b > 0 && b < i) {
                b = i;
            }

            return new Color(Math.min((int)((double)r / 0.7D), 255), Math.min((int)((double)g / 0.7D), 255), Math.min((int)((double)b / 0.7D), 255), alpha);
        }
    }

    public Color darker() {
        return new Color(Math.max((int)((double)this.getRed() * 0.7D), 0), Math.max((int)((double)this.getGreen() * 0.7D), 0), Math.max((int)((double)this.getBlue() * 0.7D), 0), this.getAlpha());
    }
}
