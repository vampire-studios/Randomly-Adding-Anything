package io.github.vampirestudios.raa.compats.recipes;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.resource.ArtificeResource;
import net.minecraft.util.Identifier;

import java.io.InputStream;

public class TechRebornRecipes extends RecipeCompat {
    protected TechRebornRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        super(dataPackBuilder);
    }

    @Override
    public void registerRecipes() {
        ArtificeResourcePack.ServerResourcePackBuilder dataBuilder = this.getDataPackBuilder();
        dataBuilder.add(new Identifier("ddd"), new ArtificeResource() {
            @Override
            public Object getData() {
                return null;
            }

            @Override
            public String toOutputString() {
                return null;
            }

            @Override
            public InputStream toInputStream() {
                return null;
            }
        });
    }
}
