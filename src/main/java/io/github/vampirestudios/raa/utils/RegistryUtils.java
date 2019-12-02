/*
 * MIT License
 *
 * Copyright (c) 2019 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.RAARegistery;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.items.RAABlockItem;
import io.github.vampirestudios.raa.items.RAABlockItemAlt;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.Builder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class RegistryUtils {

    public static Block register(Block block, Identifier name, ItemGroup itemGroup, String upperCaseName, RAABlockItem.BlockType blockType) {
        Registry.register(Registry.BLOCK, name, block);
        BlockItem item = new RAABlockItem(upperCaseName, block, (new Settings()).group(itemGroup), blockType);
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        Registry.register(Registry.ITEM, name, item);
        return block;
    }

    public static Block register(Block block, Identifier name, ItemGroup itemGroup, String upperCaseName, String type) {
        Registry.register(Registry.BLOCK, name, block);
        BlockItem item = new RAABlockItemAlt(upperCaseName, type, block, (new Settings()).group(itemGroup));
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        Registry.register(Registry.ITEM, name, item);
        return block;
    }

    public static Block register(Block block, Identifier name, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, name, block);
        BlockItem item = new BlockItem(block, new Settings().group(itemGroup));
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        Registry.register(Registry.ITEM, name, item);
        return block;
    }

    public static Block registerBlockWithoutItem(Block block, Identifier identifier) {
        Registry.register(Registry.BLOCK, identifier, block);
        return block;
    }

    public static void registerBiome(Identifier name, Biome biome) {
        Registry.register(Registry.BIOME, name, biome);
    }

    public static void forEveryBiome(Consumer<Biome> biomes) {
        Registry.BIOME.forEach(biomes);
        RegistryEntryAddedCallback.event(Registry.BIOME).register((rawId, id, biome) -> biomes.accept(biome));
    }

    public static void forEveryFeature(Consumer<Feature<?>> features) {
        Registry.FEATURE.forEach(features);
        RegistryEntryAddedCallback.event(Registry.FEATURE).register((rawId, id, feature) -> features.accept(feature));
    }

    public static Item registerItem(Item item, Identifier name) {
        if (Registry.ITEM.get(name) == Items.AIR) {
            return Registry.register(Registry.ITEM, name, item);
        } else {
            return item;
        }
    }

    public static GeneratesIn registerGeneratesIn(Identifier name, GeneratesIn generatesIn) {
        if (RAARegistery.GENERATES_IN_REGISTRY.get(name) == null) {
            return Registry.register(RAARegistery.GENERATES_IN_REGISTRY, name, generatesIn);
        } else {
            return generatesIn;
        }
    }

    public static GeneratesIn registerGeneratesIn(String name, GeneratesIn generatesIn) {
        Identifier identifier = new Identifier(RandomlyAddingAnything.MOD_ID, name);
        if (RAARegistery.GENERATES_IN_REGISTRY.get(identifier) == null) {
            return Registry.register(RAARegistery.GENERATES_IN_REGISTRY, identifier, generatesIn);
        } else {
            return generatesIn;
        }
    }

    public static GeneratesIn registerGeneratesIn(Identifier name, Block block, OreFeatureConfig.Target target) {
        if (RAARegistery.GENERATES_IN_REGISTRY.get(name) == null) {
            return Registry.register(RAARegistery.GENERATES_IN_REGISTRY, name, new GeneratesIn(name, block, target));
        } else {
            return new GeneratesIn(name, block, target);
        }
    }

    public static GeneratesIn registerGeneratesIn(String name, Block block, OreFeatureConfig.Target target) {
        Identifier identifier = new Identifier(RandomlyAddingAnything.MOD_ID, name);
        if (RAARegistery.GENERATES_IN_REGISTRY.get(identifier) == null) {
            return Registry.register(RAARegistery.GENERATES_IN_REGISTRY, identifier, new GeneratesIn(identifier, block, target));
        } else {
            return new GeneratesIn(identifier, block, target);
        }
    }

    public static OreFeatureConfig.Target registerOreTarget(String name, OreFeatureConfig.Target target) {
        Identifier identifier = new Identifier(RandomlyAddingAnything.MOD_ID, name);
        if (RAARegistery.TARGET_REGISTRY.get(identifier) == null) {
            return Registry.register(RAARegistery.TARGET_REGISTRY, identifier, target);
        } else {
            return target;
        }
    }

    public static OreFeatureConfig.Target registerOreTarget(String name, Predicate<BlockState> blockStatePredicate) {
        OreFeatureConfig.Target target = new OreFeatureConfig.Target(name, blockStatePredicate);
        Identifier identifier = new Identifier(RandomlyAddingAnything.MOD_ID, target.getName());
        if (RAARegistery.TARGET_REGISTRY.get(identifier) == null) {
            return Registry.register(RAARegistery.TARGET_REGISTRY, identifier, target);
        } else {
            return target;
        }
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(Builder<T> builder, Identifier name) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        Registry.register(Registry.BLOCK_ENTITY, name, blockEntityType);
        return blockEntityType;
    }
}