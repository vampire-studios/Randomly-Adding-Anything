package io.github.vampirestudios.raa.generation.dimensions;

public class Dimension {
    private String name;
    private int grassColor;
    private int fogColor;
    private int foliageColor;
    private int skyColor;
    private boolean hasLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean renderFog;

    public Dimension(String name, int grassColor, int fogColor, int foliageColor, int skyColor, boolean hasLight, boolean hasSky, boolean canSleep, boolean renderFog) {
        this.name = name;
        this.grassColor = grassColor;
        this.fogColor = fogColor;
        this.foliageColor = foliageColor;
        this.skyColor = skyColor;
        this.hasLight = hasLight;
        this.hasSky = hasSky;
        this.canSleep = canSleep;
        this.renderFog = renderFog;
    }

    public String getName() {
        return name;
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

    public boolean shouldRenderFog() {
        return renderFog;
    }
}