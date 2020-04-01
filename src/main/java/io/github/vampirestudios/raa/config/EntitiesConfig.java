package io.github.vampirestudios.raa.config;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.namegeneration.NameGenerator;
import io.github.vampirestudios.raa.entity.EntityData;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.GsonUtils;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.SimpleRegistry;

import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class EntitiesConfig extends RAADataConfig {
    public static SimpleRegistry<EntityData> ENTITIES = new SimpleRegistry<>();

    public EntitiesConfig(String fileName) {
        super(fileName);
    }

    @Override
    public void generate() {
        Set<Identifier> names = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            //generate names
            NameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator();
            Pair<String, Identifier> name = nameGenerator.generateUnique(names, MOD_ID);
            names.add(name.getRight());

            ENTITIES.add(name.getRight(), new EntityData(name.getRight()));
        }
    }

    @Override
    protected JsonObject upgrade(JsonObject json, int version) {
        return null;
    }

    @Override
    protected void load(JsonObject jsonObject) {
        EntityData[] entities = GsonUtils.getGson().fromJson(JsonHelper.getArray(jsonObject, "entities"), EntityData[].class);
        for (EntityData entity : entities) {
            ENTITIES.add(entity.id, entity);
        }
    }

    @Override
    protected void save(FileWriter fileWriter) {
        JsonObject main = new JsonObject();
        main.addProperty("configVersion", 2);

        main.add("entities", GsonUtils.getGson().toJsonTree(ENTITIES.stream().toArray(EntityData[]::new)));

        GsonUtils.getGson().toJson(main, fileWriter);
    }
}
