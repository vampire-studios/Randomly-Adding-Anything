package fr.arthurbambou.randomlyaddinganything;

import com.swordglowsblue.artifice.api.Artifice;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.client.ItemResourceModel;
import fr.arthurbambou.randomlyaddinganything.client.NuggetResourceModel;
import fr.arthurbambou.randomlyaddinganything.client.OreBakedModel;
import fr.arthurbambou.randomlyaddinganything.client.StorageBlockBakedModel;
import fr.arthurbambou.randomlyaddinganything.items.RAABlockItem;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.impl.client.render.ColorProviderRegistryImpl;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Function;

public class RandomlyAddingAnythingClient implements ClientModInitializer {

    private static final Map<Identifier, Map.Entry<Material, RAABlockItem.BlockType>> BLOCKS_IDENTIFIERS = new HashMap<>();
    private static final Map<Identifier, Material> ITEM_IDENTIFIERS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        while (!Materials.isIsReady()) {
            System.out.println("Not Ready");
        }
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX)
                .register((spriteAtlasTexture, registry) -> {
                    registry.register(new Identifier("block/stone"));
                    registry.register(new Identifier("block/dirt"));
                    registry.register(new Identifier("block/sand"));
                    registry.register(new Identifier("block/red_sand"));
                    registry.register(new Identifier("block/sandstone"));
                    registry.register(new Identifier("block/red_sandstone"));
                    for (Material material : Materials.MATERIAL_LIST) {
                        registry.register(material.getOreInformation().getOverlayTexture());
                        registry.register(material.getStorageBlockTexture());
                    }
                });
        Artifice.registerAssets(new Identifier(RandomlyAddingAnything.MOD_ID, "pack"), clientResourcePackBuilder -> {
            for (Material material : Materials.MATERIAL_LIST) {
                for (RAABlockItem.BlockType blockType : RAABlockItem.BlockType.values()) {
                    Identifier id = new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + blockType.getString());
                    clientResourcePackBuilder.addBlockState(id, blockStateBuilder -> blockStateBuilder.variant("", variant -> {
                        variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                    }));
                    clientResourcePackBuilder.addBlockModel(id, modelBuilder -> {
                        modelBuilder.parent(new Identifier("block/cube_all"));
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
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_gem"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_gem1"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                    ITEM_IDENTIFIERS.put(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_gem"), material);
                }
                if (material.getOreInformation().getOreType() == OreTypes.METAL) {
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot1"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot1"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
//                        modelBuilder.texture("layer0", new Identifier(id.getNamespace(), "item/" + id.getPath() + "_ingot"));
                        modelBuilder.texture("layer0", new Identifier(material.getResourceItemTexture().getNamespace(), material.getResourceItemTexture().getPath().toLowerCase()));
                    });
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_nugget"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getNuggetTexture());
                    });
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_nugget1"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getNuggetTexture());
                    });
                    ITEM_IDENTIFIERS.put(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot"), material);
                    ITEM_IDENTIFIERS.put(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_nugget"), material);
                }
                if (material.getOreInformation().getOreType() == OreTypes.CRYSTAL) {
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_crystal"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_crystal1"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getResourceItemTexture());
                    });
                    ITEM_IDENTIFIERS.put(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_crystal"), material);
                }
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_helmet"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_helmet"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_helmet1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_helmet"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_chestplate"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_chestplate"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_chestplate1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_chestplate"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_leggings"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_leggings"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_leggings1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_leggings"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_boots"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_boots"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_boots1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier("item/iron_boots"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_axe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/axe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/axe_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_axe1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/axe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/axe_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_pickaxe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/pickaxe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/pickaxe_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_pickaxe1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/pickaxe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/pickaxe_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_sword"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/sword_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/sword_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_sword1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/sword_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/sword_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_hoe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/hoe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/hoe_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_hoe1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/hoe_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/hoe_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_shovel"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/shovel_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/shovel_stick"));
                });
                clientResourcePackBuilder.addItemModel(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_shovel1"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier("raa", "item/shovel_head"));
                    modelBuilder.texture("layer1", new Identifier("raa", "item/shovel_stick"));
                });
            }
        });

        ModelLoadingRegistry.INSTANCE.registerAppender((manager, out) -> {
            for (Material material : Materials.MATERIAL_LIST) {
                out.accept(new ModelIdentifier(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_gem1"),"inventory"));
                out.accept(new ModelIdentifier(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot1"),"inventory"));
                out.accept(new ModelIdentifier(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_nugget1"),"inventory"));
                out.accept(new ModelIdentifier(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_crystal1"),"inventory"));
            }
        });


        for (Material material : Materials.MATERIAL_LIST) {
            ColorProviderRegistryImpl.ITEM.register((stack, layer) -> {
                if (layer == 0) return material.getRGBColor();
                else return -1;
            },
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_helmet")),
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_chestplate")),
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_leggings")),
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_boots"))
            );
            ColorProviderRegistryImpl.ITEM.register((stack, layer) -> {
                        if (layer == 0) return material.getRGBColor();
                        else return -1;
                    },
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_axe")),
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_shovel")),
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_pickaxe")),
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_hoe")),
                    Registry.ITEM.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_sword"))
            );
        }

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            if(!modelIdentifier.getNamespace().equals(RandomlyAddingAnything.MOD_ID)){
                return null;
            }
            Identifier identifier = new Identifier(modelIdentifier.getNamespace(), modelIdentifier.getPath());
            if (!(BLOCKS_IDENTIFIERS.containsKey(identifier) || ITEM_IDENTIFIERS.containsKey(identifier))) return null;
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
                    if (ITEM_IDENTIFIERS.containsKey(identifier)) {
                        if (identifier.getPath().endsWith("nugget")) {
                            return new NuggetResourceModel(identifier, ITEM_IDENTIFIERS.get(identifier));
                        } else {
                            return new ItemResourceModel(identifier, ITEM_IDENTIFIERS.get(identifier));
                        }
                    }
                    if (BLOCKS_IDENTIFIERS.get(identifier).getValue() == RAABlockItem.BlockType.BLOCK) {
                        return new StorageBlockBakedModel(BLOCKS_IDENTIFIERS.get(identifier).getKey());
                    } else {
                        return new OreBakedModel(BLOCKS_IDENTIFIERS.get(identifier).getKey());
                    }
                }
            };

        });
    }
}
