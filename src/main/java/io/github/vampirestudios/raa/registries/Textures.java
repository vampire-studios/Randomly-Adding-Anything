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

        addTextureToList(TextureTypes.SUNS, new Identifier(RandomlyAddingAnything.MOD_ID, "textures/environment/sun.png"));
        addTextureToList(TextureTypes.SUNS, new Identifier(RandomlyAddingAnything.MOD_ID, "textures/environment/sun_2.png"));
        addTextureToList(TextureTypes.SUNS, new Identifier(RandomlyAddingAnything.MOD_ID, "textures/environment/sun_3.png"));

        addTextureToList(TextureTypes.MOONS, new Identifier(RandomlyAddingAnything.MOD_ID, "textures/environment/moon_phases.png"));
        addTextureToList(TextureTypes.MOONS, new Identifier(RandomlyAddingAnything.MOD_ID, "textures/environment/moon_phases_2.png"));
    }

    private static void itemTextures() {
        for (int i = 1; i < 19; i++) {
            addTextureToList(TextureTypes.INGOT_TEXTURES, "item/ingots/ingot_" + i);
        }
        for (int i = 1; i < 24; i++) {
            addTextureToList(TextureTypes.GEM_ITEM_TEXTURES, "item/gems/gem_" + i);
        }
        for (int i = 1; i < 11; i++) {
            addTextureToList(TextureTypes.CRYSTAL_ITEM_TEXTURES, "item/crystals/crystal_" + i);
        }
        for (int i = 1; i < 9; i++) {
            addTextureToList(TextureTypes.METAL_NUGGET_TEXTURES, "item/nuggets/nugget_" + i);
        }

        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.HELMET_TEXTURES, "item/armor/helmet_" + i);
        }
        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.CHESTPLATE_TEXTURES, "item/armor/chestplate_" + i);
        }
        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.LEGGINGS_TEXTURES, "item/armor/leggings_" + i);
        }
        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.BOOTS_TEXTURES, "item/armor/boots_" + i);
        }

        for (int i = 1; i < 12; i++) {
            addTextureToList(TextureTypes.FRUIT_TEXTURES, "item/fruits/fruit_" + i);
        }

        addTextureToList(TextureTypes.HORSE_ARMOR_SADDLE_TEXTURES, "item/armor/horse_armor_saddle");
        addTextureToList(TextureTypes.HORSE_ARMOR_SADDLE_TEXTURES, "item/armor/horse_armor_saddle_2");

        TextureTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_1.png"));
        TextureTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_2.png"));

        addTexturesToMap(TextureTypes.HORSE_ARMOR, "item/armor/horse_armor_saddle",
                "textures/models/armor/horse/horse_armor_1.png");
        addTexturesToMap(TextureTypes.HORSE_ARMOR, "item/armor/horse_armor_saddle_2",
                "textures/models/armor/horse/horse_armor_2.png");

        addTexturesToMap(TextureTypes.HOES, "item/tools/hoe/hoe_head_1", "item/tools/hoe/hoe_stick_1");
        addTexturesToMap(TextureTypes.HOES, "item/tools/hoe/hoe_head_2", "item/tools/hoe/hoe_stick_2");
        addTexturesToMap(TextureTypes.HOES, "item/tools/hoe/hoe_head_3", "item/tools/hoe/hoe_stick_3");

        //Sword Stuff
        for (int i = 1; i < 14; i++) {
            addTextureToList(TextureTypes.SWORD_BLADES, "item/tools/sword/blade_" + i);
        }
        for (int i = 1; i < 12; i++) {
            addTextureToList(TextureTypes.SWORD_HANDLES, "item/tools/sword/handle_" + i);
        }

        //Pickaxe Stuff
        for (int i = 1; i < 10; i++) {
            addTextureToList(TextureTypes.PICKAXE_HEAD, "item/tools/pickaxe/pickaxe_" + i);
        }
        for (int i = 1; i < 10; i++) {
            addTextureToList(TextureTypes.PICKAXE_STICKS, "item/tools/pickaxe/stick_" + i);
        }

        //Axe Stuff
        for (int i = 1; i < 12; i++) {
            addTextureToList(TextureTypes.AXE_HEAD, "item/tools/axe/axe_head_" + i);
        }
        for (int i = 1; i < 9; i++) {
            addTextureToList(TextureTypes.AXE_STICKS, "item/tools/axe/axe_stick_" + i);
        }

        //Hoe Stuff
        for (int i = 1; i < 11; i++) {
            addTextureToList(TextureTypes.HOE_HEAD, "item/tools/hoe/hoe_head_" + i);
        }
        for (int i = 1; i < 11; i++) {
            addTextureToList(TextureTypes.HOE_STICKS, "item/tools/hoe/hoe_stick_" + i);
        }

        //Shovel Stuff
        for (int i = 1; i < 12; i++) {
            addTextureToList(TextureTypes.SHOVEL_HEAD, "item/tools/shovel/shovel_head_" + i);
        }
        for (int i = 1; i < 12; i++) {
            addTextureToList(TextureTypes.SHOVEL_STICKS, "item/tools/shovel/shovel_stick_" + i);
        }

        addTextureToList(TextureTypes.METAL_GEAR_TEXTURES, "item/gears/gear_1");

        addTextureToList(TextureTypes.METAL_PLATE_TEXTURES, "item/plates/plate_1");
        addTextureToList(TextureTypes.METAL_PLATE_TEXTURES, "item/plates/plate_2");

        addTextureToList(TextureTypes.SMALL_DUST_TEXTURES, "item/small_dusts/small_dust_1");

        addTextureToList(TextureTypes.DUST_TEXTURES, "item/dusts/dust_1");
        addTextureToList(TextureTypes.DUST_TEXTURES, "item/dusts/dust_2");
    }

    private static void blockTextures() {
        for (int i = 1; i < 10; i++) {
            addTextureToList(TextureTypes.METAL_ORE_TEXTURES, "block/ores/metals/ore_" + i);
        }
        for (int i = 1; i < 9; i++) {
            addTextureToList(TextureTypes.GEM_ORE_TEXTURES, "block/ores/gems/ore_" + i);
        }
        for (int i = 1; i < 11; i++) {
            addTextureToList(TextureTypes.CRYSTAL_ORE_TEXTURES, "block/ores/crystals/ore_" + i);
        }
        for (int i = 1; i < 4; i++) {
            addTextureToList(TextureTypes.METAL_ORE_TEXTURES, "block/ores/generic_ore_" + i);
            addTextureToList(TextureTypes.GEM_ORE_TEXTURES, "block/ores/generic_ore_" + i);
            addTextureToList(TextureTypes.CRYSTAL_ORE_TEXTURES, "block/ores/generic_ore_" + i);
        }


        for (int i = 1; i < 7; i++) {
            addTextureToList(TextureTypes.METAL_BLOCK_TEXTURES, "block/storage_blocks/metals/metal_" + i);
        }
        for (int i = 1; i < 6; i++) {
            addTextureToList(TextureTypes.GEM_BLOCK_TEXTURES, "block/storage_blocks/gems/gem_" + i);
        }
        for (int i = 1; i < 5; i++) {
            addTextureToList(TextureTypes.CRYSTAL_BLOCK_TEXTURES, "block/storage_blocks/crystals/crystal_" + i);
        }

        for (int i = 1; i < 11; i++) {
            addTextureToList(TextureTypes.STONE_BRICKS_TEXTURES, "block/stone/bricks_" + i);
        }
        for (int i = 1; i < 18; i++) {
            addTextureToList(TextureTypes.CHISELED_STONE_TEXTURES, "block/stone/chiseled_" + i);
        }
        for (int i = 1; i < 13; i++) {
            addTextureToList(TextureTypes.COBBLESTONE_TEXTURES, "block/stone/cobblestone_" + i);
        }
        for (int i = 1; i < 7; i++) {
            addTextureToList(TextureTypes.POLISHED_STONE_TEXTURES, "block/stone/polished_" + i);
        }
        for (int i = 1; i < 15; i++) {
            addTextureToList(TextureTypes.STONE_TEXTURES, "block/stone/stone_" + i);
        }

        addTextureToList(TextureTypes.MOSSY_STONE_BRICKS_TEXTURES, new Identifier("block/mossy_stone_bricks"));
        addTextureToList(TextureTypes.MOSSY_COBBLESTONE_TEXTURES, new Identifier("block/mossy_cobblestone"));
        addTextureToList(TextureTypes.MOSSY_CHISELED_STONE_TEXTURES, new Identifier("block/chiseled_stone_bricks"));
        addTextureToList(TextureTypes.MOSSY_CHISELED_STONE_TEXTURES, new Identifier("block/chiseled_polished_blackstone_bricks"));

        addTextureToList(TextureTypes.CRACKED_CHISELED_STONE_TEXTURES, new Identifier("block/chiseled_stone_bricks"));
        addTextureToList(TextureTypes.CRACKED_CHISELED_STONE_TEXTURES, new Identifier("block/chiseled_polished_blackstone_bricks"));
        addTextureToList(TextureTypes.CRACKED_STONE_BRICKS_TEXTURES, new Identifier("block/cracked_stone_bricks"));
        addTextureToList(TextureTypes.CRACKED_STONE_BRICKS_TEXTURES, new Identifier("block/cracked_polished_blackstone_bricks"));

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
