package io.github.vampirestudios.raa.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.config.readers.Versions;
import io.github.vampirestudios.raa.config.readers.dimensions.DimensionFields;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.registries.Materials;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DimensionSavingSystem {

    private static File CONFIG_PATH = FabricLoader.getInstance().getConfigDirectory();

    private static final Gson DEFAULT_GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();

    private static Jankson jackson;

    private static File configFile;
    private static File configPath;
    private static String configFilename = "dimension_config";
    private static Gson gson = DEFAULT_GSON;
    private static int fileNumber = 0;

    public static boolean init() {
        jackson = Jankson.builder().build();
        configPath = new File(new File(CONFIG_PATH, RandomlyAddingAnything.MOD_ID), "dimensions");
        if (!configPath.exists()) {
            configPath.mkdirs();
            return true;
        }
        configFile = new File(configPath, configFilename  + ".json");
        return !configFile.exists();
    }

    public static void createFile() {
        configFile = new File(configPath, configFilename + ".json");
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(configFile));
            DimensionData[] materialJSONS = toJSON();
            fileWriter.write("{\"configVersion\": 1,");
            fileWriter.newLine();
            fileWriter.flush();
            fileWriter.write("\"dimensions\": [");
            fileWriter.newLine();
            fileWriter.flush();

            for (int a = 0; a < materialJSONS.length; a++) {
                if (a == materialJSONS.length - 1) {
                    fileWriter.write(gson.toJson(materialJSONS[a]));
                    fileWriter.newLine();
                    fileWriter.flush();
                    continue;
                }
                fileWriter.write(gson.toJson(materialJSONS[a]) + ",");
                fileWriter.newLine();
                fileWriter.flush();
            }
            fileWriter.write("]" + "\n" + "}");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile() {
        configFile = new File(configPath, configFilename + ".json");
        try {
            JsonObject jsonObject1 = jackson.load(configFile);
            if (jsonObject1.containsKey("configVersion")) {
                int configVersion = jsonObject1.get(int.class, "configVersion");
                Versions versions = Versions.getFromInt(configVersion);
                if (versions == null) {
                    Dimensions.init();
                    DimensionSavingSystem.createFile();
                    return;
                }
                if (!jsonObject1.containsKey("dimensions")) {
                    Dimensions.init();
                    DimensionSavingSystem.createFile();
                    return;
                }
                JsonArray jsonArray = jsonObject1.get(JsonArray.class, "dimensions");
                if (jsonArray.size() == 0) {
                    Dimensions.init();
                    DimensionSavingSystem.createFile();
                    return;
                }

                for (int s = 0; s < jsonArray.size(); s++) {
                    JsonObject jsonObject = (JsonObject) jsonArray.get(s);
                    DimensionData.Builder dimensionDataBuilder = DimensionData.Builder.create();

                    for (DimensionFields dimensionFields : DimensionFields.values()) {
                        dimensionFields.read(versions, dimensionDataBuilder, jsonObject);
                    }

                    DimensionData dimensionData = dimensionDataBuilder.build();
                    String id = dimensionData.getName().toLowerCase();
                    for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionCharMap().entrySet()) {
                        id = id.replace(entry.getKey(), entry.getValue());
                    }
                    Registry.register(Dimensions.DIMENSIONS, new Identifier(RandomlyAddingAnything.MOD_ID, id), dimensionData);
                }
            } else {
                Materials.init();
                DimensionSavingSystem.createFile();
            }
        } catch (IOException | SyntaxError e) {
            e.printStackTrace();
        }
    }

    private static DimensionData[] toJSON() {
        List<DimensionData> materials = new ArrayList<>();
        Dimensions.DIMENSIONS.forEach(materials::add);
        return materials.toArray(new DimensionData[0]);
    }
}
