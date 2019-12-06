package io.github.vampirestudios.raa.generation.feature.cave;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.utils.BetterCavesConfig;
import io.github.vampirestudios.raa.utils.CaveType;
import io.github.vampirestudios.raa.utils.noise.FastNoise;
import io.github.vampirestudios.raa.utils.noise.NoiseGen;
import io.github.vampirestudios.raa.utils.noise.NoiseTuple;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.Chunk;

import java.util.List;
import java.util.Map;

/**
 * Class for generation of Better Caves caves.
 */
public class CaveBC extends AbstractBC {
    private NoiseGen noiseGen;
    private BlockState AIR = Blocks.AIR.getDefaultState();
    private BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

    public CaveBC(DimensionData dimensionData, long seed, CaveType caveType, int fOctaves, float fGain, float fFreq, int numGens, float threshold, int tOctaves,
                  float tGain, float tFreq, boolean enableTurbulence, double yComp, double xzComp, boolean yAdj,
                  float yAdjF1, float yAdjF2, BlockState vBlock) {
        super(seed, fOctaves, fGain, fFreq, numGens, threshold, tOctaves, tGain, tFreq, enableTurbulence, yComp,
                xzComp, yAdj, yAdjF1, yAdjF2, vBlock);
        this.alwaysCarvableBlocks = ImmutableSet.of(Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase() + "_stone")),
                Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
                Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM,
                Blocks.SNOW, Blocks.PACKED_ICE);

        // Determine noise to use based on cave type
        switch (caveType) {
            case CUBIC:
                this.noiseType = FastNoise.NoiseType.CubicFractal;
                break;
            default:
            case SIMPLEX:
                this.noiseType = FastNoise.NoiseType.SimplexFractal;
                break;
        }

        noiseGen = new NoiseGen(
                this.noiseType,
                seed,
                this.fractalOctaves,
                this.fractalGain,
                this.fractalFreq,
                this.turbOctaves,
                this.turbGain,
                this.turbFreq,
                this.enableTurbulence,
                this.yCompression,
                this.xzCompression
        );
    }

    @Override
    public void generateColumn(int chunkX, int chunkZ, Chunk chunkIn, int localX, int localZ, int bottomY,
                               int topY, int maxSurfaceHeight, int minSurfaceHeight, int surfaceCutoff, BlockState lavaBlock) {
        // Validate vars
        if (localX < 0 || localX > 15)
            return;
        if (localZ < 0 || localZ > 15)
            return;
        if (bottomY < 0)
            return;
        if (topY > 255)
            return;

        // Altitude at which caves start closing off so they aren't all open to the surface
        int transitionBoundary = maxSurfaceHeight - surfaceCutoff;

        // Validate transition boundary
        if (transitionBoundary < 1)
            transitionBoundary = 1;

        // Generate noise for caves.
        // The noise for an individual block is represented by a NoiseTuple, which is essentially an n-tuple of
        // floats, where n is equal to the number of generators passed to the function
        Map<Integer, NoiseTuple> noises =
                noiseGen.generateNoiseCol(chunkX, chunkZ, bottomY, topY, this.numGens, localX, localZ);

        // Pre-compute thresholds to ensure accuracy during pre-processing
        Map<Integer, Float> thresholds = generateThresholds(topY, bottomY, transitionBoundary);

        // Do some pre-processing on the noises to facilitate better cave generation.
        // Basically this makes caves taller to give players more headroom.
        // See the javadoc for the function for more info.
        if (this.enableYAdjust)
            preprocessCaveNoiseCol(noises, topY, bottomY, thresholds, this.numGens);

        /* =============== Dig out caves and caverns in this column, based on noise values =============== */
        for (int realY = topY; realY >= bottomY; realY--) {
            List<Float> noiseBlock = noises.get(realY).getNoiseValues();
            boolean digBlock = true;

            for (float noise : noiseBlock) {
                if (noise < thresholds.get(realY)) {
                    digBlock = false;
                    break;
                }
            }

            // Consider digging out the block if it passed the threshold check, using the debug visualizer if enabled
            if (BetterCavesConfig.enableDebugVisualizer)
                visualizeDigBlock(digBlock, this.vBlock, chunkIn, localX, realY, localZ);
            else if (digBlock)
                this.digBlock(chunkIn, lavaBlock, chunkX, chunkZ, localX, localZ, realY);
        }

        /* ============ Post-Processing to remove any singular floating blocks in the ease-in range ============ */
        for (int realY = transitionBoundary + 1; realY < topY; realY++) {
            // Validate y value
            if (realY < 1 || realY > 255)
                break;

            BlockState currBlock = chunkIn.getBlockState(new BlockPos(localX, realY, localZ));

            if (canCarveBlock(currBlock, AIR)
                    && (chunkIn.getBlockState(new BlockPos(localX, realY + 1, localZ)) == AIR || chunkIn.getBlockState(new BlockPos(localX, realY + 1, localZ)) == CAVE_AIR)
                    && (chunkIn.getBlockState(new BlockPos(localX, realY - 1, localZ)) == AIR || chunkIn.getBlockState(new BlockPos(localX, realY - 1, localZ)) == CAVE_AIR)
            )
                this.digBlock(chunkIn, lavaBlock, chunkX, chunkZ, localX, localZ, realY);
        }
    }
}