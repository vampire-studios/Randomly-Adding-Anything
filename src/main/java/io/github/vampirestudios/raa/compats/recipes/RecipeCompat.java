package io.github.vampirestudios.raa.compats.recipes;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public abstract class RecipeCompat {
    private ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder;

    protected RecipeCompat(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        this.dataPackBuilder = dataPackBuilder;
    }

    public ArtificeResourcePack.ServerResourcePackBuilder getDataPackBuilder() {
        return dataPackBuilder;
    }

    public abstract void registerRecipes();
}
