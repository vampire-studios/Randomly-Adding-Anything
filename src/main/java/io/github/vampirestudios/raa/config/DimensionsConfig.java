package io.github.vampirestudios.raa.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.utils.GsonUtils;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.io.FileWriter;
import java.util.Arrays;

public class DimensionsConfig extends RAADataConfig {
    public DimensionsConfig(String fileName) {
        super(fileName);
    }

    @Override
    public void generate() {
        Dimensions.generate();
    }

    @Override
    protected JsonObject upgrade(JsonObject json, int version) {
        JsonArray dimensions = JsonHelper.getArray(json, "dimensions");

        switch (version) {
            case 0:
                break;
            case 1:
                iterateArrayObjects(dimensions, dimension -> {
                    if (!JsonHelper.isString(dimension.get("id")))
                        dimension.addProperty("id", GsonUtils.idFromOldStyle(dimension.getAsJsonObject("id")).toString());
                    if (dimension.has("dimensionColorPallet")) {
                        dimension.add("dimensionColorPalette", dimension.getAsJsonObject("dimensionColorPallet"));
                    }
                    dimension.add("hasSkyLight", dimension.get("hasLight"));

                    JsonObject biomeData = dimension.getAsJsonObject("biomeData");
                    if (!JsonHelper.isString(biomeData.get("id")))
                        biomeData.addProperty("id", GsonUtils.idFromOldStyle(biomeData.getAsJsonObject("id")).toString());

                    if (!dimension.has("dimensionChunkGenerator")) {
                        dimension.addProperty("dimensionChunkGenerator", Utils.randomCG(100).name());
                    }
                });
                break;
        }

        return json;
    }

    @Override
    protected void load(JsonObject jsonObject) {
        DimensionData[] dimensionsData = GsonUtils.getGson().fromJson(JsonHelper.getArray(jsonObject, "dimensions"), DimensionData[].class);
        Arrays.stream(dimensionsData).forEach(dimensionData -> Registry.register(Dimensions.DIMENSIONS, dimensionData.getId(), dimensionData));
    }

    @Override
    protected void save(FileWriter fileWriter) {
        JsonObject main = new JsonObject();
        main.add("dimensions", GsonUtils.getGson().toJsonTree(Dimensions.DIMENSIONS.stream().toArray(DimensionData[]::new)));
        main.addProperty("configVersion", CURRENT_VERSION);
        GsonUtils.getGson().toJson(main, fileWriter);
    }
}
