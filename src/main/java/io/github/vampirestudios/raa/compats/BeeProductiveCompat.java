package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.items.BeeProductiveCompatItems;
import io.github.vampirestudios.raa.compats.items.ItemCompat;
import io.github.vampirestudios.raa.generation.materials.Material;

import java.util.HashMap;
import java.util.Map;

public class BeeProductiveCompat implements ModCompatProvider {
    public static Map<Material, Integer> materialStringMap = new HashMap<>();
    ItemCompat itemCompat;

    public BeeProductiveCompat() {
        this.itemCompat = new BeeProductiveCompatItems();
    }

    @Override
    public void generateRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {

    }

    @Override
    public boolean asCustomRecipes() {
        return true;
    }

    @Override
    public boolean asItems() {
        return true;
    }

    @Override
    public void generateItems() {
        this.itemCompat.generateItems();
    }

    @Override
    public boolean asCustomSaveFile() {
        return true;
    }

    @Override
    public void loadOrGenerateSaveFile() {

    }
}
