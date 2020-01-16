package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.models.ModelCompat;
import io.github.vampirestudios.raa.compats.models.TechRebornModels;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TechRebornCompatClient implements ModCompatClientProvider {
    ModelCompat modelCompat;

    public TechRebornCompatClient() {
        this.modelCompat = new TechRebornModels();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void generateModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {
        this.modelCompat.generate(resourcePackBuilder);
    }
}
