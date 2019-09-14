package io.github.vampirestudios.raa.materials;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.registries.Materials;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class MaterialRecipes {

    public static void init() {
        Artifice.registerData(new Identifier(MOD_ID, "recipe_pack"), serverResourcePackBuilder -> {
            Materials.MATERIALS.forEach(material -> {
                Item repairItem;
                if (material.getOreInformation().getOreType() == OreTypes.METAL) {
                    repairItem = Registry.ITEM.get(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot"));
                } else if (material.getOreInformation().getOreType() == OreTypes.CRYSTAL) {
                    repairItem = Registry.ITEM.get(new Identifier(MOD_ID, material.getName().toLowerCase() + "_crystal"));
                } else {
                    repairItem = Registry.ITEM.get(new Identifier(MOD_ID, material.getName().toLowerCase() + "_gem"));
                }
                if (material.hasArmor()) {
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_helmet"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:helmets"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "# #"
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_helmet"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_chestplate"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:chestplates"));
                        shapedRecipeBuilder.pattern(
                                "# #",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_chestplate"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_leggings"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:leggings"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "# #",
                                "# #"
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_leggings"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_boots"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:boots"));
                        shapedRecipeBuilder.pattern(
                                "# #",
                                "# #"
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_boots"), 1);
                    });
                }
                if (material.hasTools()) {
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_hoe"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:hoes"));
                        shapedRecipeBuilder.pattern(
                                "## ",
                                " % ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_hoe"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_shovel"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:shovels"));
                        shapedRecipeBuilder.pattern(
                                " # ",
                                " % ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_shovel"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_axe"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:axes"));
                        shapedRecipeBuilder.pattern(
                                "## ",
                                "#% ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_axe"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_pickaxe"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:pickaxes"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                " % ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_pickaxe"), 1);
                    });
                }
                if (material.hasWeapons()) {
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_sword"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:swords"));
                        shapedRecipeBuilder.pattern(
                                " # ",
                                " # ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_sword"), 1);
                    });
                }
                if (material.getOreInformation().getOreType() == OreTypes.METAL) {
                    serverResourcePackBuilder.addSmeltingRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot"), cookingRecipeBuilder -> {
                        cookingRecipeBuilder.cookingTime(200);
                        cookingRecipeBuilder.ingredientItem(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ore"));
                        cookingRecipeBuilder.experience(0.7);
                        cookingRecipeBuilder.result(Registry.ITEM.getId(repairItem));
                    });
                    serverResourcePackBuilder.addBlastingRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot_from_blasting"), cookingRecipeBuilder -> {
                        cookingRecipeBuilder.cookingTime(100);
                        cookingRecipeBuilder.ingredientItem(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ore"));
                        cookingRecipeBuilder.experience(0.7);
                        cookingRecipeBuilder.result(Registry.ITEM.getId(repairItem));
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot_from_nuggets"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:ingots"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', new Identifier(MOD_ID, material.getName().toLowerCase() + "_nugget"));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_block"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot"));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_block"), 1);
                    });
                    serverResourcePackBuilder.addShapelessRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot_from_" + material.getName().toLowerCase() + "_block"), shapelessRecipeBuilder -> {
                        shapelessRecipeBuilder.group(new Identifier("raa:ingots"));
                        shapelessRecipeBuilder.ingredientItem(new Identifier(MOD_ID, material.getName().toLowerCase() + "_block"));
                        shapelessRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot"), 9);
                    });
                } else if (material.getOreInformation().getOreType() == OreTypes.GEM) {
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_block"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', new Identifier(MOD_ID, material.getName().toLowerCase() + "_gem"));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_block"), 1);
                    });
                } else {
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, material.getName().toLowerCase() + "_block"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', new Identifier(MOD_ID, material.getName().toLowerCase() + "_crystal"));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, material.getName().toLowerCase() + "_block"), 1);
                    });
                }
            });
        });
    }

}
