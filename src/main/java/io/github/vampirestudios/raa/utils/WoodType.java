//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.registries.RAAMiscBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class WoodType {
    public static final WoodType SPRUCE = WoodTypeRegistry.registerVanilla(new WoodType("spruce", RAAMiscBlocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG));
    public static final WoodType OAK = WoodTypeRegistry.registerVanilla(new WoodType("oak", RAAMiscBlocks.OAK_LEAVES, Blocks.OAK_LOG));
    public static final WoodType BIRCH = WoodTypeRegistry.registerVanilla(new WoodType("birch", RAAMiscBlocks.BIRCH_LEAVES, Blocks.BIRCH_LOG));
    public static final WoodType JUNGLE = WoodTypeRegistry.registerVanilla(new WoodType("jungle", RAAMiscBlocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG));
    public static final WoodType ACACIA = WoodTypeRegistry.registerVanilla(new WoodType("acacia", RAAMiscBlocks.ACACIA_LEAVES, Blocks.ACACIA_LOG));
    public static final WoodType DARK_OAK = WoodTypeRegistry.registerVanilla(new WoodType("dark_oak", RAAMiscBlocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG));
    public static final WoodType[] VANILLA = new WoodType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};
    protected Identifier identifier;
    private Block leaves;
    private Block log;

    public WoodType(String name, Block leaves, Block log) {
        this(new Identifier(name), leaves, log);
    }

    public WoodType(Identifier identifier, Block leaves, Block log) {
        this.identifier = identifier;
        this.leaves = leaves;
        this.log = log;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public Block getLeaves() {
        return this.leaves;
    }

    public Block getLog() {
        return this.log;
    }

}