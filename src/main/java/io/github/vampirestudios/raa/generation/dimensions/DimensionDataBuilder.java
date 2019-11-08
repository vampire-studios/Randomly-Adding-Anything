package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import net.minecraft.util.Identifier;

public class DimensionDataBuilder {
    private Identifier id;
    private String name;
    private int dimensionId;
    private DimensionBiomeData biomeData;
    private DimensionColorPallet dimensionColorPallet;
    private boolean hasLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean waterVaporize;
    private boolean renderFog;
    private DimensionChunkGenerators dimensionChunkGenerator;
    private boolean corrupted;

    public static DimensionDataBuilder create(Identifier id, String name) {
        DimensionDataBuilder dimensionDataBuilder = new DimensionDataBuilder();
        dimensionDataBuilder.id = id;
        dimensionDataBuilder.name = name;
        return dimensionDataBuilder;
    }

    @Deprecated
    public static DimensionDataBuilder create() {
        return new DimensionDataBuilder();
    }

    private DimensionDataBuilder() {

    }

    public DimensionDataBuilder id(Identifier id) {
        this.id = id;
        return this;
    }

    public DimensionDataBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DimensionDataBuilder chunkGenerator(DimensionChunkGenerators dimensionChunkGenerator) {
        this.dimensionChunkGenerator = dimensionChunkGenerator;
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

    public DimensionDataBuilder isCorrupted(boolean corrupted) {
        this.corrupted = corrupted;
        return this;
    }

    public DimensionData build() {
        return new DimensionData(id, name, dimensionId, biomeData, dimensionColorPallet, hasLight, hasSky, canSleep, waterVaporize, renderFog, dimensionChunkGenerator, corrupted);
    }
}
