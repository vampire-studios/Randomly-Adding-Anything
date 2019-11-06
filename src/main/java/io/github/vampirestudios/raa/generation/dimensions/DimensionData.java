package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;

public class DimensionData {
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
    private int flags;

    public DimensionData(String name, int dimensionId, DimensionBiomeData biomeData, DimensionColorPallet dimensionColorPallet, boolean hasLight, boolean hasSky, boolean canSleep, boolean waterVaporize, boolean renderFog, DimensionChunkGenerators dimensionChunkGenerator, int flags) {
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
        this.flags = flags;
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

    public int getFlags() {
        return flags;
    }

    public static class Builder {
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
        private int flags;

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

        public Builder colorPallet(DimensionColorPallet dimensionColorPallet) {
            this.dimensionColorPallet = dimensionColorPallet;
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

        public DimensionData build() {
            return new DimensionData(name, dimensionId, biomeData,
                    dimensionColorPallet, hasLight, hasSky, canSleep, waterVaporize, renderFog, dimensionChunkGenerator, flags);
        }
    }
}