package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionBuilder {

    private String name;
    private int grassColor;
    private int fogColor;
    private int foliageColor;
    private boolean hasLight;
    private boolean hasSky;

    public static DimensionBuilder create() {
        return new DimensionBuilder();
    }

    public DimensionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DimensionBuilder grassColor(int grassColor) {
        this.grassColor = grassColor;
        return this;
    }

    public DimensionBuilder fogColor(int fogColor) {
        this.fogColor = fogColor;
        return this;
    }

    public DimensionBuilder foliageColor(int foliageColor) {
        this.foliageColor = foliageColor;
        return this;
    }

    public DimensionBuilder hasLight(boolean hasLight) {
        this.hasLight = hasLight;
        return this;
    }

    public DimensionBuilder hasSky(boolean hasSky) {
        this.hasSky = hasSky;
        return this;
    }

    public Dimension build() {
        return new Dimension(name, grassColor, fogColor, foliageColor, hasLight, hasSky);
    }

    public Dimension buildFromJSON() {
        return new Dimension(name, grassColor, fogColor, foliageColor, hasLight, hasSky);
    }

}
