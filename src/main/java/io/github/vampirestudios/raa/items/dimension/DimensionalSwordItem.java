package io.github.vampirestudios.raa.items.dimension;

import com.ibm.icu.text.MessageFormat;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class DimensionalSwordItem extends SwordItem {

    private DimensionData dimensionData;

    public DimensionalSwordItem(ToolMaterial toolMaterial, DimensionData dimensionData, Settings settings) {
        super(toolMaterial, 3, -2.4F, settings);
        this.dimensionData = dimensionData;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        MessageFormat format = new MessageFormat(new TranslatableText("text.raa.item.sword").asString());
        Object[] data = {WordUtils.capitalize(dimensionData.getName()), WordUtils.uncapitalize(dimensionData.getName()),
                WordUtils.uncapitalize(dimensionData.getName()).charAt(0), WordUtils.uncapitalize(dimensionData.getName()).charAt(dimensionData.getName().length() - 1)};
        return new LiteralText(format.format(data));
    }

}
