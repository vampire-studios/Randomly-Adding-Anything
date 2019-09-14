package io.github.vampirestudios.raa.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class RAAFoodItem extends Item {
    private String name;
    private SimpleItemType itemType;

    public RAAFoodItem(String name, Settings item$Settings_1, SimpleItemType itemType) {
        super(item$Settings_1);
        this.name = name;
        this.itemType = itemType;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.food." + getItemType().name().toLowerCase(), new LiteralText(name));
    }

    public SimpleItemType getItemType() {
        return itemType;
    }

    public enum SimpleItemType {
        APPLE,CARROT
    }
}
