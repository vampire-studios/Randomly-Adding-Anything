package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public interface ModCompatClientProvider {

    void generateModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder);
}
