package io.github.vampirestudios.raa.items.material;

import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class RAAHoeItem extends HoeItem {

    private Material material;

    public RAAHoeItem(Material material, ToolMaterial toolMaterial_1, float float_1, Settings item$Settings_1) {
        super(toolMaterial_1, float_1, item$Settings_1);
        this.material = material;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.hoe", new LiteralText(WordUtils.capitalize(material.getName())));
    }

}
