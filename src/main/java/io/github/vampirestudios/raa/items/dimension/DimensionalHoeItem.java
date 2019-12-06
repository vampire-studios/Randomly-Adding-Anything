package io.github.vampirestudios.raa.items.dimension;

import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class DimensionalHoeItem extends HoeItem {

    private DimensionData dimensionData;

    public DimensionalHoeItem(DimensionData dimensionData, ToolMaterial toolMaterial, float attackSpeed, Settings settings) {
        super(toolMaterial, attackSpeed, settings);
        this.dimensionData = dimensionData;
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return new TranslatableText("text.raa.item.hoe", new LiteralText(WordUtils.capitalize(dimensionData.getName())));
    }

}
