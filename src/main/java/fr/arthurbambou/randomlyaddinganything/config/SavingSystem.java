package fr.arthurbambou.randomlyaddinganything.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.arthurbambou.randomlyaddinganything.api.enums.AppearsIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import fr.arthurbambou.randomlyaddinganything.materials.OreInformation;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SavingSystem {

    private static File CONFIG_PATH = FabricLoader.getInstance().getConfigDirectory();

    private static final Gson DEFAULT_GSON = new GsonBuilder().setLenient().create();

    private static File configFile;
    private static File configPath;
    private static String configFilename = "materials";
    private static Gson gson = DEFAULT_GSON;

    public static boolean init() {
        configPath = new File(new File(CONFIG_PATH, "raa"), "materials");
        if (!configPath.exists()) {
            configPath.mkdirs();
            return true;
        }
        configFile = new File(configPath, configFilename + ".json");
        if (!configFile.exists()) return true;

        return false;
    }

    public static void createFile() {
        configFile = new File(configPath, configFilename + ".json");
        if (!configFile.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(configFile);
                System.out.println(gson.toJson(toJSON()));
                fileWriter.write(gson.toJson(toJSON()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readFile() {
        configFile = new File(configPath, configFilename + ".json");
        try {
            FileReader fileReader = new FileReader(configFile);
            Materials.MATERIAL_LIST.clear();
            Materials.MATERIAL_LIST.addAll(fromJSON(gson.fromJson(fileReader, MaterialJSON[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Material> fromJSON(MaterialJSON[] fromJson) {
        List<Material> materials = new ArrayList<>();
        for (MaterialJSON materialJSON : fromJson) {
            OreInformationJSON oreInformationJSON = materialJSON.oreInformationJSON;
            OreInformation oreInformation = new OreInformation(oreInformationJSON.oreTypes, oreInformationJSON.appearsIn,
                    new Identifier(oreInformationJSON.overlayTexture), oreInformationJSON.minXPAmount, oreInformationJSON.maxXPAmount);
            Material material;
            if (materialJSON.nuggetTexture == "null") {
                material = new Material(oreInformation, materialJSON.name, materialJSON.rgb,
                        new Identifier(materialJSON.storageBlockTexture), new Identifier(materialJSON.resourceItemTexture),
                        materialJSON.armor, materialJSON.tools, materialJSON.weapons, materialJSON.glowing);
            } else {
                material = new Material(oreInformation, materialJSON.name, materialJSON.rgb,
                        new Identifier(materialJSON.storageBlockTexture), new Identifier(materialJSON.resourceItemTexture), new Identifier(materialJSON.nuggetTexture),
                        materialJSON.armor, materialJSON.tools, materialJSON.weapons, materialJSON.glowing);
            }
            materials.add(material);
        }

        return materials;
    }

    public static MaterialJSON[] toJSON() {
        List<MaterialJSON> materialJSONS = new ArrayList<>();
        for (Material material : Materials.MATERIAL_LIST) {
            OreInformation oreInformation = material.getOreInformation();
            OreInformationJSON oreInformationJSON = new OreInformationJSON(oreInformation.getOreType(),
                    oreInformation.getGenerateIn(), oreInformation.getOverlayTexture().toString(), oreInformation.getMinXPAmount(), oreInformation.getMaxXPAmount());
            String nuggetTexture;
            if (material.getNuggetTexture() == null) {
                nuggetTexture = "null";
            } else {
                nuggetTexture = material.getNuggetTexture().toString();
            }
            MaterialJSON materialJSON = new MaterialJSON(oreInformationJSON, material.getName(), material.getRGBColor(),
                    material.getStorageBlockTexture().toString(), material.getResourceItemTexture().toString(), nuggetTexture,
                    material.hasArmor(), material.hasTools(), material.hasWeapons(), material.isGlowing());
            materialJSONS.add(materialJSON);
        }

        return materialJSONS.toArray(new MaterialJSON[materialJSONS.size()]);
    }

    protected static class MaterialJSON {
        public OreInformationJSON oreInformationJSON;
        public String name;
        public int rgb;
        public String storageBlockTexture;
        public String resourceItemTexture;
        public String nuggetTexture;
        public boolean armor;
        public boolean tools;
        public boolean weapons;
        public boolean glowing;

        public MaterialJSON(OreInformationJSON oreInformationJSON, String name, int rgb, String storageBlockTexture,
                            String resourceItemTexture, String nuggetTexture, boolean armor, boolean tools, boolean weapons, boolean glowing) {
            this.oreInformationJSON = oreInformationJSON;
            this.name = name;
            this.rgb = rgb;
            this.storageBlockTexture = storageBlockTexture;
            this.resourceItemTexture = resourceItemTexture;
            this.nuggetTexture = nuggetTexture;
            this.armor = armor;
            this.tools = tools;
            this.weapons = weapons;
            this.glowing = glowing;
        }
    }

    protected static class OreInformationJSON {
        public OreTypes oreTypes;
        public AppearsIn appearsIn;
        public String overlayTexture;
        public int minXPAmount;
        public int maxXPAmount;

        public  OreInformationJSON(OreTypes oreTypes, AppearsIn appearsIn, String overlayTexture, int minXPAmount, int maxXPAmount) {
            this.oreTypes = oreTypes;
            this.appearsIn = appearsIn;
            this.overlayTexture = overlayTexture;
            this.minXPAmount = minXPAmount;
            this.maxXPAmount = maxXPAmount;
        }
    }
}
