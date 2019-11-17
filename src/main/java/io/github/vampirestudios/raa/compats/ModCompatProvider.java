package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public interface ModCompatProvider {

    void generateRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder);

    boolean asCustomRecipes();

    boolean asItems();

    void generateItems();
}
