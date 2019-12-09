package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.TextureTypes;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class Textures {

    public static void init() {
        itemTextures();
        blockTextures();
    }

    private static void itemTextures() {
        for (int i = 1; i < 4; i++) {
            addTextureToList(TextureTypes.INGOT_TEXTURES, "item/ingots/ingot_" + i);
        }
        for (int i = 1; i < 10; i++) {
            addTextureToList(TextureTypes.GEM_ITEM_TEXTURES, "item/gems/gem_" + i);
        }
        for (int i = 1; i < 8; i++) {
            addTextureToList(TextureTypes.CRYSTAL_ITEM_TEXTURES, "item/crystals/crystal_" + i);
        }
        for (int i = 1; i < 6; i++) {
            addTextureToList(TextureTypes.METAL_NUGGET_TEXTURES, "item/nuggets/nugget_" + i);
        }

        for (int i = 1; i < 4; i++) {
            addTextureToList(TextureTypes.HELMET_TEXTURES, "item/armor/helmet_" + i);
        }
        for (int i = 1; i < 4; i++) {
            addTextureToList(TextureTypes.CHESTPLATE_TEXTURES, "item/armor/chestplate_" + i);
        }
        for (int i = 1; i < 4; i++) {
            addTextureToList(TextureTypes.LEGGINGS_TEXTURES, "item/armor/leggings_" + i);
        }
        for (int i = 1; i < 4; i++) {
            addTextureToList(TextureTypes.BOOTS_TEXTURES, "item/armor/boots_" + i);
        }

        for (int i = 1; i < 12; i++) {
            addTextureToList(TextureTypes.FRUIT_TEXTURES, "item/fruits/fruit_" + i);
        }

        addTextureToList(TextureTypes.HORSE_ARMOR_SADDLE_TEXTURES, "item/armor/horse_armor_saddle");
        addTextureToList(TextureTypes.HORSE_ARMOR_SADDLE_TEXTURES, "item/armor/horse_armor_saddle_2");

        TextureTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_1.png"));
        TextureTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_2.png"));
        TextureTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_3.png"));
        TextureTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_4.png"));

        addTexturesToMap(TextureTypes.HORSE_ARMOR, "item/armor/horse_armor_saddle",
                "textures/models/armor/horse/horse_armor_1.png");
        addTexturesToMap(TextureTypes.HORSE_ARMOR, "item/armor/horse_armor_saddle_2",
                "textures/models/armor/horse/horse_armor_2.png");

        addTexturesToMap(TextureTypes.AXES, "item/tools/axe/axe_head", "item/tools/axe/axe_stick");
        addTexturesToMap(TextureTypes.AXES, "item/tools/axe/axe_head_3", "item/tools/axe/axe_stick_3");

        addTexturesToMap(TextureTypes.PICKAXES, "item/tools/pickaxe/pickaxe_head", "item/tools/pickaxe/pickaxe_stick");
        addTexturesToMap(TextureTypes.PICKAXES, "item/tools/pickaxe/pickaxe_1", "item/tools/pickaxe/pickaxe_1_handle");

        addTexturesToMap(TextureTypes.HOES, "item/tools/hoe/hoe_head", "item/tools/hoe/hoe_stick");
        addTexturesToMap(TextureTypes.HOES, "item/tools/hoe/hoe_head_2", "item/tools/hoe/hoe_stick_2");

        addTexturesToMap(TextureTypes.SHOVELS, "item/tools/shovel/shovel_head", "item/tools/shovel/shovel_stick");
        addTexturesToMap(TextureTypes.SHOVELS, "item/tools/shovel/shovel_1", "item/tools/shovel/shovel_1_handle");
        addTexturesToMap(TextureTypes.SHOVELS, "item/tools/shovel/shovel_4", "item/tools/shovel/shovel_4_handle");

        addTexturesToMap(TextureTypes.SWORDS, "item/tools/sword/sword_head", "item/tools/sword/sword_stick");
        addTexturesToMap(TextureTypes.SWORDS, "item/tools/sword/sword_2", "item/tools/sword/sword_2_handle");
        addTexturesToMap(TextureTypes.SWORDS, "item/tools/sword/sword_3", "item/tools/sword/sword_3_handle");
        addTexturesToMap(TextureTypes.SWORDS, "item/tools/sword/sword_4", "item/tools/sword/sword_4_handle");
        addTexturesToMap(TextureTypes.SWORDS, "item/tools/sword/sword_6", "item/tools/sword/sword_6_handle");
        addTexturesToMap(TextureTypes.SWORDS, "item/tools/sword/sword_7", "item/tools/sword/sword_7_handle");
    }

    private static void blockTextures() {
        for (int i = 1; i < 7 ; i++) {
            addTextureToList(TextureTypes.METAL_ORE_TEXTURES, "block/ores/metals/ore_" + i);
        }
        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.GEM_ORE_TEXTURES, "block/ores/gems/ore_" + i);
        }
        for (int i = 1; i < 8; i++) {
            addTextureToList(TextureTypes.CRYSTAL_ORE_TEXTURES, "block/ores/crystals/ore_" + i);
        }

        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.METAL_BLOCK_TEXTURES, "block/storage_blocks/metals/metal_" + i);
        }
        for (int i = 1; i < 4; i++) {
            addTextureToList(TextureTypes.GEM_BLOCK_TEXTURES, "block/storage_blocks/gems/gem_" + i);
        }
        addTextureToList(TextureTypes.CRYSTAL_BLOCK_TEXTURES, "block/storage_blocks/crystals/crystal_1");

        for (int i = 1; i < 7; i++) {
            addTextureToList(TextureTypes.STONE_BRICKS_TEXTURES, "block/stone/bricks_" + i);
        }
        for (int i = 1; i < 6; i++) {
            addTextureToList(TextureTypes.CHISELED_STONE_TEXTURES, "block/stone/chiseled_" + i);
        }
        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.COBBLESTONE_TEXTURES, "block/stone/cobblestone_" + i);
        }
        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.POLISHED_STONE_TEXTURES, "block/stone/polished_" + i);
        }
        addTextureToList(TextureTypes.STONE_TEXTURES, "block/stone/stone");
        for (int i = 1; i < 6; i++) {
            addTextureToList(TextureTypes.STONE_TEXTURES, "block/stone/stone_" + i);
        }

        addTextureToList(TextureTypes.MOSSY_STONE_BRICKS_TEXTURES, new Identifier("block/mossy_stone_bricks"));
        addTextureToList(TextureTypes.MOSSY_COBBLESTONE_TEXTURES, new Identifier("block/mossy_cobblestone"));
        addTextureToList(TextureTypes.MOSSY_CHISELED_STONE_TEXTURES, new Identifier("block/chiseled_stone_bricks"));

        addTextureToList(TextureTypes.CRACKED_CHISELED_STONE_TEXTURES, new Identifier("block/chiseled_stone_bricks"));
        addTextureToList(TextureTypes.CRACKED_STONE_BRICKS_TEXTURES, new Identifier("block/cracked_stone_bricks"));

        addTextureToList(TextureTypes.ICE_TEXTURES, "block/ice");
    }

    private static void addTextureToList(List<Identifier> textures, String name) {
        textures.add(new Identifier(RandomlyAddingAnything.MOD_ID, name));
    }

    private static void addTextureToList(List<Identifier> textures, Identifier name) {
        textures.add(name);
    }

    private static void addTexturesToMap(Map<Identifier, Identifier> textureSets, String texture1, String texture2) {
        textureSets.put(
                new Identifier(RandomlyAddingAnything.MOD_ID, texture1),
                new Identifier(RandomlyAddingAnything.MOD_ID, texture2)
        );
    }

}
