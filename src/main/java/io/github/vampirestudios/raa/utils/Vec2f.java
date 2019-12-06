//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.raa.utils;

public class Vec2f {
    public static final Vec2f ZERO = new Vec2f(0.0F, 0.0F);
    public static final Vec2f SOUTH_EAST_UNIT = new Vec2f(1.0F, 1.0F);
    public static final Vec2f EAST_UNIT = new Vec2f(1.0F, 0.0F);
    public static final Vec2f WEST_UNIT = new Vec2f(-1.0F, 0.0F);
    public static final Vec2f SOUTH_UNIT = new Vec2f(0.0F, 1.0F);
    public static final Vec2f NORTH_UNIT = new Vec2f(0.0F, -1.0F);
    public static final Vec2f MAX_SOUTH_EAST = new Vec2f(3.4028235E38F, 3.4028235E38F);
    public static final Vec2f MIN_SOUTH_EAST = new Vec2f(1.4E-45F, 1.4E-45F);
    private float x;
    private float y;

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vec2f other) {
        return this.x == other.x && this.y == other.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
