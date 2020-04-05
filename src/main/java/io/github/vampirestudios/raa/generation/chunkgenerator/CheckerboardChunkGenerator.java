package io.github.vampirestudios.raa.generation.chunkgenerator;

import io.github.vampirestudios.raa.utils.ColoredBlockArrays;
import net.minecraft.block.Block;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class CheckerboardChunkGenerator extends ChunkGenerator<NoneGeneratorSettings> {

    public CheckerboardChunkGenerator(IWorld iWorld, BiomeSource biomeSource, NoneGeneratorSettings arg) {
        super(iWorld, biomeSource, arg);
    }

    private static int method_26528(int i) {
        return i & Integer.MAX_VALUE;
    }

    public void buildSurface(ChunkRegion region, Chunk chunk) {
    }

    public void generateFeatures(ChunkRegion region) {
    }

    public void carve(BiomeAccess biomeAccess, Chunk chunk, GenerationStep.Carver carver) {
    }

    public int getSpawnHeight() {
        return 100;
    }

    public void populateNoise(IWorld world, StructureAccessor StructureAccessor, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();

        for (int i = 0; i < 8; ++i) {
            int j = method_26528(chunkPos.x) ^ i ^ method_26528(chunkPos.z);
            Block[] blocks = ColoredBlockArrays.ALL[j % ColoredBlockArrays.ALL.length];
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (int k = 0; k < 16; ++k) {
                for (int l = 0; l < 16; ++l) {
                    for (int m = 0; m < 16; ++m) {
                        int n = 16 * i + l;
                        int o = k ^ n ^ m;
                        chunk.setBlockState(mutable.set(k, n, m), blocks[o % blocks.length].getDefaultState(), false);
                    }
                }
            }
        }

    }

    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return 100;
    }

    public BlockView getColumnSample(int x, int z) {
        return EmptyBlockView.INSTANCE;
    }

}