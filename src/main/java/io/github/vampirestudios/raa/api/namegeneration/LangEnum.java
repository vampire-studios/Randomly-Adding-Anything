package io.github.vampirestudios.raa.api.namegeneration;

import io.github.vampirestudios.raa.api.namegeneration.material.English;
import io.github.vampirestudios.raa.api.namegeneration.material.French;
import io.github.vampirestudios.raa.api.namegeneration.material.Norwegian;

import java.util.Map;

public enum LangEnum {
    ENGLISH(
            new English(),
            new io.github.vampirestudios.raa.api.namegeneration.biomes.English(),
            new io.github.vampirestudios.raa.api.namegeneration.dimensions.English(),
            new io.github.vampirestudios.raa.api.namegeneration.entities.English()
    ),
    FRENCH(
            new French(),
            new io.github.vampirestudios.raa.api.namegeneration.biomes.French(),
            new io.github.vampirestudios.raa.api.namegeneration.dimensions.French(),
            new io.github.vampirestudios.raa.api.namegeneration.entities.French()
    ),
    NORWEGIAN(
            new Norwegian(),
            new io.github.vampirestudios.raa.api.namegeneration.biomes.Norwegian(),
            new io.github.vampirestudios.raa.api.namegeneration.dimensions.Norwegian(),
            new io.github.vampirestudios.raa.api.namegeneration.entities.Norwegian()
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

    public String generateMaterialNames() {
        return materialNameGenerator.generate();
    }

    public Map<String, String> getMaterialCharMap() {
        return materialNameGenerator.getSpecialCharatersMap();
    }

    public String generateBiomeNames() {
        return biomeNameGenerator.generate();
    }

    public Map<String, String> getBiomeCharMap() {
        return biomeNameGenerator.getSpecialCharatersMap();
    }

    public String generateDimensionName() {
        return dimensionNameGenerator.generate();
    }

    public Map<String, String> getDimensionCharMap() {
        return dimensionNameGenerator.getSpecialCharatersMap();
    }

    public String generateEntityNames() {
        return entityNameGenerator.generate();
    }

    public Map<String, String> getEntityCharMap() {
        return entityNameGenerator.getSpecialCharatersMap();
    }

}
