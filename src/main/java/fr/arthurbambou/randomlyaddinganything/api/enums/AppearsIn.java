package fr.arthurbambou.randomlyaddinganything.api.enums;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public enum AppearsIn {
    DIRT_SURFACE,
    GRAVEL,
    SAND_DESERT,
    DIORITE,
    ANDESITE,
    GRANITE,
    SAND_BEACH,
    DIRT_ANY,
    SAND_ANY,
    DIRT_UNDERGROUND,
    SAND_ANY2,
    RED_SAND,
    END_STONE,
    NETHERRACK,
    DOES_NOT_APPEAR,
    BIOME_SPECIFIC;

    private List<Block> blockList;

    AppearsIn() {
        this.blockList = new ArrayList<>();
    }

    public List<Block> getBlockList() {
        return blockList;
    }

    public void addBlock(Block block) {
        this.blockList.add(block);
    }
}
