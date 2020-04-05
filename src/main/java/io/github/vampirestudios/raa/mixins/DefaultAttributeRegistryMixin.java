package io.github.vampirestudios.raa.mixins;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(DefaultAttributeRegistry.class)
public class DefaultAttributeRegistryMixin {
    @Mutable
    @Shadow
    @Final
    private static Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> DEFAULT_ATTRIBUTE_REGISTRY;

    @Inject(method = "get", at = @At("HEAD"))
    private static void getAndInitialize(EntityType<? extends LivingEntity> type, CallbackInfoReturnable<DefaultAttributeContainer> cir) {
        if (DEFAULT_ATTRIBUTE_REGISTRY instanceof ImmutableMap) {
            DEFAULT_ATTRIBUTE_REGISTRY = new HashMap<>(DEFAULT_ATTRIBUTE_REGISTRY);
        }

        DEFAULT_ATTRIBUTE_REGISTRY.computeIfAbsent(type, (t) -> ZombieEntity.createZombieAttributes().build());
    }
}
