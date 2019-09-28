package io.github.vampirestudios.raa;

import io.github.vampirestudios.raa.utils.Color;

public class ColorConverter {

    public static void main(String[] args) {
        Color color = new Color(-11728847);
        System.out.println(String.format("R: %s, G: %s, B: %s", color.getRed(), color.getGreen(), color.getBlue()));
    }

}
