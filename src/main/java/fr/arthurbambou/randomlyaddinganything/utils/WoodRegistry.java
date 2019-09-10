/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Team Abnormals
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package fr.arthurbambou.randomlyaddinganything.utils;

import io.github.vampirestudios.vampirelib.blocks.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class WoodRegistry {

    public Identifier name;
    private Block log;
    private Block wood;
    private Block strippedLog;
    private Block strippedWood;
    private Block stairs;
    private Block slab;
    private Block planks;
    private Block leaves;
    private Block sapling;
    private Block fence;
    private Block fenceGate;
    private Block bookshelf;
    private Block door;
    private Block trapdoor;
    private Block button;
    private Block pressurePlate;
    private Block ladder;
    private SaplingGenerator saplingGenerator;

    private WoodRegistry(Identifier name, SaplingGenerator saplingGenerator) {
        this.name = name;
        this.saplingGenerator = saplingGenerator;
    }

    private WoodRegistry(Identifier name) {
        this.name = name;
        this.saplingGenerator = null;
    }

    public static WoodRegistry.Builder of(Identifier name) {
        return new WoodRegistry.Builder().of(name);
    }

    public static WoodRegistry.Builder of(Identifier name, Block planks) {
        return new WoodRegistry.Builder().of(name, planks);
    }

    public static WoodRegistry.Builder of(Identifier name, SaplingGenerator saplingGenerator) {
        return new WoodRegistry.Builder().of(name, saplingGenerator);
    }

    public Block getLog() {
        return log;
    }

    public Block getWood() {
        return wood;
    }

    public Block getStrippedLog() {
        return strippedLog;
    }

    public Block getStrippedWood() {
        return strippedWood;
    }

    public Block getStairs() {
        return stairs;
    }

    public Block getSlab() {
        return slab;
    }

    public Block getPlanks() {
        return planks;
    }

    public Block getLeaves() {
        return leaves;
    }

    public Block getSapling() {
        return sapling;
    }

    public Block getFence() {
        return fence;
    }

    public Block getFenceGate() {
        return fenceGate;
    }

    public Block getBookshelf() {
        return bookshelf;
    }

    public Block getDoor() {
        return door;
    }

    public Block getTrapdoor() {
        return trapdoor;
    }

    public Block getButton() {
        return button;
    }

    public Block getPressurePlate() {
        return pressurePlate;
    }

    public Block getLadder() {
        return ladder;
    }

    public static class Builder {

        public Identifier name;
        private WoodRegistry woodRegistry;

        public Builder of(Identifier name) {
            this.name = name;
            woodRegistry = new WoodRegistry(name);
            return this;
        }

        public Builder of(Identifier name, Block planks) {
            this.name = name;
            woodRegistry = new WoodRegistry(name);
            woodRegistry.planks = planks;
            return this;
        }

        public Builder of(Identifier name, SaplingGenerator saplingGenerator) {
            this.name = name;
            woodRegistry = new WoodRegistry(name, saplingGenerator);
            return this;
        }

        public Builder log() {
            woodRegistry.log = RegistryUtils.register(new PillarBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.SPRUCE).hardness(2.0F)
                            .sounds(BlockSoundGroup.WOOD).build()), new Identifier(name.getNamespace(), name.getPath() + "_log"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder wood() {
            woodRegistry.wood = RegistryUtils.register(new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).hardness(2.0F)
                            .sounds(BlockSoundGroup.WOOD).build()), new Identifier(name.getNamespace(), name.getPath() + "_wood"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder strippedLog() {
            woodRegistry.strippedLog = RegistryUtils.register(new PillarBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.SPRUCE).hardness(2.0F)
                    .sounds(BlockSoundGroup.WOOD).build()), new Identifier(name.getNamespace(), "stripped_" + name.getPath() + "_log"),
                    ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder strippedWood() {
            woodRegistry.strippedWood = RegistryUtils.register(new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).hardness(2.0F)
                    .sounds(BlockSoundGroup.WOOD).build()), new Identifier(name.getNamespace(), "stripped_" + name.getPath() + "_wood"),
                    ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder stairs() {
            woodRegistry.stairs = RegistryUtils.register(new StairsBaseBlock(woodRegistry.planks.getDefaultState()), new Identifier(name.getNamespace(),
                            name.getPath() + "_stairs"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder slab() {
            woodRegistry.slab = RegistryUtils.register(new SlabBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_slab"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder planks() {
            woodRegistry.planks = RegistryUtils.register(new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
                    .strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_planks"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder leaves() {
            woodRegistry.leaves = RegistryUtils.register(new LeavesBaseBlock(), new Identifier(name.getNamespace(), name.getPath() + "_leaves"),
                    ItemGroup.DECORATIONS);
            return this;
        }

        public Builder leaves(String nameIn) {
            woodRegistry.leaves = RegistryUtils.register(new LeavesBaseBlock(), new Identifier(name.getNamespace(), nameIn + "_leaves"),
                    ItemGroup.DECORATIONS);
            return this;
        }

        public Builder coloredLeaves() {
            woodRegistry.leaves = RegistryUtils.register(new LeavesBaseBlock(), new Identifier(name.getNamespace(), name.getPath() + "_leaves"),
                    ItemGroup.DECORATIONS);
            if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                ColorProviderRegistry.BLOCK.register((block, world, pos, layer) -> {
                    BlockColorProvider provider = ColorProviderRegistry.BLOCK.get(Blocks.OAK_LEAVES);
                    return provider == null ? -1 : provider.getColor(block, world, pos, layer);
                }, woodRegistry.leaves);
                ColorProviderRegistry.ITEM.register((item, layer) -> {
                    ItemColorProvider provider = ColorProviderRegistry.ITEM.get(Blocks.OAK_LEAVES);
                    return provider == null ? -1 : provider.getColor(item, layer);
                }, woodRegistry.leaves);
            }
            return this;
        }

        public Builder sapling() {
            woodRegistry.sapling = RegistryUtils.register(new SaplingBaseBlock(woodRegistry.saplingGenerator),
                    new Identifier(name.getNamespace(), name.getPath() + "_sapling"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder fence() {
            woodRegistry.fence = RegistryUtils.register(new FenceBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_fence"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder fenceGate() {
            woodRegistry.fenceGate = RegistryUtils.register(new FenceGateBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_fence_gate"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder bookshelf() {
            woodRegistry.bookshelf = RegistryUtils.register(new Block(Block.Settings.copy(woodRegistry.planks)),
                    new Identifier(name.getNamespace(), name.getPath() + "_bookshelf"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder door() {
            woodRegistry.door = RegistryUtils.register(new DoorBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_door"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder trapdoor() {
            woodRegistry.trapdoor = RegistryUtils.register(new TrapdoorBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_trapdoor"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder button() {
            woodRegistry.button = RegistryUtils.register(new ButtonBaseBlock(true, FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_button"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder pressurePlate(PressurePlateBlock.ActivationRule type) {
            woodRegistry.pressurePlate = RegistryUtils.register(new PressurePlateBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build(), type),
                    new Identifier(name.getNamespace(), name.getPath() + "_pressure_plate"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder ladder() {
            woodRegistry.ladder = RegistryUtils.register(new CustomLadderBlock(), new Identifier(name.getNamespace(),
                    name.getPath() + "_ladder"), ItemGroup.DECORATIONS);
            return this;
        }

        public WoodRegistry build() {
            return woodRegistry;
        }
    }

}