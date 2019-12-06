package io.github.vampirestudios.raa;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.api.enums.TextureTypes;
import io.github.vampirestudios.raa.client.OreBakedModel;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.items.RAABlockItem;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Function;

public class RandomlyAddingAnythingClient implements ClientModInitializer {

    private static final Map<Identifier, Map.Entry<Material, RAABlockItem.BlockType>> MATERIAL_ORE_IDENTIFIERS = new HashMap<>();

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        initColoring();

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX)
                .register((spriteAtlasTexture, registry) -> {
                    for (Material material : Materials.MATERIALS) {
                        registry.register(material.getTexturesInformation().getOverlayTexture());
                        registry.register(material.getTexturesInformation().getStorageBlockTexture());
                    }
                    for (Material material : Materials.DIMENSION_MATERIALS) {
                        registry.register(material.getTexturesInformation().getOverlayTexture());
                        registry.register(material.getTexturesInformation().getStorageBlockTexture());
                    }
                });
        Artifice.registerAssets(new Identifier(RandomlyAddingAnything.MOD_ID, "pack"), clientResourcePackBuilder -> {
            Materials.MATERIALS.forEach(material -> {
                Identifier bid = material.getId();
                for (RAABlockItem.BlockType blockType : RAABlockItem.BlockType.values()) {
                    Identifier id = Utils.appendToPath(bid, blockType.getSuffix());
                    clientResourcePackBuilder.addBlockState(id, blockStateBuilder -> blockStateBuilder.variant("", variant -> {
                        variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                    }));
                    clientResourcePackBuilder.addBlockModel(id, modelBuilder -> {
                        if (blockType == RAABlockItem.BlockType.BLOCK) {
                            modelBuilder.parent(new Identifier("block/leaves"));
                        } else {
                            modelBuilder.parent(new Identifier("block/cube_all"));
                        }
                        modelBuilder.texture("all", material.getTexturesInformation().getStorageBlockTexture());
                    });
                    clientResourcePackBuilder.addItemModel(id, modelBuilder ->
                            modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
                    Map<Material, RAABlockItem.BlockType> map = new HashMap<>();
                    map.put(material, blockType);
                    MATERIAL_ORE_IDENTIFIERS.put(id, (Map.Entry<Material, RAABlockItem.BlockType>) map.entrySet().toArray()[0]);
                }
                if (material.getOreInformation().getOreType() == OreType.GEM) {
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_gem"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreType.METAL) {
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_ingot"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_nugget"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getNuggetTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreType.CRYSTAL) {
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_crystal"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                }
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_helmet"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getHelmetTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_chestplate"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getChestplateTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_leggings"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getLeggingsTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_boots"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getBootsTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_axe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getAxeTexture();
                    System.out.println(entry);
                    System.out.println(entry.getLeft());
                    System.out.println(entry.getRight());
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_shovel"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getShovelTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_pickaxe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getPickaxeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_sword"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getSwordTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_hoe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getHoeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_shears"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shears_base"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shears_metal"));
                });

                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_horse_armor"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/armor/horse_armor_base"));
                    modelBuilder.texture("layer1", Rands.list(TextureTypes.HORSE_ARMOR_SADDLE_TEXTURES));
                });

                if (material.hasFood()) {
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_fruit"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getFruitTexture());
                    });
                }
            });
            Dimensions.DIMENSIONS.forEach(dimensionData -> {
                Identifier identifier = new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase());
                Identifier stoneId = Utils.appendToPath(identifier, "_stone");
                clientResourcePackBuilder.addBlockState(stoneId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(stoneId.getNamespace(), "block/" + stoneId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(stoneId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getStoneTexture());
                });
                clientResourcePackBuilder.addItemModel(stoneId,
                        modelBuilder -> modelBuilder.parent(new Identifier(stoneId.getNamespace(), "block/" + stoneId.getPath())));


                Identifier stoneBricksId = Utils.appendToPath(identifier, "_stone_bricks");
                clientResourcePackBuilder.addBlockState(stoneBricksId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(stoneBricksId.getNamespace(), "block/" + stoneBricksId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(stoneBricksId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getStoneBricksTexture());
                });
                clientResourcePackBuilder.addItemModel(stoneBricksId,
                        modelBuilder -> modelBuilder.parent(new Identifier(stoneBricksId.getNamespace(), "block/" + stoneBricksId.getPath())));


                Identifier iceId = Utils.appendToPath(identifier, "_ice");
                clientResourcePackBuilder.addBlockState(iceId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(iceId.getNamespace(), "block/" + iceId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(iceId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getIceTexture());
                });
                clientResourcePackBuilder.addItemModel(iceId,
                        modelBuilder -> modelBuilder.parent(new Identifier(iceId.getNamespace(), "block/" + iceId.getPath())));


                Identifier cobblestoneId = Utils.appendToPath(identifier, "_cobblestone");
                clientResourcePackBuilder.addBlockState(cobblestoneId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(cobblestoneId.getNamespace(), "block/" + cobblestoneId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(cobblestoneId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getCobblestoneTexture());
                });
                clientResourcePackBuilder.addItemModel(cobblestoneId,
                        modelBuilder -> modelBuilder.parent(new Identifier(cobblestoneId.getNamespace(), "block/" + cobblestoneId.getPath())));


                Identifier chiseledId = Utils.prependToPath(identifier, "chiseled_");
                clientResourcePackBuilder.addBlockState(chiseledId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(chiseledId.getNamespace(), "block/" + chiseledId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(chiseledId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getChiseledTexture());
                });
                clientResourcePackBuilder.addItemModel(chiseledId,
                        modelBuilder -> modelBuilder.parent(new Identifier(chiseledId.getNamespace(), "block/" + chiseledId.getPath())));


                Identifier polishedId = Utils.prependToPath(identifier, "polished_");
                clientResourcePackBuilder.addBlockState(polishedId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(polishedId.getNamespace(), "block/" + polishedId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(polishedId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getPolishedTexture());
                });
                clientResourcePackBuilder.addItemModel(polishedId,
                        modelBuilder -> modelBuilder.parent(new Identifier(polishedId.getNamespace(), "block/" + polishedId.getPath())));

                Identifier portalId = Utils.appendToPath(identifier, "_portal");
                clientResourcePackBuilder.addBlockState(portalId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(stoneId.getNamespace(), "block/" + portalId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(portalId, modelBuilder -> modelBuilder.parent(new Identifier("raa:block/portal")));
                clientResourcePackBuilder.addItemModel(portalId,
                        modelBuilder -> modelBuilder.parent(new Identifier(portalId.getNamespace(), "block/" + portalId.getPath())));

                ColorProviderRegistry.ITEM.register((stack, layer) -> dimensionData.getDimensionColorPalette().getFogColor(), Registry.ITEM.get(portalId));
                ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) ->
                        dimensionData.getDimensionColorPalette().getFogColor(), Registry.BLOCK.get(portalId));


                Identifier pickaxeId = Utils.appendToPath(identifier, "_pickaxe");
                Identifier axeId = Utils.appendToPath(identifier, "_axe");
                Identifier shoveIdl = Utils.appendToPath(identifier, "_shovel");
                Identifier hoeId = Utils.appendToPath(identifier, "_hoe");
                Identifier swordId = Utils.appendToPath(identifier, "_sword");
                Item pickaxe = Registry.ITEM.get(pickaxeId);
                Item axe = Registry.ITEM.get(axeId);
                Item shovel = Registry.ITEM.get(shoveIdl);
                Item hoe = Registry.ITEM.get(hoeId);
                Item sword = Registry.ITEM.get(swordId);

                clientResourcePackBuilder.addItemModel(Utils.appendToPath(identifier, "_axe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/axe/stone_axe_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/axe/axe_stick"));
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(identifier, "_shovel"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shovel/stone_shovel_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shovel/shovel_stick"));
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(identifier, "_pickaxe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/pickaxe/stone_pickaxe_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/pickaxe/pickaxe_stick"));
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(identifier, "_sword"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/sword/stone_sword_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/sword/sword_stick"));
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(identifier, "_hoe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/hoe/stone_hoe_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/hoe/hoe_stick"));
                });

                ColorProviderRegistry.ITEM.register((stack, layer) -> {
                    if (layer == 0) return dimensionData.getDimensionColorPalette().getStoneColor();
                    else return -1;
                }, pickaxe, axe, shovel, hoe, sword);
            });
            Materials.DIMENSION_MATERIALS.forEach(material -> {
                Identifier bid = material.getId();
                for (RAABlockItem.BlockType blockType : RAABlockItem.BlockType.values()) {
                    Identifier id = Utils.appendToPath(bid, blockType.getSuffix());
                    clientResourcePackBuilder.addBlockState(id, blockStateBuilder -> blockStateBuilder.variant("", variant -> {
                        variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                    }));
                    clientResourcePackBuilder.addBlockModel(id, modelBuilder -> {
                        if (blockType == RAABlockItem.BlockType.BLOCK) {
                            modelBuilder.parent(new Identifier("block/leaves"));
                        } else {
                            modelBuilder.parent(new Identifier("block/cube_all"));
                        }
                        modelBuilder.texture("all", material.getTexturesInformation().getStorageBlockTexture());
                    });
                    clientResourcePackBuilder.addItemModel(id, modelBuilder ->
                            modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
                    Map<Material, RAABlockItem.BlockType> map = new HashMap<>();
                    map.put(material, blockType);
                    MATERIAL_ORE_IDENTIFIERS.put(id, (Map.Entry<Material, RAABlockItem.BlockType>) map.entrySet().toArray()[0]);
                }
                if (material.getOreInformation().getOreType() == OreType.GEM) {
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_gem"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreType.METAL) {
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_ingot"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_nugget"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getNuggetTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreType.CRYSTAL) {
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_crystal"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                }
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_helmet"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getHelmetTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_chestplate"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getChestplateTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_leggings"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getLeggingsTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_boots"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getBootsTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_axe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getAxeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_shovel"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getShovelTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_pickaxe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getPickaxeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_sword"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getSwordTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_hoe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getHoeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_shears"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shears_base"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shears_metal"));
                });

                clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_horse_armor"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/armor/horse_armor_base"));
                    modelBuilder.texture("layer1", Rands.list(TextureTypes.HORSE_ARMOR_SADDLE_TEXTURES));
                });

                if (material.hasFood()) {
                    clientResourcePackBuilder.addItemModel(Utils.appendToPath(bid, "_fruit"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getFruitTexture());
                    });
                }
            });
        });

        Materials.MATERIALS.forEach(material -> {
            Identifier id = material.getId();
            ColorProviderRegistry.ITEM.register((stack, layer) -> {
                        if (layer == 0) return material.getRGBColor();
                        else return -1;
                    },
                    Registry.ITEM.get(Utils.appendToPath(id, "_helmet")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_chestplate")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_leggings")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_boots")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_axe")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_shovel")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_pickaxe")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_hoe")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_sword")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_horse_armor")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_fruit")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_nugget")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_gem")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_crystal")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_ingot")),
                    Registry.BLOCK.get(Utils.appendToPath(id, "_block")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_shears"))
            );
            ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) -> material.getRGBColor(),
                    Registry.BLOCK.get(Utils.appendToPath(id, "_block")));
        });
        Dimensions.DIMENSIONS.forEach(dimensionData -> {
            Block stone = Registry.BLOCK.get(Utils.appendToPath(dimensionData.getId(), "_stone"));
            Block stoneBricks = Registry.BLOCK.get(Utils.appendToPath(dimensionData.getId(), "_stone_bricks"));
            Block cobblestone = Registry.BLOCK.get(Utils.appendToPath(dimensionData.getId(), "_cobblestone"));
            Block chiseled = Registry.BLOCK.get(new Identifier(dimensionData.getId().getNamespace(), "chiseled_" + dimensionData.getId().getPath()));
            Block polished = Registry.BLOCK.get(new Identifier(dimensionData.getId().getNamespace(), "polished_" + dimensionData.getId().getPath()));

            Block ice = Registry.BLOCK.get(Utils.appendToPath(dimensionData.getId(), "_ice"));

            ColorProviderRegistry.ITEM.register((stack, layer) -> {
                if (layer == 0) return dimensionData.getDimensionColorPalette().getStoneColor();
                else return -1;
            }, stone, stoneBricks, cobblestone, chiseled, polished);
            ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) ->
                    dimensionData.getDimensionColorPalette().getStoneColor(), stone, stoneBricks, cobblestone, chiseled, polished);

            ColorProviderRegistry.ITEM.register((stack, layer) -> {
                if (layer == 0) return dimensionData.getBiomeData().get(0).getWaterColor();
                else return -1;
            }, ice);
            ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) ->
                    dimensionData.getBiomeData().get(0).getWaterColor(), ice);
        });
        Materials.DIMENSION_MATERIALS.forEach(material -> {
            Identifier id = material.getId();
            ColorProviderRegistry.ITEM.register((stack, layer) -> {
                        if (layer == 0) return material.getRGBColor();
                        else return -1;
                    },
                    Registry.ITEM.get(Utils.appendToPath(id, "_helmet")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_chestplate")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_leggings")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_boots")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_axe")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_shovel")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_pickaxe")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_hoe")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_sword")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_horse_armor")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_fruit")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_nugget")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_gem")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_crystal")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_ingot")),
                    Registry.BLOCK.get(Utils.appendToPath(id, "_block")),
                    Registry.ITEM.get(Utils.appendToPath(id, "_shears"))
            );
            ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) ->
                    layer == 0 ? material.getDimensionData().getDimensionColorPalette().getStoneColor() : material.getRGBColor(),
                    Registry.BLOCK.get(Utils.appendToPath(id, "_block")));
        });

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            if (!modelIdentifier.getNamespace().equals(RandomlyAddingAnything.MOD_ID)) {
                return null;
            }
            Identifier identifier = new Identifier(modelIdentifier.getNamespace(), modelIdentifier.getPath());
            if (!MATERIAL_ORE_IDENTIFIERS.containsKey(identifier)) return null;
            if (MATERIAL_ORE_IDENTIFIERS.get(identifier).getValue() == RAABlockItem.BlockType.BLOCK) return null;
            return new UnbakedModel() {
                @Override
                public Collection<Identifier> getModelDependencies() {
                    return Collections.emptyList();
                }

                @Override
                public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<com.mojang.datafixers.util.Pair<String, String>> unresolvedTextureReferences) {
                    return Collections.emptyList();
                }
                @Override
                public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
                    return new OreBakedModel(MATERIAL_ORE_IDENTIFIERS.get(identifier).getKey());
                }
            };
        });
    }

    public static void initColoring() {
        ColorProviderRegistry.ITEM.register((stack, layer) -> {
            if (MinecraftClient.getInstance().world != null) {
                return MinecraftClient.getInstance().world.getBiomeAccess().getBiome(MinecraftClient.getInstance().player.getBlockPos())
                        .getGrassColorAt(MinecraftClient.getInstance().player.getBlockPos().getX(), MinecraftClient.getInstance().player.getBlockPos().getZ());
            } else {
                BlockState blockState_1 = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
                return MinecraftClient.getInstance().getBlockColorMap().getColor(blockState_1, MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getBlockPos());
            }
        }, Items.GRASS_BLOCK);

        ColorProviderRegistry.ITEM.register((stack, var2) ->
                        MinecraftClient.getInstance().world.getBiomeAccess().getBiome(Objects.requireNonNull(MinecraftClient.getInstance().player).getBlockPos()).getFoliageColorAt(),
                Items.OAK_LEAVES, Items.SPRUCE_LEAVES, Items.BIRCH_LEAVES, Items.JUNGLE_LEAVES, Items.ACACIA_LEAVES, Items.DARK_OAK_LEAVES, Items.FERN, Items.LARGE_FERN, Items.GRASS, Items.TALL_GRASS, Items.VINE);

        ColorProviderRegistryImpl.BLOCK.register((blockState, blockRenderView, blockPos, i) ->
                MinecraftClient.getInstance().world.getBiomeAccess().getBiome(blockPos).getFoliageColorAt(), Blocks.VINE, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES);
    }

}
