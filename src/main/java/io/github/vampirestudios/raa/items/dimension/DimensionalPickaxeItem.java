package io.github.vampirestudios.raa.items.dimension;

import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class DimensionalPickaxeItem extends PickaxeItem {

    private DimensionData dimensionData;

    public DimensionalPickaxeItem(DimensionData dimensionData, ToolMaterial toolMaterial_1, int int_1, float float_1, Settings item$Settings_1) {
        super(toolMaterial_1, int_1, float_1, item$Settings_1);
        this.dimensionData = dimensionData;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.pickaxe", new LiteralText(WordUtils.capitalize(dimensionData.getName())));
    }

}
