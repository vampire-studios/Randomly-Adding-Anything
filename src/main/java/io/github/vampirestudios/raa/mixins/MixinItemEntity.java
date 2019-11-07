package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.utils.MixinEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ItemEntity.class)
public class MixinItemEntity {

    @Shadow
    private int pickupDelay;
    @Shadow
    private UUID owner;
    @Shadow
    private int age;

    @Inject(method = "onPlayerCollision", at = @At("HEAD"))
    public void onPlayerCollision(PlayerEntity playerEntity_1, CallbackInfo ci) {
        if (!((ItemEntity)(Object)this).world.isClient) {
            MixinEvents.onPlayerPickup(playerEntity_1, (ItemEntity) (Object) this, this.pickupDelay, this.owner, this.age);
        }
    }
}
