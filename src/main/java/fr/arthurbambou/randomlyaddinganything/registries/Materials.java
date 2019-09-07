package fr.arthurbambou.randomlyaddinganything.registries;

import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import fr.arthurbambou.randomlyaddinganything.api.NameGenerator;
import fr.arthurbambou.randomlyaddinganything.api.enums.AppearsIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.blocks.LayeredOreBlock;
import fr.arthurbambou.randomlyaddinganything.client.Color;
import fr.arthurbambou.randomlyaddinganything.helpers.Rands;
import fr.arthurbambou.randomlyaddinganything.items.*;
import fr.arthurbambou.randomlyaddinganything.materials.CustomArmorMaterial;
import fr.arthurbambou.randomlyaddinganything.materials.CustomToolMaterial;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import fr.arthurbambou.randomlyaddinganything.materials.MaterialBuilder;
import fr.arthurbambou.randomlyaddinganything.utils.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Materials {
    public static final List<Material> MATERIAL_LIST = new ArrayList<>();

    public static boolean isReady = false;

    public static void init() {
        for (int a = 0; a < RandomlyAddingAnything.CONFIG.materialNumber; a++) {
            Color RGB = new Color(Rands.rand(256),Rands.rand(256),Rands.rand(256));
            Random random = new Random();
            Material material = MaterialBuilder.create()
                    .oreType(Rands.values(OreTypes.values())).name(NameGenerator.generate()).color(RGB.getColor())
                    .generatesIn(Rands.values(AppearsIn.values())).overlayTexture()
                    .resourceItemTexture().storageBlockTexture().armor(random.nextBoolean())
                    .tools(random.nextBoolean()).weapons(random.nextBoolean()).glowing(random.nextBoolean()).build();
            MATERIAL_LIST.add(material);
            // Debug Only
            if (RandomlyAddingAnything.CONFIG.debug) {
                System.out.println("\nname : " + material.getName() +
                        "\noreType : " + material.getOreInformation().getOreType().name().toLowerCase() +
                        "\nRGB color : " + RGB.getRed() + "," + RGB.getGreen() + "," + RGB.getBlue() +
                        "\nGenerate in : " + material.getOreInformation().getGenerateIn().name().toLowerCase() +
                        "\nOverlay Texture : " + material.getOreInformation().getOverlayTexture().toString() +
                        "\nResource Item Texture : " + material.getResourceItemTexture().toString() +
                        "\nHas Armor : " + material.hasArmor() +
                        "\nHas Weapons : " + material.hasWeapons() +
                        "\nHas Tools : " + material.hasTools() +
                        "\nIs Glowing : " + material.isGlowing());
            }
        }
        isReady = true;
    }

    public static boolean isIsReady() {
        return isReady;
    }

    public static void createMaterialResources() {
        for (Material material : MATERIAL_LIST) {
            Item repairItem;
            RegistryUtils.register(new Block(Block.Settings.copy(Blocks.IRON_BLOCK)),
                    new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"), RandomlyAddingAnything.ITEM_GROUP);
            RegistryUtils.register(new LayeredOreBlock(material, Block.Settings.copy(Blocks.IRON_ORE)),
                    new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore"), RandomlyAddingAnything.ITEM_GROUP);
            if (material.getOreInformation().getOreType() == OreTypes.METAL) {
                RegistryUtils.registerItem(repairItem = new Item(new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP)), new Identifier(RandomlyAddingAnything.MOD_ID,
                        material.getName().toLowerCase() + "_ingot"));
                RegistryUtils.registerItem(new Item(new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP)), new Identifier(RandomlyAddingAnything.MOD_ID,
                        material.getName().toLowerCase() + "_nugget"));
            } else {
                RegistryUtils.registerItem(repairItem = new Item(new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP)), new Identifier(RandomlyAddingAnything.MOD_ID,
                        material.getName().toLowerCase() + "_gem"));
            }
            if (material.hasArmor()) {
                RegistryUtils.registerItem(
                        new RAAArmorItem(new CustomArmorMaterial(Ingredient.ofItems(repairItem), material.getName().toLowerCase()),
                                EquipmentSlot.HEAD, (new Item.Settings()).group(RandomlyAddingAnything.ITEM_GROUP)),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_helmet")
                );
                RegistryUtils.registerItem(
                        new RAAArmorItem(new CustomArmorMaterial(Ingredient.ofItems(repairItem), material.getName().toLowerCase()),
                                EquipmentSlot.CHEST, (new Item.Settings()).group(RandomlyAddingAnything.ITEM_GROUP)),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_chestplate")
                );
                RegistryUtils.registerItem(
                        new RAAArmorItem(new CustomArmorMaterial(Ingredient.ofItems(repairItem), material.getName().toLowerCase()),
                                EquipmentSlot.LEGS, (new Item.Settings()).group(RandomlyAddingAnything.ITEM_GROUP)),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_leggings")
                );
                RegistryUtils.registerItem(
                        new RAAArmorItem(new CustomArmorMaterial(Ingredient.ofItems(repairItem), material.getName().toLowerCase()),
                                EquipmentSlot.FEET, (new Item.Settings()).group(RandomlyAddingAnything.ITEM_GROUP)),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_boots")
                );
            }
            if (material.hasTools()) {
                RegistryUtils.registerItem(
                        new RAAPickaxeItem(
                                new CustomToolMaterial(Ingredient.ofItems(repairItem), 100, 1.0F, 1.0F, 3),
                                10, 2.0F, new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP).recipeRemainder(repairItem)
                        ),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_pickaxe")
                );
                RegistryUtils.registerItem(
                        new RAAAxeItem(
                                new CustomToolMaterial(Ingredient.ofItems(repairItem), 100, 1.0F, 1.0F, 3),
                                10, 2.0F, new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP).recipeRemainder(repairItem)
                        ),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_axe")
                );
                RegistryUtils.registerItem(
                        new RAAShovelItem(
                                new CustomToolMaterial(Ingredient.ofItems(repairItem), 100, 1.0F, 1.0F, 3),
                                10, 2.0F, new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP).recipeRemainder(repairItem)
                        ),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_shovel")
                );
                RegistryUtils.registerItem(
                        new RAAHoeItem(
                                new CustomToolMaterial(Ingredient.ofItems(repairItem), 100, 1.0F, 1.0F, 3),
                                2.0F, new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP).recipeRemainder(repairItem)
                        ),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_hoe")
                );
            }
        }
    }

}
