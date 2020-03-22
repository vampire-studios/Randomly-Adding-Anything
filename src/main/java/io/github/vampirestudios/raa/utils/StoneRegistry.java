package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.vampirelib.blocks.SlabBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.StairsBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.WallBaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class StoneRegistry {
    public Identifier name;
    public Block raw;
    public Block slab;
    public Block stairs;
    public Block wall;
    public Block cobblestone;
    public Block cobblestoneSlab;
    public Block cobblestoneStairs;
    public Block cobblestoneWall;
    public Block polished;
    public Block polishedSlab;
    public Block polishedStairs;
    public Block polishedWall;
    public Block chiseled;
    public Block chiseledSlab;
    public Block chiseledStairs;
    public Block chiseledWall;
    public Block bricks;
    public Block brickSlab;
    public Block brickStairs;
    public Block brickWall;
    public Block crackedBricks;
    public Block crackedBricksWall;
    public Block crackedBricksStairs;
    public Block crackedBricksSlab;
    public Block chiseledBricks;
    public Block chiseledBricksWall;
    public Block chiseledBricksStairs;
    public Block chiseledBricksSlab;
    public Block mossyBricks;
    public Block mossyBricksWall;
    public Block mossyBricksStairs;
    public Block mossyBricksSlab;

    public StoneRegistry(Identifier name) {
        this.name = name;
    }

    public static StoneRegistry.Builder of(Identifier name) {
        return (new StoneRegistry.Builder()).of(name);
    }

    public Block getRaw() {
        return this.raw;
    }

    public Block getSlab() {
        return this.slab;
    }

    public Block getStairs() {
        return this.stairs;
    }

    public Block getWall() {
        return this.wall;
    }

    public Block getCobblestone() {
        return this.cobblestone;
    }

    public Block getCobblestoneSlab() {
        return this.cobblestoneSlab;
    }

    public Block getCobblestoneStairs() {
        return this.cobblestoneStairs;
    }

    public Block getCobblestoneWall() {
        return this.cobblestoneWall;
    }

    public Block getPolished() {
        return this.polished;
    }

    public Block getPolishedSlab() {
        return this.polishedSlab;
    }

    public Block getPolishedStairs() {
        return this.polishedStairs;
    }

    public Block getPolishedWall() {
        return this.polishedWall;
    }

    public Block getChiseled() {
        return chiseled;
    }

    public Block getChiseledSlab() {
        return chiseledSlab;
    }

    public Block getChiseledStairs() {
        return chiseledStairs;
    }

    public Block getChiseledWall() {
        return chiseledWall;
    }

    public Block getBricks() {
        return this.bricks;
    }

    public Block getBrickSlab() {
        return this.brickSlab;
    }

    public Block getBrickStairs() {
        return this.brickStairs;
    }

    public Block getBrickWall() {
        return this.brickWall;
    }

    public Block getCrackedBricks() {
        return this.crackedBricks;
    }

    public Block getCrackedBricksWall() {
        return this.crackedBricksWall;
    }

    public Block getCrackedBricksStairs() {
        return this.crackedBricksStairs;
    }

    public Block getCrackedBricksSlab() {
        return this.crackedBricksSlab;
    }

    public Block getChiseledBricks() {
        return chiseledBricks;
    }

    public Block getChiseledBricksWall() {
        return chiseledBricksWall;
    }

    public Block getChiseledBricksStairs() {
        return chiseledBricksStairs;
    }

    public Block getChiseledBricksSlab() {
        return chiseledBricksSlab;
    }

    public Block getMossyBricks() {
        return this.mossyBricks;
    }

    public Block getMossyBricksWall() {
        return this.mossyBricksWall;
    }

    public Block getMossyBricksStairs() {
        return this.mossyBricksStairs;
    }

    public Block getMossyBricksSlab() {
        return this.mossyBricksSlab;
    }

    public static class Builder {
        public Identifier name;
        private StoneRegistry stoneRegistry;
        private ItemGroup itemGroup = ItemGroup.BUILDING_BLOCKS;

        public Builder() {
        }

        public StoneRegistry.Builder of(Identifier name) {
            this.name = name;
            this.stoneRegistry = new StoneRegistry(name);
            return this;
        }

        public Builder itemGroup(ItemGroup itemGroup) {
            this.itemGroup = itemGroup;
            return this;
        }

        public StoneRegistry.Builder raw() {
            this.stoneRegistry.raw = RegistryUtils.register(new Block(Settings.copy(Blocks.STONE)), this.name, itemGroup);
            return this;
        }

        public StoneRegistry.Builder raw(Block raw) {
            this.stoneRegistry.raw = raw;
            return this;
        }

        public StoneRegistry.Builder slab() {
            this.stoneRegistry.slab = RegistryUtils.register(new SlabBaseBlock(Settings.copy(Blocks.STONE_SLAB)), new Identifier(this.name.getNamespace(),
                    this.name.getPath() + "_stone_slab"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder slab(Block slab) {
            this.stoneRegistry.slab = slab;
            return this;
        }

        public StoneRegistry.Builder stairs() {
            this.stoneRegistry.stairs = RegistryUtils.register(new StairsBaseBlock(this.stoneRegistry.raw.getDefaultState()), new Identifier(this.name.getNamespace(), this.name.getPath() + "_stone_stairs"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder stairs(Block stairs) {
            this.stoneRegistry.stairs = stairs;
            return this;
        }

        public StoneRegistry.Builder wall() {
            this.stoneRegistry.wall = RegistryUtils.register(new WallBaseBlock(Settings.copy(Blocks.COBBLESTONE_WALL)), new Identifier(this.name.getNamespace(), this.name.getPath() + "_stone_wall"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder wall(Block wall) {
            this.stoneRegistry.wall = wall;
            return this;
        }

        public StoneRegistry.Builder cobblestone() {
            this.stoneRegistry.cobblestone = RegistryUtils.register(new Block(Settings.copy(Blocks.COBBLESTONE)), new Identifier(this.name.getNamespace(), this.name.getPath() + "_cobblestone"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder cobblestone(Block cobblestone) {
            this.stoneRegistry.cobblestone = cobblestone;
            return this;
        }

        public StoneRegistry.Builder cobblestoneSlab() {
            this.stoneRegistry.cobblestoneSlab = RegistryUtils.register(new SlabBaseBlock(Settings.copy(Blocks.COBBLESTONE_SLAB)), new Identifier(this.name.getNamespace(), this.name.getPath() + "_cobblestone_slab"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder cobblestoneSlab(Block cobblestoneSlab) {
            this.stoneRegistry.cobblestoneSlab = cobblestoneSlab;
            return this;
        }

        public StoneRegistry.Builder cobblestoneStairs() {
            this.stoneRegistry.cobblestoneStairs = RegistryUtils.register(new StairsBaseBlock(this.stoneRegistry.cobblestone.getDefaultState()), new Identifier(this.name.getNamespace(), this.name.getPath() + "_cobblestone_stairs"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder cobblestoneStairs(Block cobblestoneStairs) {
            this.stoneRegistry.cobblestoneStairs = cobblestoneStairs;
            return this;
        }

        public StoneRegistry.Builder cobblestoneWall() {
            this.stoneRegistry.cobblestoneWall = RegistryUtils.register(new WallBaseBlock(Settings.copy(Blocks.COBBLESTONE_WALL)), new Identifier(this.name.getNamespace(), this.name.getPath() + "_cobblestone_wall"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder cobblestoneWall(Block cobblestoneWall) {
            this.stoneRegistry.cobblestoneWall = cobblestoneWall;
            return this;
        }

        public StoneRegistry.Builder polished(Block polished) {
            this.stoneRegistry.polished = polished;
            return this;
        }

        public StoneRegistry.Builder polished() {
            this.stoneRegistry.polished = RegistryUtils.register(new Block(Settings.copy(Blocks.COBBLESTONE)), new Identifier(this.name.getNamespace(), "polished_" + this.name.getPath() + "_stone"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder polishedSlab(Block polishedSlab) {
            this.stoneRegistry.polishedSlab = polishedSlab;
            return this;
        }

        public StoneRegistry.Builder polishedSlab() {
            this.stoneRegistry.polishedSlab = RegistryUtils.register(new SlabBaseBlock(Settings.copy(Blocks.STONE_SLAB)), new Identifier(this.name.getNamespace(), "polished_" + this.name.getPath() + "_stone_slab"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder polishedStairs(Block polishedStairs) {
            this.stoneRegistry.polishedStairs = polishedStairs;
            return this;
        }

        public StoneRegistry.Builder polishedStairs() {
            this.stoneRegistry.polishedStairs = RegistryUtils.register(new StairsBaseBlock(this.stoneRegistry.polished.getDefaultState()), new Identifier(this.name.getNamespace(), "polished_" + this.name.getPath() + "_stone_stairs"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder polishedWall(Block polishedWall) {
            this.stoneRegistry.polishedWall = polishedWall;
            return this;
        }

        public StoneRegistry.Builder polishedWall() {
            this.stoneRegistry.polishedWall = RegistryUtils.register(new WallBaseBlock(Settings.copy(Blocks.COBBLESTONE_WALL)), new Identifier(this.name.getNamespace(), "polished_" + this.name.getPath() + "_stone_wall"), itemGroup);
            return this;
        }


        public StoneRegistry.Builder chiseled(Block chiseled) {
            this.stoneRegistry.chiseled = chiseled;
            return this;
        }

        public StoneRegistry.Builder chiseled() {
            this.stoneRegistry.chiseled = RegistryUtils.register(new Block(Settings.copy(Blocks.COBBLESTONE)), new Identifier(this.name.getNamespace(), "chiseled_" + this.name.getPath() + "_stone"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder chiseledSlab(Block chiseledSlab) {
            this.stoneRegistry.chiseledSlab = chiseledSlab;
            return this;
        }

        public StoneRegistry.Builder chiseledSlab() {
            this.stoneRegistry.chiseledSlab = RegistryUtils.register(new SlabBaseBlock(Settings.copy(Blocks.STONE_SLAB)), new Identifier(this.name.getNamespace(), "chiseled_" + this.name.getPath() + "_stone_slab"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder chiseledStairs(Block chiseledStairs) {
            this.stoneRegistry.chiseledStairs = chiseledStairs;
            return this;
        }

        public StoneRegistry.Builder chiseledStairs() {
            this.stoneRegistry.chiseledStairs = RegistryUtils.register(new StairsBaseBlock(this.stoneRegistry.chiseled.getDefaultState()), new Identifier(this.name.getNamespace(), "chiseled_" + this.name.getPath() + "_stone_stairs"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder chiseledWall(Block chiseledWall) {
            this.stoneRegistry.chiseledWall = chiseledWall;
            return this;
        }

        public StoneRegistry.Builder chiseledWall() {
            this.stoneRegistry.chiseledWall = RegistryUtils.register(new WallBaseBlock(Settings.copy(Blocks.COBBLESTONE_WALL)), new Identifier(this.name.getNamespace(), "chiseled_" + this.name.getPath() + "_stone_wall"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder bricks() {
            this.stoneRegistry.bricks = RegistryUtils.register(new Block(Settings.copy(Blocks.COBBLESTONE)), new Identifier(this.name.getNamespace(), this.name.getPath() + "_stone_bricks"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder bricks(Block bricks) {
            this.stoneRegistry.bricks = bricks;
            return this;
        }

        public StoneRegistry.Builder brickSlab(Block brickSlab) {
            this.stoneRegistry.brickSlab = brickSlab;
            return this;
        }

        public StoneRegistry.Builder brickSlab() {
            this.stoneRegistry.brickSlab = RegistryUtils.register(new SlabBaseBlock(Settings.copy(Blocks.STONE_SLAB)), new Identifier(this.name.getNamespace(), this.name.getPath() + "_stone_brick_slab"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder brickStairs(Block brickStairs) {
            this.stoneRegistry.brickStairs = brickStairs;
            return this;
        }

        public StoneRegistry.Builder brickStairs() {
            this.stoneRegistry.brickStairs = RegistryUtils.register(new StairsBaseBlock(this.stoneRegistry.bricks.getDefaultState()), new Identifier(this.name.getNamespace(), this.name.getPath() + "_stone_brick_stairs"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder brickWall(Block brickWall) {
            this.stoneRegistry.brickWall = brickWall;
            return this;
        }

        public StoneRegistry.Builder brickWall() {
            this.stoneRegistry.brickWall = RegistryUtils.register(new WallBaseBlock(Settings.copy(Blocks.BRICK_WALL)), new Identifier(this.name.getNamespace(), this.name.getPath() + "_stone_brick_wall"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder crackedBricks(Block crackedBricks) {
            this.stoneRegistry.crackedBricks = crackedBricks;
            return this;
        }

        public StoneRegistry.Builder crackedBricks() {
            this.stoneRegistry.crackedBricks = RegistryUtils.register(new Block(Settings.copy(Blocks.COBBLESTONE)), new Identifier(this.name.getNamespace(), "cracked_" + this.name.getPath() + "_stone_bricks"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder crackedBricksWall(Block crackedBricksWall) {
            this.stoneRegistry.crackedBricksWall = crackedBricksWall;
            return this;
        }

        public StoneRegistry.Builder crackedBricksWall() {
            this.stoneRegistry.crackedBricksWall = RegistryUtils.register(new WallBaseBlock(Settings.copy(Blocks.COBBLESTONE_WALL)), new Identifier(this.name.getNamespace(), "cracked_" + this.name.getPath() + "_stone_bricks_wall"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder crackedBricksStairs(Block crackedBricksStairs) {
            this.stoneRegistry.crackedBricksStairs = crackedBricksStairs;
            return this;
        }

        public StoneRegistry.Builder crackedBricksStairs() {
            this.stoneRegistry.crackedBricksStairs = RegistryUtils.register(new StairsBaseBlock(this.stoneRegistry.cobblestone.getDefaultState()), new Identifier(this.name.getNamespace(), "cracked_" + this.name.getPath() + "_stone_bricks_stairs"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder crackedBricksSlab(Block crackedBricksSlab) {
            this.stoneRegistry.crackedBricksSlab = crackedBricksSlab;
            return this;
        }

        public StoneRegistry.Builder crackedBricksSlab() {
            this.stoneRegistry.crackedBricksSlab = RegistryUtils.register(new SlabBaseBlock(Settings.copy(Blocks.STONE_SLAB)), new Identifier(this.name.getNamespace(), "cracked_" + this.name.getPath() + "_stone_bricks_slab"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder chiseledBricks(Block chiseledBricks) {
            this.stoneRegistry.chiseledBricks = chiseledBricks;
            return this;
        }

        public StoneRegistry.Builder chiseledBricks() {
            this.stoneRegistry.chiseledBricks = RegistryUtils.register(new Block(Settings.copy(Blocks.COBBLESTONE)), new Identifier(this.name.getNamespace(), "chiseled_" + this.name.getPath() + "_stone_bricks"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder chiseledBricksWall(Block crackedBricksWall) {
            this.stoneRegistry.chiseledBricksWall = crackedBricksWall;
            return this;
        }

        public StoneRegistry.Builder chiseledBricksWall() {
            this.stoneRegistry.chiseledBricksWall = RegistryUtils.register(new WallBaseBlock(Settings.copy(Blocks.COBBLESTONE_WALL)), new Identifier(this.name.getNamespace(), "chiseled_" + this.name.getPath() + "_stone_bricks_wall"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder chiseledBricksStairs(Block chiseledBricksStairs) {
            this.stoneRegistry.chiseledBricksStairs = chiseledBricksStairs;
            return this;
        }

        public StoneRegistry.Builder chiseledBricksStairs() {
            this.stoneRegistry.chiseledBricksStairs = RegistryUtils.register(new StairsBaseBlock(this.stoneRegistry.cobblestone.getDefaultState()), new Identifier(this.name.getNamespace(), "chiseled_" + this.name.getPath() + "_stone_bricks_stairs"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder chiseledBricksSlab(Block crackedBricksSlab) {
            this.stoneRegistry.chiseledBricksSlab = crackedBricksSlab;
            return this;
        }

        public StoneRegistry.Builder chiseledBricksSlab() {
            this.stoneRegistry.chiseledBricksSlab = RegistryUtils.register(new SlabBaseBlock(Settings.copy(Blocks.STONE_SLAB)), new Identifier(this.name.getNamespace(), "chiseled_" + this.name.getPath() + "_stone_bricks_slab"), itemGroup);
            return this;
        }


        public StoneRegistry.Builder mossyBricks(Block mossyBricks) {
            this.stoneRegistry.mossyBricks = mossyBricks;
            return this;
        }

        public StoneRegistry.Builder mossyBricks() {
            this.stoneRegistry.bricks = RegistryUtils.register(new Block(Settings.copy(Blocks.COBBLESTONE)), new Identifier(this.name.getNamespace(), "mossy_" + this.name.getPath() + "_stone_bricks"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder mossyBricksSlab(Block mossyBricksSlab) {
            this.stoneRegistry.mossyBricksSlab = mossyBricksSlab;
            return this;
        }

        public StoneRegistry.Builder mossyBricksSlab() {
            this.stoneRegistry.mossyBricksSlab = RegistryUtils.register(new SlabBaseBlock(Settings.copy(Blocks.STONE_SLAB)), new Identifier(this.name.getNamespace(), "mossy_" + this.name.getPath() + "_stone_bricks_slab"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder mossyBricksStairs(Block mossyBricksStairs) {
            this.stoneRegistry.mossyBricksStairs = mossyBricksStairs;
            return this;
        }

        public StoneRegistry.Builder mossyBricksStairs() {
            this.stoneRegistry.mossyBricksStairs = RegistryUtils.register(new StairsBaseBlock(this.stoneRegistry.cobblestone.getDefaultState()), new Identifier(this.name.getNamespace(), "mossy_" + this.name.getPath() + "_stone_bricks_stairs"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder mossyBricksWall(Block mossyBricksWall) {
            this.stoneRegistry.mossyBricksWall = mossyBricksWall;
            return this;
        }

        public StoneRegistry.Builder mossyBricksWall() {
            this.stoneRegistry.mossyBricksWall = RegistryUtils.register(new WallBaseBlock(Settings.copy(Blocks.COBBLESTONE_WALL)), new Identifier(this.name.getNamespace(), "mossy_" + this.name.getPath() + "_stone_bricks_wall"), itemGroup);
            return this;
        }

        public StoneRegistry.Builder all() {
            return this.raw().slab().stairs().wall().cobblestone().cobblestoneSlab().cobblestoneStairs().cobblestoneWall().polished().polishedSlab().polishedStairs().polishedWall().bricks().brickSlab()
                    .brickStairs().brickWall().crackedBricks().crackedBricksSlab().crackedBricksStairs().crackedBricksWall().mossyBricks().mossyBricksSlab().mossyBricksStairs().mossyBricksWall();
        }

        public StoneRegistry build() {
            return this.stoneRegistry;
        }
    }
}
