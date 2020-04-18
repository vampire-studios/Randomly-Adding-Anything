package io.github.vampirestudios.raa.compats.items;

import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Utils;
import io.github.vampirestudios.spontaneousbucketing.impl.BucketMaterial;
import io.github.vampirestudios.spontaneousbucketing.impl.BucketRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpontaneousBucketingItems extends ItemCompat {

    public SpontaneousBucketingItems() {super();}

    @Override
    public void generateItems() {
        for (Material material : Materials.MATERIALS) {
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                Item materialItem = Registry.ITEM.get(Utils.addSuffixToPath(material.getId(), "_ingot"));
                Registry.register(BucketRegistry.BUCKETS, material.getId(), new BucketMaterial(material.getId(), materialItem, material.getColor()));
            }
        }
        for (Material material : Materials.DIMENSION_MATERIALS) {
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                Item materialItem = Registry.ITEM.get(Utils.addSuffixToPath(material.getId(), "_ingot"));
                Registry.register(BucketRegistry.BUCKETS, material.getId(), new BucketMaterial(material.getId(), materialItem, material.getColor()));
            }
        }
    }
}
