package io.github.vampirestudios.raa.config.readers;

public enum Version {
    OLD(0),
    V1(1);

    private int number;

    Version(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static Version getFromInt(int number) {
        for (Version version : values())
            if (version.getNumber() == number)
                return version;
        System.out.println("Unknown config version : " + number);
        return null;
    }
}
