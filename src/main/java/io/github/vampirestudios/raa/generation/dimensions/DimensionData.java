package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DimensionData {
    private Identifier id;
    private String name;
    private int dimensionId;
    private List<DimensionBiomeData> biomeData;
    private DimensionColorPalette dimensionColorPalette;
    private DimensionTexturesInformation texturesInformation;
    private boolean hasSkyLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean waterVaporize;
    private boolean renderFog;
    private DimensionChunkGenerators dimensionChunkGenerator;
    private int flags;
    private HashMap<String, int[]> mobs;
    private int difficulty;
    private HashMap<String, Double> civilizationInfluences;
    private int surfaceBuilder;
    private int toolDurability;

    public DimensionData(Identifier id, String name, int dimensionId, List<DimensionBiomeData> biomeData, DimensionColorPalette dimensionColorPalette, DimensionTexturesInformation texturesInformation,
                         boolean hasSkyLight, boolean hasSky, boolean canSleep, boolean waterVaporize, boolean renderFog, DimensionChunkGenerators dimensionChunkGenerator, int flags,
                         HashMap<String, int[]> mobs, int difficulty, HashMap<String, Double> civilizationInfluences, int surfaceBuilder, int toolDurability) {
        this.id = id;
        this.name = name;
        this.dimensionId = dimensionId;
        this.biomeData = biomeData;
        this.dimensionColorPalette = dimensionColorPalette;
        this.texturesInformation = texturesInformation;
        this.hasSkyLight = hasSkyLight;
        this.hasSky = hasSky;
        this.canSleep = canSleep;
        this.waterVaporize = waterVaporize;
        this.renderFog = renderFog;
        this.dimensionChunkGenerator = dimensionChunkGenerator;
        this.flags = flags;
        this.mobs = mobs;
        this.difficulty = difficulty;
        this.civilizationInfluences = civilizationInfluences;
        this.surfaceBuilder = surfaceBuilder;
        this.toolDurability = toolDurability;
    }

    public Identifier getId() {
        return id;
    }

    public void setId(String id) {
        this.id = new Identifier(RandomlyAddingAnything.MOD_ID, id);
    }

    public String getName() {
        return name;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public List<DimensionBiomeData> getBiomeData() {
        return biomeData;
    }

    public DimensionColorPalette getDimensionColorPalette() {
        return dimensionColorPalette;
    }

    public DimensionTexturesInformation getTexturesInformation() {
        return texturesInformation;
    }

    public boolean hasSkyLight() {
        return hasSkyLight;
    }

    @Deprecated
    public void setHasSkyLight(boolean hasSkyLight) {
        this.hasSkyLight = hasSkyLight;
    }

    public boolean hasSky() {
        return hasSky;
    }

    @Deprecated
    public void setHasSky(boolean hasSky) {
        this.hasSky = hasSky;
    }

    public boolean canSleep() {
        return canSleep;
    }

    @Deprecated
    public void setCanSleep(boolean canSleep) {
        this.canSleep = canSleep;
    }

    public boolean doesWaterVaporize() {
        return waterVaporize;
    }

    @Deprecated
    public void setWaterVaporize(boolean waterVaporize) {
        this.waterVaporize = waterVaporize;
    }

    public boolean shouldRenderFog() {
        return renderFog;
    }

    @Deprecated
    public void setRenderFog(boolean renderFog) {
        this.renderFog = renderFog;
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

    public int getDifficulty() {
        return difficulty;
    }

    public HashMap<String, Double> getCivilizationInfluences() {
        return civilizationInfluences;
    }

    public int getSurfaceBuilder() {
        return surfaceBuilder;
    }

    public int getToolDurability() {
        return toolDurability;
    }

    public void setToolDurability(int toolDurability) {
        this.toolDurability = toolDurability;
    }

    public static class Builder {
        HashMap<String, int[]> mobs;
        private Identifier id;
        private String name;
        private int dimensionId;
        private List<DimensionBiomeData> biomeData;
        private DimensionColorPalette dimensionColorPalette;
        private DimensionTexturesInformation texturesInformation;
        private boolean hasSkyLight;
        private boolean hasSky;
        private boolean canSleep;
        private boolean waterVaporize;
        private boolean renderFog;
        private DimensionChunkGenerators dimensionChunkGenerator;
        private int flags;
        private int difficulty;
        private HashMap<String, Double> civilizationInfluences;
        private int surfaceBuilder;
        private int toolDurability;

        private Builder() {

        }

        public static Builder create(Identifier id, String name) {
            Builder builder = new Builder();
            builder.id = id;
            builder.name = name;
            builder.biomeData = new ArrayList<>();
            return builder;
        }

        @Deprecated
        public static Builder create() {
            return new Builder();
        }

        public Builder id(Identifier id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            if (id == null) {
                this.id = new Identifier(RandomlyAddingAnything.MOD_ID, name.toLowerCase());
            }
            return this;
        }

        public Builder chunkGenerator(DimensionChunkGenerators dimensionChunkGenerator) {
            this.dimensionChunkGenerator = dimensionChunkGenerator;
            return this;
        }

        @Deprecated
        public Builder dimensionId(int dimensionId) {
            this.dimensionId = dimensionId;
            return this;
        }

        public Builder biome(DimensionBiomeData biomeData) {
            this.biomeData.add(biomeData);
            return this;
        }

        public Builder colorPalette(DimensionColorPalette dimensionColorPalette) {
            this.dimensionColorPalette = dimensionColorPalette;
            return this;
        }

        public Builder texturesInformation(DimensionTexturesInformation texturesInformation) {
            this.texturesInformation = texturesInformation;
            return this;
        }

        public Builder hasSkyLight(boolean hasSkyLight) {
            this.hasSkyLight = hasSkyLight;
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

        public Builder waterVaporize(boolean waterVaporize) {
            this.waterVaporize = waterVaporize;
            return this;
        }

        public Builder shouldRenderFog(boolean renderFog) {
            this.renderFog = renderFog;
            return this;
        }

        public Builder flags(int flags) {
            this.flags = flags;
            return this;
        }

        public Builder mobs(HashMap<String, int[]> mobs) {
            this.mobs = mobs;
            return this;
        }

        public Builder difficulty(int difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder civilizationInfluences(HashMap<String, Double> civilizationInfluences) {
            this.civilizationInfluences = civilizationInfluences;
            return this;
        }

        public Builder surfaceBuilder(int surfaceBuilder) {
            this.surfaceBuilder = surfaceBuilder;
            return this;
        }


        public Builder toolDurability(int toolDurability) {
            this.toolDurability = toolDurability;
            return this;
        }

        public DimensionData build() {
            return new DimensionData(id, name, dimensionId, biomeData, dimensionColorPalette, texturesInformation, hasSkyLight, hasSky, canSleep, waterVaporize, renderFog, dimensionChunkGenerator, flags,
                    mobs, difficulty, civilizationInfluences, surfaceBuilder, toolDurability);
        }
    }
}