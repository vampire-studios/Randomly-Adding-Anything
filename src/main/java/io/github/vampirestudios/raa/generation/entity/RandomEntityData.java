package io.github.vampirestudios.raa.generation.entity;

import net.minecraft.util.Identifier;

public class RandomEntityData {

    public int colorOne;
    public int colorTwo;
    public Identifier id;
    public int trackingDistance;
    public int updateIntervalTicks;
    public boolean alwaysUpdateVelocity;
    public float sizeX;
    public float sizeY;

    public RandomEntityData(int colorOne, int colorTwo, Identifier id, int trackingDistance, int updateIntervalTicks, boolean alwaysUpdateVelocity, float sizeX, float sizeY) {
        this.colorOne = colorOne;
        this.colorTwo = colorTwo;
        this.id = id;
        this.trackingDistance = trackingDistance;
        this.updateIntervalTicks = updateIntervalTicks;
        this.alwaysUpdateVelocity = alwaysUpdateVelocity;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getColorOne() {
        return colorOne;
    }

    public int getColorTwo() {
        return colorTwo;
    }

    public Identifier getId() {
        return id;
    }

    public int getTrackingDistance() {
        return trackingDistance;
    }

    public int getUpdateIntervalTicks() {
        return updateIntervalTicks;
    }

    public boolean isAlwaysUpdateVelocity() {
        return alwaysUpdateVelocity;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

}