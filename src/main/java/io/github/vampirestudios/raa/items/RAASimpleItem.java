package io.github.vampirestudios.raa.items;

import com.ibm.icu.text.MessageFormat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Locale;

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
        MessageFormat format = new MessageFormat(new TranslatableText("text.raa.item." + getItemType().name().toLowerCase()).getString());
        Object[] data = {WordUtils.capitalize(name), WordUtils.uncapitalize(name), WordUtils.uncapitalize(name).charAt(0), WordUtils.uncapitalize(name).charAt(name.length() - 1)};
        return new LiteralText(format.format(data));
    }

    public SimpleItemType getItemType() {
        return itemType;
    }

    public enum SimpleItemType {
        GEM, INGOT, NUGGET, CRYSTAL
    }
}
