package fr.arthurbambou.randomlyaddinganything.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.predicate.block.BlockPredicate;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OreFeatureConfig implements FeatureConfig {
    public final OreFeatureConfig.Target target;
    public final int size;
    public final BlockState state;

    public OreFeatureConfig(OreFeatureConfig.Target oreFeatureConfig$Target_1, BlockState blockState_1, int int_1) {
        this.size = int_1;
        this.state = blockState_1;
        this.target = oreFeatureConfig$Target_1;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> dynamicOps_1) {
        return new Dynamic<>(dynamicOps_1, dynamicOps_1.createMap(ImmutableMap.of(dynamicOps_1.createString("size"), dynamicOps_1.createInt(this.size), dynamicOps_1.createString("target"), dynamicOps_1.createString(this.target.getName()), dynamicOps_1.createString("state"), BlockState.serialize(dynamicOps_1, this.state).getValue())));
    }

    public static OreFeatureConfig deserialize(Dynamic<?> dynamic_1) {
        int int_1 = dynamic_1.get("size").asInt(0);
        OreFeatureConfig.Target oreFeatureConfig$Target_1 = OreFeatureConfig.Target.byName(dynamic_1.get("target").asString(""));
        BlockState blockState_1 = dynamic_1.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        return new OreFeatureConfig(oreFeatureConfig$Target_1, blockState_1, int_1);
    }

    public enum Target {
        STONE("stone", new BlockPredicate(Blocks.STONE)),
        ANDESITE("andesite", new BlockPredicate(Blocks.ANDESITE)),
        DIORITE("diorite", new BlockPredicate(Blocks.DIORITE)),
        GRANITE("granite", new BlockPredicate(Blocks.GRANITE)),
        GRASS_BLOCK("grass_block", new BlockPredicate(Blocks.GRASS_BLOCK)),
        GRAVEL("gravel", new BlockPredicate(Blocks.GRAVEL)),
        DIRT("dirt", (blockState_1) -> {
            if (blockState_1 == null) {
                return false;
            } else {
                Block block_1 = blockState_1.getBlock();
                return block_1 == Blocks.DIRT || block_1 == Blocks.COARSE_DIRT ;
            }
        }),
        SAND("sand", (blockState_1) -> {
            if (blockState_1 == null) {
                return false;
            } else {
                Block block_1 = blockState_1.getBlock();
                return block_1 == Blocks.SAND || block_1 == Blocks.SANDSTONE;
            }
        }),
        RED_SAND("red_sand", (blockState_1) -> {
            if (blockState_1 == null) {
                return false;
            } else {
                Block block_1 = blockState_1.getBlock();
                return block_1 == Blocks.RED_SAND || block_1 == Blocks.RED_SANDSTONE;
            }
        }),
        NETHERRACK("netherrack", new BlockPredicate(Blocks.NETHERRACK)),
        END_STONE("end_stone", new BlockPredicate(Blocks.END_STONE));

        private static final Map<String, OreFeatureConfig.Target> nameMap = Arrays.stream(values()).collect(Collectors.toMap(OreFeatureConfig.Target::getName,
                (oreFeatureConfig$Target_1) -> oreFeatureConfig$Target_1));
        private final String name;
        private final Predicate<BlockState> predicate;

        Target(String string_1, Predicate<BlockState> predicate_1) {
            this.name = string_1;
            this.predicate = predicate_1;
        }

        public String getName() {
            return this.name;
        }

        public static OreFeatureConfig.Target byName(String string_1) {
            return nameMap.get(string_1);
        }

        public Predicate<BlockState> getCondition() {
            return this.predicate;
        }
    }
}
