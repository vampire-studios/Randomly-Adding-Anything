package fr.arthurbambou.randomlyaddinganything.registries;

import fr.arthurbambou.randomlyaddinganything.api.objects.OreTypes;
import net.minecraft.util.Identifier;

public class Textures {

    public static void init() {
        ores();
    }

    private static void ores() {
        OreTypes.MATERIAL_ORE.add(new Identifier("block/andesite"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/diorite"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/dirt"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/end_stone"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/gravel"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/netherrack"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/packed_ice"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/sand"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/soul_sand"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/stone"));
        OreTypes.MATERIAL_ORE.add(new Identifier("block/terracotta"));

        OreTypes.METAL_TEXTURES.add(new Identifier("item/melon_seeds"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/prismarine_crystals"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/cocoa_beans"));

        OreTypes.GEM_TEXTURES.add(new Identifier("item/ghast_tear"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/coal"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/diamond"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/glowstone_dust"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/gold_nugget"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/iron_nugget"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/gunpowder"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/lapis_lazuli"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/prismarine_shard"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/quartz"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/redstone"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/ruby"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/sugar"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/emerald"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/ender_pearl"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/charcoal"));
    }
}
