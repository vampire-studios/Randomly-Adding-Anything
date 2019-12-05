package io.github.vampirestudios.raa.generation.feature;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.utils.FeatureUtils;
import io.github.vampirestudios.raa.utils.noise.OctaveOpenSimplexNoise;
import io.github.vampirestudios.raa.utils.Rands;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

//Code kindly taken from The Hallow, thanks to everyone who is working on it!
public class TombFeature extends Feature<DefaultFeatureConfig> {


    private static final OctaveOpenSimplexNoise offsetNoise = new OctaveOpenSimplexNoise(new Random(0), 2, 30D, 4D, 2D);
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final EntityType<?> SKELETON = EntityType.SKELETON;
    private static final Identifier LOOT_TABLE = new Identifier(RandomlyAddingAnything.MOD_ID, "chest/tomb");
    private static BlockState STONE;

    public TombFeature(DimensionData dimensionData) {
        super(DefaultFeatureConfig::deserialize);
        STONE = Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase() + "_stone")).getDefaultState();
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random rand, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.add(0, -3, 0)).isAir() || !world.getBlockState(pos.add(0, -3, 0)).isOpaque() || world.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState()))
            return true;
        final BiomeSource source = chunkGenerator.getBiomeSource();

        return this.generate(world, rand, pos.add(0, -3, 0), (x, y, z) -> source.getBiomeForNoiseGen(x, y, z).getSurfaceConfig());
    }

    private boolean generate(IWorld world, Random rand, BlockPos pos, Coordinate3iFunction<SurfaceConfig> configFunction) {
        int centreX = pos.getX() + rand.nextInt(16) - 8;
        int centreZ = pos.getZ() + rand.nextInt(16) - 8;
        int lowY = pos.getY() - 3;

        int radius = rand.nextInt(10) + 7;
        int height = rand.nextInt(8) + 6;

        double radiusSquared = radius * radius;

        Vec3d origin = new Vec3d(centreX, 0, centreZ);

        BlockPos.Mutable posMutable = new BlockPos.Mutable();

        for (int xOffset = -radius; xOffset <= radius; ++xOffset) {
            int x = centreX + xOffset;

            for (int zOffset = -radius; zOffset <= radius; ++zOffset) {
                int z = centreZ + zOffset;

                Vec3d position = new Vec3d(x, 0, z);
                double sqrDistTo = position.squaredDistanceTo(origin);
                if (sqrDistTo <= radiusSquared) {
                    double progress = MathHelper.perlinFade(sqrDistTo / radiusSquared);
                    int heightOffset = (int) MathHelper.lerp(progress, height, 0);
                    heightOffset += (int) MathHelper.lerp(progress, offsetNoise.sample(x, z), 0);

                    posMutable.setX(x);
                    posMutable.setZ(z);

                    this.generateBarrowColumn(world, rand, lowY, heightOffset, posMutable, configFunction.get(x, lowY + heightOffset, z));
                }
            }
        }

        try {
            String path;
            World world2 = world.getWorld();
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) path = "saves/" + ((ServerWorld) world2).getSaveHandler().getWorldDir().getName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/tomb_spawns.txt";
            else path = world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/tomb_spawns.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void generateBarrowColumn(IWorld world, Random rand, int lowY, int heightOffset, BlockPos.Mutable pos, SurfaceConfig surfaceConfig) {
        int upperY = lowY + heightOffset;

        for (int y = upperY; y >= lowY; --y) {
            pos.setY(y);
            if (y == upperY) {
                world.setBlockState(pos, surfaceConfig.getTopMaterial(), 19);
            } else if (y > upperY - 3) {
                world.setBlockState(pos, surfaceConfig.getUnderMaterial(), 19);
            } else if (y == lowY && rand.nextInt(48) == 0) {
                if (rand.nextInt(4) == 0) {
                    FeatureUtils.setLootChest(world, pos, LOOT_TABLE, rand);
                } else {
                    FeatureUtils.setSpawner(world, pos, Rands.chance(2) ? SKELETON : EntityType.ZOMBIE);
                }
            } else {
                world.setBlockState(pos, y <= lowY + 1 ? STONE : AIR, 19);
            }
        }
    }

    private interface Coordinate2iFunction<T> {
        T get(int x, int z);
    }

    private interface Coordinate3iFunction<T> {
        T get(int x, int y, int z);
    }

}