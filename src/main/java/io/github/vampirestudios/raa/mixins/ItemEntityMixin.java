package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.world.player.PlayerDiscoveryProvider;
import io.github.vampirestudios.raa.world.player.PlayerDiscoveryState;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.world.player.OreDiscoverState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Shadow
    private int pickupDelay;
    @Shadow
    private UUID owner;
    @Shadow
    private int age;

    @Inject(method = "onPlayerCollision", at = @At("HEAD"))
    public void onPlayerCollision(PlayerEntity playerEntity_1, CallbackInfo ci) {
        if (!((ItemEntity) (Object) this).world.isClient) {
            ItemStack itemStack = ((ItemEntity) (Object) this).getStack().copy();
            if (this.pickupDelay == 0 && (this.owner == null || 6000 - this.age <= 200
                    || this.owner.equals(playerEntity_1.getUuid()))
//                    TODO: find an alternative to this.
//                    && playerEntity_1.inventory.insertStack(((ItemEntity)(Object)this).getStack())
            ) {
                Material material = null;
                for (Material materiale : Materials.MATERIALS) {
                    if ((materiale.getName() + "_ore").equals(Registry.ITEM.getId(itemStack.getItem()).getPath()) && Registry.ITEM.getId(itemStack.getItem()).getNamespace().equals("raa")) {
                        material = materiale;
                        break;
                    }
                }
                if (material != null) {
                    if (playerEntity_1 instanceof ServerPlayerEntity && playerEntity_1 instanceof PlayerDiscoveryProvider) {
                        PlayerDiscoveryState discoveryState = ((PlayerDiscoveryProvider) playerEntity_1).getDiscoveryState();
                        List<OreDiscoverState> materialDiscoveryStates = discoveryState.getMaterialDiscoveryState();
                        for (int i = 0; i < materialDiscoveryStates.size(); i++) {
                            if (materialDiscoveryStates.get(i).getMaterial() == material) {
                                if (!materialDiscoveryStates.get(i).isDiscovered()) {
                                    System.out.println("You Discovered a new material!");
                                    materialDiscoveryStates.set(i, materialDiscoveryStates.get(i).discover());
                                } else {
                                    for (int z = 0; z < itemStack.getCount(); z++)
                                        System.out.println("You already discovered this material " + materialDiscoveryStates.get(i).getDiscoverTimes() + " time before");
                                    materialDiscoveryStates.set(i, materialDiscoveryStates.get(i).alreadyDiscovered());
                                }
                            }
                        }
                    }
                } else {
                    for (Material materiale : Materials.DIMENSION_MATERIALS) {
                        if ((materiale.getName() + "_ore").equals(Registry.ITEM.getId(itemStack.getItem()).getPath()) && Registry.ITEM.getId(itemStack.getItem()).getNamespace().equals("raa")) {
                            material = materiale;
                            break;
                        }
                    }
                    if (material != null) {
                        if (playerEntity_1 instanceof ServerPlayerEntity && playerEntity_1 instanceof PlayerDiscoveryProvider) {
                            PlayerDiscoveryState discoveryState = ((PlayerDiscoveryProvider) playerEntity_1).getDiscoveryState();
                            List<OreDiscoverState> materialDiscoveryStates = discoveryState.getDimensionMaterialDiscoveryState();
                            for (int i = 0; i < materialDiscoveryStates.size(); i++) {
                                if (materialDiscoveryStates.get(i).getMaterial() == material) {
                                    if (!materialDiscoveryStates.get(i).isDiscovered()) {
                                        System.out.println("You Discovered a new material!");
                                        materialDiscoveryStates.set(i, materialDiscoveryStates.get(i).discover());
                                    } else {
                                        for (int z = 0; z < itemStack.getCount(); z++)
                                            System.out.println("You already discovered this dimensional material " + materialDiscoveryStates.get(i).getDiscoverTimes() + " time before");
                                        materialDiscoveryStates.set(i, materialDiscoveryStates.get(i).alreadyDiscovered());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
