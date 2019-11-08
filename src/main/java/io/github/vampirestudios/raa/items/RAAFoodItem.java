package io.github.vampirestudios.raa.items;

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
        return new TranslatableText("text.raa.item.food", new LiteralText(WordUtils.capitalize(name)));
    }

}
