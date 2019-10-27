package io.github.vampirestudios.raa.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.api.enums.TextureTypes;
import io.github.vampirestudios.raa.generation.materials.CustomArmorMaterial;
import io.github.vampirestudios.raa.generation.materials.CustomToolMaterial;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.generation.materials.MaterialBuilder;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Rands;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.*;
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
            JsonObject jsonObject1 = jackson.load(configFile);
            if (jsonObject1.containsKey("configVersion")) {
                int configVersion = jsonObject1.get(int.class, "configVersion");
                if (configVersion != 1) return;
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
                    String name = jsonObject.get(String.class, "name");
                    int miningLevel = jsonObject.get(int.class, "miningLevel");
                    MaterialBuilder materialBuilder = MaterialBuilder.create();
                    materialBuilder.name(name)
                            .color(jsonObject.get(int.class,"color"))
                            .storageBlockTexture(idFromJson(jsonObject,"storageBlockTexture"))
                            .resourceItemTexture(idFromJson(jsonObject, "resourceItemTexture"))
                            .armor(jsonObject.get(boolean.class, "armor"))
                            .tools(jsonObject.get(boolean.class, "tools"))
                            .weapons(jsonObject.get(boolean.class, "weapons"))
                            .glowing(jsonObject.get(boolean.class, "glowing"))
                            .oreFlower(jsonObject.get(boolean.class, "oreFlower"))
                            .miningLevel(miningLevel)
                            .food(jsonObject.get(boolean.class, "food"));
                    JsonObject oreInfo = jsonObject.getObject("oreInformation");
                    OreTypes oreTypes = oreInfo.get(OreTypes.class, "oreType");
                    materialBuilder.oreType(oreTypes)
                            .generatesIn(oreInfo.get(GeneratesIn.class, "generateIn"))
                            .overlayTexture(idFromJson(oreInfo,"overlayTexture"))
                            .minXPAmount(0)
                            .maxXPAmount(oreInfo.get(int.class, "maxXPAmount"))
                            .oreClusterSize(oreInfo.get(int.class, "oreClusterSize"));

                    if (!jsonObject.containsKey("nuggetTexture")) {
                        materialBuilder.nuggetTexture(null);
                    } else materialBuilder.nuggetTexture(idFromJson(jsonObject, "nuggetTexture"));

                    if (jsonObject.get(boolean.class, "armor")) {
                        JsonObject armorObject = jsonObject.getObject("armorMaterial");
                        int durabilityMultiplier = armorObject.get(int.class, "durabilityMultiplier");
                        int[] protectionAmounts = armorObject.get(int[].class, "protectionAmounts");
                        int enchantability = armorObject.get(int.class, "enchantability");
                        float toughness = armorObject.get(float.class, "toughness");
                        int horseArmorBonus = armorObject.get(int.class, "horseArmorBonus");
                        CustomArmorMaterial armorMaterial = new CustomArmorMaterial(
                                name, oreTypes, durabilityMultiplier, protectionAmounts,
                                enchantability, toughness, horseArmorBonus
                        );
                        materialBuilder.armor(armorMaterial);
                    }

                    if (jsonObject.get(boolean.class, "tools")) {
                        JsonObject toolObject = jsonObject.getObject("toolMaterial");
                        int durability = toolObject.get(int.class, "durability");
                        int enchantability = toolObject.get(int.class, "enchantability");
                        float miningSpeed = toolObject.get(float.class, "miningSpeed");
                        float attackDamage = toolObject.get(float.class, "attackDamage");
                        float hoeAttackSpeed = toolObject.get(float.class, "hoeAttackSpeed");
                        float axeAttackDamage = toolObject.get(float.class, "axeAttackDamage");
                        float axeAttackSpeed = toolObject.get(float.class, "axeAttackSpeed");
                        float swordAttackDamage = toolObject.get(float.class, "swordAttackDamage");
                        CustomToolMaterial toolMaterial = new CustomToolMaterial(
                                name, oreTypes, durability, miningSpeed, attackDamage,
                                miningLevel, enchantability, hoeAttackSpeed, axeAttackDamage,
                                axeAttackSpeed, swordAttackDamage
                        );
                        materialBuilder.tools(toolMaterial);
                    }

                    if (jsonObject.get(boolean.class, "weapons")) {
                        JsonObject toolObject = jsonObject.getObject("toolMaterial");
                        int durability = toolObject.get(int.class, "durability");
                        int enchantability = toolObject.get(int.class, "enchantability");
                        float miningSpeed = toolObject.get(float.class, "miningSpeed");
                        float attackDamage = toolObject.get(float.class, "attackDamage");
                        float hoeAttackSpeed = toolObject.get(float.class, "hoeAttackSpeed");
                        float axeAttackDamage = toolObject.get(float.class, "axeAttackDamage");
                        float axeAttackSpeed = toolObject.get(float.class, "axeAttackSpeed");
                        float swordAttackDamage = toolObject.get(float.class, "swordAttackDamage");
                        CustomToolMaterial toolMaterial = new CustomToolMaterial(
                                name, oreTypes, durability, miningSpeed, attackDamage,
                                miningLevel, enchantability, hoeAttackSpeed, axeAttackDamage,
                                axeAttackSpeed, swordAttackDamage
                        );
                        materialBuilder.weapons(toolMaterial);
                    }

                    Material material = materialBuilder.buildFromJSON();
                    String id = material.getName().toLowerCase();
                    for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialCharMap().entrySet()) {
                        id = id.replace(entry.getKey(), entry.getValue());
                    }
                    Registry.register(Materials.MATERIALS, new Identifier(RandomlyAddingAnything.MOD_ID, id), material);
                }
            } else {
                Materials.init();
                SavingSystem.createFile();
            }
        } catch (IOException | SyntaxError e) {
            System.out.println(e);
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
                materialBuilder.name(jsonObject.get(String.class, "name"))
                        .color(jsonObject.get(int.class,"rgb"))
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

                Identifier resourceItem = new Identifier(jsonObject.get(String.class, "resourceItemTexture"));
                if (TextureTypes.INGOT_TEXTURES.contains(resourceItem) || TextureTypes.CRYSTAL_ITEM_TEXTURES.contains(resourceItem) || TextureTypes.GEM_ITEM_TEXTURES.contains(resourceItem)) {
                    materialBuilder.resourceItemTexture(resourceItem);
                } else {
                    materialBuilder.resourceItemTexture();
                }

                Identifier storageBlock = new Identifier(jsonObject.get(String.class, "storageBlockTexture"));
                if (TextureTypes.METAL_BLOCK_TEXTURES.contains(storageBlock) || TextureTypes.CRYSTAL_BLOCK_TEXTURES.contains(storageBlock) || TextureTypes.GEM_BLOCK_TEXTURES.contains(storageBlock)) {
                    materialBuilder.storageBlockTexture(storageBlock);
                } else {
                    materialBuilder.storageBlockTexture();
                }

                Material material = materialBuilder.buildFromJSON();
                String id = material.getName().toLowerCase();
                for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialCharMap().entrySet()) {
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
