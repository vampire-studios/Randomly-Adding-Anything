package io.github.vampirestudios.raa.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
            JsonObject jsonObject = jackson.load(configFile);
            if (jsonObject.containsKey("configVersion")) {
                int configVersion = jsonObject.get(int.class, "configVersion");
                System.out.println(configVersion);
                if (configVersion != 1) return;
                if (!jsonObject.containsKey("materials")) {
                    Materials.init();
                    SavingSystem.createFile();
                    return;
                }
                JsonArray jsonArray = jsonObject.get(JsonArray.class, "materials");
                if (jsonArray.size() == 0) {
                    Materials.init();
                    SavingSystem.createFile();
                    return;
                }

                for (int s = 0; s < jsonArray.size(); s++) {
                    Material material = jsonArray.get(Material.class, s);
                    assert material != null;
                    String id = material.getName().toLowerCase();
                    for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getCharMap().entrySet()) {
                        id = id.replace(entry.getKey(), entry.getValue());
                    }
                    Registry.register(Materials.MATERIALS, new Identifier(RandomlyAddingAnything.MOD_ID, id), material);
                }
//                JsonObject jsonElement = (JsonObject) jsonArray.get(0);
//                System.out.println(jsonElement.toString());
//                for (Map.Entry<String, JsonElement> entry : jsonElement.entrySet()) {
//                    System.out.println(entry.getKey() + "=" + entry.getValue().toString());
//                }
            } else {
                Materials.init();
                SavingSystem.createFile();
                return;
            }
        } catch (IOException | SyntaxError e) {
            fromOldFile(configFile);
        }
    }

    private static void fromOldFile(File file) {
        try {
            Object[] objects = gson.fromJson(new FileReader(file),Object[].class);
            for (Object object : objects) {
                JsonObject jsonObject = jackson.load(gson.toJson(object));
                MaterialBuilder materialBuilder = MaterialBuilder.create();
                materialBuilder.name(jsonObject.get(String.class, "name"))
                        .color(jsonObject.get(int.class,"rgb"))
                        .storageBlockTexture(new Identifier(jsonObject.get(String.class, "storageBlockTexture")))
                        .resourceItemTexture(new Identifier(jsonObject.get(String.class, "resourceItemTexture")))
                        .armor(jsonObject.get(boolean.class, "armor"))
                        .tools(jsonObject.get(boolean.class, "tools"))
                        .weapons(jsonObject.get(boolean.class, "weapons"))
                        .glowing(jsonObject.get(boolean.class, "glowing"))
                        .oreFlower(jsonObject.get(boolean.class, "oreFlower"));

                if (!jsonObject.containsKey("miningLevel")) {
                    materialBuilder.miningLevel(Rands.randInt(4));
                } else {
                    materialBuilder.miningLevel(jsonObject.get(int.class, "miningLevel"));
                }

                if (jsonObject.get(String.class, "nuggetTexture").equals("null")) {
                    materialBuilder.nuggetTexture(null);
                } else materialBuilder.nuggetTexture(new Identifier(jsonObject.get(String.class, "nuggetTexture")));

                if (!jsonObject.containsKey("food")) {
                    materialBuilder.food(Rands.chance(4));
                } else {
                    materialBuilder.food(jsonObject.get(boolean.class, "food"));
                }

                if (jsonObject.get(boolean.class, "armor")) {
                    materialBuilder.armor();
                }

                if (jsonObject.get(boolean.class, "tools")) {
                    materialBuilder.tools();
                }

                if (jsonObject.get(boolean.class, "weapons")) materialBuilder.weapons();

                if (jsonObject.containsKey("oreInformationJSON")) {
                    JsonObject oreInfo = jsonObject.getObject("oreInformationJSON");
                    materialBuilder.oreType(oreInfo.get(OreTypes.class, "oreTypes"))
                            .generatesIn(oreInfo.get(GeneratesIn.class, "generatesIn"))
                            .overlayTexture(new Identifier(oreInfo.get(String.class, "overlayTexture")))
                            .minXPAmount(0)
                            .maxXPAmount(Rands.randIntRange(0, 4))
                            .oreClusterSize(Rands.randIntRange(2, 6));
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
