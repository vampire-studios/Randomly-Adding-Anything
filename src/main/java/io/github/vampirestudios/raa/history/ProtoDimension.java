package io.github.vampirestudios.raa.history;

import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.HashMap;

public class ProtoDimension {
    private Pair<String, Identifier> name;
    private int flags;
    private float temperature;
    private float scale;
    private double x, y;
    private HashMap<String, Double> civilizationInfluences;

    //The least amount of dimension data needed for civilization simulation
    public ProtoDimension(Pair<String, Identifier> name, int flags, float temperature, float scale) {
        this.name = name;
        this.flags = flags;
        this.temperature = temperature;
        this.scale = scale;
        civilizationInfluences = new HashMap<>();
    }

    public void setDead() {
        this.flags |= Utils.DEAD;
    }

    public void setAbandoned() {
        this.flags |= Utils.ABANDONED;
    }

    public void setCivilized() {
        this.flags |= Utils.CIVILIZED;
    }

    public void removeLush() {
        this.flags &= ~Utils.LUSH;
    }

    public void addInfluence(String name, double percent) {
        civilizationInfluences.put(name, percent);
    }

    public void setXandY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Pair<String, Identifier> getName() {
        return name;
    }

    public int getFlags() {
        return flags;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getScale() {
        return scale;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public HashMap<String, Double> getCivilizationInfluences() {
        return civilizationInfluences;
    }
}
