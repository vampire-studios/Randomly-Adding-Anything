package io.github.vampirestudios.raa.blocks;

import net.minecraft.block.Block;

public class OreBlockSettings {

    private Block.Settings props;
    private int minXp;
    private int maxXp;

    public OreBlockSettings(Block.Settings props, int minXp, int maxXp) {
        this.props = props;
        this.minXp = minXp;
        this.maxXp = maxXp;
    }

    public Block.Settings getSettings() {
        return props;
    }

    public int getMinXp() {
        return minXp;
    }

    public int getMaxXp() {
        return maxXp;
    }
}