package io.github.vampirestudios.raa.items.dimension;

import com.ibm.icu.text.MessageFormat;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
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
        MessageFormat format = new MessageFormat(new TranslatableText("text.raa.item.shovel").asString());
        Object[] data = {WordUtils.capitalize(dimensionData.getName()), WordUtils.uncapitalize(dimensionData.getName()),
                WordUtils.uncapitalize(dimensionData.getName()).charAt(0), WordUtils.uncapitalize(dimensionData.getName()).charAt(dimensionData.getName().length() - 1)};
        return new LiteralText(format.format(data));
    }

}
