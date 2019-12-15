package io.github.vampirestudios.raa.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.RAARegistery;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.stream.Collectors;

public class OreFeatureTargetTypeAdapter extends TypeAdapter<OreFeatureConfig.Target> {

    @Override
    public void write(JsonWriter out, OreFeatureConfig.Target value) throws IOException {
        if (value == null)
            out.nullValue();
        else
            out.value(new Identifier(RandomlyAddingAnything.MOD_ID, value.getName()).toString());
    }

    @Override
    public OreFeatureConfig.Target read(JsonReader in) throws IOException {
        JsonToken jsonToken = in.peek();
        if (jsonToken == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            String s = in.nextString();
            for (OreFeatureConfig.Target value : RAARegistery.TARGET_REGISTRY.stream().collect(Collectors.toList()))
                if (new Identifier(RandomlyAddingAnything.MOD_ID, value.getName()).toString().equals(s))
                    return value;

            throw new NullPointerException("Invalid Target: " + s);
        }
    }

}