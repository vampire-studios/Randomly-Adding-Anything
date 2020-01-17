package io.github.vampirestudios.raa.compats.items;

import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.init.BeeProdItems;
import io.github.alloffabric.beeproductive.item.NectarItem;
import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class MaterialNectarItem extends NectarItem {
    private Material material;

    public MaterialNectarItem(Material material, Nectar nectar) {
        super(nectar, new Item.Settings().group(BeeProdItems.NECTAR_GROUP));
        this.material = material;
    }

    @Override
    public Text getName() {
        return super.getName();
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.compat.beeproductive.nectar", new LiteralText(WordUtils.capitalize(this.material.getName())));
    }
}
