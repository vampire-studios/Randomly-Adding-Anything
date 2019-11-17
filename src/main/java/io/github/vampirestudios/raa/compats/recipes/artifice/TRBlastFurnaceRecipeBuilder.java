package io.github.vampirestudios.raa.compats.recipes.artifice;

import com.swordglowsblue.artifice.api.builder.data.recipe.RecipeBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class TRBlastFurnaceRecipeBuilder extends RecipeBuilder<TRBlastFurnaceRecipeBuilder> {

    public TRBlastFurnaceRecipeBuilder() {}

    public TRBlastFurnaceRecipeBuilder multiIngredient(Processor<RAAMultiIngredientBuilder> settings) {
        this.root.add("ingredients", ((RAAMultiIngredientBuilder)settings.process(new RAAMultiIngredientBuilder())).build());
        return this;
    }

    public TRBlastFurnaceRecipeBuilder multiResult(Processor<RAAMultiResultBuilder> settings) {
        this.root.add("results", ((RAAMultiResultBuilder)settings.process(new RAAMultiResultBuilder())).build());
        return this;
    }

    public TRBlastFurnaceRecipeBuilder heat(int heat) {
        this.root.addProperty("heat", heat);
        return this;
    }

    public TRBlastFurnaceRecipeBuilder time(int time) {
        this.root.addProperty("time", time);
        return this;
    }

    public TRBlastFurnaceRecipeBuilder power(int power) {
        this.root.addProperty("power", power);
        return this;
    }
}
