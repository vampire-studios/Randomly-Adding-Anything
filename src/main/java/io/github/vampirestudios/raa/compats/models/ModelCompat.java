package io.github.vampirestudios.raa.compats.models;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public abstract class ModelCompat {

    public ModelCompat() {}

    public void generate(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {
        blockStates(resourcePackBuilder);
        itemModels(resourcePackBuilder);
        blockModels(resourcePackBuilder);
    }

    protected abstract void blockStates(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder);

    protected abstract void itemModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder);

    protected abstract void blockModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder);
}
