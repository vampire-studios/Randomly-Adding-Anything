package io.github.vampirestudios.raa.generation.dimensions.data;

import net.minecraft.util.Identifier;

import java.util.List;

public class DimensionBiomeData {
    private Identifier id;
    private String biomeName;
    private int surfaceBuilderVariantChance;
    private float depth;
    private float scale;
    private float temperature;
    private float downfall;
    private int waterColor;
    private int grassColor;
    private int foliageColor;
    private List<DimensionTreeData> treeData;
    private float corruptedCratersChance;
    private float nonCorruptedCratersChance;
    private boolean spawnsCratersInNonCorrupted;
    private float largeSkeletonTreeChance;
    private float campfireChance;
    private float outpostChance;
    private float towerChance;
    private boolean hasMushrooms;
    private boolean hasMossyRocks;

    DimensionBiomeData(Identifier id, String biomeName, int surfaceBuilderVariantChance, float depth, float scale, float temperature, float downfall, int waterColor,
                       int grassColor, int foliageColor, List<DimensionTreeData> treeData, float corruptedCratersChance, float nonCorruptedCratersChance,
                       boolean spawnsCratersInNonCorrupted, float largeSkeletonTreeChance, float campfireChance, float outpostChance, float towerChance,
                       boolean hasMushrooms, boolean hasMossyRocks) {
        this.id = id;
        this.biomeName = biomeName;
        this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
        this.depth = depth;
        this.scale = scale;
        this.temperature = temperature;
        this.downfall = downfall;
        this.waterColor = waterColor;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
        this.treeData = treeData;
        this.corruptedCratersChance = corruptedCratersChance;
        this.nonCorruptedCratersChance = nonCorruptedCratersChance;
        this.spawnsCratersInNonCorrupted = spawnsCratersInNonCorrupted;
        this.largeSkeletonTreeChance = largeSkeletonTreeChance;
        this.campfireChance = campfireChance;
        this.outpostChance = outpostChance;
        this.towerChance = towerChance;
        this.hasMushrooms = hasMushrooms;
        this.hasMossyRocks = hasMossyRocks;
    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public String getName() {
        return biomeName;
    }

    public void setName(String biomeName) {
        this.biomeName = biomeName;
    }

    public int getSurfaceBuilderVariantChance() {
        return surfaceBuilderVariantChance;
    }

    public void setSurfaceBuilderVariantChance(int surfaceBuilderVariantChance) {
        this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getDownfall() {
        return downfall;
    }

    public void setDownfall(float downfall) {
        this.downfall = downfall;
    }

    public int getWaterColor() {
        return waterColor;
    }

    public void setWaterColor(int waterColor) {
        this.waterColor = waterColor;
    }

    public int getGrassColor() {
        return grassColor;
    }

    public int getFoliageColor() {
        return foliageColor;
    }

    public void setFoliageColor(int foliageColor) {
        this.foliageColor = foliageColor;
    }

    public List<DimensionTreeData> getTreeData() {
        return treeData;
    }

    public float getCorruptedCratersChance() {
        return corruptedCratersChance;
    }

    public float getNonCorruptedCratersChance() {
        return nonCorruptedCratersChance;
    }

    public boolean spawnsCratersInNonCorrupted() {
        return spawnsCratersInNonCorrupted;
    }

    public float getLargeSkeletonTreeChance() {
        return largeSkeletonTreeChance;
    }

    public float getCampfireChance() {
        return campfireChance;
    }

    public float getOutpostChance() {
        return outpostChance;
    }

    public float getTowerChance() {
        return towerChance;
    }

    public boolean hasMushrooms() {
        return hasMushrooms;
    }

    public boolean hasMossyRocks() {
        return hasMossyRocks;
    }

    public static class Builder {
        private Identifier id;
        private String name;
        private int surfaceBuilderVariantChance;
        private float depth;
        private float scale;
        private float temperature;
        private float downfall;
        private int waterColor;
        private int grassColor;
        private int foliageColor;
        private List<DimensionTreeData> treeData;
        private float corruptedCratersChance;
        private float nonCorruptedCratersChance;
        private boolean spawnsCratersInNonCorrupted;
        private float largeSkeletonTreeChance;
        private float campfireChance;
        private float outpostChance;
        private float towerChance;
        private boolean hasMushrooms;
        private boolean hasMossyRocks;

        private Builder() {

        }

        public static Builder create(Identifier id, String name) {
            Builder builder = new Builder();
            builder.id = id;
            builder.name = name;
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
            return this;
        }

        public Builder surfaceBuilderVariantChance(int surfaceBuilderVariantChance) {
            this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
            return this;
        }

        public Builder depth(float depth) {
            this.depth = depth;
            return this;
        }

        public Builder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder downfall(float downfall) {
            this.downfall = downfall;
            return this;
        }

        public Builder waterColor(int waterColor) {
            this.waterColor = waterColor;
            return this;
        }

        public Builder grassColor(int grassColor) {
            this.grassColor = grassColor;
            return this;
        }

        public Builder foliageColor(int foliageColor) {
            this.foliageColor = foliageColor;
            return this;
        }

        public Builder treeData(List<DimensionTreeData> treeData) {
            this.treeData = treeData;
            return this;
        }

        public DimensionBiomeData build() {
            return new DimensionBiomeData(id, name, surfaceBuilderVariantChance, depth, scale, temperature, downfall, waterColor, grassColor, foliageColor, treeData, corruptedCratersChance, nonCorruptedCratersChance, spawnsCratersInNonCorrupted, largeSkeletonTreeChance, campfireChance, outpostChance, towerChance, hasMushrooms, hasMossyRocks);
        }

        public Builder hasMossyRocks(boolean hasMossyRocks) {
            this.hasMossyRocks = hasMossyRocks;
            return this;
        }

        public Builder hasMushrooms(boolean hasMushrooms) {
            this.hasMushrooms = hasMushrooms;
            return this;
        }

        public Builder towerChance(float towerChance) {
            this.towerChance = towerChance;
            return this;
        }

        public Builder outpostChance(float outpostChance) {
            this.outpostChance = outpostChance;
            return this;
        }

        public Builder campfireChance(float campfireChance) {
            this.campfireChance = campfireChance;
            return this;
        }

        public Builder largeSkeletonTreeChance(float largeSkeletonTreeChance) {
            this.largeSkeletonTreeChance = largeSkeletonTreeChance;
            return this;
        }

        public Builder spawnsCratersInNonCorrupted(boolean spawnsCratersInNonCorrupted) {
            this.spawnsCratersInNonCorrupted = spawnsCratersInNonCorrupted;
            return this;
        }

        public Builder nonCorruptedCratersChance(float nonCorruptedCratersChance) {
            this.nonCorruptedCratersChance = nonCorruptedCratersChance;
            return this;
        }

        public Builder corruptedCratersChance(float corruptedCratersChance) {
            this.corruptedCratersChance = corruptedCratersChance;
            return this;
        }
    }
}