package io.github.vampirestudios.raa.api.namegeneration;

import io.github.vampirestudios.raa.api.namegeneration.dimensions.EnglishDimensions;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.FrenchDimensions;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.NorwegianDimensions;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.SpanishDimensions;
import io.github.vampirestudios.raa.api.namegeneration.material.EnglishMaterials;
import io.github.vampirestudios.raa.api.namegeneration.material.FrenchMaterials;
import io.github.vampirestudios.raa.api.namegeneration.material.NorwegianMaterials;
import io.github.vampirestudios.raa.api.namegeneration.material.SpanishMaterials;
import io.github.vampirestudios.raa.api.namegeneration.trees.EnglishTrees;
import org.apache.commons.lang3.text.WordUtils;

public enum LangEnum {
    ENGLISH(
        new EnglishMaterials(),
        new EnglishDimensions(),
        new EnglishTrees()),
    FRENCH(
        new FrenchMaterials(),
        new FrenchDimensions(),
        new EnglishTrees()),
    NORWEGIAN(
        new NorwegianMaterials(),
        new NorwegianDimensions(),
        new EnglishTrees()),
    SPANISH(
        new SpanishMaterials(),
        new SpanishDimensions(),
        new EnglishTrees());

    private final NameGenerator materialNameGenerator;
    private final NameGenerator dimensionNameGenerator;
    private final NameGenerator treeNameGenerator;

    LangEnum(NameGenerator material, NameGenerator dimensions, NameGenerator treeNameGenerator) {
        this.materialNameGenerator = material;
        this.dimensionNameGenerator = dimensions;
        this.treeNameGenerator = treeNameGenerator;
    }

    public NameGenerator getMaterialNameGenerator() {
        return materialNameGenerator;
    }

    public NameGenerator getDimensionNameGenerator() {
        return dimensionNameGenerator;
    }

    public NameGenerator getTreeNameGenerator() {
        return treeNameGenerator;
    }

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(name());
    }
}
