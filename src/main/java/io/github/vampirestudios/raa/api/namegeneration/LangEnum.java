package io.github.vampirestudios.raa.api.namegeneration;

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
        new EnglishDimensions()
    ),
    FRENCH(
        new FrenchMaterials(),
        new FrenchDimensions()
    ),
    NORWEGIAN(
        new NorwegianMaterials(),
        new NorwegianDimensions()
    ),
    SPANISH(
        new SpanishMaterials(),
        new SpanishDimensions()
    );

    private final NameGenerator materialNameGenerator;
    private final NameGenerator dimensionNameGenerator;

    LangEnum(NameGenerator material, NameGenerator dimensions) {
        this.materialNameGenerator = material;
        this.dimensionNameGenerator = dimensions;
    }

    public NameGenerator getMaterialNameGenerator() {
        return materialNameGenerator;
    }

    public NameGenerator getDimensionNameGenerator() {
        return dimensionNameGenerator;
    }

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(name());
    }
}
