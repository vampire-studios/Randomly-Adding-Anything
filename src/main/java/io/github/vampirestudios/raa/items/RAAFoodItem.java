package io.github.vampirestudios.raa.items;

import com.ibm.icu.text.MessageFormat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class RAAFoodItem extends Item {
    private String name;

    public RAAFoodItem(String name, Settings item$Settings_1) {
        super(item$Settings_1);
        this.name = name;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        MessageFormat format = new MessageFormat(new TranslatableText("text.raa.item.food").asString());
        Object[] data = {WordUtils.capitalize(name), WordUtils.uncapitalize(name), WordUtils.uncapitalize(name).charAt(0), WordUtils.uncapitalize(name).charAt(name.length() - 1)};
        return new LiteralText(format.format(data));
    }

}
