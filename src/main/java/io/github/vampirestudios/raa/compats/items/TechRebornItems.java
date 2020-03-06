package io.github.vampirestudios.raa.compats.items;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.RegistryUtils;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class TechRebornItems extends ItemCompat {
    private ItemGroup DUST_ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(RandomlyAddingAnything.MOD_ID, "tr_dust"), () -> new ItemStack(Items.GUNPOWDER));
    private ItemGroup PLATE_ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(RandomlyAddingAnything.MOD_ID, "tr_plate"), () -> new ItemStack(Items.GUNPOWDER));
    private ItemGroup GEAR_ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(RandomlyAddingAnything.MOD_ID, "tr_gear"), () -> new ItemStack(Items.GUNPOWDER));

    public TechRebornItems() {
        super();
    }

    @Override
    public void generateItems() {
        for (Material material : Materials.MATERIALS) {
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                RegistryUtils.registerItem(new OrePowder(material.getName(), new Item.Settings().group(DUST_ITEM_GROUP)), Utils.addSuffixToPath(material.getId(), "_dust"));
                RegistryUtils.registerItem(new OrePowder(material.getName(), new Item.Settings().group(DUST_ITEM_GROUP)), Utils.addSuffixToPath(material.getId(), "_small_dust"));
                RegistryUtils.registerItem(new OrePlate(material.getName(), new Item.Settings().group(PLATE_ITEM_GROUP)), Utils.addSuffixToPath(material.getId(), "_plate"));
                RegistryUtils.registerItem(new OreGear(material.getName(), new Item.Settings().group(GEAR_ITEM_GROUP)), Utils.addSuffixToPath(material.getId(), "_gear"));
            }
        }
    }
}
