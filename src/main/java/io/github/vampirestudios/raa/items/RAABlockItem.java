package io.github.vampirestudios.raa.items;

import com.ibm.icu.text.MessageFormat;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;

public class RAABlockItem extends BlockItem {
    private String name;
    private BlockType blockType;

    public RAABlockItem(String name, Block block_1, Settings item$Settings_1, BlockType blockType) {
        super(block_1, item$Settings_1);
        this.name = name;
        this.blockType = blockType;
    }

    @Override
    public Text getName() {
        return super.getName();
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        MessageFormat format = new MessageFormat(new TranslatableText("text.raa.block." + getBlockType().name().toLowerCase()).asString());
        Object[] data = {WordUtils.capitalize(name), WordUtils.uncapitalize(name), WordUtils.uncapitalize(name).charAt(0), WordUtils.uncapitalize(name).charAt(name.length() - 1)};
        return new LiteralText(format.format(data));
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public enum BlockType {
        ORE("_ore"),
        BLOCK("_block");

        private String suffix;

        BlockType(String suffix) {
            this.suffix = suffix;
        }

        public String getSuffix() {
            return suffix;
        }
    }
}
