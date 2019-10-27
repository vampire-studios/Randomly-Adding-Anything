package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionDataBuilder {

    private String name;
    private int dimensionId;
    private DimensionBiomeData biomeData;
    private DimensionColorPallet dimensionColorPallet;
    private boolean hasLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean waterVaporize;
    private boolean renderFog;

    public static DimensionDataBuilder create() {
        return new DimensionDataBuilder();
    }

    public DimensionDataBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DimensionDataBuilder dimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
        return this;
    }

    public DimensionDataBuilder biome(DimensionBiomeData biomeData) {
        this.biomeData = biomeData;
        return this;
    }

    public DimensionDataBuilder colorPallet(DimensionColorPallet dimensionColorPallet) {
        this.dimensionColorPallet = dimensionColorPallet;
        return this;
    }

    public DimensionDataBuilder hasLight(boolean hasLight) {
        this.hasLight = hasLight;
        return this;
    }

    public DimensionDataBuilder hasSky(boolean hasSky) {
        this.hasSky = hasSky;
        return this;
    }

    public DimensionDataBuilder canSleep(boolean canSleep) {
        this.canSleep = canSleep;
        return this;
    }

    public DimensionDataBuilder doesWaterVaporize(boolean waterVaporize) {
        this.waterVaporize = waterVaporize;
        return this;
    }

    public DimensionDataBuilder shouldRenderFog(boolean renderFog) {
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
