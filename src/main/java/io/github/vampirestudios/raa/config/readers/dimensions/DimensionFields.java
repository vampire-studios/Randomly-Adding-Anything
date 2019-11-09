package io.github.vampirestudios.raa.config.readers.dimensions;

import blue.endless.jankson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import io.github.vampirestudios.raa.config.readers.ConfigVersion;
import io.github.vampirestudios.raa.generation.dimensions.DimensionBiomeData;
import io.github.vampirestudios.raa.generation.dimensions.DimensionColorPalette;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public enum DimensionFields {
    ID(ConfigVersion.V1, "id", (configVersion, builder, jsonObject) -> {
        if(jsonObject.containsKey("id")) {
            return builder.id(Identifier.tryParse(jsonObject.get(String.class, "id")));
        } else {
            return builder.id(new Identifier(RandomlyAddingAnything.MOD_ID, RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator().asId(jsonObject.get(String.class, "name"))));
        }
    }),
    NAME(ConfigVersion.V1, "name", (configVersion, builder, jsonObject) -> {
        return builder.name(jsonObject.get(String.class, "name"));
    }),
    DIMENSION_ID(ConfigVersion.V1, "dimensionId", (configVersion, builder, jsonObject) -> {
        return builder.dimensionId(jsonObject.get(int.class, "dimensionId"));
    }),
    DIMENSION_PALETTE(ConfigVersion.V1, "dimensionColorPalette", (configVersion, builder, jsonObject) -> {
        JsonObject colorPalletObject = jsonObject.getObject("dimensionColorPallet");
        DimensionColorPalette pallet = DimensionColorPalette.Builder.create()
                .skyColor(colorPalletObject.get(int.class, "skyColor"))
                .grassColor(colorPalletObject.get(int.class, "grassColor"))
                .fogColor(colorPalletObject.get(int.class, "fogColor"))
                .foliageColor(colorPalletObject.get(int.class, "foliageColor"))
                .stoneColor(colorPalletObject.get(int.class, "stoneColor")).build();
        return builder.colorPalette(pallet);
    }),
    HAS_LIGHT(ConfigVersion.V1, "hasLight", (configVersion, builder, jsonObject) -> {
        return builder.hasSkyLight(jsonObject.get(boolean.class, "hasLight"));
    }),
    HAS_SKY(ConfigVersion.V1, "hasSky", (configVersion, builder, jsonObject) -> {
        return builder.hasSky(jsonObject.get(boolean.class, "hasSky"));
    }),
    CAN_SLEEP(ConfigVersion.V1, "canSleep", (configVersion, builder, jsonObject) -> {
        return builder.canSleep(jsonObject.get(boolean.class, "canSleep"));
    }),
    FLAGS(ConfigVersion.V1, "flags", (configVersion, builder, jsonObject) -> {
        return builder.flags(jsonObject.get(int.class, "flags"));
    }),
    MOBS(ConfigVersion.V1, "mobs", (configVersion, builder, jsonObject) -> {
        //this disaster is needed because Jankson can't deserialize hashmaps for whatever reason
        //TODO: Optimize this
		try {
            HashMap<String, int[]> map = new Gson().fromJson(jsonObject.get("mobs").toJson(), new TypeToken<HashMap<String, int[]>>() {}.getType());
            System.out.println(map);
            return builder.mobs(map);
        } catch (Throwable e) {
		    System.out.println("Failed to read mobs from config!");
		    e.printStackTrace();
            return builder.mobs(new HashMap<>());
        }
    }),
    BIOME_DATA(ConfigVersion.OLD, "tools", (configVersion, builder, jsonObject) -> {
        JsonObject biomeDataObject = jsonObject.getObject("biomeData");
        String name = biomeDataObject.get(String.class, "biomeName");
        Identifier id;
        if(biomeDataObject.containsKey("id")) {
            id = Identifier.tryParse(biomeDataObject.get(String.class, "id"));
        } else {
            id = new Identifier(RandomlyAddingAnything.MOD_ID, RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator().asId(name));
        }
        DimensionBiomeData biomeData = DimensionBiomeData.Builder.create(id, name)
                .surfaceBuilderVariantChance(biomeDataObject.get(int.class, "surfaceBuilderVariantChance"))
                .depth(biomeDataObject.get(int.class, "depth"))
                .scale(biomeDataObject.get(int.class, "scale"))
                .temperature(biomeDataObject.get(int.class, "temperature"))
                .downfall(biomeDataObject.get(int.class, "downfall"))
                .waterColor(biomeDataObject.get(int.class, "waterColor")).build();
        return builder.biome(biomeData);
    }),
    CHUNK_GENERATOR(ConfigVersion.V1, "dimensionChunkGenerator", (configVersion, builder, jsonObject) -> {
        DimensionChunkGenerators dimensionChunkGenerators = jsonObject.get(DimensionChunkGenerators.class, "dimensionChunkGenerator");
        if (dimensionChunkGenerators != null) builder.chunkGenerator(dimensionChunkGenerators);
        else builder.chunkGenerator(Utils.randomCG(100));
        return builder;
    });

    private ConfigVersion implementedVersion;
    private String name;
    private MaterialFieldsInterface fieldsInterface;

    DimensionFields(ConfigVersion implementedVersion, String name, MaterialFieldsInterface fieldsInterface) {
        this.implementedVersion = implementedVersion;
        this.name = name;
        this.fieldsInterface = fieldsInterface;
    }

    public String getName() {
        return name;
    }

    public DimensionData.Builder read(ConfigVersion configVersion, DimensionData.Builder builder, JsonObject jsonObject) {
        try {
            return this.fieldsInterface.read(configVersion, builder, jsonObject);
        } catch (Throwable e) {
            System.out.println("Failed to read config property");
            e.printStackTrace();
        }
        return builder;
    }

    protected interface MaterialFieldsInterface {
        DimensionData.Builder read(ConfigVersion configVersion, DimensionData.Builder builder, JsonObject jsonObject);
    }

    private static Identifier idFromJson(JsonObject jsonObject, String name) {
        JsonObject jsonObject1 = jsonObject.getObject(name);
        if (jsonObject1.containsKey("namespace")) return new Identifier(jsonObject1.get(String.class, "namespace"), jsonObject1.get(String.class, "path"));
        else return new Identifier(jsonObject1.get(String.class, "field_13353"), jsonObject1.get(String.class, "field_13355"));
    }
}
