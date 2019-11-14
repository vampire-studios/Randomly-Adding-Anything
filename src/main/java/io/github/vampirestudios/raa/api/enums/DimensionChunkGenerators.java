package io.github.vampirestudios.raa.api.enums;

import io.github.vampirestudios.raa.api.interfaces.DimensionChunkGenerator;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.ChunkGenerators;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.*;

public enum DimensionChunkGenerators implements DimensionChunkGenerator {
    CAVE,
    FLOATING,
    OVERWORLD,
    LAYEREDFLOATING,
    PRECLASSICFLOATING,
    FLATCAVES,
    HIGHCAVES;

    @Override
    public ChunkGenerator<?> getChunkGenerator(World world, BiomeSource biomeSource, DimensionData data, Block stoneBlock) {

        CavesChunkGeneratorConfig caveConfig = new CavesChunkGeneratorConfig();
        caveConfig.setDefaultBlock(stoneBlock.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN)) caveConfig.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) caveConfig.setDefaultFluid(Blocks.AIR.getDefaultState());
        if (this == CAVE) return ChunkGeneratorType.CAVES.create(world, biomeSource, caveConfig);

        CavesChunkGeneratorConfig caveConfig2 = new CavesChunkGeneratorConfig();
        caveConfig2.setDefaultBlock(stoneBlock.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN)) caveConfig2.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) caveConfig2.setDefaultFluid(Blocks.AIR.getDefaultState());
        if (this == FLATCAVES) return ChunkGenerators.FLAT_CAVES.create(world, biomeSource, caveConfig2);

        CavesChunkGeneratorConfig caveConfig3 = new CavesChunkGeneratorConfig();
        caveConfig3.setDefaultBlock(stoneBlock.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN)) caveConfig3.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) caveConfig3.setDefaultFluid(Blocks.AIR.getDefaultState());
        if (this == HIGHCAVES) return ChunkGenerators.HIGH_CAVES.create(world, biomeSource, caveConfig3);

        FloatingIslandsChunkGeneratorConfig floatingConfig = new FloatingIslandsChunkGeneratorConfig();
        floatingConfig.setDefaultBlock(stoneBlock.getDefaultState());
        if (this == FLOATING) return ChunkGeneratorType.FLOATING_ISLANDS.create(world, biomeSource, floatingConfig);

        FloatingIslandsChunkGeneratorConfig floatingConfig2 = new FloatingIslandsChunkGeneratorConfig();
        floatingConfig2.setDefaultBlock(stoneBlock.getDefaultState());
        if (this == LAYEREDFLOATING) return ChunkGenerators.LAYERED_FLOATING.create(world, biomeSource, floatingConfig2);

        FloatingIslandsChunkGeneratorConfig floatingConfig3 = new FloatingIslandsChunkGeneratorConfig();
        floatingConfig3.setDefaultBlock(stoneBlock.getDefaultState());
        if (this == PRECLASSICFLOATING) return ChunkGenerators.PRECLASSIC_FLOATING.create(world, biomeSource, floatingConfig3);

        OverworldChunkGeneratorConfig config = new OverworldChunkGeneratorConfig();
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN)) config.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) config.setDefaultFluid(Blocks.AIR.getDefaultState());
        config.setDefaultBlock(stoneBlock.getDefaultState());
        return ChunkGeneratorType.SURFACE.create(world, biomeSource, config);

    }
}
