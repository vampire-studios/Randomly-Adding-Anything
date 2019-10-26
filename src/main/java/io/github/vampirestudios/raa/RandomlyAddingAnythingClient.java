package io.github.vampirestudios.raa;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.client.OreBakedModel;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.items.RAABlockItem;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Rands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.impl.client.render.ColorProviderRegistryImpl;
import net.minecraft.block.Block;
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
        while (!Dimensions.isReady()) {
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
                    clientResourcePackBuilder.addItemModel(id, modelBuilder ->
                            modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
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
                    modelBuilder.texture("layer0", Rands.list(OreTypes.HELMET_TEXTURES));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_chestplate"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", Rands.list(OreTypes.CHESTPLATE_TEXTURES));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_leggings"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", Rands.list(OreTypes.LEGGINGS_TEXTURES));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_boots"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", Rands.list(OreTypes.BOOTS_TEXTURES));
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
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, bid + "_shears"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/shears_base"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/shears_metal"));
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
            Dimensions.DIMENSIONS.forEach(dimensionData -> {
                Identifier id = new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase() + "_stone");
                clientResourcePackBuilder.addBlockState(id, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(id, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", new Identifier(RandomlyAddingAnything.MOD_ID, "block/stone"));
                });
                clientResourcePackBuilder.addItemModel(id,
                        modelBuilder -> modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
            });
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
                Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_block")),
                Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_shears"))
            );
            ColorProviderRegistryImpl.BLOCK.register((blockstate, blockview, blockpos, layer) -> material.getRGBColor(),
                    Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_block")));
        });

        Dimensions.DIMENSIONS.forEach(dimensionData -> {
            String id = dimensionData.getName().toLowerCase();
            for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionCharMap().entrySet()) {
                id = id.replace(entry.getKey(), entry.getValue());
            }
            Block block = Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_stone"));

            System.out.println(Registry.BLOCK.getId(block).toString());

            ColorProviderRegistryImpl.ITEM.register((stack, layer) -> {
                if (layer == 0) return dimensionData.getStoneColor();
                    else return -1;
            }, block);
            ColorProviderRegistryImpl.BLOCK.register((blockstate, blockview, blockpos, layer) -> dimensionData.getStoneColor(), block);
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
                public BakedModel bake(ModelLoader modelLoader, Function<Identifier, Sprite> identifierSpriteFunction, ModelBakeSettings bakeSettings, Identifier identifier2) {
                    return new OreBakedModel(BLOCKS_IDENTIFIERS.get(identifier).getKey());
                }
            };
        });
    }
}
