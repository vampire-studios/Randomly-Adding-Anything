package io.github.vampirestudios.raa.compats.items;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.vampirestudios.raa.compats.BeeProductiveCompat;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public class BeeProductiveCompatItems extends ItemCompat {

    public BeeProductiveCompatItems() {}

    @Override
    public void generateItems() {
        for (Map.Entry<Material, Integer> entry : BeeProductiveCompat.materialStringMap.entrySet()) {
            HoneyFlavor honeyFlavor = Registry.register(BeeProductive.HONEY_FLAVORS, entry.getKey().getId(),
                    new HoneyFlavor(new ItemStack(entry.getKey().getMaterialResourceItem(), entry.getValue().intValue()), new ItemStack(Items.HONEY_BOTTLE)));
            Nectar nectar = Registry.register(BeeProductive.NECTARS, entry.getKey().getId(), (beeEntity, beehive) -> beehive.addHoneyFlavor(honeyFlavor));
            Item item = Registry.register(Registry.ITEM, Utils.appendToPath(entry.getKey().getId(), "_nectar"),
                    new MaterialNectarItem(entry.getKey(), nectar));
        }
    }
}
