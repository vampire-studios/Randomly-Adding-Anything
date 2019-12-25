package io.github.vampirestudios.raa.api.enums;

public enum OreType {
    METAL("_ingot"),
    GEM("_gem"),
    CRYSTAL("_crystal");

    private final String suffix;

    OreType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
