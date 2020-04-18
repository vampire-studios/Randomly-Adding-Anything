package io.github.vampirestudios.raa.compats.models;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

public class SpontaneousBucketingTranslation extends ModelCompat {

    public SpontaneousBucketingTranslation() {super();}

    @Override
    protected void blockStates(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {

    }

    @Override
    protected void itemModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {
            resourcePackBuilder.addTranslations(new Identifier("raa:en_us"), translationBuilder -> {
                for (Material material : Materials.MATERIALS) {
                    translationBuilder.entry("item." + material.getId().getNamespace() + ".bucket." + material.getId().getPath(), WordUtils.capitalize(material.getName()));
                }
                for (Material material : Materials.DIMENSION_MATERIALS) {
                    translationBuilder.entry("item." + material.getId().getNamespace() + ".bucket." + material.getId().getPath(), WordUtils.capitalize(material.getName()));
                }
            });
    }

    @Override
    protected void blockModels(ArtificeResourcePack.ClientResourcePackBuilder resourcePackBuilder) {

    }
}
