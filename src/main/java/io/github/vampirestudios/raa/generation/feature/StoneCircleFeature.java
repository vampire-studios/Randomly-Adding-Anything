package io.github.vampirestudios.raa.generation.feature;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.utils.FeatureUtils;
import io.github.vampirestudios.raa.utils.noise.OctaveOpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

//Code kindly taken from The Hallow, thanks to everyone who is working on it!
public class StoneCircleFeature extends Feature<DefaultFeatureConfig> {

    private static final OctaveOpenSimplexNoise offsetNoise = new OctaveOpenSimplexNoise(new Random(0), 2, 25D, 4D, 3D);
    private static final Identifier LOOT_TABLE = new Identifier(RandomlyAddingAnything.MOD_ID, "chest/stone_circle");
    private static BlockState STONE;
    private static BlockState COBBLESTONE;

    public StoneCircleFeature(DimensionData dimensionData) {
        super(DefaultFeatureConfig::deserialize);
        STONE = Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase() + "_stone")).getDefaultState();
        COBBLESTONE = Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase() + "_cobblestone")).getDefaultState();
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random rand, BlockPos pos, DefaultFeatureConfig config) {
        return this.generate(world, rand, pos);
    }

    private boolean generate(IWorld world, Random rand, BlockPos pos) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir() || !world.getBlockState(pos.add(0, -1, 0)).isOpaque() || world.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState()))
            return true;

        int centreX = pos.getX() + rand.nextInt(16) - 8;
        int centreZ = pos.getZ() + rand.nextInt(16) - 8;
        int lowY = pos.getY() - 1;

        int radius = rand.nextInt(6) + 14;

        int squaredRadius = radius * radius;
        int baseHeight = rand.nextInt(3) + 5;

        final double pythagRadiusPart = Math.sqrt((double) squaredRadius / 2D);

        BlockPos.Mutable posMutable = new BlockPos.Mutable();
        BlockPos.Mutable posMutable2 = new BlockPos.Mutable();

        for (int quarter = 0; quarter < 4; ++quarter) {
            for (int localPosition = -1; localPosition < 2; ++localPosition) {
                int qBit1 = quarter & 0b01;
                int qBit2 = quarter & 0b10;

                double xOffset = 0;
                double zOffset = 0;

                if (qBit1 == 0) {
                    switch (localPosition) {
                        case -1:
                            xOffset = -pythagRadiusPart;
                            break;
                        case 1:
                            xOffset = pythagRadiusPart;
                            break;
                        default:
                            break;
                    }

                    if (qBit2 == 0) {
                        zOffset = (xOffset == 0) ? radius : pythagRadiusPart;
                    } else {
                        zOffset = (xOffset == 0) ? -radius : -pythagRadiusPart;
                    }
                } else {
                    switch (localPosition) {
                        case -1:
                            zOffset = -pythagRadiusPart;
                            break;
                        case 1:
                            zOffset = pythagRadiusPart;
                            break;
                        default:
                            break;
                    }

                    if (qBit2 == 0) {
                        xOffset = (zOffset == 0) ? radius : pythagRadiusPart;
                    } else {
                        xOffset = (zOffset == 0) ? -radius : -pythagRadiusPart;
                    }
                }

                posMutable.setX((int) (centreX + xOffset));
                posMutable.setZ((int) (centreZ + zOffset));

                posMutable2.setX((int) (centreX + xOffset));
                posMutable2.setZ((int) (centreZ + zOffset));

                generateStone(world, rand, posMutable, posMutable2, baseHeight + rand.nextInt(3), lowY);
            }
        }

        if (rand.nextInt(3) == 0) {
            FeatureUtils.setLootChest(world, new BlockPos(centreX + rand.nextInt(3) - 1, lowY - 2 - rand.nextInt(3), centreZ + rand.nextInt(3) - 1), LOOT_TABLE, rand);
        }

        return true;
    }

    private void generateStone(ModifiableWorld world, Random rand, final BlockPos centre, BlockPos.Mutable mutable, int height, int lowY) {
        final int posX = centre.getX();
        final int posZ = centre.getZ();

        for (int xOffset = -3; xOffset < 4; ++xOffset) {
            mutable.setX(posX + xOffset);
            for (int zOffset = -3; zOffset < 4; ++zOffset) {
                mutable.setZ(posZ + zOffset);
                mutable.setY(0);

                double squaredDistanceTo = centre.getSquaredDistance(mutable) / 9D;

                int localHeight = (int) offsetNoise.sample(mutable.getX(), mutable.getZ()) + (int) MathHelper.lerp(squaredDistanceTo, height, 0D) + lowY;

                for (int y = lowY - 5; y < localHeight + 1; ++y) {
                    mutable.setY(y);
                    world.setBlockState(mutable, rand.nextInt(3) == 0 ? COBBLESTONE : STONE, 19);
                }
            }
        }
    }
}