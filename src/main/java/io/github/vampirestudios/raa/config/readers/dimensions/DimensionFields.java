package io.github.vampirestudios.raa.config.readers.dimensions;

import blue.endless.jankson.JsonObject;
import io.github.vampirestudios.raa.config.readers.Versions;
import io.github.vampirestudios.raa.generation.dimensions.*;
import net.minecraft.util.Identifier;

public enum DimensionFields {
    NAME(Versions.V1, "name", (configVersion, builder, jsonObject) -> {
        return builder.name(jsonObject.get(String.class, "name"));
    }),
    DIMENSION_ID(Versions.V1, "dimensionId", (configVersion, builder, jsonObject) -> {
        return builder.dimensionId(jsonObject.get(int.class, "dimensionId"));
    }),
    DIMENSION_PALLETS(Versions.V1, "dimensionColorPallet", (configVersion, builder, jsonObject) -> {
        DimensionColorPallet pallet = DimensionColorPalletBuilder.create()
                .skyColor(jsonObject.get(int.class, "skyColor"))
                .grassColor(jsonObject.get(int.class, "grassColor"))
                .fogColor(jsonObject.get(int.class, "fogColor"))
                .foliageColor(jsonObject.get(int.class, "foliageColor"))
                .stoneColor(jsonObject.get(int.class, "stoneColor")).build();
        return builder.colorPallet(pallet);
    }),
    HAS_LIGHT(Versions.V1, "hasLight", (configVersion, builder, jsonObject) -> {
        return builder.hasLight(jsonObject.get(boolean.class, "hasLight"));
    }),
    HAS_SKY(Versions.V1, "hasSky", (configVersion, builder, jsonObject) -> {
        return builder.hasSky(jsonObject.get(boolean.class, "hasSky"));
    }),
    CAN_SLEEP(Versions.V1, "canSleep", (configVersion, builder, jsonObject) -> {
        return builder.canSleep(jsonObject.get(boolean.class, "canSleep"));
    }),
    BIOME_DATA(Versions.OLD, "tools", (configVersion, builder, jsonObject) -> {
        DimensionBiomeData biomeData = DimensionBiomeDataBuilder.create()
                .name(jsonObject.get(String.class, "biomeName"))
                .surfaceBuilderVariantChance(jsonObject.get(int.class, "surfaceBuilderVariantChance"))
                .depth(jsonObject.get(int.class, "depth"))
                .scale(jsonObject.get(int.class, "scale"))
                .temperature(jsonObject.get(int.class, "temperature"))
                .downfall(jsonObject.get(int.class, "downfall"))
                .waterColor(jsonObject.get(int.class, "waterColor")).build();
        return builder.biome(biomeData);
    });

    private Versions implementedVersion;
    private String name;
    private MaterialFieldsInterface fieldsInterface;

    DimensionFields(Versions implementedVersion, String name, MaterialFieldsInterface fieldsInterface) {
        this.implementedVersion = implementedVersion;
        this.name = name;
        this.fieldsInterface = fieldsInterface;
    }

    public String getName() {
        return name;
    }

    public DimensionBuilder read(Versions configVersion, DimensionBuilder builder, JsonObject jsonObject) {
        return this.fieldsInterface.read(configVersion, builder, jsonObject);
    }

    protected interface MaterialFieldsInterface {
        DimensionBuilder read(Versions configVersion, DimensionBuilder builder, JsonObject jsonObject);
    }

    private static Identifier idFromJson(JsonObject jsonObject, String name) {
        JsonObject jsonObject1 = jsonObject.getObject(name);
        if (jsonObject1.containsKey("namespace")) return new Identifier(jsonObject1.get(String.class, "namespace"), jsonObject1.get(String.class, "path"));
        else return new Identifier(jsonObject1.get(String.class, "field_13353"), jsonObject1.get(String.class, "field_13355"));
    }
}
