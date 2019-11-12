package io.github.vampirestudios.raa.generation.feature;

import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.CustomDimensionalBiome;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class LargeDeadwoodTreeFeature extends AbstractTreeFeature<TreeFeatureConfig> {
	private static BlockState LOG;
	private static BlockState LEAVES;
	
	public LargeDeadwoodTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> function) {
		super(function);
		LOG = CustomDimensionalBiome.randomLog();
		LEAVES = CustomDimensionalBiome.randomLeaves();
	}

	@Override
	public boolean generate(IWorld iWorld, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
		return this.generate(iWorld, new Random(), blockPos, Sets.newTreeSet(), Sets.newTreeSet(), BlockBox.empty(), treeFeatureConfig);
	}

	@Override
	public boolean generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos, Set<BlockPos> set, Set<BlockPos> set1, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
		int height = random.nextInt(3) + random.nextInt(2) + 6;
		int x = blockPos.getX();
		int y = blockPos.getY();
		int z = blockPos.getZ();
		if (y >= 1 && y + height + 1 < 256) {
			BlockPos downPos = blockPos.down(1);
			if (!isNaturalDirtOrGrass(modifiableTestableWorld, downPos)) {
				return false;
			} else if (!this.doesTreeFit(modifiableTestableWorld, blockPos, height)) {
				return false;
			} else {
				this.setToDirt(modifiableTestableWorld, downPos);
				this.setToDirt(modifiableTestableWorld, downPos.east());
				this.setToDirt(modifiableTestableWorld, downPos.south());
				this.setToDirt(modifiableTestableWorld, downPos.south().east());
				Direction dir = Direction.Type.HORIZONTAL.random(random);
				int baseTrunkHeight = height - random.nextInt(4);
				int heightVariance = 2 - random.nextInt(3);
				int startX = x;
				int startZ = z;
				int startY = y + height - 1;
				
				for (int localHeight = 0; localHeight < height; ++localHeight) {
					if (localHeight >= baseTrunkHeight && heightVariance > 0) {
						startX += dir.getOffsetX();
						startZ += dir.getOffsetZ();
						--heightVariance;
					}
					
					int localY = y + localHeight;
					BlockPos trunkPos = new BlockPos(startX, localY, startZ);
					if (isAirOrLeaves(modifiableTestableWorld, trunkPos)) {
						this.addLog(modifiableTestableWorld, trunkPos, blockBox);
						this.addLog(modifiableTestableWorld, trunkPos.east(), blockBox);
						this.addLog(modifiableTestableWorld, trunkPos.south(), blockBox);
						this.addLog(modifiableTestableWorld, trunkPos.east().south(), blockBox);
					}
				}
				
				for (int xOffset = -2; xOffset <= 0; ++xOffset) {
					for (int zOffset = -2; zOffset <= 0; ++zOffset) {
						int yOffset = -1;
						this.addLeaves(modifiableTestableWorld, startX + xOffset, startY + yOffset, startZ + zOffset, blockBox);
						this.addLeaves(modifiableTestableWorld, 1 + startX - xOffset, startY + yOffset, startZ + zOffset, blockBox);
						this.addLeaves(modifiableTestableWorld, startX + xOffset, startY + yOffset, 1 + startZ - zOffset, blockBox);
						this.addLeaves(modifiableTestableWorld, 1 + startX - xOffset, startY + yOffset, 1 + startZ - zOffset, blockBox);
						if ((xOffset > -2 || zOffset > -1) && (xOffset != -1 || zOffset != -2)) {
							yOffset = 1;
							this.addLeaves(modifiableTestableWorld, startX + xOffset, startY + yOffset, startZ + zOffset, blockBox);
							this.addLeaves(modifiableTestableWorld, 1 + startX - xOffset, startY + yOffset, startZ + zOffset, blockBox);
							this.addLeaves(modifiableTestableWorld, startX + xOffset, startY + yOffset, 1 + startZ - zOffset, blockBox);
							this.addLeaves(modifiableTestableWorld, 1 + startX - xOffset, startY + yOffset, 1 + startZ - zOffset, blockBox);
						}
					}
				}
				
				if (random.nextBoolean()) {
					this.addLeaves(modifiableTestableWorld, startX, startY + 2, startZ, blockBox);
					this.addLeaves(modifiableTestableWorld, startX + 1, startY + 2, startZ, blockBox);
					this.addLeaves(modifiableTestableWorld, startX + 1, startY + 2, startZ + 1, blockBox);
					this.addLeaves(modifiableTestableWorld, startX, startY + 2, startZ + 1, blockBox);
				}
				
				for (int xOffset = -3; xOffset <= 4; ++xOffset) {
					for (int zOffset = -3; zOffset <= 4; ++zOffset) {
						if ((xOffset != -3 || zOffset != -3) && (xOffset != -3 || zOffset != 4) && (xOffset != 4 || zOffset != -3) && (xOffset != 4 || zOffset != 4) && (Math.abs(xOffset) < 3 || Math.abs(zOffset) < 3)) {
							this.addLeaves(modifiableTestableWorld, startX + xOffset, startY, startZ + zOffset, blockBox);
						}
					}
				}
				
				for (int xOffset = -1; xOffset <= 2; ++xOffset) {
					for (int zOffset = -1; zOffset <= 2; ++zOffset) {
						if ((xOffset < 0 || xOffset > 1 || zOffset < 0 || zOffset > 1) && random.nextInt(3) <= 0) {
							int trunkHeight = random.nextInt(3) + 2;
							
							for (int localY = 0; localY < trunkHeight; ++localY) {
								this.addLog(modifiableTestableWorld, new BlockPos(x + xOffset, startY - localY - 1, z + zOffset), blockBox);
							}
							
							for (int localX = -1; localX <= 1; ++localX) {
								for (int localZ = -1; localZ <= 1; ++localZ) {
									this.addLeaves(modifiableTestableWorld, startX + xOffset + localX, startY, startZ + zOffset + localZ, blockBox);
								}
							}
							
							for (int localX = -2; localX <= 2; ++localX) {
								for (int localZ = -2; localZ <= 2; ++localZ) {
									if (Math.abs(localX) != 2 || Math.abs(localZ) != 2) {
										this.addLeaves(modifiableTestableWorld, startX + xOffset + localX, startY - 1, startZ + zOffset + localZ, blockBox);
									}
								}
							}
						}
					}
				}
				return true;
			}
		} else {
			return false;
		}
	}
	
	private boolean doesTreeFit(TestableWorld world, BlockPos blockPos, int height) {
		int x = blockPos.getX();
		int y = blockPos.getY();
		int z = blockPos.getZ();
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		
		for (int localY = 0; localY <= height + 1; ++localY) {
			int leafSize = 1;
			if (localY == 0) {
				leafSize = 0;
			}
			
			if (localY >= height - 1) {
				leafSize = 2;
			}
			
			for (int localX = -leafSize; localX <= leafSize; ++localX) {
				for (int localZ = -leafSize; localZ <= leafSize; ++localZ) {
					if (!canTreeReplace(world, mutablePos.set(x + localX, y + localY, z + localZ))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private void addLog(ModifiableTestableWorld world, BlockPos blockPos, BlockBox blockBox) {
		if (canTreeReplace(world, blockPos)) {
			this.setBlockState(world, blockPos, LOG, blockBox);
		}
		
	}
	
	private void addLeaves(ModifiableTestableWorld world, int x, int y, int z, BlockBox blockBox) {
		BlockPos blockPos = new BlockPos(x, y, z);
		if (isAir(world, blockPos)) {
			this.setBlockState(world, blockPos, LEAVES, blockBox);
		}
		
	}

}