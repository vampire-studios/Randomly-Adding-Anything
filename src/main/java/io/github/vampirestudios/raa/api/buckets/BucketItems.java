package io.github.vampirestudios.raa.api.buckets;

import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.items.material.RAABucketItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;

import java.util.HashMap;
import java.util.Map;

public class BucketItems {
    private Map<Fluid, BucketItem> fluidBucketItemsMap = new HashMap<Fluid, BucketItem>();
    private Material material;

    public BucketItems(Material material) {
        this.material = material;
    }

    public BucketItem registerFluid(Fluid fluid) {
        if (!this.fluidBucketItemsMap.containsKey(fluid)) {
            this.fluidBucketItemsMap.put(fluid, new RAABucketItem(fluid, this.material));
        }
        return this.fluidBucketItemsMap.get(fluid);
    }

    public BucketItem getBucketItemFromFluid(Fluid fluid) {
        if (this.fluidBucketItemsMap.containsKey(fluid)) {
            return this.fluidBucketItemsMap.get(fluid);
        } else {
            return this.fluidBucketItemsMap.get(Fluids.EMPTY);
        }
    }

    public Material getMaterial() {
        return material;
    }
}
