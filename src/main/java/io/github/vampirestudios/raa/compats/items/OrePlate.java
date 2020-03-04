package io.github.vampirestudios.raa.compats.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class OrePlate extends Item {
    private String name;

    public OrePlate(String name, Settings settings) {
        super(settings);
        this.name = name;
    }

    @Override
    public Text getName() {
        return super.getName();
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.compat.techreborn.plate", new LiteralText(WordUtils.capitalize(name)));
    }
}
