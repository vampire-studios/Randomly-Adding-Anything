package io.github.vampirestudios.raa.effects;

import com.google.gson.JsonElement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

@FunctionalInterface
public interface ItemEffectFunction {
    void apply(World world, LivingEntity target, LivingEntity attacker, JsonElement config);
}
