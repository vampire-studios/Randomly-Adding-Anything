package io.github.vampirestudios.raa.items.material;

import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class RAASwordItem extends SwordItem {

    private Material material;

    public RAASwordItem(Material material, Settings settings) {
        super(material.getToolMaterial(), (int) material.getToolMaterial().getSwordAttackDamage(), 1.0F, settings);
        this.material = material;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.sword", new LiteralText(WordUtils.capitalize(material.getName())));
    }

}
