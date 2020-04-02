package io.github.vampirestudios.raa.compats.savefiles;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.materials.DimensionMaterial;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.GsonUtils;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BeeProductionSaveFileCompat extends SaveFileCompat {
    public BeeProductionSaveFileCompat() {
        super("nectar", "beeproductive/materials");
    }

    @Override
    public void generate() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (Material material : Materials.MATERIALS) {
            JsonObject nectarObject = new JsonObject();
            nectarObject.addProperty("materialName", material.getId().toString());
            nectarObject.addProperty("nectarDrop", Rands.randIntRange(1, 9));
            jsonArray.add(nectarObject);
        }
        jsonObject.add("materials", jsonArray);
        JsonArray jsonArray2 = new JsonArray();
        for (DimensionMaterial material : Materials.DIMENSION_MATERIALS) {
            JsonObject nectarObject = new JsonObject();
            nectarObject.addProperty("materialName", material.getId().toString());
            nectarObject.addProperty("nectarDrop", Rands.randIntRange(1, 9));
            jsonArray2.add(nectarObject);
        }
        jsonObject.add("dimensionMaterials", jsonArray2);

        try {
            new File(getFile().getParent()).mkdirs();
            FileWriter fileWriter = new FileWriter(getFile());
            GsonUtils.getGson().toJson(jsonObject, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Material, Integer> load() {
        Map<Material, Integer> materialIntegerMap = new HashMap<>();
        try {
            JsonObject json = GsonUtils.getGson().fromJson(new FileReader(getFile()), JsonObject.class);

            JsonArray materials = JsonHelper.getArray(json, "materials");
            for (JsonElement jsonElement : materials) {
                JsonObject jsonObject = (JsonObject) jsonElement;
                Material material = Materials.MATERIALS.get(new Identifier(JsonHelper.getString(jsonObject, "materialName")));
                int nectar = JsonHelper.getInt(jsonObject, "nectarDrop");
                materialIntegerMap.put(material, nectar);
            }

//            Crashing because material is null
            JsonArray dimensionMaterials = JsonHelper.getArray(json, "dimensionMaterials");
            for (JsonElement jsonElement : dimensionMaterials) {
                JsonObject jsonObject = (JsonObject) jsonElement;
                Material material = Materials.DIMENSION_MATERIALS.get(new Identifier(JsonHelper.getString(jsonObject, "materialName")));
                int nectar = JsonHelper.getInt(jsonObject, "nectarDrop");
                materialIntegerMap.put(material, nectar);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return materialIntegerMap;
    }
}
