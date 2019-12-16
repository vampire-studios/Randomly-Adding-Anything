package io.github.vampirestudios.raa.api.dimension;

import io.github.vampirestudios.raa.generation.chunkgenerator.config.CustomOverworldChunkGeneratorConfig;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.ChunkGenerators;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

public enum DimensionChunkGenerators {
    OVERWORLD,
    QUADRUPLE_AMPLIFIED,
    PILLAR_WORLD,
    CUSTOM_OVERWORLD,

    FLOATING,
    LAYERED_FLOATING,
    PRE_CLASSIC_FLOATING,

    CAVE,
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

        if (this == CAVE) return ChunkGenerators.CAVES.create(world, biomeSource, caveConfig);
        if (this == FLAT_CAVES) return ChunkGenerators.FLAT_CAVES.create(world, biomeSource, caveConfig);
        if (this == HIGH_CAVES) return ChunkGenerators.HIGH_CAVES.create(world, biomeSource, caveConfig);

        if (this == FLOATING) return ChunkGenerators.FLOATING_ISLANDS.create(world, biomeSource, floatingConfig);
        if (this == LAYERED_FLOATING)
            return ChunkGenerators.LAYERED_FLOATING.create(world, biomeSource, floatingConfig);
        if (this == PRE_CLASSIC_FLOATING)
            return ChunkGenerators.PRECLASSIC_FLOATING.create(world, biomeSource, floatingConfig);

        if (this == QUADRUPLE_AMPLIFIED) return ChunkGenerators.QUADRUPLE_AMPLIFIED.create(world, biomeSource, config);
        if (this == PILLAR_WORLD) return ChunkGenerators.PILLAR_WORLD.create(world, biomeSource, config);

        CustomOverworldChunkGeneratorConfig customConfig = new CustomOverworldChunkGeneratorConfig();
        if (Utils.checkBitFlag(data.getFlags(), Utils.MOLTEN))
            customConfig.setDefaultFluid(Blocks.LAVA.getDefaultState());
        if (Utils.checkBitFlag(data.getFlags(), Utils.DRY)) customConfig.setDefaultFluid(Blocks.AIR.getDefaultState());
        customConfig.setDefaultBlock(stoneBlock.getDefaultState());
        customConfig.shouldSacrificeAccuracyForSpeed(true);
        customConfig.shouldAddDetailNoise(true);
        customConfig.setBaseHeight(Rands.randIntRange(60, 140));
        customConfig.setBaseOctaveAmount(Rands.randIntRange(5, 15));
        customConfig.setBiomeScaleAmount(Rands.randIntRange(3, 14));
        customConfig.setBaseHeight(Rands.randIntRange(70, 130));
        if (this == CUSTOM_OVERWORLD && FabricLoader.getInstance().isModLoaded("simplexterrain"))
            return ChunkGenerators.CUSTOM_SURFACE.create(world, biomeSource, customConfig);
        else {
            if (!FabricLoader.getInstance().isModLoaded("simplexterrain")) {
                data.setDimensionChunkGenerator(Rands.values(DimensionChunkGenerators.values()));
                data.getDimensionChunkGenerator().getChunkGenerator(world, biomeSource, data, stoneBlock);
            }
        }

        return ChunkGenerators.SURFACE.create(world, biomeSource, config);
    }

}
