package io.github.vampirestudios.raa.api.namegeneration;

import io.github.vampirestudios.raa.api.namegeneration.biomes.*;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.EnglishDimensions;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.FrenchDimensions;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.NorwegianDimensions;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.SpanishDimensions;
import io.github.vampirestudios.raa.api.namegeneration.material.EnglishMaterials;
import io.github.vampirestudios.raa.api.namegeneration.material.FrenchMaterials;
import io.github.vampirestudios.raa.api.namegeneration.material.NorwegianMaterials;
import io.github.vampirestudios.raa.api.namegeneration.material.SpanishMaterials;
import org.apache.commons.lang3.text.WordUtils;

public enum LangEnum {
    ENGLISH(
        new EnglishMaterials(),
        new EnglishBiomes(),
        new EnglishDimensions()
    ),
    FRENCH(
        new FrenchMaterials(),
        new FrenchBiomes(),
        new FrenchDimensions()
    ),
    NORWEGIAN(
        new NorwegianMaterials(),
        new NorwegianBiomes(),
        new NorwegianDimensions()
    ),
    SPANISH(
        new SpanishMaterials(),
        new SpanishBiomes(),
        new SpanishDimensions()
    ),
    CHINESE(
        new ChineseBiomes(),
        new ChineseBiomes(),
        new ChineseBiomes()
    );

    private INameGenerator materialNameGenerator;
    private INameGenerator biomeNameGenerator;
    private INameGenerator dimensionNameGenerator;

    LangEnum(INameGenerator material, INameGenerator biomes, INameGenerator dimensions) {
        this.materialNameGenerator = material;
        this.biomeNameGenerator = biomes;
        this.dimensionNameGenerator = dimensions;
    }

    public INameGenerator getMaterialNameGenerator() {
        return materialNameGenerator;
    }

    public INameGenerator getBiomeNameGenerator() {
        return biomeNameGenerator;
    }

    public INameGenerator getDimensionNameGenerator() {
        return dimensionNameGenerator;
    }

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(name());
    }
}
