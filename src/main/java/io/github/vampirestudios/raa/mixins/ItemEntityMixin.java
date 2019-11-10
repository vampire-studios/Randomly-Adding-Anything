package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverProvider;
import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverState;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.state.OreDiscoverState;
import io.github.vampirestudios.raa.state.PlayerDiscoverState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
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
        if (!((ItemEntity)(Object)this).world.isClient) {
            ItemStack itemStack = ((ItemEntity)(Object)this).getStack().copy();
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
                    World world = playerEntity_1.getEntityWorld();
                    if (world instanceof ServerWorld && world instanceof PlayerMaterialDiscoverProvider) {
                        PlayerMaterialDiscoverState discoverState = ((PlayerMaterialDiscoverProvider) world).getMaterialDiscoverState();
                        List<PlayerDiscoverState> map = discoverState.getPlayerMap();
                        PlayerDiscoverState playerDiscoverState = null;
                        int c =0;
                        for (PlayerDiscoverState playerDiscoverState1 : map) {
                            if (playerDiscoverState1.getUuid() == playerEntity_1.getUuid()) {
                                playerDiscoverState = playerDiscoverState1;
                                c++;
                                break;
                            }
                        }
                        if (playerDiscoverState != null) {
                            List<OreDiscoverState> list = playerDiscoverState.getDiscoverStateList();
                            System.out.println(list.toString());
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getMaterial() == material) {
                                    if (!list.get(i).isDiscovered()) {
                                        System.out.println("You Discovered a new material!");
                                        list.set(i, list.get(i).discover());
                                    } else {
                                        for (int z = 0; z < itemStack.getCount(); z++)
                                            System.out.println("You already discovered this material " + list.get(i).getDiscoverTimes() + " time before");
                                        list.set(i, list.get(i).alreadyDiscovered());
                                    }
                                }
                            }
                            map.set(c, new PlayerDiscoverState(playerEntity_1.getUuid(), list));
                        }
                    }
                }
            }
        }
    }
}
