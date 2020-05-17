package io.github.vampirestudios.raa.api.dimension;

import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.registries.ChunkGenerators;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.class_5284;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

public enum DimensionChunkGenerators {
    OVERWORLD,
    QUADRUPLE_AMPLIFIED,
    PILLAR_WORLD,
    LAYERED_OVERWORLD,
    SMOOTH_OVERWORLD,
    CHAOS,
    ROLLING_HILLS,

    FLOATING,
    LAYERED_FLOATING,
    PRE_CLASSIC_FLOATING,

    CAVES,
    FLAT_CAVES,
    HIGH_CAVES,

    TOTALLY_CUSTOM,

    TEST;

    public ChunkGenerator getChunkGenerator(World world, BiomeSource biomeSource, DimensionData data, Block stoneBlock) {
        OverworldChunkGeneratorConfig config = new OverworldChunkGeneratorConfig();
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN)) config.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) config.setDefaultFluid(Blocks.AIR.getDefaultState());
        config.setDefaultBlock(stoneBlock.getDefaultState());

        CavesChunkGeneratorConfig caveConfig = new CavesChunkGeneratorConfig(new ChunkGeneratorConfig()) { //set the bedrock ceiling y to 256
            @Override
            public int getBedrockCeilingY() {
                return 255;
            }
        };
        caveConfig.setDefaultBlock(stoneBlock.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN))
            caveConfig.setDefaultFluid(Blocks.LAVA.getDefaultState());

        class_5284 floatingConfig = new class_5284(new ChunkGeneratorConfig());
        floatingConfig.setDefaultBlock(stoneBlock.getDefaultState());

        if (this == CAVES) return ChunkGenerators.CAVES.create(world, biomeSource, caveConfig);
        if (this == FLAT_CAVES) return ChunkGenerators.FLAT_CAVES.create(world, biomeSource, caveConfig);
        if (this == HIGH_CAVES) return ChunkGenerators.HIGH_CAVES.create(world, biomeSource, caveConfig);

//        if (this == FLOATING) return ChunkGenerators.FLOATING_ISLANDS.create(world, biomeSource, floatingConfig);
        if (this == LAYERED_FLOATING)
            return ChunkGenerators.LAYERED_FLOATING.create(world, biomeSource, floatingConfig);
        if (this == FLOATING)
            return ChunkGenerators.LAYERED_FLOATING.create(world, biomeSource, floatingConfig);
        if (this == PRE_CLASSIC_FLOATING)
            return ChunkGenerators.PRE_CLASSIC_FLOATING.create(world, biomeSource, floatingConfig);

        if (this == QUADRUPLE_AMPLIFIED) return ChunkGenerators.QUADRUPLE_AMPLIFIED.create(world, biomeSource, config);
        if (this == PILLAR_WORLD) return ChunkGenerators.PILLAR_WORLD.create(world, biomeSource, config);

        if (this == SMOOTH_OVERWORLD) return ChunkGenerators.SMOOTH.create(world, biomeSource, config);
        if (this == TOTALLY_CUSTOM) return ChunkGenerators.TOTALLY_CUSTOM.create(world, biomeSource, config);
        if (this == LAYERED_OVERWORLD) return ChunkGenerators.LAYERED_OVERWORLD.create(world, biomeSource, config);
        if (this == CHAOS) return ChunkGenerators.CHAOS.create(world, biomeSource, config);
        if (this == ROLLING_HILLS) return ChunkGenerators.ROLLING_HILLS.create(world, biomeSource, config);

        if (this == TEST) return ChunkGenerators.TEST.create(world, biomeSource, config);

        return ChunkGenerators.SURFACE.create(world, biomeSource, config);
    }

}
