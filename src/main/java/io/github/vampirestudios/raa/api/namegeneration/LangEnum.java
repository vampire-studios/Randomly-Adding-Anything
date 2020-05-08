package io.github.vampirestudios.raa.api.namegeneration;

import io.github.vampirestudios.raa.api.namegeneration.civs.EnglishCivs;
import io.github.vampirestudios.raa.api.namegeneration.civs.FrenchCivs;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.*;
import io.github.vampirestudios.raa.api.namegeneration.material.*;
import io.github.vampirestudios.raa.api.namegeneration.stone.*;
import io.github.vampirestudios.raa.api.namegeneration.trees.EnglishTrees;
import org.apache.commons.lang3.text.WordUtils;

public enum LangEnum {
    ENGLISH(
        new EnglishMaterials(),
        new EnglishDimensions(),
        new EnglishTrees(),
        new EnglishCivs(),
        new EnglishStones()
    ),
    FRENCH(
        new FrenchMaterials(),
        new FrenchDimensions(),
        new EnglishTrees(),
        new FrenchCivs(),
        new FrenchStones()
    ),
    NORWEGIAN(
        new NorwegianMaterials(),
        new NorwegianDimensions(),
        new EnglishTrees(),
        new EnglishCivs(),
        new NorwegianStones()
    ),
    SPANISH(
        new SpanishMaterials(),
        new SpanishDimensions(),
        new EnglishTrees(),
        new EnglishCivs(),
        new SpanishStones()
    ),
    CHINESE(
        new ChineseMaterials(),
        new ChineseDimensions(),
        new EnglishTrees(),
        new EnglishCivs(),
        new ChineseStones()
    );

    private final NameGenerator materialNameGenerator;
    private final NameGenerator dimensionNameGenerator;
    private final NameGenerator treeNameGenerator;
    private final NameGenerator civilizationNameGenerator;
    private final NameGenerator stoneNameGenerator;

    LangEnum(NameGenerator material, NameGenerator dimensions, NameGenerator treeNameGenerator, NameGenerator civilizationNameGenerator,
             NameGenerator stoneNameGenerator) {
        this.materialNameGenerator = material;
        this.dimensionNameGenerator = dimensions;
        this.treeNameGenerator = treeNameGenerator;
        this.civilizationNameGenerator = civilizationNameGenerator;
        this.stoneNameGenerator = stoneNameGenerator;
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

    public NameGenerator getCivilizationNameGenerator() {
        return civilizationNameGenerator;
    }

    public NameGenerator getStoneNameGenerator() {
        return stoneNameGenerator;
    }

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(name());
    }
}
