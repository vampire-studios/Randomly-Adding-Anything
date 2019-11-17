package io.github.vampirestudios.raa.items.dimension;

import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class DimensionalShovelItem extends ShovelItem {

    private DimensionData dimensionData;

    public DimensionalShovelItem(DimensionData dimensionData, ToolMaterial toolMaterial_1, float attackDamage, float attackSpeed, Settings item$Settings_1) {
        super(toolMaterial_1, attackDamage, attackSpeed, item$Settings_1);
        this.dimensionData = dimensionData;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.shovel", new LiteralText(WordUtils.capitalize(dimensionData.getName())));
    }

}
