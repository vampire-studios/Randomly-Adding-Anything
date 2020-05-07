package io.github.vampirestudios.raa.items.dimension;

import com.ibm.icu.text.MessageFormat;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class DimensionalHoeItem extends HoeItem {

    private DimensionData dimensionData;

    public DimensionalHoeItem(DimensionData dimensionData, ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, (int) attackDamage, attackSpeed, settings);
        this.dimensionData = dimensionData;
    }

    @Override
    public Text getName(ItemStack itemStack) {
        MessageFormat format = new MessageFormat(new TranslatableText("text.raa.item.hoe").asString());
        Object[] data = {WordUtils.capitalize(dimensionData.getName()), WordUtils.uncapitalize(dimensionData.getName()),
                WordUtils.uncapitalize(dimensionData.getName()).charAt(0), WordUtils.uncapitalize(dimensionData.getName()).charAt(dimensionData.getName().length() - 1)};
        return new LiteralText(format.format(data));
    }

}
