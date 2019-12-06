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
                Identifier identifier = new Identifier(MOD_ID, dimension.getName().toLowerCase());

                serverResourcePackBuilder.addShapedRecipe(Utils.appendToPath(identifier, "_hoe"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:hoes"));
                    shapedRecipeBuilder.pattern(
                            "## ",
                            " % ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.appendToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.appendToPath(identifier, "_hoe"), 1);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.appendToPath(identifier, "_shovel"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:shovels"));
                    shapedRecipeBuilder.pattern(
                            " # ",
                            " % ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.appendToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.appendToPath(identifier, "_shovel"), 1);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.appendToPath(identifier, "_axe"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:axes"));
                    shapedRecipeBuilder.pattern(
                            "## ",
                            "#% ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.appendToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.appendToPath(identifier, "_axe"), 1);
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.appendToPath(identifier, "_pickaxe"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:pickaxes"));
                    shapedRecipeBuilder.pattern(
                            "###",
                            " % ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.appendToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.appendToPath(identifier, "_pickaxe"), 1);
                });
                serverResourcePackBuilder.addItemTag(new Identifier("fabric", "pickaxes"), tagBuilder -> {
                    tagBuilder.replace(false);
                    tagBuilder.value(Utils.appendToPath(identifier, "_pickaxe"));
                });
                serverResourcePackBuilder.addItemTag(new Identifier("fabric", "shovels"), tagBuilder -> {
                    tagBuilder.replace(false);
                    tagBuilder.value(Utils.appendToPath(identifier, "_shovel"));
                });
                serverResourcePackBuilder.addShapedRecipe(Utils.appendToPath(identifier, "_sword"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.group(new Identifier("raa:swords"));
                    shapedRecipeBuilder.pattern(
                            " # ",
                            " # ",
                            " % "
                    );
                    shapedRecipeBuilder.ingredientItem('#', Utils.appendToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
                    shapedRecipeBuilder.result(Utils.appendToPath(identifier, "_sword"), 1);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.appendToPath(identifier, "_portal"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.appendToPath(identifier, "_stone"));
                    shapedRecipeBuilder.ingredientItem('O', new Identifier("obsidian"));
                    shapedRecipeBuilder.ingredientItem('C', new Identifier("cauldron"));
                    shapedRecipeBuilder.pattern("SOS", "SCS", "SSS");
                    shapedRecipeBuilder.result(Utils.appendToPath(identifier, "_portal"), 1);
                });

                serverResourcePackBuilder.addShapedRecipe(Utils.appendToPath(identifier, "_stone_bricks"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.appendToPath(identifier, "_stone"));
                    shapedRecipeBuilder.pattern("SS", "SS");
                    shapedRecipeBuilder.result(Utils.appendToPath(identifier, "_stone_bricks"), 1);
                });

                serverResourcePackBuilder.addShapedRecipe(new Identifier("raa:" + identifier.getPath() + "_furnace"), shapedRecipeBuilder -> {
                    shapedRecipeBuilder.ingredientItem('S', Utils.appendToPath(identifier, "_cobblestone"));
                    shapedRecipeBuilder.pattern("SSS", "S S", "SSS");
                    shapedRecipeBuilder.result(new Identifier("furnace"), 1);
                });
            }
        ));
    }

}
