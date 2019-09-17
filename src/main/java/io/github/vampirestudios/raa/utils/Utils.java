package io.github.vampirestudios.raa.utils;

public class Utils {

    public static String toTitleCase(String lowerCase) {
        return "" + Character.toUpperCase(lowerCase.charAt(0))+lowerCase.substring(1);
    }

}
