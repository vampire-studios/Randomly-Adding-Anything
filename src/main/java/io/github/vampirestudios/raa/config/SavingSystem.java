package io.github.vampirestudios.raa.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vampirestudios.raa.config.readers.material.MaterialFields;
import io.github.vampirestudios.raa.config.readers.material.Versions;
import io.github.vampirestudios.raa.materials.*;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.utils.Rands;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.UncaughtExceptionHandler;
import net.minecraft.util.registry.Registry;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SavingSystem {

    public static final Versions latestVersion = Versions.V1;

    private static File CONFIG_PATH = FabricLoader.getInstance().getConfigDirectory();

    private static final Gson DEFAULT_GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();

    private static Jankson jackson;

    private static File configFile;
    private static File configPath;
    private static String configFilename = "materials";
    private static Gson gson = DEFAULT_GSON;
    private static int fileNumber = 0;

    public static boolean init() {
        jackson = Jankson.builder().build();
        configPath = new File(new File(CONFIG_PATH, "raa"), "materials");
        if (!configPath.exists()) {
            configPath.mkdirs();
            return true;
        }
        configFile = new File(configPath, configFilename + "_" + fileNumber + ".json");
        return !configFile.exists();
    }

    public static void createFile() {
        configFile = new File(configPath, configFilename + "_" + fileNumber + ".json");
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(configFile));
            Material[] materialJSONS = toJSON();
            fileWriter.write("{\"configVersion\":1,");
            fileWriter.newLine();
            fileWriter.flush();
            fileWriter.write("\"materials\": [");
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
            fileWriter.write("]}");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile() {
        configFile = new File(configPath, configFilename + "_" + fileNumber + ".json");
        try {
            JsonObject jsonObject1 = jackson.load(configFile);
            if (jsonObject1.containsKey("configVersion")) {
                int configVersion = jsonObject1.get(int.class, "configVersion");
                Versions versions = Versions.getFromInt(configVersion);
                if (versions == null) {
                    Materials.init();
                    SavingSystem.createFile();
                    return;
                }
                if (!jsonObject1.containsKey("materials")) {
                    Materials.init();
                    SavingSystem.createFile();
                    return;
                }
                JsonArray jsonArray = jsonObject1.get(JsonArray.class, "materials");
                if (jsonArray.size() == 0) {
                    Materials.init();
                    SavingSystem.createFile();
                    return;
                }

                for (int s = 0; s < jsonArray.size(); s++) {
                    JsonObject jsonObject = (JsonObject) jsonArray.get(s);
                    MaterialBuilder materialBuilder = MaterialBuilder.create();
                    for (MaterialFields materialFields : MaterialFields.values()) {
                        materialFields.read(versions, materialBuilder, jsonObject);
                    }

                    Material material = materialBuilder.buildFromJSON();
                    String id = material.getName().toLowerCase();
                    for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getCharMap().entrySet()) {
                        id = id.replace(entry.getKey(), entry.getValue());
                    }
                    Registry.register(Materials.MATERIALS, new Identifier(RandomlyAddingAnything.MOD_ID, id), material);
                }
            } else {
                Materials.init();
                SavingSystem.createFile();
                return;
            }
        } catch (IOException | SyntaxError e) {
            fromOldFile(configFile);
        }
    }

    private static Identifier idFromJson(JsonObject jsonObject, String name) {
        JsonObject jsonObject1 = jsonObject.getObject(name);
        if (jsonObject1.containsKey("namespace")) return new Identifier(jsonObject1.get(String.class, "namespace"), jsonObject1.get(String.class, "path"));
        else return new Identifier(jsonObject1.get(String.class, "field_13353"), jsonObject1.get(String.class, "field_13355"));
    }

    private static void fromOldFile(File file) {
        try {
            Object[] objects = gson.fromJson(new FileReader(file),Object[].class);
            for (Object object : objects) {
                JsonObject jsonObject = jackson.load(gson.toJson(object));
                MaterialBuilder materialBuilder = MaterialBuilder.create();
                for (MaterialFields materialFields : MaterialFields.values()) {
                    materialFields.read(Versions.OLD, materialBuilder, jsonObject);
                }

                Material material = materialBuilder.buildFromJSON();
                String id = material.getName().toLowerCase();
                for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getCharMap().entrySet()) {
                    id = id.replace(entry.getKey(), entry.getValue());
                }
                Registry.register(Materials.MATERIALS, new Identifier(RandomlyAddingAnything.MOD_ID, id), material);
            }
            createFile();
        } catch (FileNotFoundException | SyntaxError e) {
            e.printStackTrace();
        }
    }

    private static Material[] toJSON() {
        List<Material> materials = new ArrayList<>();
        Materials.MATERIALS.forEach(materials::add);
        return materials.toArray(new Material[0]);
    }
}
