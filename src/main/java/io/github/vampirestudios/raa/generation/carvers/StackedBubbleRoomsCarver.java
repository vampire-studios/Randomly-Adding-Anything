package io.github.vampirestudios.raa.generation.carvers;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

// Thanks to TelepathicGrunt for this class
public class StackedBubbleRoomsCarver extends CaveCarver {

    public StackedBubbleRoomsCarver(DimensionData dimensionData) {
        super(ProbabilityConfig::deserialize, 256); // The 256 is the maximum height that this carver can cave to
        this.alwaysCarvableBlocks = ImmutableSet.of(Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase() + "_stone")),
                Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
                Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM,
                Blocks.SNOW, Blocks.PACKED_ICE);
    }


    /**
     * Height of the caves Can be randomized with random parameter
     */
    @Override
    protected int getCaveY(Random random) {
        return 100;
    }


    /**
     * This is what calls carveCave and carveTunnel.
     * Here, we are doing just carveCave as we don't need any tunnels. Just the cave room.
     */
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> biomeFunction, Random random, int seaLevel, int xChunk1, int zChunk1, int xChunk2, int zChunk2,
                         BitSet caveMask, ProbabilityConfig chanceConfig) {
        int numberOfRooms = 4; // 4 sphere will be carved out.

        for (int roomCount = 0; roomCount < numberOfRooms; ++roomCount) {
            double x = xChunk1 * 16 + random.nextInt(16);   // Uncomment to randomizes spot of each room
            double y = (double) this.getCaveY(random) - roomCount * 20;  // Lowers each room/sphere by 20 blocks so they are stacked
            double z = zChunk1 * 16+ random.nextInt(16);   // Uncomment to randomizes spot of each room

            float caveRadius = 20.0F + random.nextFloat() * 10.0F; // How big the cave sphere is (radius)

            // The 0.5D is multiplied to the radius for the y direction. So this sphere will be squished vertically by half of the full radius.
            this.carveCave(chunk, biomeFunction, random.nextLong(), seaLevel, xChunk2, zChunk2, x, y, z, caveRadius, 0.5D, caveMask);
        }

        return true;
    }


    /**
     * Does the actual carving. Replacing any valid stone with cave air.
     * Though the carver could be customized to place blocks instead which would be interesting.
     */
    @Override
    protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> biomeFunction, BitSet carvingMask, Random random, BlockPos.Mutable posHere,
								   BlockPos.Mutable posAbove, BlockPos.Mutable posBelow, int seaLevel, int xChunk, int zChunk, int globalX, int globalZ, int x, int y, int z,
                                   AtomicBoolean foundSurface) {
        /*
         * Not sure what this specific section is doing.
         * I know this mask is used so other features can find caves space.
         * Used by SeaGrass to generate at cave openings underwater
         */
        int index = x | z << 4 | y << 8;
        if (carvingMask.get(index)) {
            return false;
        }
        carvingMask.set(index);


        posHere.set(globalX, y, globalZ);
        BlockState blockState = chunk.getBlockState(posHere);
        BlockState blockStateAbove = chunk.getBlockState(posAbove.set(posHere).move(Direction.UP));
		// Makes sure we aren't carving a non terrain or liquid space
        if (!this.canCarveBlock(blockState, blockStateAbove)) {
            return false;
        }

		// carves air when above lava level
        if (y > 10) {
            chunk.setBlockState(posHere, CAVE_AIR, false);
			// sets lava below lava level
        } else {
            chunk.setBlockState(posHere, LAVA.getBlockState(), false);
        }

        return true;
    }


    /**
     * Used to determine what blocks the carver can carve through.
     * Can be highly customized.
     */
    @Override
    protected boolean canCarveBlock(BlockState blockState, BlockState aboveBlockState) {
        if (blockState.getBlock() == Blocks.BEDROCK)
            return false;

        Material material = blockState.getMaterial();
        Material aboveMaterial = aboveBlockState.getMaterial();
        return (material == Material.STONE || material == Material.EARTH || material == Material.ORGANIC) &&
                material != Material.WATER &&
                material != Material.LAVA &&
                aboveMaterial != Material.WATER &&
                aboveMaterial != Material.LAVA;
    }

}