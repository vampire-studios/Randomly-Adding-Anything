package io.github.vampirestudios.raa.generation.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.decorator.DecoratorConfig;


public class ChanceAndTypeConfig implements DecoratorConfig {
	public final float chanceModifier;
	public final Type type;


	public ChanceAndTypeConfig(float chance, Type typeIn)
	{
		this.chanceModifier = chance;
		this.type = typeIn;
	}

	public enum Type {
		SUNSHRINE, STONEHENGE, HANGING_RUINS
	}


	// cannot fit boolean in
	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops)
	{
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("chance"), ops.createFloat(this.chanceModifier), ops.createString("type"), ops.createString(this.type.name()))));
	}


	public static ChanceAndTypeConfig deserialize(Dynamic<?> ops)
	{
		float chance = ops.get("chance").asFloat(0);
		Type type = Type.valueOf(ops.get("type").asString("SUNSHRINE"));
		return new ChanceAndTypeConfig(chance, type);
	}
}