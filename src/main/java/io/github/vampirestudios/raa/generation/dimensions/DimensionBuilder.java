package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionBuilder {

    private String name;
    private int dimensionId;
    private DimensionBiomeData biomeData;
    private DimensionColorPallet dimensionColorPallet;
    private boolean hasLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean waterVaporize;
    private boolean renderFog;

    public static DimensionBuilder create() {
        return new DimensionBuilder();
    }

    public DimensionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DimensionBuilder dimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
        return this;
    }

    public DimensionBuilder biome(DimensionBiomeData biomeData) {
        this.biomeData = biomeData;
        return this;
    }

    public DimensionBuilder colorPallet(DimensionColorPallet dimensionColorPallet) {
        this.dimensionColorPallet = dimensionColorPallet;
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

    public DimensionBuilder doesWaterVaporize(boolean waterVaporize) {
        this.waterVaporize = waterVaporize;
        return this;
    }

    public DimensionBuilder shouldRenderFog(boolean renderFog) {
        this.renderFog = renderFog;
        return this;
    }

    public DimensionData build() {
        return new DimensionData(name, dimensionId, biomeData,
                dimensionColorPallet, hasLight, hasSky, canSleep, waterVaporize, renderFog);
    }

    public DimensionData buildFromJSON() {
        return new DimensionData(name, dimensionId, biomeData,
                dimensionColorPallet, hasLight, hasSky, canSleep, waterVaporize, renderFog);
    }

}
