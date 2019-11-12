package io.github.vampirestudios.raa;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.client.OreBakedModel;
import io.github.vampirestudios.raa.generation.entity.RandomEntityRenderer;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.items.RAABlockItem;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.registries.Entities;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Rands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.impl.client.render.ColorProviderRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Function;

public class RandomlyAddingAnythingClient implements ClientModInitializer {

    private static final Map<Identifier, Map.Entry<Material, RAABlockItem.BlockType>> BLOCKS_IDENTIFIERS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((var1, var2) -> MinecraftClient.getInstance().world.getBiome(MinecraftClient.getInstance().player.getBlockPos())
                .getGrassColorAt(MinecraftClient.getInstance().player.getBlockPos()), Items.GRASS_BLOCK);

        ColorProviderRegistry.ITEM.register((var1, var2) -> MinecraftClient.getInstance().world.getBiome(MinecraftClient.getInstance().player.getBlockPos())
                .getFoliageColorAt(MinecraftClient.getInstance().player.getBlockPos()), Items.OAK_LEAVES, Items.SPRUCE_LEAVES, Items.BIRCH_LEAVES,
                Items.JUNGLE_LEAVES, Items.ACACIA_LEAVES, Items.DARK_OAK_LEAVES, Items.FERN, Items.LARGE_FERN, Items.GRASS,
                Items.TALL_GRASS);

        while (!Materials.isIsReady()) {
            System.out.println("Not Ready");
        }
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX)
                .register((spriteAtlasTexture, registry) -> {
                    for (Material material : Materials.MATERIALS) {
                        registry.register(material.getOreInformation().getOverlayTexture());
                        registry.register(material.getStorageBlockTexture());
                    }
                });
        Artifice.registerAssets(new Identifier(RandomlyAddingAnything.MOD_ID, "pack"), clientResourcePackBuilder -> {
            Materials.MATERIALS.forEach(material -> {
                String bid = material.getName().toLowerCase();
                for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialCharMap().entrySet()) {
                    bid = bid.replace(entry.getKey(), entry.getValue());
                }
                for (RAABlockItem.BlockType blockType : RAABlockItem.BlockType.values()) {
                    Identifier id = new Identifier(RandomlyAddingAnything.MOD_ID, bid + blockType.getString());
                    clientResourcePackBuilder.addBlockState(id, blockStateBuilder -> blockStateBuilder.variant("", variant -> {
                        variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                    }));
                    clientResourcePackBuilder.addBlockModel(id, modelBuilder -> {
                        if (blockType == RAABlockItem.BlockType.BLOCK) {
                            modelBuilder.parent(new Identifier("block/leaves"));
                        } else {
                            modelBuilder.parent(new Identifier("block/cube_all"));
                        }
                        modelBuilder.texture("all", material.getStorageBlockTexture());
                    });
                    clientResourcePackBuilder.addItemModel(id, modelBuilder -> {
                        modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                    });
                    Map<Material, RAABlockItem.BlockType> map = new HashMap<>();
                    map.put(material, blockType);
                    BLOCKS_IDENTIFIERS.put(id, (Map.Entry<Material, RAABlockItem.BlockType>) map.entrySet().toArray()[0]);
                }
                if (material.getOreInformation().getOreType() == OreTypes.GEM) {
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_gem"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreTypes.METAL) {
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_ingot"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_nugget"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getNuggetTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreTypes.CRYSTAL) {
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_crystal"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                }
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_helmet"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_helmet"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_chestplate"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_chestplate"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_leggings"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_leggings"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_boots"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_boots"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_axe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/axe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/axe_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_shovel"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/shovel_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/shovel_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_pickaxe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/pickaxe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/pickaxe_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_sword"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/sword_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/sword_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_hoe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/hoe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/hoe_stick"));
                });

                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_horse_armor"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/horse_armor_base"));
                    modelBuilder.texture("layer1", Rands.list(OreTypes.HORSE_ARMOR_SADDLE_TEXTURES));
                });

                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_apple"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/apple"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_carrot"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/carrot"));
                });
            });
            Entities.ENTITIES.forEach(randomEntityData ->
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, randomEntityData.id.getPath() + "_spawn_egg"),
                            modelBuilder -> modelBuilder.parent(new Identifier("item/template_spawn_egg"))));
        });


        Materials.MATERIALS.forEach(material -> {
            String id = material.getName().toLowerCase();
            for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialCharMap().entrySet()) {
                id = id.replace(entry.getKey(), entry.getValue());
            }
            ColorProviderRegistryImpl.ITEM.register((stack, layer) -> {
                if (layer == 0) return material.getRGBColor();
                    else return -1;
                },
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_helmet")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_chestplate")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_leggings")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_boots")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_axe")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_shovel")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_pickaxe")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_hoe")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_sword")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_horse_armor")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_apple")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_carrot")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_nugget")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_gem")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_crystal")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_ingot")),
                Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_block"))
            );
            ColorProviderRegistryImpl.BLOCK.register((blockstate, blockview, blockpos, layer) -> {
                        return material.getRGBColor();
                    },
                    Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_block")));
        });

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            if(!modelIdentifier.getNamespace().equals(RandomlyAddingAnything.MOD_ID)){
                return null;
            }
            Identifier identifier = new Identifier(modelIdentifier.getNamespace(), modelIdentifier.getPath());
            if (!BLOCKS_IDENTIFIERS.containsKey(identifier)) return null;
            if (BLOCKS_IDENTIFIERS.get(identifier).getValue() == RAABlockItem.BlockType.BLOCK) return null;
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
                    return new OreBakedModel(BLOCKS_IDENTIFIERS.get(identifier).getKey());
                }
            };
        });

        Entities.ENTITIES.forEach(randomEntityData -> EntityRendererRegistry.INSTANCE.register(Registry.ENTITY_TYPE.get(randomEntityData.id),
                (entityRenderDispatcher, context) -> new RandomEntityRenderer(entityRenderDispatcher)));
    }
}
