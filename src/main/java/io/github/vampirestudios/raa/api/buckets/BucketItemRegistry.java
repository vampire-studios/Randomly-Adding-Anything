package io.github.vampirestudios.raa.api.buckets;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public class BucketItemRegistry {
    public static final DefaultedRegistry<BucketItems> BUCKET_ITEMS = Registry.create("buckets", "null", () -> null);

    public static void init() {
        for (Material material : Materials.MATERIALS) {
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                BucketItems bucketItems = new BucketItems(material);
                for (Fluid fluid : Registry.FLUID) {
                    if (fluid.isStill(fluid.getDefaultState()) || fluid == Fluids.EMPTY) {
                        BucketItem bucketItem = bucketItems.registerFluid(fluid);
                        Registry.register(Registry.ITEM, new Identifier(RandomlyAddingAnything.MOD_ID,
                                material.getId().getPath() + "_" + Registry.FLUID.getId(fluid).toString().replace("minecraft:","").replace(":","_") + "_bucket"), bucketItem);
                    }
                }
                Registry.register(BUCKET_ITEMS, material.getId(), bucketItems);
            }
        }

        for (Material material : Materials.DIMENSION_MATERIALS) {
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                BucketItems bucketItems = new BucketItems(material);
                for (Fluid fluid : Registry.FLUID) {
                    if (fluid.isStill(fluid.getDefaultState()) || fluid == Fluids.EMPTY) {
                        BucketItem bucketItem = bucketItems.registerFluid(fluid);
                        Registry.register(Registry.ITEM, new Identifier(RandomlyAddingAnything.MOD_ID,
                                material.getId().getPath() + "_" + Registry.FLUID.getId(fluid).toString().replace("minecraft:","").replace(":","_") + "_bucket"), bucketItem);
                    }
                }
                Registry.register(BUCKET_ITEMS, material.getId(), bucketItems);
            }
        }

        RegistryEntryAddedCallback.event(Registry.FLUID).register((i, identifier, fluid) -> {
            if (fluid.isStill(fluid.getDefaultState())) {
                for (BucketItems bucketItems : BUCKET_ITEMS) {
                    BucketItem bucketItem = bucketItems.registerFluid(fluid);
                    Registry.register(Registry.ITEM, new Identifier(RandomlyAddingAnything.MOD_ID,
                            bucketItems.getMaterial().getId().getPath() + "_" + Registry.FLUID.getId(fluid).toString().replace("minecraft:","").replace(":","_") + "_bucket"), bucketItem);
                }
            }
        });
    }
}
