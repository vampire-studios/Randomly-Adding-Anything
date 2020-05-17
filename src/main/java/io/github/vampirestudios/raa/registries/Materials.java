package io.github.vampirestudios.raa.registries;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.RAARegisteries;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.api.namegeneration.NameGenerator;
import io.github.vampirestudios.raa.blocks.LayeredOreBlock;
import io.github.vampirestudios.raa.blocks.RAABlock;
import io.github.vampirestudios.raa.effects.MaterialEffects;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.generation.materials.DimensionMaterial;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.generation.materials.data.MaterialFoodData;
import io.github.vampirestudios.raa.items.*;
import io.github.vampirestudios.raa.items.material.*;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.RegistryUtils;
import io.github.vampirestudios.raa.utils.Utils;
import io.github.vampirestudios.raa.utils.debug.ConsolePrinting;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import io.github.vampirestudios.vampirelib.utils.Color;
import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.predicate.block.BlockPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class Materials {
    public static final Set<Identifier> MATERIAL_IDS = new HashSet<>();
    public static final DefaultedRegistry<Material> MATERIALS = Registry.create("materials", "null", () -> null);
    public static final Set<Identifier> DIMENSION_MATERIAL_IDS = new HashSet<>();
    public static final DefaultedRegistry<DimensionMaterial> DIMENSION_MATERIALS = Registry.create("dimension_materials", "null", () -> null);
    public static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    public static boolean ready = false;
    public static boolean dimensionReady = false;

    public static void generate() {
        for (int a = 0; a < RandomlyAddingAnything.CONFIG.materialGenAmount; a++) {
            Color RGB = Rands.randColor();
            NameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialNameGenerator();

            String name;
            Identifier id;

            do {
                String generatedName = nameGenerator.generate();
                name = generatedName;
                id = new Identifier(RandomlyAddingAnything.MOD_ID, nameGenerator.asId(generatedName));
            } while (MATERIAL_IDS.contains(id));
            MATERIAL_IDS.add(id);

            MaterialFoodData materialFoodData = MaterialFoodData.Builder.create()
                    .alwaysEdible(Rands.chance(10))
                    .hunger(Rands.randIntRange(4, 30))
                    .meat(Rands.chance(5))
                    .saturationModifier(Rands.randFloatRange(1.0F, 4.0F))
                    .snack(Rands.chance(10))
                    .build();

            Material material = Material.Builder.create(id, name)
                    .oreType(Rands.values(OreType.values()))
                    .color(RGB.getColor())
                    .foodData(materialFoodData)
                    .target(Objects.requireNonNull(RAARegisteries.TARGET_REGISTRY.getRandom(Rands.getRandom())).getId())
                    .armor(Rands.chance(2))
                    .tools(!Rands.chance(3))
                    .oreFlower(Rands.chance(4))
                    .weapons(!Rands.chance(3))
                    .glowing(Rands.chance(4))
                    .minXPAmount(0)
                    .maxXPAmount(Rands.randIntRange(0, 4))
                    .oreClusterSize(Rands.randIntRange(2, 9))
                    .food(Rands.chance(4))
                    .compostbleAmount(Rands.randFloatRange(0.3F, 3.0F))
                    .compostable(Rands.chance(10))
                    .beaconBase(Rands.chance(10))
                    .specialEffects(generateSpecialEffects())
                    .build();

            Registry.register(MATERIALS, id, material);

            // Debug Only
            if (RandomlyAddingAnything.CONFIG.debug) {
                ConsolePrinting.materialDebug(material, RGB);
            }
        }
        ready = true;
    }

    private static Map<MaterialEffects, JsonElement> generateSpecialEffects() {
        Map<MaterialEffects, JsonElement> effects = new HashMap<>();

        //50% of materials have an effect
        if (Rands.chance(2)) {

            //generate a few effects
            for (int i = 0; i < Rands.randIntRange(1, 3); i++) {
                JsonElement e = new JsonObject();

                //get an effect from a weighted list
                MaterialEffects effect = Utils.EFFECT_LIST.pickRandom(Rands.getRandom());
                effect.jsonConsumer.accept(e);
                effects.put(effect, e);
            }
        }

        return effects;
    }

    public static void generateDimensionMaterials() {
        for (DimensionData dimensionData : Dimensions.DIMENSIONS) {
            for (int a = 0; a < Rands.randIntRange(0, RandomlyAddingAnything.CONFIG.dimensionMaterialGenAmount); a++) {
                int[] dimensionColors = Color.intToRgb(dimensionData.getDimensionColorPalette().getSkyColor());
                Color RGB = Rands.randColor();
                Color color = new Color(RGB.getRed() + dimensionColors[0], RGB.getBlue() + dimensionColors[1], RGB.getGreen() + dimensionColors[2]);
                Random random = Rands.getRandom();
                NameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialNameGenerator();

                String name;
                Identifier id;
                do {
                    String generatedName = nameGenerator.generate();
                    name = dimensionData.getName() + " " + generatedName;
                    id = new Identifier(RandomlyAddingAnything.MOD_ID, nameGenerator.asId(dimensionData.getName() + "_" + generatedName));
                } while (DIMENSION_MATERIAL_IDS.contains(id));
                DIMENSION_MATERIAL_IDS.add(id);

                MaterialFoodData materialFoodData = MaterialFoodData.Builder.create()
                        .alwaysEdible(Rands.chance(10))
                        .hunger(Rands.randIntRange(4, 30))
                        .meat(Rands.chance(5))
                        .saturationModifier(Rands.randFloatRange(1.0F, 4.0F))
                        .snack(Rands.chance(10))
                        .build();

                Identifier stoneName = Utils.addSuffixToPath(dimensionData.getId(), "_stone");
                Block block = Registry.BLOCK.get(stoneName);
                RegistryUtils.registerOreTarget(stoneName, new OreFeatureConfig.Target(stoneName, new BlockPredicate(block), block));
                DimensionMaterial material = DimensionMaterial.Builder.create(id, name)
                        .oreType(Rands.values(OreType.values()))
                        .color(color.getColor())
                        .target(stoneName)
                        .foodData(materialFoodData)
                        .armor(random.nextBoolean())
                        .tools(!Rands.chance(3))
                        .oreFlower(Rands.chance(4))
                        .weapons(!Rands.chance(3))
                        .glowing(Rands.chance(4))
                        .minXPAmount(0)
                        .maxXPAmount(Rands.randIntRange(0, 4))
                        .oreCount(Rands.randInt(19) + 1)
                        .oreClusterSize(Rands.randIntRange(2, 6))
                        .miningLevel(Rands.randInt(4))
                        .food(Rands.chance(4))
                        .compostbleAmount(Rands.randFloatRange(0.3F, 3.0F))
                        .compostable(Rands.chance(10))
                        .beaconBase(Rands.chance(10))
                        .build();

                Registry.register(DIMENSION_MATERIALS, id, material);

                // Debug Only
                if (RandomlyAddingAnything.CONFIG.debug) {
                    ConsolePrinting.materialDebug(material, RGB);
                }
            }
        }
        dimensionReady = true;
    }

    public static void createMaterialResources() {
        MATERIALS.forEach(material -> {
            Identifier identifier = material.getId();
            Item repairItem;
            FabricBlockSettings blockSettings;
            Block idk = Objects.requireNonNull(RAARegisteries.TARGET_REGISTRY.get(material.getOreInformation().getTargetId()), "Invalid target! " + material.getOreInformation().getTargetId().toString()).getBlock();
            if (material.getOreInformation().getTargetId() != CustomTargets.DOES_NOT_APPEAR.getId()) {
                blockSettings = FabricBlockSettings.copy(idk != null ? idk : Blocks.STONE);
            } else {
                blockSettings = FabricBlockSettings.copy(Blocks.STONE);
            }

            Block baseBlock = material.getOreInformation().getTargetId() != CustomTargets.DOES_NOT_APPEAR.getId() ? idk != null ? idk : Blocks.STONE : Blocks.STONE;
            net.minecraft.block.Material baseBlockMaterial = baseBlock.getDefaultState().getMaterial();
            if (baseBlockMaterial == net.minecraft.block.Material.STONE) {
                blockSettings.breakByTool(FabricToolTags.PICKAXES, material.getMiningLevel());
            } else if (baseBlockMaterial == net.minecraft.block.Material.SOIL) {
                blockSettings.breakByTool(FabricToolTags.SHOVELS, material.getMiningLevel());
            } else {
                blockSettings.breakByHand(true);
            }

             Block block = RegistryUtils.register(
                    new RAABlock(),
                    Utils.addSuffixToPath(identifier, "_block"),
                    RandomlyAddingAnything.RAA_RESOURCES,
                    material.getName(),
                    RAABlockItem.BlockType.BLOCK
            );
            if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(Registry.ITEM.get(Registry.BLOCK.getId(block)), material.getCompostableAmount());
            if (!material.getOreInformation().getTargetId().toString().equals(CustomTargets.DOES_NOT_APPEAR.getId().toString())) {
                RegistryUtils.register(
                        new LayeredOreBlock(material, blockSettings.build()),
                        Utils.addSuffixToPath(identifier, "_ore"),
                        RandomlyAddingAnything.RAA_ORES,
                        material.getName(),
                        RAABlockItem.BlockType.ORE);
//                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(Registry.ITEM.get(Registry.BLOCK.getId(block2)), material.getCompostableAmount());
            }
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                Item item;
                item = RegistryUtils.registerItem(
                        repairItem = new RAASimpleItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_RESOURCES),
                                RAASimpleItem.SimpleItemType.INGOT
                        ),
                        Utils.addSuffixToPath(identifier, "_ingot")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount() - 1.0F);
                item = RegistryUtils.registerItem(
                        new RAASimpleItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_RESOURCES),
                                RAASimpleItem.SimpleItemType.NUGGET
                        ),
                        Utils.addSuffixToPath(identifier, "_nugget")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount() - 0.5F);
            } else if (material.getOreInformation().getOreType() == OreType.GEM) {
                Item item = RegistryUtils.registerItem(
                        repairItem = new RAASimpleItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_RESOURCES),
                                RAASimpleItem.SimpleItemType.GEM
                        ),
                        Utils.addSuffixToPath(identifier, "_gem")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount() + 0.5F);
            } else {
                Item item = RegistryUtils.registerItem(
                        repairItem = new RAASimpleItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_RESOURCES),
                                RAASimpleItem.SimpleItemType.CRYSTAL
                        ),
                        Utils.addSuffixToPath(identifier, "_crystal")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount() + 0.5F);
            }
            if (material.hasArmor()) {
                Item item;
                item = RegistryUtils.registerItem(
                        new RAAArmorItem(
                                material,
                                EquipmentSlot.HEAD,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_ARMOR).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_helmet")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAArmorItem(
                                material,
                                EquipmentSlot.CHEST,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_ARMOR).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_chestplate")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAArmorItem(
                                material,
                                EquipmentSlot.LEGS,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_ARMOR).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_leggings")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAArmorItem(
                                material,
                                EquipmentSlot.FEET,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_ARMOR).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_boots")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAHorseArmorItem(material),
                        Utils.addSuffixToPath(identifier, "_horse_armor")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
            }
            if (material.hasTools()) {
                Item item;
                item = RegistryUtils.registerItem(
                        new RAAPickaxeItem(
                                material,
                                material.getToolMaterial(),
                                1,
                                -2.8F,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_pickaxe")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAAxeItem(
                                material,
                                material.getToolMaterial(),
                                5.0F + material.getToolMaterial().getAxeAttackDamage(),
                                -3.2F + material.getToolMaterial().getAxeAttackSpeed(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_axe")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAShovelItem(
                                material,
                                material.getToolMaterial(),
                                1.5F,
                                -3.0F,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_shovel")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAHoeItem(
                                material,
                                material.getToolMaterial(),
                                1.5F,
                                -3.0F + material.getToolMaterial().getHoeAttackSpeed(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_hoe")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAShearItem(
                                material,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_shears")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new FabricShield(
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem),
                                material.getToolMaterial().getCooldownTicks(),
                                material.getToolMaterial().getDurability(),
                                repairItem
                        ),
                        Utils.addSuffixToPath(identifier, "_shield")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
            }
            if (material.hasWeapons()) {
                Item item = RegistryUtils.registerItem(
                        new RAASwordItem(
                                material,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_WEAPONS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_sword")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
            }
            if (material.hasFood()) {
                FoodComponent.Builder foodComponent = new FoodComponent.Builder();
                if (material.getFoodData().isAlwaysEdible()) foodComponent.alwaysEdible();
                if (material.getFoodData().isMeat()) foodComponent.meat();
                if (material.getFoodData().isSnack()) foodComponent.snack();
                foodComponent.hunger(material.getFoodData().getHunger());
                foodComponent.saturationModifier(material.getFoodData().getSaturationModifier());

                Item item = RegistryUtils.registerItem(
                        new RAAFoodItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_FOOD).food(foodComponent.build())
                        ),
                        Utils.addSuffixToPath(identifier, "_fruit")
                );

                CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
            }
        });
    }

    public static void createDimensionMaterialResources() {
        DIMENSION_MATERIALS.forEach(material -> {
            Identifier dimensionId = new Identifier(material.getId().getNamespace(), material.getId().getPath().split("_")[0]);
            Identifier stoneName = Utils.addSuffixToPath(dimensionId, "_stone");
            Block blockIn = Registry.BLOCK.get(stoneName);
            RegistryUtils.registerOreTarget(stoneName, new OreFeatureConfig.Target(stoneName, new BlockPredicate(blockIn), blockIn));

            Identifier identifier = material.getId();
            Item repairItem;
            FabricBlockSettings blockSettings = FabricBlockSettings.copy(Objects.requireNonNull(RAARegisteries.TARGET_REGISTRY.get(material.getOreInformation().getTargetId())).getBlock());

            Block baseBlock = Objects.requireNonNull(RAARegisteries.TARGET_REGISTRY.get(material.getOreInformation().getTargetId())).getBlock();
            net.minecraft.block.Material baseBlockMaterial = baseBlock.getDefaultState().getMaterial();
            if (baseBlockMaterial == net.minecraft.block.Material.STONE) {
                blockSettings.breakByTool(FabricToolTags.PICKAXES, material.getMiningLevel());
            } else if (baseBlockMaterial == net.minecraft.block.Material.SOIL) {
                blockSettings.breakByTool(FabricToolTags.SHOVELS, material.getMiningLevel());
            } else {
                blockSettings.breakByHand(true);
            }

            Block block = RegistryUtils.register(
                    new RAABlock(),
                    Utils.addSuffixToPath(identifier, "_block"),
                    RandomlyAddingAnything.RAA_RESOURCES,
                    material.getName(),
                    RAABlockItem.BlockType.BLOCK
            );
            if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(Registry.ITEM.get(Registry.BLOCK.getId(block)), material.getCompostableAmount() + 0.5F);

            if (material.getOreInformation().getTargetId() != CustomTargets.DOES_NOT_APPEAR.getId()) {
                Block block2 = RegistryUtils.register(
                        new LayeredOreBlock(material, blockSettings.build()),
                        Utils.addSuffixToPath(identifier, "_ore"),
                        RandomlyAddingAnything.RAA_ORES,
                        material.getName(),
                        RAABlockItem.BlockType.ORE);
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(Registry.ITEM.get(Registry.BLOCK.getId(block2)), material.getCompostableAmount());
            }
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                Item item;
                item = RegistryUtils.registerItem(
                        repairItem = new RAASimpleItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_RESOURCES),
                                RAASimpleItem.SimpleItemType.INGOT
                        ),
                        Utils.addSuffixToPath(identifier, "_ingot")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount() - 1.0F);
                item = RegistryUtils.registerItem(
                        new RAASimpleItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_RESOURCES),
                                RAASimpleItem.SimpleItemType.NUGGET
                        ),
                        Utils.addSuffixToPath(identifier, "_nugget")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount() - 0.5F);
            } else if (material.getOreInformation().getOreType() == OreType.GEM) {
                Item item = RegistryUtils.registerItem(
                        repairItem = new RAASimpleItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_RESOURCES),
                                RAASimpleItem.SimpleItemType.GEM
                        ),
                        Utils.addSuffixToPath(identifier, "_gem")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount() + 0.5F);
            } else {
                Item item = RegistryUtils.registerItem(
                        repairItem = new RAASimpleItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_RESOURCES),
                                RAASimpleItem.SimpleItemType.CRYSTAL
                        ),
                        Utils.addSuffixToPath(identifier, "_crystal")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount() + 0.5F);
            }
            if (material.hasArmor()) {
                Item item;
                item = RegistryUtils.registerItem(
                        new RAAArmorItem(
                                material,
                                EquipmentSlot.HEAD,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_ARMOR).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_helmet")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAArmorItem(
                                material,
                                EquipmentSlot.CHEST,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_ARMOR).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_chestplate")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAArmorItem(
                                material,
                                EquipmentSlot.LEGS,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_ARMOR).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_leggings")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAArmorItem(
                                material,
                                EquipmentSlot.FEET,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_ARMOR).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_boots")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAHorseArmorItem(material),
                        Utils.addSuffixToPath(identifier, "_horse_armor")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
            }
            if (material.hasTools()) {
                Item item;
                item = RegistryUtils.registerItem(
                        new RAAPickaxeItem(
                                material,
                                material.getToolMaterial(),
                                1,
                                -2.8F,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_pickaxe")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAAxeItem(
                                material,
                                material.getToolMaterial(),
                                5.0F + material.getToolMaterial().getAxeAttackDamage(),
                                -3.2F + material.getToolMaterial().getAxeAttackSpeed(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_axe")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAShovelItem(
                                material,
                                material.getToolMaterial(),
                                1.5F,
                                -3.0F,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_shovel")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAHoeItem(
                                material,
                                material.getToolMaterial(),
                                1.5F + material.getToolMaterial().getAttackDamage(),
                                -3.0F + material.getToolMaterial().getHoeAttackSpeed(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_hoe")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new RAAShearItem(
                                material,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_shears")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
                item = RegistryUtils.registerItem(
                        new FabricShield(
                                new Item.Settings().group(RandomlyAddingAnything.RAA_TOOLS).recipeRemainder(repairItem),
                                material.getToolMaterial().getCooldownTicks(),
                                material.getToolMaterial().getDurability(),
                                repairItem
                        ),
                        Utils.addSuffixToPath(identifier, "_shield")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
            }
            if (material.hasWeapons()) {
                Item item = RegistryUtils.registerItem(
                        new RAASwordItem(
                                material,
                                new Item.Settings().group(RandomlyAddingAnything.RAA_WEAPONS).recipeRemainder(repairItem)
                        ),
                        Utils.addSuffixToPath(identifier, "_sword")
                );
                if (material.isCompostable()) CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
            }
            if (material.hasFood()) {
                FoodComponent.Builder foodComponent = new FoodComponent.Builder();
                if (material.getFoodData().isAlwaysEdible()) foodComponent.alwaysEdible();
                if (material.getFoodData().isMeat()) foodComponent.meat();
                if (material.getFoodData().isSnack()) foodComponent.snack();
                foodComponent.hunger(material.getFoodData().getHunger());
                foodComponent.saturationModifier(material.getFoodData().getSaturationModifier());

                Item item = RegistryUtils.registerItem(
                        new RAAFoodItem(
                                material.getName(),
                                new Item.Settings().group(RandomlyAddingAnything.RAA_FOOD).food(foodComponent.build())
                        ),
                        Utils.addSuffixToPath(identifier, "_fruit")
                );

                CompostingChanceRegistry.INSTANCE.add(item, material.getCompostableAmount());
            }
        });
    }

}
