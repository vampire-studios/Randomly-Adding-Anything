package io.github.vampirestudios.raa.compats.recipes.artifice;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.builder.data.recipe.MultiIngredientBuilder;
import net.minecraft.util.Identifier;

public class RAAMultiIngredientBuilder {
    private final JsonArray ingredients = new JsonArray();

    RAAMultiIngredientBuilder() {
    }

    public RAAMultiIngredientBuilder item(Identifier id) {
        this.ingredients.add((JsonElement)(new JsonObjectBuilder()).add("item", id.toString()).build());
        return this;
    }

    public RAAMultiIngredientBuilder tag(Identifier id) {
        this.ingredients.add((JsonElement)(new JsonObjectBuilder()).add("tag", id.toString()).build());
        return this;
    }

    JsonArray build() {
        return this.ingredients;
    }
}
