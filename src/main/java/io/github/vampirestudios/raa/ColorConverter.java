package io.github.vampirestudios.raa;

import io.github.vampirestudios.vampirelib.utils.Color;

public class ColorConverter {

    public static void main(String[] args) {
        Color color = new Color(-164);
        System.out.println(String.format("R: %s, G: %s, B: %s", color.getRed(), color.getGreen(), color.getBlue()));
        System.out.println(String.format("H: %s, S: %s, V: %s", color.getHue(), color.getHSLSaturation(), color.getValue()));
    }

}
