package io.github.vampirestudios.raa.generation.dimensions;

public class Dimension {
    private String name;
    private int grassColor;
    private int fogColor;
    private int foliageColor;
    private boolean hasLight;
    private boolean hasSky;

    public Dimension(String name, int grassColor, int fogColor, int foliageColor, boolean hasLight, boolean hasSky) {
        this.name = name;
        this.grassColor = grassColor;
        this.fogColor = fogColor;
        this.foliageColor = foliageColor;
        this.hasLight = hasLight;
        this.hasSky = hasSky;
    }

    public String getName() {
        return name;
    }

    public int getGrassColor() {
        return grassColor;
    }

    public int getFogColor() {
        return fogColor;
    }

    public int getFoliageColor() {
        return foliageColor;
    }

    public boolean hasSkyLight() {
        return hasLight;
    }

    public boolean hasSky() {
        return hasSky;
    }

}