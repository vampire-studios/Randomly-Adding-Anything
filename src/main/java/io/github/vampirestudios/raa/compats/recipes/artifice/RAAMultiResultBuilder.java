package io.github.vampirestudios.raa.compats.recipes.artifice;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import net.minecraft.util.Identifier;

public class RAAMultiResultBuilder {
    private final JsonArray results = new JsonArray();

    RAAMultiResultBuilder() {
    }

    public RAAMultiResultBuilder item(Identifier id) {
        this.results.add((JsonElement)(new JsonObjectBuilder()).add("item", id.toString()).build());
        return this;
    }

    public RAAMultiResultBuilder item(Identifier id, int count) {
        this.results.add((JsonElement)(new JsonObjectBuilder()).add("item", id.toString()).add("count", count).build());
        return this;
    }

    public RAAMultiResultBuilder tag(Identifier id) {
        this.results.add((JsonElement)(new JsonObjectBuilder()).add("tag", id.toString()).build());
        return this;
    }

    public RAAMultiResultBuilder tag(Identifier id, int count) {
        this.results.add((JsonElement)(new JsonObjectBuilder()).add("tag", id.toString()).add("count", count).build());
        return this;
    }

    JsonArray build() {
        return this.results;
    }
}
