package io.github.vampirestudios.raa.config.readers.dimensions;

import blue.endless.jankson.JsonObject;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import io.github.vampirestudios.raa.config.readers.Version;
import io.github.vampirestudios.raa.generation.dimensions.*;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public enum DimensionFields {
    ID(Version.V1, "id", (configVersion, builder, jsonObject) -> {
        if(jsonObject.containsKey("id")) {
            return builder.id(Identifier.tryParse(jsonObject.get(String.class, "id")));
        } else {
            return builder.id(new Identifier(RandomlyAddingAnything.MOD_ID, RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator().asId(jsonObject.get(String.class, "name"))));
        }
    }),
    NAME(Version.V1, "name", (configVersion, builder, jsonObject) -> {
        return builder.name(jsonObject.get(String.class, "name"));
    }),
    DIMENSION_ID(Version.V1, "dimensionId", (configVersion, builder, jsonObject) -> {
        return builder.dimensionId(jsonObject.get(int.class, "dimensionId"));
    }),
    DIMENSION_PALLETS(Version.V1, "dimensionColorPalette", (configVersion, builder, jsonObject) -> {
        JsonObject colorPalletObject = jsonObject.getObject("dimensionColorPalette");
        DimensionColorPalette pallet = DimensionColorPalette.Builder.create()
                .skyColor(colorPalletObject.get(int.class, "skyColor"))
                .grassColor(colorPalletObject.get(int.class, "grassColor"))
                .fogColor(colorPalletObject.get(int.class, "fogColor"))
                .foliageColor(colorPalletObject.get(int.class, "foliageColor"))
                .stoneColor(colorPalletObject.get(int.class, "stoneColor")).build();
        return builder.colorPallet(pallet);
    }),
    HAS_LIGHT(Version.V1, "hasLight", (configVersion, builder, jsonObject) -> {
        return builder.hasLight(jsonObject.get(boolean.class, "hasLight"));
    }),
    HAS_SKY(Version.V1, "hasSky", (configVersion, builder, jsonObject) -> {
        return builder.hasSky(jsonObject.get(boolean.class, "hasSky"));
    }),
    CAN_SLEEP(Version.V1, "canSleep", (configVersion, builder, jsonObject) -> {
        return builder.canSleep(jsonObject.get(boolean.class, "canSleep"));
    }),
    FLAGS(Version.V1, "flags", (configVersion, builder, jsonObject) -> {
        return builder.setFlags(jsonObject.get(int.class, "flags"));
    }),
    MOBS(Version.V1, "mobs", (configVersion, builder, jsonObject) -> {
        //this disaster is needed because Jankson can't deseralize hashmaps for whatever reason
        //TODO: Optimize this
        HashMap<String, int[]> map = new Gson().fromJson(jsonObject.get("mobs").toJson(), new TypeToken<HashMap<String, int[]>>(){}.getType());
        System.out.println(map);
        return builder.mobs(map);
    }),
    BIOME_DATA(Version.OLD, "tools", (configVersion, builder, jsonObject) -> {
        JsonObject biomeDataObject = jsonObject.getObject("biomeData");
        String name = biomeDataObject.get(String.class, "biomeName");
        Identifier id;
        if(biomeDataObject.containsKey("id")) {
            id = Identifier.tryParse(biomeDataObject.get(String.class, "id"));
        } else {
            id = new Identifier(RandomlyAddingAnything.MOD_ID, RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator().asId(name));
        }
        DimensionBiomeData biomeData = DimensionBiomeDataBuilder.create(id, name)
                .surfaceBuilderVariantChance(biomeDataObject.get(int.class, "surfaceBuilderVariantChance"))
                .depth(biomeDataObject.get(int.class, "depth"))
                .scale(biomeDataObject.get(int.class, "scale"))
                .temperature(biomeDataObject.get(int.class, "temperature"))
                .downfall(biomeDataObject.get(int.class, "downfall"))
                .waterColor(biomeDataObject.get(int.class, "waterColor")).build();
        return builder.biome(biomeData);
    }),
    CHUNK_GENERATOR(Version.V1, "dimensionChunkGenerator", (configVersion, builder, jsonObject) -> {
        DimensionChunkGenerators dimensionChunkGenerators = jsonObject.get(DimensionChunkGenerators.class, "dimensionChunkGenerator");
        if (dimensionChunkGenerators != null) builder.chunkGenerator(dimensionChunkGenerators);
        else builder.chunkGenerator(Utils.randomCG(100));
        return builder;
    });

    private Version implementedVersion;
    private String name;
    private MaterialFieldsInterface fieldsInterface;

    DimensionFields(Version implementedVersion, String name, MaterialFieldsInterface fieldsInterface) {
        this.implementedVersion = implementedVersion;
        this.name = name;
        this.fieldsInterface = fieldsInterface;
    }

    public String getName() {
        return name;
    }

    public DimensionDataBuilder read(Version configVersion, DimensionDataBuilder builder, JsonObject jsonObject) {
        return this.fieldsInterface.read(configVersion, builder, jsonObject);
    }

    protected interface MaterialFieldsInterface {
        DimensionDataBuilder read(Version configVersion, DimensionDataBuilder builder, JsonObject jsonObject);
    }

    private static Identifier idFromJson(JsonObject jsonObject, String name) {
        JsonObject jsonObject1 = jsonObject.getObject(name);
        if (jsonObject1.containsKey("namespace")) return new Identifier(jsonObject1.get(String.class, "namespace"), jsonObject1.get(String.class, "path"));
        else return new Identifier(jsonObject1.get(String.class, "field_13353"), jsonObject1.get(String.class, "field_13355"));
    }
}
