package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionColorPalette {

    private int skyColor;
    private int grassColor;
    private int fogColor;
    private int foliageColor;
    private int stoneColor;

    public DimensionColorPalette(int skyColor, int grassColor, int fogColor, int foliageColor, int stoneColor) {
        this.skyColor = skyColor;
        this.grassColor = grassColor;
        this.fogColor = fogColor;
        this.foliageColor = foliageColor;
        this.stoneColor = stoneColor;
    }

    public int getSkyColor() {
        return skyColor;
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

}