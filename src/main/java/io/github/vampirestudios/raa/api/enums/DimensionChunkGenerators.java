package io.github.vampirestudios.raa.api.enums;

import io.github.vampirestudios.raa.api.interfaces.DimensionChunkGenerator;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.*;

import java.lang.reflect.InvocationTargetException;

public enum DimensionChunkGenerators implements DimensionChunkGenerator {
    CAVE,
    FLOATING,
    OVERWORLD;

    @Override
    public ChunkGenerator<?> getChunkGenerator(World world, BiomeSource biomeSource, DimensionData data, Block stoneBlock) {
        CavesChunkGeneratorConfig caveConfig = new CavesChunkGeneratorConfig();
        caveConfig.setDefaultBlock(stoneBlock.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN)) caveConfig.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) caveConfig.setDefaultFluid(Blocks.AIR.getDefaultState());
        if (this == CAVE) return ChunkGeneratorType.CAVES.create(world, biomeSource, caveConfig);
        if (this == FLOATING) return ChunkGeneratorType.FLOATING_ISLANDS.create(world, biomeSource, new FloatingIslandsChunkGeneratorConfig());
        OverworldChunkGeneratorConfig config = new OverworldChunkGeneratorConfig();
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN)) config.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) config.setDefaultFluid(Blocks.AIR.getDefaultState());
        config.setDefaultBlock(stoneBlock.getDefaultState());
        return ChunkGeneratorType.SURFACE.create(world, biomeSource, config);
    }
}
