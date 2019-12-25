package io.github.vampirestudios.raa.items;

import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class RAAShearItem extends ShearsItem {

    private Material material;

    public RAAShearItem(Material material, Settings item$Settings_1) {
        super(item$Settings_1);
        this.material = material;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.shears", new LiteralText(WordUtils.capitalize(material.getName())));
    }

}
