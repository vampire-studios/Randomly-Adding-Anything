package io.github.vampirestudios.raa.generation.feature;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.feature.config.ColumnBlocksConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

// Thanks to TelepathicGrunt and the UltraAmplified mod for this class
public class ColumnRampFeature extends Feature<ColumnBlocksConfig> {
	protected long seed;
	private final BlockState AIR = Blocks.AIR.getDefaultState();
	public final Set<Block> irreplacableBlocks;

	public ColumnRampFeature(Function<Dynamic<?>, ? extends ColumnBlocksConfig> configDeserializer, Function<Random, ? extends ColumnBlocksConfig> function) {
		super(configDeserializer, function);

		irreplacableBlocks = ImmutableSet.of(Blocks.BEE_NEST, Blocks.AIR, Blocks.CAVE_AIR, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM, Blocks.CACTUS);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> changedBlock, Random rand, BlockPos position, ColumnBlocksConfig blocksConfig) {
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), position.getY(), position.getZ());
		int minWidth = 4;
		int currentHeight = 0;
		int ceilingHeight = currentHeight;
		int bottomFloorHeight = currentHeight;
		int topFloorHeight = currentHeight;
		int heightDiff = 0;

		//finds ceiling
		while (!world.getBlockState(blockpos$Mutable.up(currentHeight)).isAir())
		{
			//too high for ramp to generate
			if (blockpos$Mutable.getY() > 254)
			{
				return false;
			}
			blockpos$Mutable.move(Direction.UP, 2);
		}
		ceilingHeight = blockpos$Mutable.getY();

		//finds floor above ceiling
		while (world.getBlockState(blockpos$Mutable.up(currentHeight)).isAir())
		{
			//too high for ramp to generate
			if (blockpos$Mutable.getY() > 254)
			{
				return false;
			}
			blockpos$Mutable.move(Direction.UP);
		}
		topFloorHeight = blockpos$Mutable.up(currentHeight).getY();

		//too thick or thin for ramp to generate
		if (topFloorHeight - ceilingHeight > 7 || topFloorHeight - ceilingHeight < 2)
		{
			return false;
		}

		//find floor
		blockpos$Mutable.set(position); // reset back to normal height
		currentHeight = 0;
		while (!world.getBlockState(blockpos$Mutable).isAir())
		{
			//too low/tall for column to generate
			if (blockpos$Mutable.getY() < 70)
			{
				return false;
			}
			blockpos$Mutable.move(Direction.DOWN, 2);
		}
		bottomFloorHeight = blockpos$Mutable.getY();

		heightDiff = ceilingHeight - bottomFloorHeight;
		if (heightDiff > 27 || heightDiff < 8)
		{
			//too tall or short for a column ramp to spawn
			return false;
		}

		//how much to turn on a range of -1 to 1. -1 for north, 0 for south
		float randFloat = rand.nextFloat();
		float xTurningValue = (float) Math.sin(randFloat * Math.PI * 2);
		float zTurningValue = (float) Math.cos(randFloat * Math.PI * 2);

		//min thickness   where we are in height  /  controls thickening rate 
		int widthAtHeight = getWidthAtHeight(0, heightDiff + 5, minWidth);

		//gets center of the ceiling position and floor position
		int xPosCeiling = position.getX() + getOffsetAtHeight(heightDiff + 1, heightDiff, xTurningValue);
		int zPosCeiling = position.getZ() + getOffsetAtHeight(0, heightDiff, zTurningValue);
		int xPosFloor = position.getX() - getOffsetAtHeight(heightDiff - 1, heightDiff, xTurningValue);
		int zPosFloor = position.getZ() + getOffsetAtHeight(0, heightDiff, zTurningValue);

		//checks to see if there is enough land above and below to hold pillar
		for (int x = -widthAtHeight; x <= widthAtHeight; x++)
		{
			for (int z = -widthAtHeight; z <= widthAtHeight; z++)
			{
				if (x * x + z * z > widthAtHeight * widthAtHeight * 0.85 && x * x + z * z < widthAtHeight * widthAtHeight)
				{
					BlockState block1 = world.getBlockState(blockpos$Mutable.set(xPosCeiling + x, ceilingHeight + 2, zPosCeiling + z));
					BlockState block2 = world.getBlockState(blockpos$Mutable.set(xPosFloor + x, bottomFloorHeight - 2, zPosFloor + z));

					//debugging
					//world.setBlockState(blockpos$Mutable.setPos(position.getX() + x + getOffsetAtHeight(heightDiff + 1, heightDiff, xTurningValue), ceilingHeight + 2, position.getZ() + z + getOffsetAtHeight(0, heightDiff, zTurningValue)), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
					//world.setBlockState(blockpos$Mutable.setPos(position.getX() + x - getOffsetAtHeight(-1, heightDiff, xTurningValue), bottomFloorHeight - 2, position.getZ() + z - getOffsetAtHeight(0, heightDiff, zTurningValue)), Blocks.LAPIS_BLOCK.getDefaultState(), 2);

					//there is not enough land to contain bases of ramp
					if (!block1.isAir() || !block2.isAir())
					{
						return false;
					}
				}
			}
		}

		//If this is reached, position is valid for pillar gen.

		//debugging
		//        if(heightDiff > 18) {
		//        	UltraAmplified.LOGGER.info("Large Ramp: "+position.getX()+" "+position.getY()+" "+position.getZ());
		//        }

		int xOffset = 0;
		int zOffset = 0;
		int xDiff = 0;
		int zDiff = 0;

		//clears hole for ramp
		for (int y = -2; y <= heightDiff + 3; y++)
		{
			// Method interprets input as:  min thickness  ,  where we are in height  ,  controls thickening rate 
			widthAtHeight = getWidthAtHeight(y, heightDiff + 2, minWidth);

			if (heightDiff < 16)
			{
				xOffset = (int) (getOffsetAtHeight(y, heightDiff, xTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, xTurningValue) / 2) * 2);
				zOffset = (int) (getOffsetAtHeight(y, heightDiff, zTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, zTurningValue) / 2) * 2);
			}
			else if (heightDiff < 21)
			{
				xOffset = (int) (getOffsetAtHeight(y, heightDiff, xTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, xTurningValue) / 3) * 4);
				zOffset = (int) (getOffsetAtHeight(y, heightDiff, zTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, zTurningValue) / 3) * 4);
			}
			else
			{
				xOffset = (int) (getOffsetAtHeight(y, heightDiff, xTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, xTurningValue) / 3) * 6);
				zOffset = (int) (getOffsetAtHeight(y, heightDiff, zTurningValue) - Math.signum(getOffsetAtHeight(y, heightDiff, zTurningValue) / 3) * 6);
			}

			//Begin clearing gen
			for (int x = position.getX() - widthAtHeight - 1; x <= position.getX() + widthAtHeight + 1; ++x)
			{
				for (int z = position.getZ() - widthAtHeight - 1; z <= position.getZ() + widthAtHeight + 1; ++z)
				{
					xDiff = x - position.getX();
					zDiff = z - position.getZ();
					blockpos$Mutable.set(x + xOffset, y + bottomFloorHeight + 3, z + zOffset);

					//creates pillar with inside block
					int xzDiffSquaredStretched = (xDiff * xDiff) + (zDiff * zDiff);
					int circleBounds = (int) ((widthAtHeight - 1) * (widthAtHeight - 1) - 0.5F);

					if (y > heightDiff)
					{
						circleBounds *= (0.6f / (y - heightDiff));
					}

					BlockState block = world.getBlockState(blockpos$Mutable);
					if (!block.isIn(BlockTags.LEAVES) && !block.isIn(BlockTags.LOGS) && !irreplacableBlocks.contains(block.getBlock()) && xzDiffSquaredStretched <= circleBounds)
					{
						world.setBlockState(blockpos$Mutable, AIR, 2);

						//adds top block to exposed middle block after air was set
						BlockState blockBelowAir = world.getBlockState(blockpos$Mutable.down());
						BlockState blockBelowBelowAir = world.getBlockState(blockpos$Mutable.down(2));
						if (blockBelowAir.isAir())
						{
							if (blocksConfig.topBlock.getMaterial() == Material.SAND && blockBelowBelowAir.getMaterial() == Material.AIR)
							{
								world.setBlockState(blockpos$Mutable.down(), blocksConfig.middleBlock, 2);
							}
							else
							{
								world.setBlockState(blockpos$Mutable.down(), blocksConfig.topBlock, 2);
							}

						}
					}
				}
			}
		}

		//makes ramp
		for (int y = -2; y <= heightDiff + 4; y++)
		{
			// Method interprets input as:  min thickness  ,  where we are in height  ,  controls thickening rate
			widthAtHeight = getWidthAtHeight(y, heightDiff + 5, minWidth);
			xOffset = getOffsetAtHeight(y, heightDiff, xTurningValue);
			zOffset = getOffsetAtHeight(y, heightDiff, zTurningValue);

			//Begin column gen
			for (int x = position.getX() - widthAtHeight - 1; x <= position.getX() + widthAtHeight + 1; ++x)
			{
				for (int z = position.getZ() - widthAtHeight - 1; z <= position.getZ() + widthAtHeight + 1; ++z)
				{
					xDiff = x - position.getX();
					zDiff = z - position.getZ();
					blockpos$Mutable.set(x + xOffset, y + bottomFloorHeight, z + zOffset);

					//creates pillar with inside block
					int xzDiffSquaredStretched = (xDiff * xDiff) + (zDiff * zDiff);
					int circleBounds = (int) ((widthAtHeight - 1) * (widthAtHeight - 1) - 0.5F);

					if (y > heightDiff - 3)
					{
						circleBounds *= (0.8f / (y - (heightDiff - 3)));
					}

					if (y <= heightDiff && xzDiffSquaredStretched <= circleBounds)
					{
						if (!world.getBlockState(blockpos$Mutable).isAir())
						{
							world.setBlockState(blockpos$Mutable, blocksConfig.insideBlock, 2);
						}
					}
					//We are at non-pillar space 
					//adds top and middle block to pillar part exposed in the below half of pillar
					else if (y > heightDiff || xzDiffSquaredStretched <= (widthAtHeight + 3) * (widthAtHeight + 3))
					{
						//top block followed by 4 middle blocks below that
						for (int downward = 0; downward < 6 && y - downward >= -3; downward++)
						{
							BlockState block = world.getBlockState(blockpos$Mutable.down(downward));
							BlockState blockBelow = world.getBlockState(blockpos$Mutable.down(downward + 1));
							if (block == blocksConfig.insideBlock)
							{
								if (downward == 1 && !(blocksConfig.topBlock.getMaterial() == Material.SAND && blockBelow.getMaterial() == Material.AIR))
								{
									world.setBlockState(blockpos$Mutable.down(downward), blocksConfig.topBlock, 2);
								}
								else
								{
									world.setBlockState(blockpos$Mutable.down(downward), blocksConfig.middleBlock, 2);
								}

							}
						}
					}
				}
			}
		}

		return true;
	}


	private int getWidthAtHeight(int y, int heightDiff, int thinnestWidth)
	{
		float yFromCenter = y - heightDiff * 0.5F;
		yFromCenter = Math.abs(yFromCenter * 0.4F) + 3;

		return thinnestWidth + (int) ((yFromCenter * yFromCenter) / 8);
	}


	private int getOffsetAtHeight(int y, int heightDiff, float turningValue)
	{
		float yFromCenter = y - heightDiff / 2F;
		return (int) (turningValue * yFromCenter);
	}
}