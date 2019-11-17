package io.github.vampirestudios.raa.compats.items;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
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
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

public class TechRebornItems extends ItemCompat {
    private ItemGroup POWDER_ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(RandomlyAddingAnything.MOD_ID, "tr_dust"), () -> new ItemStack(Items.GUNPOWDER));

    public TechRebornItems() {super();}

    @Override
    public void generateItems() {
        for (Material material : Materials.MATERIALS) {
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                RegistryUtils.registerItem(new OrePowder(material.getName(), new Item.Settings().group(POWDER_ITEM_GROUP)), Utils.appendToPath(material.getId(), "_dust"));
            }
        }
    }
}
