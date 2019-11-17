package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.items.ItemCompat;
import io.github.vampirestudios.raa.compats.items.TechRebornItems;
import io.github.vampirestudios.raa.compats.recipes.RecipeCompat;
import io.github.vampirestudios.raa.compats.recipes.TechRebornRecipes;

public class TechReborn implements ModCompatProvider {
    RecipeCompat recipeCompat;
    ItemCompat itemCompat;

    public TechReborn() {
        this.recipeCompat = new TechRebornRecipes();
        this.itemCompat = new TechRebornItems();
    }

    @Override
    public void generateRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        this.recipeCompat.registerRecipes(dataPackBuilder);
    }

    @Override
    public boolean asCustomRecipes() {
        return true;
    }

    @Override
    public boolean asItems() {
        return true;
    }

    @Override
    public void generateItems() {
        this.itemCompat.generateItems();
    }
}
