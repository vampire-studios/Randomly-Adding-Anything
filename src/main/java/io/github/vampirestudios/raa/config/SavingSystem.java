package io.github.vampirestudios.raa.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vampirestudios.raa.materials.Material;
import io.github.vampirestudios.raa.materials.OreInformation;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreTypes;
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
        if (!configFile.exists()) {
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
    }

    public static void readFile() {
        configFile = new File(configPath, configFilename + "_" + fileNumber + ".json");
        try {
            JsonObject jsonObject = jackson.load(configFile);
            System.out.println(jsonObject.toString());
//            if (jsonObject.containsKey("configVersion")) {
                int configVersion = jsonObject.get(int.class, "configVersion");
                System.out.println(configVersion);
//                if (configVersion != 0) return;
//                if (!jsonObject.containsKey("materials")) {
//                    Materials.init();
//                    SavingSystem.createFile();
//                    return;
//                }
                JsonArray jsonArray = jsonObject.get(JsonArray.class, "materials");
                System.out.println(jsonArray.toString());
//                if (jsonArray.size() == 0) {
//                    Materials.init();
//                    SavingSystem.createFile();
//                    return;
//                }
                JsonObject jsonElement = (JsonObject) jsonArray.get(0);
                System.out.println(jsonElement.toString());
                for (Field field : Material.class.getDeclaredFields()) {
                    System.out.println(jsonElement.containsKey(field.getName()) + "," + field.getName());
                }
//            }
//            for (Material material : fromJSON(gson.fromJson(fileReader, MaterialJSON[].class))) {
//                String id = material.getName().toLowerCase();
//                for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getCharMap().entrySet()) {
//                    id = id.replace(entry.getKey(), entry.getValue());
//                }
//                Registry.register(Materials.MATERIALS, new Identifier(RandomlyAddingAnything.MOD_ID, id), material);
//            }
        } catch (IOException | SyntaxError e) {
            e.printStackTrace();
        }
    }

//    private static List<Material> fromJSON(MaterialJSON[] fromJson) {
//        List<Material> materials = new ArrayList<>();
//        for (MaterialJSON materialJSON : fromJson) {
//            OreInformationJSON oreInformationJSON = materialJSON.oreInformationJSON;
//            OreInformation oreInformation = new OreInformation(oreInformationJSON.oreTypes, oreInformationJSON.generatesIn, new Identifier(oreInformationJSON.overlayTexture),
//                    oreInformationJSON.oreCount, oreInformationJSON.minXPAmount, oreInformationJSON.maxXPAmount, oreInformationJSON.oreClusterSize);
//            Material material = new Material(oreInformation, materialJSON.name, materialJSON.rgb, materialJSON.miningLevel, new Identifier(materialJSON.storageBlockTexture), new Identifier(materialJSON.resourceItemTexture),
//                    new Identifier(materialJSON.nuggetTexture), materialJSON.armor, materialJSON.armorMaterial, materialJSON.tools, materialJSON.weapons, materialJSON.toolMaterial,
//                    materialJSON.glowing, materialJSON.oreFlower, materialJSON.food);
//            materials.add(material);
//        }
//
//        return materials;
//    }

    private static Material[] toJSON() {
        List<Material> materials = new ArrayList<>();
        Materials.MATERIALS.forEach(materials::add);
        return materials.toArray(new Material[0]);
    }

//    protected static class MaterialJSON {
////        OreInformationJSON oreInformationJSON;
////        public String name;
////        int rgb;
////        int miningLevel;
////        String storageBlockTexture;
////        String resourceItemTexture;
////        String nuggetTexture;
////        boolean armor;
////        CustomArmorMaterial armorMaterial;
////        boolean tools;
////        boolean weapons;
////        CustomToolMaterial toolMaterial;
////        boolean glowing;
////        boolean oreFlower;
////        boolean food;
////
////        MaterialJSON(OreInformationJSON oreInformationJSON, String name, int rgb, int miningLevel, String storageBlockTexture,
////                     String resourceItemTexture, String nuggetTexture, boolean armor, CustomArmorMaterial armorMaterial,
////                     boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing, boolean oreFlower, boolean food) {
////            this.oreInformationJSON = oreInformationJSON;
////            this.name = name;
////            this.rgb = rgb;
////            this.miningLevel = miningLevel;
////            this.storageBlockTexture = storageBlockTexture;
////            this.resourceItemTexture = resourceItemTexture;
////            this.nuggetTexture = nuggetTexture;
////            this.armor = armor;
////            this.armorMaterial = armorMaterial;
////            this.tools = tools;
////            this.weapons = weapons;
////            this.toolMaterial = toolMaterial;
////            this.glowing = glowing;
////            this.oreFlower = oreFlower;
////            this.food = food;
////        }
////    }
////
////    protected static class OreInformationJSON {
////        OreTypes oreTypes;
////        GeneratesIn generatesIn;
////        String overlayTexture;
////        int oreCount;
////        int minXPAmount;
////        int maxXPAmount;
////        private int oreClusterSize;
////
////        OreInformationJSON(OreTypes oreTypes, GeneratesIn generatesIn, String overlayTexture, int oreCount, int minXPAmount, int maxXPAmount, int oreClusterSize) {
////            this.oreTypes = oreTypes;
////            this.generatesIn = generatesIn;
////            this.overlayTexture = overlayTexture;
////            this.oreCount = oreCount;
////            this.minXPAmount = minXPAmount;
////            this.maxXPAmount = maxXPAmount;
////            this.oreClusterSize = oreClusterSize;
////        }
////    }
}
