package io.github.vampirestudios.raa;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;

public class ModCompat {
    private List<ModCompatProvider> modCompatProviders = new ArrayList<>();
    private List<ModCompatClientProvider> modCompatClientProviders = new ArrayList<>();

    public ModCompat() {
        if (FabricLoader.getInstance().isModLoaded("techreborn")) {
            modCompatProviders.add(new TechReborn());
            modCompatClientProviders.add(new TechRebornCompatClient());
        }
        if (FabricLoader.getInstance().isModLoaded("beeproductive")) {
            modCompatProviders.add(new BeeProductiveCompat());
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

    public void generateCompatSaveFiles() {
        for (ModCompatProvider modCompatProvider : modCompatProviders) {
            if (modCompatProvider.asCustomSaveFile()) modCompatProvider.loadOrGenerateSaveFile();
        }
    }

    @Environment(EnvType.CLIENT)
    public void generateCompatModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {
        for (ModCompatClientProvider modCompatClientProvider : modCompatClientProviders)
            modCompatClientProvider.generateModels(resourcePackBuilder);
    }
}
