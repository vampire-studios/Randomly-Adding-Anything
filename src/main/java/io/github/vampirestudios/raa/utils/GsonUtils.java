package io.github.vampirestudios.raa.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.github.vampirestudios.raa.api.RAARegistery;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.IOException;
import java.util.stream.Collectors;

public class GsonUtils {
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .registerTypeAdapter(Identifier.class, new TypeAdapter<Identifier>() {
                    @Override
                    public void write(JsonWriter out, Identifier value) throws IOException {
                        if (value == null)
                            out.nullValue();
                        else
                            out.value(value.toString());
                    }

                    @Override
                    public Identifier read(JsonReader in) throws IOException {
                        JsonToken jsonToken = in.peek();
                        if (jsonToken == JsonToken.NULL) {
                            in.nextNull();
                            return null;
                        } else {
                            return new Identifier(in.nextString());
                        }
                    }
                })
                .registerTypeAdapter(OreFeatureConfig.Target.class, new TypeAdapter<OreFeatureConfig.Target>() {
                    @Override
                    public void write(JsonWriter out, OreFeatureConfig.Target value) throws IOException {
                        if (value == null)
                            out.nullValue();
                        else
                            out.value(value.getName());
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
                                if (value.getName().equals(s))
                                    return value;

                            throw new NullPointerException("Invalid Target: " + s);
                        }
                    }
                })
                .serializeNulls()
                .setPrettyPrinting()
                .create();
    }

    public static Gson getGson() {
        return GSON;
    }

    public static Identifier idFromOldStyle(JsonObject jsonObject) {
        if (jsonObject.has("namespace"))
            return new Identifier(JsonHelper.getString(jsonObject, "namespace"), JsonHelper.getString(jsonObject, "path"));
        return new Identifier(JsonHelper.getString(jsonObject, "field_13353"), JsonHelper.getString(jsonObject, "field_13355"));
    }
}
