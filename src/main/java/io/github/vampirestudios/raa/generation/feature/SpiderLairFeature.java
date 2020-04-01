package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.utils.FeatureUtils;
import io.github.vampirestudios.raa.utils.Utils;
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

import java.util.Random;
import java.util.function.Function;

//Code kindly taken from The Hallow, thanks to everyone who is working on it!
public class SpiderLairFeature extends Feature<DefaultFeatureConfig> {

    public SpiderLairFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer, Function<Random, ? extends DefaultFeatureConfig> function) {
        super(configDeserializer, function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig defaultFeatureConfig) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir() || !world.getBlockState(pos.add(0, -1, 0)).isOpaque() || world.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState()))
            return true;
        if (world.getBlockState(pos.down(1)).getBlock() == Blocks.GRASS_BLOCK) {
            FeatureUtils.setSpawner(world, pos, EntityType.SPIDER);

            for (int i = 0; i < 64; ++i) {
                BlockPos pos_2 = pos.add(random.nextInt(6) - random.nextInt(6), random.nextInt(3) - random.nextInt(3), random.nextInt(6) - random.nextInt(6));
                if (world.isAir(pos_2) || world.getBlockState(pos_2).getBlock() == Blocks.GRASS_BLOCK) {
                    world.setBlockState(pos_2, Blocks.COBWEB.getDefaultState(), 2);
                }
            }

            BlockPos chestPos = pos.add(0, -1, 0);
            world.setBlockState(chestPos, StructurePiece.method_14916(world, chestPos, Blocks.CHEST.getDefaultState()), 2);
            LootableContainerBlockEntity.setLootTable(world, random, chestPos, new Identifier(RandomlyAddingAnything.MOD_ID, "chest/spider_lair"));

            Utils.createSpawnsFile("spider_lair", world, pos);
            return true;
        } else {
            return false;
        }
    }
}