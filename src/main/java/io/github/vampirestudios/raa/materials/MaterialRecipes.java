package io.github.vampirestudios.raa.materials;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.registries.Materials;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class MaterialRecipes {

    public static void init() {
        Artifice.registerData(new Identifier(MOD_ID, "recipe_pack"), serverResourcePackBuilder -> {
            Materials.MATERIALS.forEach(material -> {
                String id = material.getName().toLowerCase();
                for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getCharMap().entrySet()) {
                    id = id.replace(entry.getKey(), entry.getValue());
                }
                Item repairItem;
                if (material.getOreInformation().getOreType() == OreTypes.METAL) {
                    repairItem = Registry.ITEM.get(new Identifier(MOD_ID, id + "_ingot"));
                } else if (material.getOreInformation().getOreType() == OreTypes.CRYSTAL) {
                    repairItem = Registry.ITEM.get(new Identifier(MOD_ID, id + "_crystal"));
                } else {
                    repairItem = Registry.ITEM.get(new Identifier(MOD_ID, id + "_gem"));
                }
                if (material.hasArmor()) {
                    String finalId = id;
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_helmet"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:helmets"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "# #"
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId + "_helmet"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_chestplate"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:chestplates"));
                        shapedRecipeBuilder.pattern(
                                "# #",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId + "_chestplate"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_leggings"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:leggings"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "# #",
                                "# #"
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId + "_leggings"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_boots"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:boots"));
                        shapedRecipeBuilder.pattern(
                                "# #",
                                "# #"
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId + "_boots"), 1);
                    });
                }
                if (material.hasTools()) {
                    String finalId5 = id;
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_hoe"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:hoes"));
                        shapedRecipeBuilder.pattern(
                                "## ",
                                " % ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId5 + "_hoe"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_shovel"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:shovels"));
                        shapedRecipeBuilder.pattern(
                                " # ",
                                " % ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId5 + "_shovel"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_axe"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:axes"));
                        shapedRecipeBuilder.pattern(
                                "## ",
                                "#% ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId5 + "_axe"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_pickaxe"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:pickaxes"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                " % ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId5 + "_pickaxe"), 1);
                    });
                    serverResourcePackBuilder.addItemTag(new Identifier("fabric","pickaxes"), tagBuilder -> {
                        tagBuilder.replace(false);
                        tagBuilder.value(new Identifier(MOD_ID, finalId5 + "_pickaxe"));
                    });
                    serverResourcePackBuilder.addItemTag(new Identifier("fabric","shovels"), tagBuilder -> {
                        tagBuilder.replace(false);
                        tagBuilder.value(new Identifier(MOD_ID, finalId5 + "_shovel"));
                    });
                }
                if (material.hasWeapons()) {
                    String finalId4 = id;
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_sword"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:swords"));
                        shapedRecipeBuilder.pattern(
                                " # ",
                                " # ",
                                " % "
                        );
                        shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
                        shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId4 + "_sword"), 1);
                    });
                }
                if (material.getOreInformation().getOreType() == OreTypes.METAL) {
                    String finalId1 = id;
                    serverResourcePackBuilder.addSmeltingRecipe(new Identifier(MOD_ID, id + "_ingot"), cookingRecipeBuilder -> {
                        cookingRecipeBuilder.cookingTime(200);
                        cookingRecipeBuilder.ingredientItem(new Identifier(MOD_ID, finalId1 + "_ore"));
                        cookingRecipeBuilder.experience(0.7);
                        cookingRecipeBuilder.result(Registry.ITEM.getId(repairItem));
                    });
                    serverResourcePackBuilder.addBlastingRecipe(new Identifier(MOD_ID, id + "_ingot_from_blasting"), cookingRecipeBuilder -> {
                        cookingRecipeBuilder.cookingTime(100);
                        cookingRecipeBuilder.ingredientItem(new Identifier(MOD_ID, finalId1 + "_ore"));
                        cookingRecipeBuilder.experience(0.7);
                        cookingRecipeBuilder.result(Registry.ITEM.getId(repairItem));
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_ingot_from_nuggets"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:ingots"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', new Identifier(MOD_ID, finalId1 + "_nugget"));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId1 + "_ingot"), 1);
                    });
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_block"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', new Identifier(MOD_ID, finalId1 + "_ingot"));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId1 + "_block"), 1);
                    });
                    serverResourcePackBuilder.addShapelessRecipe(new Identifier(MOD_ID, id + "_ingot_from_" + id + "_block"), shapelessRecipeBuilder -> {
                        shapelessRecipeBuilder.group(new Identifier("raa:ingots"));
                        shapelessRecipeBuilder.ingredientItem(new Identifier(MOD_ID, finalId1 + "_block"));
                        shapelessRecipeBuilder.result(new Identifier(MOD_ID, finalId1 + "_ingot"), 9);
                    });
                } else if (material.getOreInformation().getOreType() == OreTypes.GEM) {
                    String finalId2 = id;
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_block"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', new Identifier(MOD_ID, finalId2 + "_gem"));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId2 + "_block"), 1);
                    });
                } else {
                    String finalId3 = id;
                    serverResourcePackBuilder.addShapedRecipe(new Identifier(MOD_ID, id + "_block"), shapedRecipeBuilder -> {
                        shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
                        shapedRecipeBuilder.pattern(
                                "###",
                                "###",
                                "###"
                        );
                        shapedRecipeBuilder.ingredientItem('#', new Identifier(MOD_ID, finalId3 + "_crystal"));
                        shapedRecipeBuilder.result(new Identifier(MOD_ID, finalId3 + "_block"), 1);
                    });
                }
            });
        });
    }

}
