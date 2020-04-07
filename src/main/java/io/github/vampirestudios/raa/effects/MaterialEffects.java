package io.github.vampirestudios.raa.effects;

import com.google.gson.JsonElement;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.function.Consumer;

public enum MaterialEffects {
    LIGHTNING(ItemEffectHandler::spawnLightning, (element -> {
        element.getAsJsonObject().addProperty("chance", Rands.randIntRange(2, 8));
    })),
    EFFECT(ItemEffectHandler::statusEffectForTarget, (element -> {
        element.getAsJsonObject().addProperty("type", Rands.list(new ArrayList<>(Registry.STATUS_EFFECT.getIds())).toString());
        element.getAsJsonObject().addProperty("duration", Rands.randIntRange(5, 15));
        element.getAsJsonObject().addProperty("amplifier", Rands.randIntRange(0, 2));
    })),
    FIREBALL(ItemEffectHandler::spawnFireball, (element -> {
        element.getAsJsonObject().addProperty("chance", Rands.randIntRange(2, 8));
    })),
    FREEZE(ItemEffectHandler::stopEntity, (element -> {
        element.getAsJsonObject().addProperty("duration", Rands.randIntRange(5, 20));
    }));

    public ItemEffectFunction function;
    public Consumer<JsonElement> jsonConsumer;

    MaterialEffects(ItemEffectFunction function, Consumer<JsonElement> jsonConsumer) {
        this.function = function;
        this.jsonConsumer = jsonConsumer;
    }

    public void apply(World world, LivingEntity target, LivingEntity attacker, JsonElement config) {
        this.function.apply(world, target, attacker, config);
    }
}
