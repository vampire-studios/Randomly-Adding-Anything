package io.github.vampirestudios.raa;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.api.enums.TextureTypes;
import io.github.vampirestudios.raa.client.DimensionalOreBakedModel;
import io.github.vampirestudios.raa.client.OreBakedModel;
import io.github.vampirestudios.raa.client.entity.RandomEntityRenderer;
import io.github.vampirestudios.raa.generation.materials.DimensionMaterial;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.items.RAABlockItem;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.registries.Entities;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.ModelUtils;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.EntityType;
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
    private static final Map<Identifier, Map.Entry<DimensionMaterial, RAABlockItem.BlockType>> DIMENSION_MATERIAL_ORE_IDENTIFIERS = new HashMap<>();

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
                        MinecraftClient.getInstance().world.getBiomeAccess().getBiome(Objects.requireNonNull(MinecraftClient.getInstance().player).getBlockPos()).getFoliageColor(),
                Items.OAK_LEAVES, Items.SPRUCE_LEAVES, Items.BIRCH_LEAVES, Items.JUNGLE_LEAVES, Items.ACACIA_LEAVES, Items.DARK_OAK_LEAVES, Items.FERN, Items.LARGE_FERN, Items.GRASS, Items.TALL_GRASS, Items.VINE);

        ColorProviderRegistryImpl.BLOCK.register((blockState, blockRenderView, blockPos, i) ->
                MinecraftClient.getInstance().world.getBiomeAccess().getBiome(blockPos).getFoliageColor(), Blocks.VINE, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
//        initColoring();

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
                    Identifier id = Utils.addSuffixToPath(bid, blockType.getSuffix());
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
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_gem"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreType.METAL) {
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_ingot"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_nugget"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getNuggetTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreType.CRYSTAL) {
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_crystal"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                }
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_helmet"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getHelmetTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_chestplate"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getChestplateTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_leggings"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getLeggingsTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_boots"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getBootsTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_axe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getAxeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_shovel"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getShovelTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_pickaxe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getPickaxeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_sword"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getSwordTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_hoe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getHoeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_shears"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shears_base"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shears_metal"));
                });

                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_horse_armor"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/armor/horse_armor_base"));
                    modelBuilder.texture("layer1", Rands.list(TextureTypes.HORSE_ARMOR_SADDLE_TEXTURES));
                });

                if (material.hasFood()) {
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_fruit"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getFruitTexture());
                    });
                }
                RandomlyAddingAnything.MODCOMPAT.generateCompatModels(clientResourcePackBuilder);
            });
            Dimensions.DIMENSIONS.forEach(dimensionData -> {
                Identifier identifier = new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase());
                Identifier stoneId = Utils.addSuffixToPath(identifier, "_stone");
                clientResourcePackBuilder.addBlockState(stoneId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(stoneId.getNamespace(), "block/" + stoneId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(stoneId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getStoneTexture());
                });
                clientResourcePackBuilder.addItemModel(stoneId,
                        modelBuilder -> modelBuilder.parent(new Identifier(stoneId.getNamespace(), "block/" + stoneId.getPath())));

                Identifier stoneStairsId = Utils.addSuffixToPath(identifier, "_stone_stairs");
                ModelUtils.stairs(clientResourcePackBuilder, stoneStairsId, dimensionData.getTexturesInformation().getStoneTexture());

                Identifier stoneSlabId = Utils.addSuffixToPath(identifier, "_stone_slab");
                ModelUtils.slab(clientResourcePackBuilder, stoneSlabId, stoneId, dimensionData.getTexturesInformation().getStoneTexture());

                Identifier stoneWallId = Utils.addSuffixToPath(identifier, "_stone_wall");
                ModelUtils.wall(clientResourcePackBuilder, stoneWallId, dimensionData.getTexturesInformation().getStoneTexture());


                Identifier stoneBricksId = Utils.addSuffixToPath(identifier, "_stone_bricks");
                clientResourcePackBuilder.addBlockState(stoneBricksId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(stoneBricksId.getNamespace(), "block/" + stoneBricksId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(stoneBricksId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getStoneBricksTexture());
                });
                clientResourcePackBuilder.addItemModel(stoneBricksId,
                        modelBuilder -> modelBuilder.parent(new Identifier(stoneBricksId.getNamespace(), "block/" + stoneBricksId.getPath())));


                Identifier stoneBricksStairsId = Utils.addSuffixToPath(identifier, "_stone_brick_stairs");
                ModelUtils.stairs(clientResourcePackBuilder, stoneBricksStairsId, dimensionData.getTexturesInformation().getStoneBricksTexture());

                Identifier stoneBricksSlabId = Utils.addSuffixToPath(identifier, "_stone_brick_slab");
                ModelUtils.slab(clientResourcePackBuilder, stoneBricksSlabId, stoneBricksId, dimensionData.getTexturesInformation().getStoneBricksTexture());

                Identifier stoneBricksWallId = Utils.addSuffixToPath(identifier, "_stone_brick_wall");
                ModelUtils.wall(clientResourcePackBuilder, stoneBricksWallId, dimensionData.getTexturesInformation().getStoneBricksTexture());


                Identifier iceId = Utils.addSuffixToPath(identifier, "_ice");
                clientResourcePackBuilder.addBlockState(iceId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(iceId.getNamespace(), "block/" + iceId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(iceId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getIceTexture());
                });
                clientResourcePackBuilder.addItemModel(iceId,
                        modelBuilder -> modelBuilder.parent(new Identifier(iceId.getNamespace(), "block/" + iceId.getPath())));


                Identifier cobblestoneId = Utils.addSuffixToPath(identifier, "_cobblestone");
                clientResourcePackBuilder.addBlockState(cobblestoneId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(cobblestoneId.getNamespace(), "block/" + cobblestoneId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(cobblestoneId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getCobblestoneTexture());
                });
                clientResourcePackBuilder.addItemModel(cobblestoneId,
                        modelBuilder -> modelBuilder.parent(new Identifier(cobblestoneId.getNamespace(), "block/" + cobblestoneId.getPath())));


                Identifier cobblestoneStairsId = Utils.addSuffixToPath(identifier, "_cobblestone_stairs");
                ModelUtils.stairs(clientResourcePackBuilder, cobblestoneStairsId, dimensionData.getTexturesInformation().getCobblestoneTexture());

                Identifier cobblestoneSlabId = Utils.addSuffixToPath(identifier, "_cobblestone_slab");
                ModelUtils.slab(clientResourcePackBuilder, cobblestoneSlabId, cobblestoneId, dimensionData.getTexturesInformation().getCobblestoneTexture());

                Identifier cobblestoneWallId = Utils.addSuffixToPath(identifier, "_cobblestone_wall");
                ModelUtils.wall(clientResourcePackBuilder, cobblestoneWallId, dimensionData.getTexturesInformation().getCobblestoneTexture());


                Identifier chiseledId = Utils.addPrefixAndSuffixToPath(identifier, "chiseled_", "_stone_bricks");
                clientResourcePackBuilder.addBlockState(chiseledId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(chiseledId.getNamespace(), "block/" + chiseledId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(chiseledId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getChiseledTexture());
                });
                clientResourcePackBuilder.addItemModel(chiseledId,
                        modelBuilder -> modelBuilder.parent(new Identifier(chiseledId.getNamespace(), "block/" + chiseledId.getPath())));


                Identifier polishedId = Utils.addPrefixToPath(identifier, "polished_");
                clientResourcePackBuilder.addBlockState(polishedId, blockStateBuilder -> blockStateBuilder.variant("", variant ->
                        variant.model(new Identifier(polishedId.getNamespace(), "block/" + polishedId.getPath())))
                );
                clientResourcePackBuilder.addBlockModel(polishedId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("block/leaves"));
                    modelBuilder.texture("all", dimensionData.getTexturesInformation().getPolishedTexture());
                });
                clientResourcePackBuilder.addItemModel(polishedId,
                        modelBuilder -> modelBuilder.parent(new Identifier(polishedId.getNamespace(), "block/" + polishedId.getPath())));


                Identifier polishedStairsId = Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_stairs");
                ModelUtils.stairs(clientResourcePackBuilder, polishedStairsId, dimensionData.getTexturesInformation().getPolishedTexture());

                Identifier polishedSlabId = Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_slab");
                ModelUtils.slab(clientResourcePackBuilder, polishedSlabId, polishedId, dimensionData.getTexturesInformation().getPolishedTexture());

                Identifier polishedWallId = Utils.addPrefixAndSuffixToPath(identifier, "polished_", "_wall");
                ModelUtils.wall(clientResourcePackBuilder, polishedWallId, dimensionData.getTexturesInformation().getPolishedTexture());


                Identifier portalId = Utils.addSuffixToPath(identifier, "_portal");
                clientResourcePackBuilder.addBlockState(portalId, blockStateBuilder -> {
                    blockStateBuilder.variant("activated=true", variant ->
                            variant.model(new Identifier(stoneId.getNamespace(), "block/" + portalId.getPath() + "_activated")));
                    blockStateBuilder.variant("activated=false", variant ->
                            variant.model(new Identifier(stoneId.getNamespace(), "block/" + portalId.getPath())));
                } );
                clientResourcePackBuilder.addBlockModel(portalId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("raa:block/portal"));
                    modelBuilder.texture("0", dimensionData.getTexturesInformation().getStoneTexture());
                    modelBuilder.texture("2", new Identifier("raa:block/metal_top"));
                    modelBuilder.texture("3", new Identifier("raa:block/metal_side"));
                    modelBuilder.texture("particle", dimensionData.getTexturesInformation().getStoneTexture());
                });
                clientResourcePackBuilder.addBlockModel(Utils.addSuffixToPath(portalId, "_activated"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("raa:block/portal_activated"));
                    modelBuilder.texture("0", dimensionData.getTexturesInformation().getStoneTexture());
                    modelBuilder.texture("2", new Identifier("raa:block/metal_top_activated"));
                    modelBuilder.texture("3", new Identifier("raa:block/metal_side"));
                    modelBuilder.texture("4", new Identifier("raa:block/portal_top"));
                    modelBuilder.texture("5", new Identifier("raa:block/metal_side_activated_overlay"));
                    modelBuilder.texture("particle", dimensionData.getTexturesInformation().getStoneTexture());
                });
                clientResourcePackBuilder.addItemModel(portalId,
                        modelBuilder -> modelBuilder.parent(new Identifier(portalId.getNamespace(), "block/" + portalId.getPath())));

                ColorProviderRegistry.ITEM.register((stack, layer) ->  {
                    if (layer == 0) return dimensionData.getCustomSkyInformation().hasSky() ? dimensionData.getDimensionColorPalette().getSkyColor() :
                            dimensionData.getDimensionColorPalette().getFogColor();
                    if (layer == 1) return dimensionData.getDimensionColorPalette().getStoneColor();
                    else return -1;
                }, Registry.ITEM.get(portalId));
                ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) ->  {
                    if (layer == 0) return dimensionData.getCustomSkyInformation().hasSky() ? dimensionData.getDimensionColorPalette().getSkyColor() :
                            dimensionData.getDimensionColorPalette().getFogColor();
                    if (layer == 1) return dimensionData.getDimensionColorPalette().getStoneColor();
                    else return -1;
                }, Registry.BLOCK.get(portalId));
                BlockRenderLayerMapImpl.INSTANCE.putBlock(Registry.BLOCK.get(portalId), RenderLayer.getCutout());


                Identifier pickaxeId = Utils.addSuffixToPath(identifier, "_pickaxe");
                Identifier axeId = Utils.addSuffixToPath(identifier, "_axe");
                Identifier shoveIdl = Utils.addSuffixToPath(identifier, "_shovel");
                Identifier hoeId = Utils.addSuffixToPath(identifier, "_hoe");
                Identifier swordId = Utils.addSuffixToPath(identifier, "_sword");
                Item pickaxe = Registry.ITEM.get(pickaxeId);
                Item axe = Registry.ITEM.get(axeId);
                Item shovel = Registry.ITEM.get(shoveIdl);
                Item hoe = Registry.ITEM.get(hoeId);
                Item sword = Registry.ITEM.get(swordId);

                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(identifier, "_axe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/axe/stone_axe_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/axe/axe_stick_1"));
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(identifier, "_shovel"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shovel/stone_shovel_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shovel/shovel_4_handle"));
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(identifier, "_pickaxe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/pickaxe/stone_pickaxe_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/pickaxe/pickaxe_1_handle"));
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(identifier, "_sword"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/sword/stone_sword_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/sword/sword_1_handle"));
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(identifier, "_hoe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/hoe/stone_hoe_head"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/hoe/hoe_stick_1"));
                });

                ColorProviderRegistry.ITEM.register((stack, layer) -> {
                    if (layer == 0) return dimensionData.getDimensionColorPalette().getStoneColor();
                    else return -1;
                }, pickaxe, axe, shovel, hoe, sword);


                Identifier portalKeyId = Utils.addSuffixToPath(identifier, "_portal_key");
                Item portalKey = Registry.ITEM.get(portalKeyId);
                clientResourcePackBuilder.addItemModel(portalKeyId, modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/portal_key"));
                });
                ColorProviderRegistry.ITEM.register((stack, layer) -> {
                    if (layer == 0) return dimensionData.getDimensionColorPalette().getSkyColor();
                    else return -1;
                }, portalKey);
            });
            Materials.DIMENSION_MATERIALS.forEach(material -> {
                Identifier bid = material.getId();
                for (RAABlockItem.BlockType blockType : RAABlockItem.BlockType.values()) {
                    Identifier id = Utils.addSuffixToPath(bid, blockType.getSuffix());
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
                    Map<DimensionMaterial, RAABlockItem.BlockType> map = new HashMap<>();
                    map.put(material, blockType);
                    DIMENSION_MATERIAL_ORE_IDENTIFIERS.put(id, (Map.Entry<DimensionMaterial, RAABlockItem.BlockType>) map.entrySet().toArray()[0]);
                }
                if (material.getOreInformation().getOreType() == OreType.GEM) {
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_gem"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreType.METAL) {
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_ingot"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_nugget"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getNuggetTexture());
                    });
                }
                if (material.getOreInformation().getOreType() == OreType.CRYSTAL) {
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_crystal"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getResourceItemTexture());
                    });
                }
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_helmet"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getHelmetTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_chestplate"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getChestplateTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_leggings"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getLeggingsTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_boots"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", material.getTexturesInformation().getBootsTexture());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_axe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getAxeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_shovel"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getShovelTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_pickaxe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getPickaxeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_sword"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getSwordTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_hoe"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/handheld"));
                    Pair<Identifier, Identifier> entry = material.getTexturesInformation().getHoeTexture();
                    if (entry.getLeft() == null || entry.getRight() == null) return;
                    modelBuilder.texture("layer0", entry.getLeft());
                    modelBuilder.texture("layer1", entry.getRight());
                });
                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_shears"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer1", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shears_base"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/tools/shears_metal"));
                });

                clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_horse_armor"), modelBuilder -> {
                    modelBuilder.parent(new Identifier("item/generated"));
                    modelBuilder.texture("layer0", new Identifier(RandomlyAddingAnything.MOD_ID, "item/armor/horse_armor_base"));
                    modelBuilder.texture("layer1", Rands.list(TextureTypes.HORSE_ARMOR_SADDLE_TEXTURES));
                });

                if (material.hasFood()) {
                    clientResourcePackBuilder.addItemModel(Utils.addSuffixToPath(bid, "_fruit"), modelBuilder -> {
                        modelBuilder.parent(new Identifier("item/generated"));
                        modelBuilder.texture("layer0", material.getTexturesInformation().getFruitTexture());
                    });
                }
            });
        });

        Materials.MATERIALS.forEach(material -> {
            Identifier id = material.getId();
            ColorProviderRegistry.ITEM.register((stack, layer) -> {
                        if (layer == 0) return material.getColor();
                        else return -1;
                    },
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_helmet")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_chestplate")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_leggings")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_boots")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_axe")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_shovel")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_pickaxe")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_hoe")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_sword")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_horse_armor")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_fruit")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_nugget")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_gem")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_crystal")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_ingot")),
                    Registry.BLOCK.get(Utils.addSuffixToPath(id, "_block")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_shears"))
            );
            ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) -> material.getColor(),
                    Registry.BLOCK.get(Utils.addSuffixToPath(id, "_block")));
        });
        Dimensions.DIMENSIONS.forEach(dimensionData -> {
            Block stone = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone"));
            Block stoneStairs = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone_stairs"));
            Block stoneSlab = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone_slab"));
            Block stoneWall = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone_wall"));
            Block stoneBricks = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone_bricks"));
            Block stoneBrickStairs = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone_brick_stairs"));
            Block stoneBrickSlab = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone_brick_slab"));
            Block stoneBrickWall = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone_brick_wall"));
            Block cobblestone = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_cobblestone"));
            Block cobblestoneStairs = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_cobblestone_stairs"));
            Block cobblestoneSlab = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_cobblestone_slab"));
            Block cobblestoneWall = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_cobblestone_wall"));
            Block chiseled = Registry.BLOCK.get(Utils.addPrefixAndSuffixToPath(dimensionData.getId(), "chiseled_", "_stone_bricks"));
            Block polished = Registry.BLOCK.get(new Identifier(dimensionData.getId().getNamespace(), "polished_" + dimensionData.getId().getPath()));
            Block polishedStairs = Registry.BLOCK.get(new Identifier(dimensionData.getId().getNamespace(), "polished_" + dimensionData.getId().getPath() + "_stairs"));
            Block polishedSlab = Registry.BLOCK.get(new Identifier(dimensionData.getId().getNamespace(), "polished_" + dimensionData.getId().getPath() + "_slab"));
            Block polishedWall = Registry.BLOCK.get(new Identifier(dimensionData.getId().getNamespace(), "polished_" + dimensionData.getId().getPath() + "_wall"));

            Block ice = Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_ice"));

            ColorProviderRegistry.ITEM.register((stack, layer) -> {
                if (layer == 0) return dimensionData.getDimensionColorPalette().getStoneColor();
                else return -1;
            }, stone, stoneSlab, stoneStairs, stoneWall, stoneBricks, stoneBrickSlab, stoneBrickStairs, stoneBrickWall, cobblestone,
                    cobblestoneSlab, cobblestoneStairs, cobblestoneWall, chiseled, polished, polishedSlab, polishedStairs, polishedWall);
            ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) ->
                    dimensionData.getDimensionColorPalette().getStoneColor(),
                    stone, stoneSlab, stoneStairs, stoneWall, stoneBricks, stoneBrickSlab, stoneBrickStairs, stoneBrickWall, cobblestone,
                cobblestoneSlab, cobblestoneStairs, cobblestoneWall, chiseled, polished, polishedSlab, polishedStairs, polishedWall);

            ColorProviderRegistry.ITEM.register((stack, layer) -> {
                if (layer == 0) return dimensionData.getDimensionColorPalette().getSkyColor();
                else return -1;
            }, ice);
            ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) ->
                    dimensionData.getDimensionColorPalette().getSkyColor(), ice);
        });
        Materials.DIMENSION_MATERIALS.forEach(material -> {
            Identifier id = material.getId();
            ColorProviderRegistry.ITEM.register((stack, layer) -> {
                        if (layer == 0) return material.getColor();
                        else return -1;
                    },
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_helmet")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_chestplate")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_leggings")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_boots")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_axe")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_shovel")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_pickaxe")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_hoe")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_sword")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_horse_armor")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_fruit")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_nugget")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_gem")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_crystal")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_ingot")),
                    Registry.BLOCK.get(Utils.addSuffixToPath(id, "_block")),
                    Registry.ITEM.get(Utils.addSuffixToPath(id, "_shears"))
            );
            ColorProviderRegistry.BLOCK.register((blockstate, blockview, blockpos, layer) -> material.getColor(),
                    Registry.BLOCK.get(Utils.addSuffixToPath(id, "_block")));
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

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            if (!modelIdentifier.getNamespace().equals(RandomlyAddingAnything.MOD_ID)) {
                return null;
            }
            Identifier identifier = new Identifier(modelIdentifier.getNamespace(), modelIdentifier.getPath());
            if (!DIMENSION_MATERIAL_ORE_IDENTIFIERS.containsKey(identifier)) return null;
            if (DIMENSION_MATERIAL_ORE_IDENTIFIERS.get(identifier).getValue() == RAABlockItem.BlockType.BLOCK)
                return null;
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
                    return new DimensionalOreBakedModel(DIMENSION_MATERIAL_ORE_IDENTIFIERS.get(identifier).getKey());
                }
            };
        });

        for (EntityType<?> entity : Entities.RANDOM_ZOMBIES) {
            EntityRendererRegistry.INSTANCE.register(entity, (dispatcher, context) -> new RandomEntityRenderer(dispatcher));
        }
    }

}
