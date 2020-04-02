package io.github.vampirestudios.raa.generation.dimensions.data;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DimensionData {
    private Identifier id;
    private String name;
    private final List<DimensionBiomeData> biomeData;
    private final DimensionColorPalette dimensionColorPalette;
    private final DimensionTextureData texturesInformation;
    private boolean hasSkyLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean waterVaporize;
    private boolean renderFog;
    private final DimensionChunkGenerators dimensionChunkGenerator;
    private final int flags;
    private final HashMap<String, int[]> mobs;
    private final int difficulty;
    private final HashMap<String, Double> civilizationInfluences;
    private final float cloudHeight;
    private float stoneJumpHeight;
    private float stoneHardness;
    private float stoneResistance; //blast resistance
    private boolean hasCustomGravity;
    private float gravity;

    public DimensionData(Identifier id, String name, List<DimensionBiomeData> biomeData, DimensionColorPalette dimensionColorPalette, DimensionTextureData texturesInformation,
                         boolean hasSkyLight, boolean hasSky, boolean canSleep, boolean waterVaporize, boolean renderFog, DimensionChunkGenerators dimensionChunkGenerator, int flags,
                         HashMap<String, int[]> mobs, int difficulty, HashMap<String, Double> civilizationInfluences, float cloudHeight, float stoneJumpHeight, float stoneHardness,
                         float stoneResistance, boolean hasCustomGravity, float gravity) {
        this.id = id;
        this.name = name;
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
        this.cloudHeight = cloudHeight;
        this.stoneJumpHeight = stoneJumpHeight;
        this.stoneHardness = stoneHardness;
        this.stoneResistance = stoneResistance;
        this.hasCustomGravity = hasCustomGravity;
        this.gravity = gravity;
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

    public void setName(String name) {
        this.name = name;
    }

    public List<DimensionBiomeData> getBiomeData() {
        return biomeData;
    }

    public DimensionColorPalette getDimensionColorPalette() {
        return dimensionColorPalette;
    }

    public DimensionTextureData getTexturesInformation() {
        return texturesInformation;
    }

    public boolean hasSkyLight() {
        return hasSkyLight;
    }

    public void setHasSkyLight(boolean hasSkyLight) {
        this.hasSkyLight = hasSkyLight;
    }

    public boolean hasSky() {
        return hasSky;
    }

    public void setHasSky(boolean hasSky) {
        this.hasSky = hasSky;
    }

    public boolean canSleep() {
        return canSleep;
    }

    public void setCanSleep(boolean canSleep) {
        this.canSleep = canSleep;
    }

    public boolean doesWaterVaporize() {
        return waterVaporize;
    }

    public void setWaterVaporize(boolean waterVaporize) {
        this.waterVaporize = waterVaporize;
    }

    public boolean hasThickFog() {
        return renderFog;
    }

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

    public float getCloudHeight() {
        return cloudHeight;
    }

    public float getStoneJumpHeight() {
        return stoneJumpHeight;
    }

    public void setStoneJumpHeight(float stoneJumpHeight) {
        this.stoneJumpHeight = stoneJumpHeight;
    }

    public float getStoneHardness() {
        return stoneHardness;
    }

    public float getStoneResistance() {
        return stoneResistance;
    }

    public boolean hasCustomGravity() {
        return hasCustomGravity;
    }

    public float getGravity() {
        return gravity;
    }

    public static class Builder {
        HashMap<String, int[]> mobs;
        private Identifier id;
        private String name;
        private List<DimensionBiomeData> biomeData;
        private DimensionColorPalette dimensionColorPalette;
        private DimensionTextureData texturesInformation;
        private boolean hasSkyLight;
        private boolean hasSky;
        private boolean canSleep;
        private boolean waterVaporize;
        private boolean renderFog;
        private DimensionChunkGenerators dimensionChunkGenerator;
        private int flags;
        private int difficulty;
        private HashMap<String, Double> civilizationInfluences;
        private float cloudHeight;
        private float stoneJumpHeight;
        private float stoneHardness;
        private float stoneResistance; //blast resistance
        private boolean hasCustomGravity;
        private float gravity;

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

        public Builder biome(DimensionBiomeData biomeData) {
            this.biomeData.add(biomeData);
            return this;
        }

        public Builder colorPalette(DimensionColorPalette dimensionColorPalette) {
            this.dimensionColorPalette = dimensionColorPalette;
            return this;
        }

        public Builder texturesInformation(DimensionTextureData texturesInformation) {
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

        public Builder cloudHeight(float cloudHeight) {
            this.cloudHeight = cloudHeight;
            return this;
        }

        public Builder stoneJumpHeight(float stoneJumpHeight) {
            this.stoneJumpHeight = stoneJumpHeight;
            return this;
        }

        public Builder stoneHardness(float stoneHardness, float stoneResistance) {
            this.stoneHardness = stoneHardness;
            this.stoneResistance = stoneResistance;

            return this;
        }

        public Builder hasCustomGravity(boolean hasCustomGravity) {
            this.hasCustomGravity = hasCustomGravity;
            return this;
        }

        public Builder gravity(float gravity) {
            this.gravity = gravity;
            return this;
        }

        public DimensionData build() {
            return new DimensionData(id, name, biomeData, dimensionColorPalette, texturesInformation, hasSkyLight, hasSky, canSleep, waterVaporize,
                    renderFog, dimensionChunkGenerator, flags, mobs, difficulty, civilizationInfluences, cloudHeight, stoneJumpHeight, stoneHardness,
                    stoneResistance, hasCustomGravity, gravity);
        }
    }
}