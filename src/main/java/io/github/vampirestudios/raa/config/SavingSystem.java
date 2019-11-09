package io.github.vampirestudios.raa.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cottonmc.jankson.JanksonFactory;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.config.readers.Version;
import io.github.vampirestudios.raa.config.readers.material.MaterialFields;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.generation.materials.MaterialBuilder;
import io.github.vampirestudios.raa.registries.Materials;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SavingSystem {

    public static final Version latestVersion = Version.V1;

    private static File CONFIG_PATH = FabricLoader.getInstance().getConfigDirectory();

    private static final Gson DEFAULT_GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();

    private static Jankson jackson;

    private static File configFile;
    private static File configPath;
    private static String configFilename = "material_config";
    private static Gson gson = DEFAULT_GSON;
    private static int fileNumber = 0;

    public static boolean init() {
        jackson = JanksonFactory.builder().build();
        configPath = new File(new File(CONFIG_PATH, RandomlyAddingAnything.MOD_ID), "materials");
        if (!configPath.exists()) {
            configPath.mkdirs();
            return true;
        }
        configFile = new File(configPath, configFilename + ".json");
        return !configFile.exists();
    }

    public static void createFile() {
        configFile = new File(configPath, configFilename+ ".json");
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(configFile));
            Material[] materialJSONS = toJSON();
            fileWriter.write("{");
            fileWriter.newLine();
            fileWriter.write("\"configVersion\":1,");
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
        configFile = new File(configPath, configFilename + ".json");
        try {
            JsonObject jsonObject1 = jackson.load(configFile);
            if (jsonObject1.containsKey("configVersion")) {
                int configVersion = jsonObject1.get(int.class, "configVersion");
                Version version = Version.getFromInt(configVersion);
                if (version == null) {
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
                if (jsonArray.isEmpty()) {
                    Materials.init();
                    SavingSystem.createFile();
                    return;
                }

                for (int s = 0; s < jsonArray.size(); s++) {
                    JsonObject jsonObject = (JsonObject) jsonArray.get(s);
                    MaterialBuilder materialBuilder = MaterialBuilder.create();
                    for (MaterialFields materialFields : MaterialFields.values()) {
                        materialFields.read(version, materialBuilder, jsonObject);
                    }

                    Material material = materialBuilder.build();
                    Registry.register(Materials.MATERIALS, material.getId(), material);
                }
            } else {
                Materials.init();
                SavingSystem.createFile();
            }

        } catch (IOException | SyntaxError e) {
            e.printStackTrace();
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
                    materialFields.read(Version.OLD, materialBuilder, jsonObject);
                }

                Material material = materialBuilder.build();
                Registry.register(Materials.MATERIALS, material.getId(), material);
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
