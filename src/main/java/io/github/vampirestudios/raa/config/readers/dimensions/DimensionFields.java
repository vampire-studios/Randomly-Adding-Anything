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
