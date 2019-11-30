package io.github.vampirestudios.raa.generation.dimensions;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.util.Identifier;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class DimensionRecipes {

    public static void init() {
        Artifice.registerData(new Identifier(MOD_ID, "dimension_recipe_pack"), serverResourcePackBuilder ->
            Dimensions.DIMENSIONS.forEach(dimension -> {
                Identifier identifier = new Identifier(MOD_ID, dimension.getName().toLowerCase());

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
            }
        ));
    }

}
