package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionBuilder {

    private String name;
    private int grassColor;
    private int fogColor;
    private int foliageColor;
    private int skyColor;
    private boolean hasLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean renderFog;

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

    public DimensionBuilder skyColor(int skyColor) {
        this.skyColor = skyColor;
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

    public DimensionBuilder canSleep(boolean canSleep) {
        this.canSleep = canSleep;
        return this;
    }

    public DimensionBuilder shouldRenderFog(boolean renderFog) {
        this.renderFog = renderFog;
        return this;
    }

    public DimensionData build() {
        return new DimensionData(name, grassColor, fogColor, foliageColor, skyColor, hasLight, hasSky, canSleep, renderFog);
    }

    public DimensionData buildFromJSON() {
        return new DimensionData(name, grassColor, fogColor, foliageColor, skyColor, hasLight, hasSky, canSleep, renderFog);
    }

}
