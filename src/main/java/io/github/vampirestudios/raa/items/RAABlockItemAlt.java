package io.github.vampirestudios.raa.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class RAABlockItemAlt extends BlockItem {
    private String name, type;

    public RAABlockItemAlt(String name, String type, Block block_1, Settings item$Settings_1) {
        super(block_1, item$Settings_1);
        this.name = name;
        this.type = type;
    }

    @Override
    public Text getName() {
        return super.getName();
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.block." + type, new LiteralText(WordUtils.capitalize(name)));
    }

}