package io.github.vampirestudios.raa.items;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.DebugMessagesBuilder;
import io.github.vampirestudios.raa.registries.Materials;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class RAADebugItem extends Item {

    public RAADebugItem() {
        super(new Settings().group(RandomlyAddingAnything.RAA_RESOURCES).maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext itemUsageContext_1) {
        BlockPos blockPos = itemUsageContext_1.getBlockPos();
        World world = itemUsageContext_1.getWorld();
        Block block = world.getBlockState(blockPos).getBlock();
        Identifier identifier = Registry.BLOCK.getId(block);
        if (!identifier.getNamespace().equals(RandomlyAddingAnything.MOD_ID)) return ActionResult.FAIL;
        Materials.MATERIALS.forEach(material -> {
            if (identifier.getPath().equals(material.getName().toLowerCase() + "_ore") || identifier.getPath().equals(material.getName().toLowerCase() + "_block")) {
                DebugMessagesBuilder.create(material, itemUsageContext_1.getPlayer())
                        .name().oreType().generatesIn().textures().armor().weapons().tools().glowing().oreFlower();
            }
        });
        return ActionResult.PASS;
    }
}
