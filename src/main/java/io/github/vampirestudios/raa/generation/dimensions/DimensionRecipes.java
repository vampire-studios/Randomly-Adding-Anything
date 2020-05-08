package io.github.vampirestudios.raa.generation.dimensions;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class DimensionRecipes {

    public static void init() {
        Artifice.registerData(new Identifier(MOD_ID, "dimension_recipe_pack"), serverResourcePackBuilder ->
            Dimensions.DIMENSIONS.forEach(dimension -> {
                Identifier identifier = dimension.getId();

                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_hoe"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:hoes"));
                    shapedRecipeBuilder.pattern(
                            "## ",
                            " % ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_hoe"), 1);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_shovel"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:shovels"));
                    shapedRecipeBuilder.pattern(
                            " # ",
                            " % ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_shovel"), 1);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_axe"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:axes"));
                    shapedRecipeBuilder.pattern(
                            "## ",
                            "#% ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_axe"), 1);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_pickaxe"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:pickaxes"));
                    shapedRecipeBuilder.pattern(
                            "###",
                            " % ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_pickaxe"), 1);
                });
                serverResourcePackBuilder.addItemTag(new Identifier("fabric", "pickaxes"), tagBuilder -> {
                    tagBuilder.replace(false);
                    tagBuilder.value(Utils.addSuffixToPath(identifier, "_pickaxe"));
                });
                serverResourcePackBuilder.addItemTag(new Identifier("fabric", "shovels"), tagBuilder -> {
                    tagBuilder.replace(false);
                    tagBuilder.value(Utils.addSuffixToPath(identifier, "_shovel"));
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_sword"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:swords"));
                    shapedRecipeBuilder.pattern(
                            " # ",
                            " # ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_sword"), 1);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_portal"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone"));
                    shapedRecipeBuilder.ingredientItem('O', new Identifier("obsidian"));
                    shapedRecipeBuilder.ingredientItem('C', new Identifier("cauldron"));
                    shapedRecipeBuilder.pattern("SOS", "SCS", "SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_portal"), 1);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_stone_bricks"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone"));
                    shapedRecipeBuilder.pattern("SS", "SS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_bricks"), 1);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.addPrefixAndSuffixToPath(identifier, "chiseled_", "_stone_bricks"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone"));
                    shapedRecipeBuilder.pattern("SS", "SS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_bricks"), 1);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_furnace"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.pattern("SSS", "S S", "SSS");
                    shapedRecipeBuilder.result(new Identifier("furnace"), 1);
                });
                serverResourcePackBuilder.addSmeltingRecipe(Utils.addSuffixToPath(identifier, "_cobblestone_to_" + identifier.getPath() + "_stone"), cookingRecipeBuilder -> {
                    cookingRecipeBuilder.cookingTime(200);
                    cookingRecipeBuilder.experience(0.1);
                    cookingRecipeBuilder.ingredientItem(Utils.addSuffixToPath(identifier, "_cobblestone"));
                    cookingRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone"));
                });


                serverResourcePackBuilder.addShapedRecipe(Utils.addPrefixToPath(identifier, "polished_"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone"));
                    shapedRecipeBuilder.pattern("SS", "SS");
                    shapedRecipeBuilder.result(Utils.addPrefixToPath(identifier, "polished_"), 4);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_stone_bricks"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addPrefixToPath(identifier, "polished_"));
                    shapedRecipeBuilder.pattern("SS", "SS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_bricks"), 4);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addPrefixAndSuffixToPath(identifier, "chiseled_", "_stone_bricks"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone_brick_slab"));
                    shapedRecipeBuilder.pattern("S", "S");
                    shapedRecipeBuilder.result(Utils.addPrefixAndSuffixToPath(identifier, "chiseled_", "_stone_bricks"), 1);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_stone_slab"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone"));
                    shapedRecipeBuilder.pattern("SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_slab"), 6);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_stone_stairs"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone"));
                    shapedRecipeBuilder.pattern("S  ", "SS ", "SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_stairs"), 4);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_stone_wall"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone"));
                    shapedRecipeBuilder.pattern("SSS", "SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_wall"), 6);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_cobblestone_slab"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.pattern("SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_cobblestone_slab"), 6);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_cobblestone_stairs"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.pattern("S  ", "SS ", "SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_cobblestone_stairs"), 4);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_cobblestone_wall"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.pattern("SSS", "SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_cobblestone_wall"), 6);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_stone_brick_slab"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone_bricks"));
                    shapedRecipeBuilder.pattern("SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_brick_slab"), 6);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_stone_brick_stairs"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone_bricks"));
                    shapedRecipeBuilder.pattern("S  ", "SS ", "SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_brick_stairs"), 4);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addSuffixToPath(identifier, "_stone_brick_wall"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addSuffixToPath(identifier, "_stone_bricks"));
                    shapedRecipeBuilder.pattern("SSS", "SSS");
                    shapedRecipeBuilder.result(Utils.addSuffixToPath(identifier, "_stone_brick_wall"), 6);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_slab"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addPrefixToPath(identifier, "polished_"));
                    shapedRecipeBuilder.pattern("SSS");
                    shapedRecipeBuilder.result(Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_slab"), 6);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_stairs"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addPrefixToPath(identifier, "polished_"));
                    shapedRecipeBuilder.pattern("S  ", "SS ", "SSS");
                    shapedRecipeBuilder.result(Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_stairs"), 4);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_wall"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.addPrefixToPath(identifier, "polished_"));
                    shapedRecipeBuilder.pattern("SSS", "SSS");
                    shapedRecipeBuilder.result(Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_wall"), 6);
                });


                /*serverResourcePackBuilder.addBlockTag(new Identifier("minecraft:walls"), tagBuilder -> {
                    tagBuilder.replace(false);
                    tagBuilder.values(
                        Utils.addSuffixToPath(identifier, "_stone_wall"),
                        Utils.addSuffixToPath(identifier, "_stone_brick_wall"),
                        Utils.addSuffixToPath(identifier, "_cobblestone_wall"),
                        Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_wall")
                    );
                });*/
            }
        ));
    }

}
