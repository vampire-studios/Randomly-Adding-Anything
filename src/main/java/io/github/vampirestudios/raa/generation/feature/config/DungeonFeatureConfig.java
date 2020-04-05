package io.github.vampirestudios.raa.generation.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.FeatureConfig;

public class DungeonFeatureConfig implements FeatureConfig {
   public final Identifier startPool;
   public final int size;

   public DungeonFeatureConfig(String startPool, int size) {
      this.startPool = new Identifier(startPool);
      this.size = size;
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("start_pool"), ops.createString(this.startPool.toString()), ops.createString("size"), ops.createInt(this.size))));
   }

   public static <T> DungeonFeatureConfig deserialize(Dynamic<T> dynamic) {
      String string = dynamic.get("start_pool").asString("");
      int i = dynamic.get("size").asInt(6);
      return new DungeonFeatureConfig(string, i);
   }
}
