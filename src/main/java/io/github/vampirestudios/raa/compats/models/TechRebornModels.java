package io.github.vampirestudios.raa.compats.models;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.util.Identifier;

public class TechRebornModels extends ModelCompat {

    public TechRebornModels() {
        super();
    }

    @Override
    protected void blockStates(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {

    }

    @Override
    protected void itemModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {
        for (Material material : Materials.MATERIALS) {
            if (material.getOreInformation().getOreType() == OreType.METAL) {
                resourcePackBuilder.addItemModel(Utils.addSuffixToPath(material.getId(), "dust"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                });
                resourcePackBuilder.addItemModel(Utils.addSuffixToPath(material.getId(), "small_dust"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getSmallDustTexture());
                });
                resourcePackBuilder.addItemModel(Utils.addSuffixToPath(material.getId(), "plate"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getPlateTexture());
                });
                resourcePackBuilder.addItemModel(Utils.addSuffixToPath(material.getId(), "gear"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getGearTexture());
                });
            }
        }
    }

    @Override
    protected void blockModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {

    }

}