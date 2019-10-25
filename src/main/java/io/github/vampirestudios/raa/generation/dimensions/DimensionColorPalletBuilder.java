package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionColorPalletBuilder {

    private int skyColor;
    private int grassColor;
    private int fogColor;
    private int foliageColor;
    private int stoneColor;

    public static DimensionColorPalletBuilder create() {
        return new DimensionColorPalletBuilder();
    }

    public DimensionColorPalletBuilder skyColor(int skyColor) {
        this.skyColor = skyColor;
        return this;
    }

    public DimensionColorPalletBuilder grassColor(int grassColor) {
        this.grassColor = grassColor;
        return this;
    }

    public DimensionColorPalletBuilder fogColor(int fogColor) {
        this.fogColor = fogColor;
        return this;
    }

    public DimensionColorPalletBuilder foliageColor(int foliageColor) {
        this.foliageColor = foliageColor;
        return this;
    }

    public DimensionColorPalletBuilder stoneColor(int stoneColor) {
        this.stoneColor = stoneColor;
        return this;
    }

    public DimensionColorPallet build() {
        return new DimensionColorPallet(skyColor, grassColor, fogColor, foliageColor, stoneColor);
    }

}