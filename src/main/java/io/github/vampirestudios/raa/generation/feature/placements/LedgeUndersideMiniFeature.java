package io.github.vampirestudios.raa.generation.feature.placements;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.feature.config.ChanceAndTypeConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.decorator.Decorator;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class LedgeUndersideMiniFeature extends Decorator<ChanceAndTypeConfig> {

	public LedgeUndersideMiniFeature(Function<Dynamic<?>, ? extends ChanceAndTypeConfig> configFactory) {
		super(configFactory);
	}

	@Override
	public Stream<BlockPos> getPositions(WorldAccess world, ChunkGenerator chunkGenerator, Random random, ChanceAndTypeConfig placementConfig, BlockPos pos) {
		float chance;

		//hacky workaround as biome/feature registration happens at MC startup before the config file is loaded in a world. 
		//We have to read the config file here as placement is being found to have the config file be read in real time.
		if (placementConfig.type == ChanceAndTypeConfig.Type.HANGING_RUINS) {
			chance = (int) (40 * placementConfig.chanceModifier);
		} else {
			chance = 0;
		}

		//more logical to do chance like this as this feature does not need to have chances less than 1% while other features/structures do
		//We do it based on percentage
		if (random.nextFloat() * 100 < chance)
		{
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int yPosOfSurface = YPositionOfBottomOfLayer(world, pos.add(x, random.nextInt(174) + 74, z));

			//cannot be too low or high 
			if (yPosOfSurface < 75 || yPosOfSurface > 248)
			{
				return Stream.empty();
			}

			return Stream.of(pos.add(x - 4, yPosOfSurface - 1, z - 4));
		}
		else
		{
			return Stream.empty();
		}

	}


	private int YPositionOfBottomOfLayer(WorldAccess world, BlockPos pos) {
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());

		// if height is inside a non-air block, move up until we reached an air block
		while (blockpos$Mutable.getY() < 255) {
			if (world.isAir(blockpos$Mutable)) {
				break;
			}
			blockpos$Mutable.move(Direction.UP);
		}

		// if height is an air block, move up until we reached a solid block. We are now
		// on the bottom of a piece of land
		while (blockpos$Mutable.getY() < 255) {
			if (!world.isAir(blockpos$Mutable)) {
				break;
			}

			blockpos$Mutable.move(Direction.UP);
		}

		return Math.min(blockpos$Mutable.getY(), 255);
	}
}