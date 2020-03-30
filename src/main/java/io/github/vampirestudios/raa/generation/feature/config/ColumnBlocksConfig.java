package io.github.vampirestudios.raa.generation.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.FeatureConfig;

// Thanks to TelepathicGrunt and the UltraAmplified mod for this class
public class ColumnBlocksConfig implements FeatureConfig {
	public final BlockState topBlock;
	public final BlockState middleBlock;
	public final BlockState insideBlock;


	public ColumnBlocksConfig(BlockState blockState, BlockState blockState2, BlockState blockState3) {
		this.topBlock = blockState;
		this.middleBlock = blockState2;
		this.insideBlock = blockState3;
	}


	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(
			ops,
			ops.createMap(
				ImmutableMap.of(
					ops.createString("top_block"), ops.createString(Registry.BLOCK.getId(topBlock.getBlock()).toString()),
					ops.createString("middle_block"), ops.createString(Registry.BLOCK.getId(middleBlock.getBlock()).toString()),
					ops.createString("inside_block"), ops.createString(Registry.BLOCK.getId(insideBlock.getBlock()).toString())
				)
			)
		);
	}


	public static <T> ColumnBlocksConfig deserialize(Dynamic<T> ops) {
		BlockState topBlock = ops.get("top_block").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		BlockState middleBlock = ops.get("middle_block").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		BlockState insideBlock = ops.get("inside_block").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		return new ColumnBlocksConfig(topBlock, middleBlock, insideBlock);
	}
}