package io.github.vampirestudios.raa.compats;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.compats.items.ItemCompat;
import io.github.vampirestudios.raa.compats.items.SpontaneousBucketingItems;
import io.github.vampirestudios.raa.compats.models.ModelCompat;
import io.github.vampirestudios.raa.compats.models.SpontaneousBucketingTranslation;

public class SpontaneousBucketing implements ModCompatProvider {

    private ItemCompat itemCompat;
    private ModelCompat modelCompat;

    public SpontaneousBucketing() {
        this.itemCompat = new SpontaneousBucketingItems();
        this.modelCompat = new SpontaneousBucketingTranslation();
    }

    @Override
    public boolean hasItems() {
        return true;
    }

    @Override
    public void generateItems() {
        this.itemCompat.generateItems();
    }

    @Override
    public boolean hasCustomRecipes() {
        return false;
    }

    @Override
    public boolean hasCustomModels() {
        return true;
    }

    @Override
    public void generateRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {

    }

    @Override
    public void generateModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {
        this.modelCompat.generate(resourcePackBuilder);
    }
}
