package io.github.vampirestudios.raa.generation.carvers;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

//Carver abstraction not place lava in floating island dimensions
public abstract class RAACarver<C extends CarverConfig> extends Carver<C> {
    private final DimensionData data;

    public RAACarver(Function<Dynamic<?>, ? extends C> configDeserializer, DimensionData data) {
        super(configDeserializer, 256);
        this.data = data;
        this.alwaysCarvableBlocks = ImmutableSet.of(Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, data.getName().toLowerCase() + "_stone")),
                Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
                Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM,
                Blocks.SNOW, Blocks.PACKED_ICE, Blocks.RED_SAND);
    }

    @Override
    protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> posBiomeFunction, BitSet bitSet, Random random, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int mainChunkX, int mainChunkZ, int i, int j, int k, int l, int m, int n, AtomicBoolean atomicBoolean) {
        int i1 = l | n << 4 | m << 8;
        if (bitSet.get(i1)) {
            return false;
        } else {
            bitSet.set(i1);
            mutable.set(j, m, k);
            BlockState blockState = chunk.getBlockState(mutable);
            BlockState upState = chunk.getBlockState(mutable2.set(mutable).offset(Direction.UP));
            if (blockState.getBlock() == Blocks.GRASS_BLOCK || blockState.getBlock() == Blocks.MYCELIUM) {
                atomicBoolean.set(true);
            }

            if (!this.canCarveBlock(blockState, upState)) {
                return false;
            } else {
                if (m < 11) {
                    DimensionChunkGenerators generator = data.getDimensionChunkGenerator();
                    if (generator == DimensionChunkGenerators.FLOATING || generator == DimensionChunkGenerators.PRE_CLASSIC_FLOATING || generator == DimensionChunkGenerators.LAYERED_FLOATING)
                        return true;
                    chunk.setBlockState(mutable, LAVA.getBlockState(), false);
                } else {
                    chunk.setBlockState(mutable, CAVE_AIR, false);
                    if (atomicBoolean.get()) {
                        mutable3.set(mutable).offset(Direction.DOWN);
                        if (chunk.getBlockState(mutable3).getBlock() == Blocks.DIRT) {
                            chunk.setBlockState(mutable3, posBiomeFunction.apply(mutable).getSurfaceConfig().getTopMaterial(), false);
                        }
                    }
                }

                return true;
            }
        }
    }
}
