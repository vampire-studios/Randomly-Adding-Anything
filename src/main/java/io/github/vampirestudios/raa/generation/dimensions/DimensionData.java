package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;

import java.util.HashMap;

public class DimensionData {
    private String name;
    private int dimensionId;
    private DimensionBiomeData biomeData;
    private DimensionColorPalette dimensionColorPalette;
    private boolean hasLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean waterVaporize;
    private boolean renderFog;
    private DimensionChunkGenerators dimensionChunkGenerator;
    private int flags;
    private HashMap<String, int[]> mobs;

    public DimensionData(String name, int dimensionId, DimensionBiomeData biomeData, DimensionColorPalette dimensionColorPalette, boolean hasLight, boolean hasSky, boolean canSleep, boolean waterVaporize, boolean renderFog, DimensionChunkGenerators dimensionChunkGenerator, int flags, HashMap<String, int[]> mobs) {
        this.name = name;
        this.dimensionId = dimensionId;
        this.biomeData = biomeData;
        this.dimensionColorPalette = dimensionColorPalette;
        this.hasLight = hasLight;
        this.hasSky = hasSky;
        this.canSleep = canSleep;
        this.waterVaporize = waterVaporize;
        this.renderFog = renderFog;
        this.dimensionChunkGenerator = dimensionChunkGenerator;
        this.flags = flags;
        this.mobs = mobs;
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

    public DimensionColorPalette getDimensionColorPallet() {
        return dimensionColorPalette;
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

    public int getFlags() {
        return flags;
    }

    public HashMap<String, int[]> getMobs() {
        return mobs;
    }

    public static class Builder {
        private String name;
        private int dimensionId;
        private DimensionBiomeData biomeData;
        private DimensionColorPalette dimensionColorPalette;
        private boolean hasLight;
        private boolean hasSky;
        private boolean canSleep;
        private boolean waterVaporize;
        private boolean renderFog;
        private DimensionChunkGenerators dimensionChunkGenerator;
        private int flags;
        private HashMap<String, int[]> mobs;

        public static Builder create() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder chunkGenerator(DimensionChunkGenerators dimensionChunkGenerator) {
            this.dimensionChunkGenerator = dimensionChunkGenerator;
            return this;
        }

        public Builder dimensionId(int dimensionId) {
            this.dimensionId = dimensionId;
            return this;
        }

        public Builder biome(DimensionBiomeData biomeData) {
            this.biomeData = biomeData;
            return this;
        }

        public Builder colorPallet(DimensionColorPalette dimensionColorPalette) {
            this.dimensionColorPalette = dimensionColorPalette;
            return this;
        }

        public Builder hasLight(boolean hasLight) {
            this.hasLight = hasLight;
            return this;
        }

        public Builder hasSky(boolean hasSky) {
            this.hasSky = hasSky;
            return this;
        }

        public Builder canSleep(boolean canSleep) {
            this.canSleep = canSleep;
            return this;
        }

        public Builder doesWaterVaporize(boolean waterVaporize) {
            this.waterVaporize = waterVaporize;
            return this;
        }

        public Builder shouldRenderFog(boolean renderFog) {
            this.renderFog = renderFog;
            return this;
        }

        public Builder setFlags(int flags) {
            this.flags = flags;
            return this;
        }

        public Builder mobs(HashMap<String, int[]> mobs) {
            this.mobs = mobs;
            return this;
        }

        public DimensionData build() {
            return new DimensionData(name, dimensionId, biomeData,
                    dimensionColorPalette, hasLight, hasSky, canSleep, waterVaporize, renderFog, dimensionChunkGenerator, flags, mobs);
        }
    }
}