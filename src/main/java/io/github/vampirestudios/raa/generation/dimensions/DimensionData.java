package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import net.minecraft.util.Identifier;

public class DimensionData {
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

    DimensionData(Identifier id, String name, int dimensionId, DimensionBiomeData biomeData, DimensionColorPallet dimensionColorPallet, boolean hasLight, boolean hasSky, boolean canSleep, boolean waterVaporize, boolean renderFog, DimensionChunkGenerators dimensionChunkGenerator, boolean corrupted) {
        this.id = id;
        this.name = name;
        this.dimensionId = dimensionId;
        this.biomeData = biomeData;
        this.dimensionColorPallet = dimensionColorPallet;
        this.hasLight = hasLight;
        this.hasSky = hasSky;
        this.canSleep = canSleep;
        this.waterVaporize = waterVaporize;
        this.renderFog = renderFog;
        this.dimensionChunkGenerator = dimensionChunkGenerator;
        this.corrupted = corrupted;
    }

    public Identifier getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public DimensionBiomeData getBiomeData() {
        return biomeData;
    }

    public DimensionColorPallet getDimensionColorPallet() {
        return dimensionColorPallet;
    }

    public boolean hasSkyLight() {
        return hasLight;
    }

    public boolean hasSky() {
        return hasSky;
    }

    public boolean canSleep() {
        return canSleep;
    }

    public boolean doesWaterVaporize() {
        return waterVaporize;
    }

    public boolean shouldRenderFog() {
        return renderFog;
    }

    public DimensionChunkGenerators getDimensionChunkGenerator() {
        return dimensionChunkGenerator;
    }

    public boolean getCorrupted() {
        return corrupted;
    }
}