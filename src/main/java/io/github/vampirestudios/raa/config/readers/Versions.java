package io.github.vampirestudios.raa.config.readers;

public enum Versions {
    OLD(0),
    V1(1);

    private int number;

    Versions(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static Versions getFromInt(int number) {
        for (Versions versions : values())
            if (versions.getNumber() == number)
                return versions;
        System.out.println("Unknown config version : " + number);
        return null;
    }
}
