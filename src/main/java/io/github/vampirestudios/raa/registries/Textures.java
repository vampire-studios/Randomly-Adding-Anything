package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import net.minecraft.util.Identifier;

import java.util.List;

public class Textures {

    public static void init() {
        textures();
    }

    private static void textures() {
        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/ingot_1"));
        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/ingot_2"));
        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/ingot_3"));

        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_1"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_2"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_3"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_4"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_5"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_6"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_7"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_8"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/gem_10"));

        OreTypes.CRYSTAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/crystal_1"));
        OreTypes.CRYSTAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/crystal_2"));
        OreTypes.CRYSTAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/crystal_4"));
        OreTypes.CRYSTAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/crystal_5"));
        OreTypes.CRYSTAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/crystal_6"));
        OreTypes.CRYSTAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/crystal_7"));
        OreTypes.CRYSTAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/crystal_8"));

        OreTypes.METAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_1"));
        OreTypes.METAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_10"));
        OreTypes.METAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_11"));
        OreTypes.METAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_13"));
        OreTypes.METAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_17"));

        OreTypes.GEM_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_2"));
        OreTypes.GEM_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_7"));
        OreTypes.GEM_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_10"));
        OreTypes.GEM_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_11"));

        OreTypes.CRYSTAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_3"));
        OreTypes.CRYSTAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_4"));
        OreTypes.CRYSTAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_8"));
        OreTypes.CRYSTAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_9"));
        OreTypes.CRYSTAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_16"));
        OreTypes.CRYSTAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_15"));
        OreTypes.CRYSTAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_14"));
        OreTypes.CRYSTAL_ORE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_12"));

        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/metal_1"));
        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/metal_2"));
        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/metal_3"));
        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/metal_4"));

        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/gem_1"));
        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/gem_2"));
        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/gem_3"));
        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/gem_4"));

        OreTypes.CRYSTAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/crystal_1"));

        OreTypes.METAL_NUGGET_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/nugget_1"));
        OreTypes.METAL_NUGGET_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/nugget_2"));
        OreTypes.METAL_NUGGET_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/nugget_3"));
        OreTypes.METAL_NUGGET_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/nugget_4"));
        OreTypes.METAL_NUGGET_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/nugget_5"));
        OreTypes.METAL_NUGGET_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/nugget_6"));

        OreTypes.HORSE_ARMOR_SADDLE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/horse_armor_saddle"));
        OreTypes.HORSE_ARMOR_SADDLE_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/horse_armor_saddle_2"));

        OreTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_1.png"));
        OreTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_2.png"));
        OreTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_3.png"));
        OreTypes.HORSE_ARMOR_MODEL_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_4.png"));

        addTextureToList(OreTypes.HELMET_TEXTURES, "item/helmet_1");
        addTextureToList(OreTypes.HELMET_TEXTURES, "item/helmet_2");
        addTextureToList(OreTypes.HELMET_TEXTURES, "item/helmet_3");

        addTextureToList(OreTypes.CHESTPLATE_TEXTURES, "item/chestplate_1");
        addTextureToList(OreTypes.CHESTPLATE_TEXTURES, "item/chestplate_2");
        addTextureToList(OreTypes.CHESTPLATE_TEXTURES, "item/chestplate_3");

        addTextureToList(OreTypes.LEGGINGS_TEXTURES, "item/leggings_1");
        addTextureToList(OreTypes.LEGGINGS_TEXTURES, "item/leggings_2");
        addTextureToList(OreTypes.LEGGINGS_TEXTURES, "item/leggings_3");

        addTextureToList(OreTypes.BOOTS_TEXTURES, "item/boots_1");
        addTextureToList(OreTypes.BOOTS_TEXTURES, "item/boots_2");
        addTextureToList(OreTypes.BOOTS_TEXTURES, "item/boots_3");

        OreTypes.HORSE_ARMOR.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/horse_armor_saddle"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_1.png"));
        OreTypes.HORSE_ARMOR.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/horse_armor_saddle_2"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/horse/horse_armor_2.png"));

        OreTypes.AXES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_head_1"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_stick_1.png"));
        OreTypes.AXES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_head_2"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_stick_2.png"));
        OreTypes.AXES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_head_3"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_stick_3.png"));
        OreTypes.AXES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_head_4"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_stick_4.png"));
        OreTypes.AXES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_head_5"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/axe_stick_5.png"));

        OreTypes.HOES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_head_1"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_stick_1.png"));
        OreTypes.HOES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_head_2"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_stick_2.png"));
        OreTypes.HOES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_head_3"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_stick_3.png"));
        OreTypes.HOES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_head_4"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_stick_4.png"));
        OreTypes.HOES.put(new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_head_5"),
                new Identifier(RandomlyAddingAnything.MOD_ID, "item/hoe_stick_5.png"));
    }

    private static void addTextureToList (List<Identifier> textures, String name) {
        textures.add(new Identifier(RandomlyAddingAnything.MOD_ID, name));
    }

}
