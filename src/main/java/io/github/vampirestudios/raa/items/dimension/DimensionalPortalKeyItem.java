package io.github.vampirestudios.raa.items.dimension;

import com.ibm.icu.text.MessageFormat;
import io.github.vampirestudios.raa.blocks.CustomPortalBlock;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import org.apache.commons.lang3.text.WordUtils;

public class DimensionalPortalKeyItem extends Item {

    private final DimensionData dimensionData;

    public DimensionalPortalKeyItem(DimensionData dimensionData) {
        super(new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
        this.dimensionData = dimensionData;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        /*Block block = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_custom_portal"));
        if(block == net.minecraft.block.Blocks.AIR) block = Blocks.OBSIDIAN;
        NetherPortalBlock

        if(context.getWorld().getBlockState(context.getBlockPos()).getBlock() == block) {
//            MinerPortalBlock.createPortalAtTesting(dimensionData, context.getWorld(), context.getBlockPos().up());
        }*/
        CustomPortalBlock.createPortalAt(dimensionData, context.getWorld(), context.getBlockPos().up());
        return super.useOnBlock(context);
    }

    @Override
    public Text getName(ItemStack stack) {
        MessageFormat format = new MessageFormat(new TranslatableText("text.raa.item.portal_key").getString());
        Object[] data = {WordUtils.capitalize(dimensionData.getName()), WordUtils.uncapitalize(dimensionData.getName()),
                WordUtils.uncapitalize(dimensionData.getName()).charAt(0), WordUtils.uncapitalize(dimensionData.getName()).charAt(dimensionData.getName().length() - 1)};
        return new LiteralText(format.format(data));
    }
}
