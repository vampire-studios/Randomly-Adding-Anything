package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.recipes.RecipeCompat;
import io.github.vampirestudios.raa.compats.recipes.TechRebornRecipes;

public class TechReborn implements ModCompatProvider {
    RecipeCompat recipeCompat;

    public TechReborn() {
        this.recipeCompat = new TechRebornRecipes();
    }

    @Override
    public void generateRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        this.recipeCompat.registerRecipes(dataPackBuilder);
    }

    @Override
    public boolean asCustomRecipes() {
        return true;
    }
}
