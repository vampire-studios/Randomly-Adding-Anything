package io.github.vampirestudios.raa.generation.entity;

import net.minecraft.util.Identifier;

public class RandomEntityBuilder {

    public int colorOne;
    public int colorTwo;
    public Identifier id;
    public int trackingDistance;
    public int updateIntervalTicks;
    public boolean alwaysUpdateVelocity;
    public float sizeX;
    public float sizeY;

    public static RandomEntityBuilder create() {
        return new RandomEntityBuilder();
    }

    public RandomEntityBuilder colorOne(int colorOne) {
        this.colorOne = colorOne;
        return this;
    }

    public RandomEntityBuilder colorTwo(int colorTwo) {
        this.colorTwo = colorTwo;
        return this;
    }

    public RandomEntityBuilder id(Identifier id) {
        this.id = id;
        return this;
    }

    public RandomEntityBuilder trackingDistance(int trackingDistance) {
        this.trackingDistance = trackingDistance;
        return this;
    }

    public RandomEntityBuilder updateIntervalTicks(int updateIntervalTicks) {
        this.updateIntervalTicks = updateIntervalTicks;
        return this;
    }

    public RandomEntityBuilder alwaysUpdateVelocity(boolean alwaysUpdateVelocity) {
        this.alwaysUpdateVelocity = alwaysUpdateVelocity;
        return this;
    }

    public RandomEntityBuilder sizeX(float sizeX) {
        this.sizeX = sizeX;
        return this;
    }

    public RandomEntityBuilder sizeY(float sizeY) {
        this.sizeY = sizeY;
        return this;
    }

    public RandomEntityData build() {
        return new RandomEntityData(colorOne, colorTwo, id, trackingDistance, updateIntervalTicks, alwaysUpdateVelocity, sizeX, sizeY);
    }

}