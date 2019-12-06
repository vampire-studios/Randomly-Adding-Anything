package io.github.vampirestudios.raa.config.readers;

public enum ConfigVersion {
    OLD(0),
    V1(1),
    V2(2);

    private int number;

    ConfigVersion(int number) {
        this.number = number;
    }

    public static ConfigVersion getFromInt(int number) {
        for (ConfigVersion version : values())
            if (version.getNumber() == number)
                return version;
        System.out.println("Unknown config version : " + number);
        return null;
    }

    public int getNumber() {
        return number;
    }
}
