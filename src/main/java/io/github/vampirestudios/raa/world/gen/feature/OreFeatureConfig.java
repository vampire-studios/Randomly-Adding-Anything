package io.github.vampirestudios.raa.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import io.github.vampirestudios.raa.api.RAARegisteries;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OreFeatureConfig implements FeatureConfig {
    public final OreFeatureConfig.Target target;
    public final int size;
    public final BlockState state;

    public OreFeatureConfig(Target target, BlockState state, int size) {
        this.size = size;
        this.state = state;
        this.target = target;
    }

    public static OreFeatureConfig deserialize(Dynamic<?> dynamic) {
        int size = dynamic.get("size").asInt(0);
        OreFeatureConfig.Target target = RAARegisteries.TARGET_REGISTRY.get(new Identifier(dynamic.get("target").asString("")));
        BlockState blockState = dynamic.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        return new OreFeatureConfig(target, blockState, size);
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("size"),
                ops.createInt(this.size), ops.createString("target"), ops.createString(this.target.getId().toString()),
                ops.createString("state"), BlockState.serialize(ops, this.state).getValue())));
    }

    public static class Target {

        private final Identifier name;
        private final Predicate<BlockState> predicate;
        private final Block block;

        public Target(Identifier name, Predicate<BlockState> predicate, Block block) {
            this.name = name;
            this.predicate = predicate;
            this.block = block;
        }

        public static List<Target> getValues() {
            return RAARegisteries.TARGET_REGISTRY.stream().collect(Collectors.toList());
        }

        public Identifier getId() {
            return this.name;
        }

        public Predicate<BlockState> getCondition() {
            return this.predicate;
        }

        public Block getBlock() {
            return block;
        }

    }

}