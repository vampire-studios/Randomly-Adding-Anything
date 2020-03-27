package io.github.vampirestudios.raa.config;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.targets.OreTargetGenerator;
import io.github.vampirestudios.raa.utils.GsonUtils;

import java.io.FileWriter;

public class OreTargetConfig extends RAADataConfig {
    public OreTargetConfig(String fileName) {
        super(fileName);
    }

    @Override
    public void generate() {

    }

    @Override
    protected JsonObject upgrade(JsonObject json, int version) {
        return null;
    }

    @Override
    protected void load(JsonObject jsonObject) {
        OreTargetGenerator.load(jsonObject);
    }

    @Override
    protected void save(FileWriter fileWriter) {
        JsonObject main = new JsonObject();
        main.addProperty("configVersion", 2);
        OreTargetGenerator.save(main);
        GsonUtils.getGson().toJson(main, fileWriter);
    }
}
