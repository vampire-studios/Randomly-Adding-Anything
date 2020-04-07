package io.github.vampirestudios.raa.effects;

import com.google.gson.JsonElement;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ItemEffectHandler {
    public static void spawnLightning(World world, LivingEntity target, LivingEntity attacker, JsonElement config) {
        if (world.getRandom().nextInt(config.getAsJsonObject().get("chance").getAsInt()) == 0) {
            if (!world.isClient()) {
                world.spawnEntity(new LightningEntity(world, target.getBlockPos().getX(), target.getBlockPos().getZ(), target.getBlockPos().getZ(), false));
            }
        }
    }

    public static void statusEffectForTarget(World world, LivingEntity target, LivingEntity attacker, JsonElement config) {
        if (!world.isClient()) {
            target.addStatusEffect(new StatusEffectInstance(
                    Registry.STATUS_EFFECT.get(new Identifier(config.getAsJsonObject().get("type").getAsString())),
                    config.getAsJsonObject().get("duration").getAsInt(),
                    config.getAsJsonObject().get("amplifier").getAsInt()));
        }
    }

    public static void spawnFireball(World world, LivingEntity target, LivingEntity attacker, JsonElement config) {
        if (world.getRandom().nextInt(config.getAsJsonObject().get("chance").getAsInt()) == 0) {
            if (!world.isClient()) {
                Vec3d vec3d = attacker.getRotationVec(1.0F);
                double f = target.getX() - (attacker.getX() + vec3d.x * 4.0D);
                double g = target.getBodyY(0.5D) - (0.5D + attacker.getBodyY(0.5D));
                double h = target.getZ() - (attacker.getZ() + vec3d.z * 4.0D);

                FireballEntity fireballEntity = new FireballEntity(world, target, f, g, h);
                fireballEntity.explosionPower = 1;
                world.spawnEntity(fireballEntity);
            }
        }
    }

    public static void stopEntity(World world, LivingEntity target, LivingEntity attacker, JsonElement config) {
        if (!world.isClient()) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, config.getAsJsonObject().get("duration").getAsInt(), 127, false, false, false));
        }
    }
}
