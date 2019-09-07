package fr.arthurbambou.randomlyaddinganything.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SavingSystem {

    private static File CONFIG_PATH = FabricLoader.getInstance().getConfigDirectory();

    private static final Gson DEFAULT_GSON = new GsonBuilder().setPrettyPrinting().create();

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
        configFile = new File(configPath, configFilename + ".mdat");
        if (!configFile.exists()) return true;

        return false;
    }

    public static void createFile() {
        configFile = new File(configPath, configFilename + ".mdat");
        if (!configFile.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(configFile);
                fileWriter.write(gson.toJson(new SavingSystem.SavingConfig().setMaterials(Materials.MATERIAL_LIST)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readFile() {
        configFile = new File(configPath, configFilename + ".mdat");
        try {
            FileReader fileReader = new FileReader(configFile);
            Materials.MATERIAL_LIST.clear();
            Materials.MATERIAL_LIST.addAll(gson.fromJson(fileReader, SavingConfig.class).getMaterials());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class SavingConfig {
        private List<Material> materials;

        public List<Material> getMaterials() {
            return materials;
        }

        public SavingConfig setMaterials(List<Material> materials) {
            this.materials = materials;
            return this;
        }
    }
}
