package io.github.vampirestudios.raa.api.namegeneration;

import io.github.vampirestudios.raa.api.namegeneration.biomes.EnglishBiomes;
import io.github.vampirestudios.raa.api.namegeneration.biomes.FrenchBiomes;
import io.github.vampirestudios.raa.api.namegeneration.biomes.NorwegianBiomes;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.EnglishDimensions;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.FrenchDimensions;
import io.github.vampirestudios.raa.api.namegeneration.dimensions.NorwegianDimensions;
import io.github.vampirestudios.raa.api.namegeneration.entities.EnglishEntities;
import io.github.vampirestudios.raa.api.namegeneration.entities.FrenchEntities;
import io.github.vampirestudios.raa.api.namegeneration.entities.NorwegianEntities;
import io.github.vampirestudios.raa.api.namegeneration.material.EnglishMaterials;
import io.github.vampirestudios.raa.api.namegeneration.material.FrenchMaterials;
import io.github.vampirestudios.raa.api.namegeneration.material.NorwegianMaterials;

public enum LangEnum {
    ENGLISH(
            new EnglishMaterials(),
            new EnglishBiomes(),
            new EnglishDimensions(),
            new EnglishEntities()
    ),
    FRENCH(
            new FrenchMaterials(),
            new FrenchBiomes(),
            new FrenchDimensions(),
            new FrenchEntities()
    ),
    NORWEGIAN(
            new NorwegianMaterials(),
            new NorwegianBiomes(),
            new NorwegianDimensions(),
            new NorwegianEntities()
    );

    private INameGenerator materialNameGenerator;
    private INameGenerator biomeNameGenerator;
    private INameGenerator dimensionNameGenerator;
    private INameGenerator entityNameGenerator;

    LangEnum(INameGenerator material, INameGenerator biomes, INameGenerator dimensions, INameGenerator entities) {
        this.materialNameGenerator = material;
        this.biomeNameGenerator = biomes;
        this.dimensionNameGenerator = dimensions;
        this.entityNameGenerator = entities;
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

    public INameGenerator getEntityNameGenerator() {
        return entityNameGenerator;
    }
}
