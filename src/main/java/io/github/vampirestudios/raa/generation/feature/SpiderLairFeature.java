package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.utils.FeatureUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.function.Function;

//Code kindly taken from The Hallow, thanks to everyone who is working on it!
public class SpiderLairFeature extends Feature<DefaultFeatureConfig> {

    public SpiderLairFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld iWorld, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig defaultFeatureConfig) {
        if (iWorld.getBlockState(pos.add(0, -1, 0)).isAir() || !iWorld.getBlockState(pos.add(0, -1, 0)).isOpaque() || iWorld.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState()))
            return true;
        if (iWorld.getBlockState(pos.down(1)).getBlock() == Blocks.GRASS_BLOCK) {
            FeatureUtils.setSpawner(iWorld, pos, EntityType.SPIDER);

            for (int i = 0; i < 64; ++i) {
                BlockPos pos_2 = pos.add(random.nextInt(6) - random.nextInt(6), random.nextInt(3) - random.nextInt(3), random.nextInt(6) - random.nextInt(6));
                if (iWorld.isAir(pos_2) || iWorld.getBlockState(pos_2).getBlock() == Blocks.GRASS_BLOCK) {
                    iWorld.setBlockState(pos_2, Blocks.COBWEB.getDefaultState(), 2);
                }
            }

            BlockPos chestPos = pos.add(0, -1, 0);
            iWorld.setBlockState(chestPos, StructurePiece.method_14916(iWorld, chestPos, Blocks.CHEST.getDefaultState()), 2);
            LootableContainerBlockEntity.setLootTable(iWorld, random, chestPos, new Identifier(RandomlyAddingAnything.MOD_ID, "chest/spider_lair"));

            try {
                String path;
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) path = "saves/" + iWorld.getLevelProperties().getLevelName() + "/DIM_raa_" + iWorld.getDimension().getType().getSuffix().substring(4) + "/data/spider_lair_spawns.txt";
                else path = iWorld.getLevelProperties().getLevelName() + "/DIM_raa_" + iWorld.getDimension().getType().getSuffix().substring(4) + "/data/spider_lair_spawns.txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}