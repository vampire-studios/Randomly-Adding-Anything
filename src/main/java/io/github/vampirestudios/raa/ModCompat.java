package io.github.vampirestudios.raa;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.ModCompatProvider;
import io.github.vampirestudios.raa.compats.TechReborn;
import net.fabricmc.loader.FabricLoader;

import java.util.ArrayList;
import java.util.List;

public class ModCompat {
    private List<ModCompatProvider> modCompatProviders = new ArrayList<>();

    public ModCompat() {
        if (FabricLoader.INSTANCE.isModLoaded("techreborn")) {
            modCompatProviders.add(new TechReborn());
        }
    }

    public void generateCompatRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        for (ModCompatProvider modCompatProvider : modCompatProviders) {
            if (modCompatProvider.asCustomRecipes()) {
                modCompatProvider.generateRecipes(dataPackBuilder);
            }
        }
    }

    public void generateCompatItems() {
        for (ModCompatProvider modCompatProvider : modCompatProviders) {
            if (modCompatProvider.asItems()) modCompatProvider.generateItems();
        }
    }
}
