package fr.arthurbambou.randomlyaddinganything.items;

import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import fr.arthurbambou.randomlyaddinganything.api.enums.TextureType;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
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
        if (identifier.getNamespace() != RandomlyAddingAnything.MOD_ID) return ActionResult.FAIL;
        for (Material material : Materials.MATERIAL_LIST) {
            if (identifier.getPath().equals(material.getName().toLowerCase() + "_ore") || identifier.getPath().equals(material.getName().toLowerCase() + "_block")) {
                System.out.println("\nname : " + material.getName() +
                        "\noreType : " + material.getOreInformation().getOreType().name().toLowerCase() +
                        "\nGenerate in : " + material.getOreInformation().getGenerateIn().name().toLowerCase() +
                        "\nOverlay Texture : " + material.getTEXTURES().get(TextureType.ORE_OVERLAY).toString() +
                        "\nResource Item Texture : " + material.getTEXTURES().get(TextureType.RESOURCE_ITEM).toString() +
                        "\nHas Armor : " + material.hasArmor() +
                        "\nHas Weapons : " + material.hasWeapons() +
                        "\nHas Tools : " + material.hasTools() +
                        "\nIs Glowing : " + material.isGlowing() +
                        "\nHas Ore Flower : " + material.hasOreFlower()
                );
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
