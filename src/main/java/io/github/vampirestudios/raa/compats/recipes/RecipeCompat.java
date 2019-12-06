package io.github.vampirestudios.raa.compats.recipes;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.ArtificeResource;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.IdUtils;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public abstract class RecipeCompat {
    private ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder;

    protected RecipeCompat() {}

    public ArtificeResourcePack.ServerResourcePackBuilder getDataPackBuilder() {
        return dataPackBuilder;
    }

    public abstract void registerRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder);

    protected <T extends TypedJsonBuilder<? extends JsonResource>> void addRecipes(Identifier id, Processor<T> f, Supplier<T> ctor) {
        this.dataPackBuilder.add(IdUtils.wrapPath("recipes/", id, ".json"), (ArtificeResource)((TypedJsonBuilder)f.process(ctor.get())).build());
    }

    public void setDataPackBuilder(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        this.dataPackBuilder = dataPackBuilder;
    }
}
