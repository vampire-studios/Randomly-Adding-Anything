package io.github.vampirestudios.raa.api.dimension;

import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.ChunkGenerators;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.*;

public enum DimensionChunkGenerators {
    CAVE,
    FLOATING,
    OVERWORLD,
    QUADRUPLE_AMPLIFIED,
    PILLAR_WORLD,
    LAYERED_FLOATING,
    PRE_CLASSIC_FLOATING,
    FLAT_CAVES,
    HIGH_CAVES;

    public ChunkGenerator<?> getChunkGenerator(World world, BiomeSource biomeSource, DimensionData data, Block stoneBlock) {

        OverworldChunkGeneratorConfig config = new OverworldChunkGeneratorConfig();
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN)) config.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) config.setDefaultFluid(Blocks.AIR.getDefaultState());
        config.setDefaultBlock(stoneBlock.getDefaultState());

        CavesChunkGeneratorConfig caveConfig = new CavesChunkGeneratorConfig();
        caveConfig.setDefaultBlock(stoneBlock.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN))
            caveConfig.setDefaultFluid(Blocks.LAVA.getDefaultState());

        FloatingIslandsChunkGeneratorConfig floatingConfig = new FloatingIslandsChunkGeneratorConfig();
        floatingConfig.setDefaultBlock(stoneBlock.getDefaultState());

        if (this == CAVE) return ChunkGeneratorType.CAVES.create(world, biomeSource, caveConfig);
        if (this == FLAT_CAVES) return ChunkGenerators.FLAT_CAVES.create(world, biomeSource, caveConfig);
        if (this == HIGH_CAVES) return ChunkGenerators.HIGH_CAVES.create(world, biomeSource, caveConfig);
        if (this == FLOATING) return ChunkGeneratorType.FLOATING_ISLANDS.create(world, biomeSource, floatingConfig);
        if (this == LAYERED_FLOATING)
            return ChunkGenerators.LAYERED_FLOATING.create(world, biomeSource, floatingConfig);
        if (this == PRE_CLASSIC_FLOATING)
            return ChunkGenerators.PRECLASSIC_FLOATING.create(world, biomeSource, floatingConfig);
        if (this == QUADRUPLE_AMPLIFIED) return ChunkGenerators.QUADRUPLE_AMPLIFIED.create(world, biomeSource, config);
        if (this == PILLAR_WORLD) return ChunkGenerators.PILLAR_WORLD.create(world, biomeSource, config);

        return ChunkGeneratorType.SURFACE.create(world, biomeSource, config);


    }

}
