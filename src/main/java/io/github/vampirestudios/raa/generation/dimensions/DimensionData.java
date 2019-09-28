package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.math.Vec3d;

public class DimensionData {
    private String name;
    private int grassColor;
    private int fogColor;
    private int foliageColor;
    private int skyColor;
    private boolean hasLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean renderFog;
    public Vec3d skyColorRGB;

    public DimensionData(String name, int grassColor, int fogColor, int foliageColor, int skyColor, boolean hasLight, boolean hasSky, boolean canSleep, boolean renderFog) {
        this.name = name;
        this.grassColor = grassColor;
        this.fogColor = fogColor;
        this.foliageColor = foliageColor;
        this.skyColor = skyColor;
        this.hasLight = hasLight;
        this.hasSky = hasSky;
        this.canSleep = canSleep;
        this.renderFog = renderFog;
        this.skyColorRGB = new Vec3d(Rands.randInt(255),Rands.randInt(255),Rands.randInt(255));
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