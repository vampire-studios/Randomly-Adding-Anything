package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.items.ItemCompat;
import io.github.vampirestudios.raa.compats.items.TechRebornItems;
import io.github.vampirestudios.raa.compats.models.ModelCompat;
import io.github.vampirestudios.raa.compats.models.TechRebornModels;
import io.github.vampirestudios.raa.compats.recipes.RecipeCompat;
import io.github.vampirestudios.raa.compats.recipes.TechRebornRecipes;

public class TechReborn implements ModCompatProvider {
    RecipeCompat recipeCompat;
    ItemCompat itemCompat;
    ModelCompat modelCompat;

    public TechReborn() {
        this.recipeCompat = new TechRebornRecipes();
        this.itemCompat = new TechRebornItems();
        this.modelCompat = new TechRebornModels();
    }

    @Override
    public void generateRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        this.recipeCompat.registerRecipes(dataPackBuilder);
    }

    @Override
    public void generateModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {
        this.modelCompat.generate(resourcePackBuilder);
    }

    @Override
    public boolean hasCustomRecipes() {
        return false;
    }

    @Override
    public boolean hasCustomModels() {
        return true;
    }

    @Override
    public boolean hasItems() {
        return true;
    }

    @Override
    public void generateItems() {
        this.itemCompat.generateItems();
    }
}
