package io.github.vampirestudios.raa;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.ModCompatProvider;
import io.github.vampirestudios.raa.compats.SpontaneousBucketing;
import io.github.vampirestudios.raa.compats.TechReborn;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;

public class ModCompat {
    private List<ModCompatProvider> modCompatProviders = new ArrayList<>();

    public ModCompat() {
        if (FabricLoader.getInstance().isModLoaded("techreborn")) {
            modCompatProviders.add(new TechReborn());
        }
        if (FabricLoader.getInstance().isModLoaded("spontaneousbucketing") && RandomlyAddingAnything.CONFIG.materialBuckets) {
            modCompatProviders.add(new SpontaneousBucketing());
        }
    }

    public void generateCompatRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        for (ModCompatProvider modCompatProvider : modCompatProviders) {
            if (modCompatProvider.hasCustomRecipes()) {
                modCompatProvider.generateRecipes(dataPackBuilder);
            }
        }
    }

    public void generateCompatModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {
        for (ModCompatProvider modCompatProvider : modCompatProviders) {
            if (modCompatProvider.hasCustomModels()) {
                modCompatProvider.generateModels(resourcePackBuilder);
            }
        }
    }

    public void generateCompatItems() {
        for (ModCompatProvider modCompatProvider : modCompatProviders) {
            if (modCompatProvider.hasItems()) modCompatProvider.generateItems();
        }
    }
}
