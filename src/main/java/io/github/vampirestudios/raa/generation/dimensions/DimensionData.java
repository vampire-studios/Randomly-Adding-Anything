package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionData {
    private String name;
    private DimensionBiomeData biomeData;
    private int grassColor;
    private int fogColor;
    private int foliageColor;
    private int skyColor;
    private int stoneColor;
    private boolean hasLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean waterVaporize;
    private boolean renderFog;

    public DimensionData(String name, DimensionBiomeData biomeData, int grassColor, int fogColor, int foliageColor, int skyColor, int stoneColor, boolean hasLight, boolean hasSky, boolean canSleep, boolean waterVaporize, boolean renderFog) {
        this.name = name;
        this.biomeData = biomeData;
        this.grassColor = grassColor;
        this.fogColor = fogColor;
        this.foliageColor = foliageColor;
        this.skyColor = skyColor;
        this.stoneColor = stoneColor;
        this.hasLight = hasLight;
        this.hasSky = hasSky;
        this.canSleep = canSleep;
        this.waterVaporize = waterVaporize;
        this.renderFog = renderFog;
    }

    public String getName() {
        return name;
    }

    public DimensionBiomeData getBiomeData() {
        return biomeData;
    }

    public int getGrassColor() {
        return grassColor;
    }

    public int getFogColor() {
        return fogColor;
    }

    public int getFoliageColor() {
        return foliageColor;
    }

    public int getStoneColor() {
        return stoneColor;
    }

    public boolean hasSkyLight() {
        return hasLight;
    }

    public boolean hasSky() {
        return hasSky;
    }

    public int getSkyColor() {
        return skyColor;
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

}