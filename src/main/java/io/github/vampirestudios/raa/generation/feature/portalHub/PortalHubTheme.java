package io.github.vampirestudios.raa.generation.feature.portalHub;

import net.minecraft.block.Block;

public class PortalHubTheme {

    private Block block, wall, slab, stairs;

    public PortalHubTheme(Block block, Block wall, Block slab, Block stairs) {
        this.block = block;
        this.wall = wall;
        this.slab = slab;
        this.stairs = stairs;
    }

    public Block getBlock() {
        return block;
    }

    public Block getWall() {
        return wall;
    }

    public Block getSlab() {
        return slab;
    }

    public Block getStairs() {
        return stairs;
    }

}