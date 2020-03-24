package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.feature.config.ColumnBlocksConfig;
import io.github.vampirestudios.raa.utils.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

// Thanks to TelepathicGrunt and the UltraAmplified mod for this class
public class ColumnVertical extends Feature<ColumnBlocksConfig> {
	protected OpenSimplexNoise noiseGen;
	protected long seed;


	public void setSeed(long seed)
	{
		if (this.seed != seed || this.noiseGen == null)
		{
			this.noiseGen = new OpenSimplexNoise(seed);
			this.seed = seed;
		}
	}


	public ColumnVertical(Function<Dynamic<?>, ? extends ColumnBlocksConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> changedBlock, Random rand, BlockPos position, ColumnBlocksConfig blocksConfig) {
		setSeed(world.getSeed());
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), position.getY(), position.getZ());
		int minWidth = 3;
		int maxWidth = 10;
		int currentHeight = 0;
		int ceilingHeight = currentHeight;
		int floorHeight = currentHeight;
		int heightDiff = 0;

		//checks to see if position is acceptable for pillar gen
		//finds ceiling
		while (!world.getBlockState(blockpos$Mutable).isAir())
		{
			//too high for column to generate
			if (blockpos$Mutable.getY() > 254)
			{
				return false;
			}
			blockpos$Mutable.move(Direction.UP, 2);
		}
		ceilingHeight = blockpos$Mutable.getY();

		//find floor
		blockpos$Mutable.set(position); // reset back to normal height
		currentHeight = 0;
		while (!world.getBlockState(blockpos$Mutable.up(currentHeight)).isAir())
		{
			//too low for column to generate
			if (blockpos$Mutable.getY() < 50)
			{
				return false;
			}
			blockpos$Mutable.move(Direction.DOWN, 2);
		}
		floorHeight = blockpos$Mutable.getY();

		heightDiff = ceilingHeight - floorHeight;
		if (heightDiff > 100 || heightDiff < 10)
		{
			//too tall or short for a column to spawn
			return false;
		}

		//finds how big the smallest part of column should be
		int thinnestWidth = (int) (maxWidth * ((heightDiff) / 100F));
		if (thinnestWidth < minWidth)
		{
			thinnestWidth = minWidth;
		}

		int widthAtHeight = 0;
		int currentWidth = 0;
		widthAtHeight = getWidthAtHeight(0, heightDiff, thinnestWidth);

		//checks to see if there is enough circular land above and below to hold pillar
		for (int x = position.getX() - widthAtHeight; x <= position.getX() + widthAtHeight; x += 3)
		{
			for (int z = position.getZ() - widthAtHeight; z <= position.getZ() + widthAtHeight; z += 3)
			{
				int xDiff = x - position.getX();
				int zDiff = z - position.getZ();
				if (xDiff * xDiff + zDiff * zDiff <= (widthAtHeight) * (widthAtHeight))
				{
					BlockState block1 = world.getBlockState(blockpos$Mutable.set(x, ceilingHeight + 3, z));
					BlockState block2 = world.getBlockState(blockpos$Mutable.set(x, floorHeight - 2, z));

					//there is not enough land to contain bases of pillar
					if (!block1.isAir() || !block2.isAir())
					{
						return false;
					}
				}
			}
		}

		//position is valid for pillar gen.
		int xMod = 0;
		int zMod = 0;
		//adds perlin noise to the pillar shape to make it more oval
		//larger pillars will be more oval shaped
		boolean flagImperfection1 = rand.nextBoolean();
		boolean flagImperfection2 = rand.nextBoolean();

		if (flagImperfection1 && flagImperfection2)
		{
			xMod = heightDiff / 20 + 1;
			zMod = heightDiff / 20 + 1;
		}
		else if (flagImperfection1)
		{
			xMod = heightDiff / 20 + 1;
			zMod = 0;
		}
		else if (flagImperfection2)
		{
			xMod = 0;
			zMod = heightDiff / 20 + 1;
		}
		else
		{
			xMod = 0;
			zMod = 0;
		}

		//Begin column gen
		for (int y = -2; y <= heightDiff + 2; y++)
		{
			widthAtHeight = getWidthAtHeight(y, heightDiff, thinnestWidth);

			for (int x = position.getX() - widthAtHeight - xMod - 1; x <= position.getX() + widthAtHeight + xMod + 1; ++x)
			{
				for (int z = position.getZ() - widthAtHeight - zMod - 1; z <= position.getZ() + widthAtHeight + zMod + 1; ++z)
				{
					int xDiff = x - position.getX();
					int zDiff = z - position.getZ();
					blockpos$Mutable.set(x, y + floorHeight, z);

					//scratches the surface for more imperfection
					//cut the number of scratches on smallest part of pillar by 4
					boolean flagImperfection3 = this.noiseGen.eval(x * 0.06D, z * 0.6D, y * 0.02D) < 0;
					if (flagImperfection3 && (widthAtHeight > thinnestWidth || (widthAtHeight == thinnestWidth && rand.nextInt(4) == 0)))
					{
						currentWidth = widthAtHeight - 1;
					}
					else
					{
						currentWidth = widthAtHeight;
					}

					//creates pillar with inside block
					int xzDiffSquaredStretched = (xMod + 1) * (xDiff * xDiff) + (zMod + 1) * (zDiff * zDiff);
					int xzDiffSquared = (xDiff * xDiff) + (zDiff * zDiff);
					if (xzDiffSquaredStretched <= (currentWidth - 1) * (currentWidth - 1))
					{
						BlockState block = world.getBlockState(blockpos$Mutable);

						if (!block.isAir())
						{
							world.setBlockState(blockpos$Mutable, blocksConfig.insideBlock, 2);
						}
					}
					//We are at non-pillar space 
					//adds top and middle block to pillar part exposed in the below half of pillar
					else if (y < heightDiff / 2 && xzDiffSquared <= (widthAtHeight + 2) * (widthAtHeight + 2))
					{
						//top block followed by 4 middle blocks below that
						for (int downward = 0; downward < 6 && y - downward >= -3; downward++)
						{
							BlockState block = world.getBlockState(blockpos$Mutable);
							if (block == blocksConfig.insideBlock)
							{
								world.setBlockState(blockpos$Mutable, downward == 1 ? blocksConfig.topBlock : blocksConfig.middleBlock, 2);
							}

							blockpos$Mutable.move(Direction.DOWN); //moves down 1 every loop
						}
					}
				}
			}
		}

		return true;
	}


	private int getWidthAtHeight(int y, int heightDiff, int thinnestWidth)
	{
		if (heightDiff > 80)
		{
			float yFromCenter = Math.abs(y - heightDiff / 2F) - 2;
			return thinnestWidth + (int) ((yFromCenter / 4F) * (yFromCenter / 4F) / 10);
		}
		else if (heightDiff > 60)
		{
			float yFromCenter = Math.abs(y - heightDiff / 2F) - 1;
			return thinnestWidth + (int) ((yFromCenter / 3F) * (yFromCenter / 3F) / 9);
		}
		else if (heightDiff > 30)
		{
			float yFromCenter = Math.abs(y - heightDiff / 2F);
			return thinnestWidth + (int) ((yFromCenter / 2.6F) * (yFromCenter / 2.6F) / 6);
		}
		else if (heightDiff > 18)
		{
			float yFromCenter = Math.abs(y - heightDiff / 2F) + 1;
			return thinnestWidth + (int) ((yFromCenter / 2.8F) * (yFromCenter / 2.8F) / 3);
		}
		else
		{
			float yFromCenter = Math.abs(y - heightDiff / 2F) + 3;
			return thinnestWidth + (int) ((yFromCenter / 2.7f) * (yFromCenter / 2.7f) / 3);
		}
	}
}
