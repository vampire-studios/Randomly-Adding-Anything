package fr.arthurbambou.randomlyaddinganything;

import com.swordglowsblue.artifice.api.Artifice;
import fr.arthurbambou.randomlyaddinganything.blocks.RAABlockItem;
import fr.arthurbambou.randomlyaddinganything.client.OreBackedModel;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Function;

public class RandomlyAddingAnythingClient implements ClientModInitializer {

    private static final Map<Identifier, Map.Entry<Material, RAABlockItem.BlockType>> IDENTIFIERS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        while (!Materials.isIsReady()) {
            System.out.println("Not Ready");
        }
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX)
                .register((spriteAtlasTexture, registry) -> {
                    registry.register(new Identifier("block/stone"));
                    for (Material material : Materials.MATERIAL_LIST) {
                        registry.register(material.getOverlayTexture());
                        registry.register(material.getRessourceItemTexture());
                    }
                });
        Artifice.registerAssets(new Identifier("raa","pack"), clientResourcePackBuilder -> {
            for (Material material : Materials.MATERIAL_LIST) {
                for (RAABlockItem.BlockType blockType : RAABlockItem.BlockType.values()) {
                    Identifier id = new Identifier("raa", material.getName().toLowerCase() + blockType.getString());
                    clientResourcePackBuilder.addBlockState(id, blockStateBuilder -> {
                        blockStateBuilder.variant("", variant -> {
                            variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                        });
                    });
                    clientResourcePackBuilder.addBlockModel(id, modelBuilder -> {
                        modelBuilder.parent(new Identifier("block/cube_all"));
                        modelBuilder.texture("all", new Identifier(id.getNamespace(), "blocks/" + id.getPath()));
                    });
                    clientResourcePackBuilder.addItemModel(id, modelBuilder -> {
                        modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                    });
                    Map<Material, RAABlockItem.BlockType> map = new HashMap<>();
                    map.put(material, blockType);
                    IDENTIFIERS.put(id, (Map.Entry<Material, RAABlockItem.BlockType>) map.entrySet().toArray()[0]);
                }
            }
        });

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            if(!modelIdentifier.getNamespace().equals("raa")){
                return null;
            }
            Identifier identifier = new Identifier(modelIdentifier.getNamespace(), modelIdentifier.getPath());
            if (!(IDENTIFIERS.containsKey(identifier))) return null;
            System.out.println(modelIdentifier.toString());
            return new UnbakedModel() {
                @Override
                public Collection<Identifier> getModelDependencies() {
                    return Collections.emptyList();
                }

                @Override
                public Collection<Identifier> getTextureDependencies(Function<Identifier, UnbakedModel> var1, Set<String> var2) {
                    return Collections.emptyList();
                }

                @Override
                public BakedModel bake(ModelLoader var1, Function<Identifier, Sprite> var2, ModelBakeSettings var3) {
                    return new OreBackedModel(IDENTIFIERS.get(identifier).getKey());
                }
            };

        });
    }
}
