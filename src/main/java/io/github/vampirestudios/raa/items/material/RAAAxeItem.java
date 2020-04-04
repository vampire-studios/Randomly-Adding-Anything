package io.github.vampirestudios.raa.items.material;

import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class RAAAxeItem extends AxeItem {

    private final Material material;

    public RAAAxeItem(Material material, ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.axe", new LiteralText(WordUtils.capitalize(material.getName())));
    }

}
