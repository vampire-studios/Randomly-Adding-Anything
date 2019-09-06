package fr.arthurbambou.randomlyaddinganything.registries;

import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import fr.arthurbambou.randomlyaddinganything.api.NameGenerator;
import fr.arthurbambou.randomlyaddinganything.api.enums.AppearsIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.blocks.RAABlockItem;
import fr.arthurbambou.randomlyaddinganything.config.Config;
import fr.arthurbambou.randomlyaddinganything.helpers.Rands;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class Materials {
    public static final List<Material> MATERIAL_LIST = new ArrayList<>();

    public static boolean isReady = false;

    public static void init() {
        for (int a = 0; a < new Config().materialNumber; a++) {
            Material material = new Material();
            material.setOreType(Rands.values(OreTypes.values()));
            material.setName(NameGenerator.generate());
            int[] RGB = new int[]{Rands.rand(256),Rands.rand(256),Rands.rand(256)};
            material.setRGB(RGB);
            material.setGenerateIn(Rands.values(AppearsIn.values()));
            material.setOverlayTexture(Rands.list(OreTypes.METAL_TEXTURES));
            material.setRessourceItemTexture(Rands.list(OreTypes.GEM_TEXTURES));
            MATERIAL_LIST.add(material);
            // Debug Only
            System.out.println("\nname : " + material.getName() +
                    "\noreType : " + material.getOreType().name().toLowerCase() +
                    "\nRGB color : " + RGB[0] + "," + RGB[1] + "," + RGB[2] +
                    "\nGenerate in : " + material.getGenerateIn().name().toLowerCase() +
                    "\nOverlay Texture : " + material.getOverlayTexture().toString() +
                    "\nItem Texture : " + material.getRessourceItemTexture().toString());
        }
        isReady = true;
    }

    public static boolean isIsReady() {
        return isReady;
    }

    public static void createBlocks() {
        for (Material material : MATERIAL_LIST) {
            for (RAABlockItem.BlockType blockType : RAABlockItem.BlockType.values()) {
                Block block = new Block(Block.Settings.copy(Blocks.IRON_BLOCK));
                Registry.register(Registry.BLOCK, new Identifier("raa", material.getName().toLowerCase() + blockType.getString()), block);
                Registry.register(Registry.ITEM, new Identifier("raa", material.getName().toLowerCase() + blockType.getString()),
                        new RAABlockItem(material.getName(), block, new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP), blockType));
            }
        }
    }


}
