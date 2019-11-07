package io.github.vampirestudios.raa.utils;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class MixinEvents {

    public static void onPlayerPickup(PlayerEntity playerEntity, ItemEntity itemEntity, int pickupDelay, UUID owner, int age) {
        ItemStack itemStack = itemEntity.getStack().copy();
        if (pickupDelay == 0 && (owner == null || 6000 - age <= 200 || owner.equals(playerEntity.getUuid())) && playerEntity.inventory.insertStack(itemEntity.getStack())) {
            itemEntity.setStack(itemStack);
            System.out.println(itemEntity);
        }
    }
}
