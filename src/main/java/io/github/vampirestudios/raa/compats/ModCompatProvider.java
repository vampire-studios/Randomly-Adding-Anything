package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public interface ModCompatProvider {

    void generateRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder);

    void generateModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder);

    boolean hasCustomRecipes();

    boolean hasCustomModels();

    boolean hasItems();

    void generateItems();
}
