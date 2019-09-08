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
    public static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};

    public static void init() {
        for (int a = 0; a < RandomlyAddingAnything.CONFIG.materialNumber; a++) {
            Color RGB = new Color(Rands.randInt(256),Rands.randInt(256),Rands.randInt(256));
            Random random = new Random();
            Material material = MaterialBuilder.create()
                    .oreType(Rands.values(OreTypes.values())).name(NameGenerator.generate()).color(RGB.getColor())
                    .generatesIn(Rands.values(AppearsIn.values())).overlayTexture()
                    .resourceItemTexture().storageBlockTexture().armor((random.nextBoolean() && random.nextBoolean()))
                    .tools((random.nextBoolean() && random.nextBoolean()))
                    .weapons((random.nextBoolean() && random.nextBoolean())).glowing((random.nextBoolean() && random.nextBoolean()))
                    .build();
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
                    new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"), RandomlyAddingAnything.ITEM_GROUP, material.getName(), RAABlockItem.BlockType.BLOCK);
            RegistryUtils.register(new LayeredOreBlock(material, Block.Settings.copy(Blocks.IRON_ORE)),
                    new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore"), RandomlyAddingAnything.ITEM_GROUP, material.getName(), RAABlockItem.BlockType.ORE);
            if (material.getOreInformation().getOreType() == OreTypes.METAL) {
                RegistryUtils.registerItem(repairItem = new RAASimpleItem(material.getName(), new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP),
                                RAASimpleItem.SimpleItemType.INGOT), new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot"));
                RegistryUtils.registerItem(new RAASimpleItem(material.getName(), new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP),
                        RAASimpleItem.SimpleItemType.NUGGET), new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_nugget"));
            } else if (material.getOreInformation().getOreType() == OreTypes.GEM) {
                RegistryUtils.registerItem(repairItem = new RAASimpleItem(material.getName(), new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP),
                        RAASimpleItem.SimpleItemType.GEM), new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_gem"));
            } else {
                RegistryUtils.registerItem(repairItem = new RAASimpleItem(material.getName(), new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP),
                        RAASimpleItem.SimpleItemType.CRYSTAL), new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_crystal"));
            }
            if (material.hasArmor()) {
                RegistryUtils.registerItem(
                        new RAAArmorItem(material,
                                EquipmentSlot.HEAD, (new Item.Settings()).group(RandomlyAddingAnything.ITEM_GROUP)),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_helmet")
                );
                RegistryUtils.registerItem(
                        new RAAArmorItem(material,
                                EquipmentSlot.CHEST, (new Item.Settings()).group(RandomlyAddingAnything.ITEM_GROUP)),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_chestplate")
                );
                RegistryUtils.registerItem(
                        new RAAArmorItem(material,
                                EquipmentSlot.LEGS, (new Item.Settings()).group(RandomlyAddingAnything.ITEM_GROUP)),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_leggings")
                );
                RegistryUtils.registerItem(
                        new RAAArmorItem(material,
                                EquipmentSlot.FEET, (new Item.Settings()).group(RandomlyAddingAnything.ITEM_GROUP)),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_boots")
                );
            }
            if (material.hasTools()) {
                RegistryUtils.registerItem(
                        new RAAPickaxeItem(material,
                                material.getToolMaterial(),
                                1, -2.8F, new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP).recipeRemainder(repairItem)
                        ),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_pickaxe")
                );
                RegistryUtils.registerItem(
                        new RAAAxeItem(material,
                                material.getToolMaterial(),
                                (5.0F + material.getToolMaterial().getAxeAttackDamage()), (-3.2F + material.getToolMaterial().getAxeAttackSpeed()), new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP).recipeRemainder(repairItem)
                        ),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_axe")
                );
                RegistryUtils.registerItem(
                        new RAAShovelItem(material,
                                material.getToolMaterial(),
                                1.5F, -3.0F, new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP).recipeRemainder(repairItem)
                        ),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_shovel")
                );
                RegistryUtils.registerItem(
                        new RAAHoeItem(material,
                                material.getToolMaterial(),
                                (-3.0F + material.getToolMaterial().getHoeAttackSpeed()), new Item.Settings().group(RandomlyAddingAnything.ITEM_GROUP).recipeRemainder(repairItem)
                        ),
                        new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_hoe")
                );
            }
        }
    }

}
