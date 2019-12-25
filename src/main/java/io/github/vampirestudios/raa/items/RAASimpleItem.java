package io.github.vampirestudios.raa.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class RAASimpleItem extends Item {
    private String name;
    private SimpleItemType itemType;

    public RAASimpleItem(String name, Settings item$Settings_1, SimpleItemType itemType) {
        super(item$Settings_1);
        this.name = name;
        this.itemType = itemType;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item." + getItemType().name().toLowerCase(), new LiteralText(WordUtils.capitalize(name)));
    }

    public SimpleItemType getItemType() {
        return itemType;
    }

    public enum SimpleItemType {
        GEM, INGOT, NUGGET, CRYSTAL
    }
}
