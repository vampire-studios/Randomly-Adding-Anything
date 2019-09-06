package fr.arthurbambou.randomlyaddinganything.registries;

import fr.arthurbambou.randomlyaddinganything.api.objects.OreTypes;
import net.minecraft.util.Identifier;

public class Textures {

    public static void init() {
        ores();
    }

    private static void ores() {
        OreTypes.METAL_TEXTURES.add(new Identifier("items/melon_seeds"));
        OreTypes.METAL_TEXTURES.add(new Identifier("items/prismarine_crystals"));
        OreTypes.METAL_TEXTURES.add(new Identifier("items/cocoa_beans"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/ghast_tear"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/coal"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/diamond"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/glowstone_dust"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/gold_nugget"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/iron_nugget"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/gunpowder"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/lapis_lazuli"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/prismarine_shard"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/quartz"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/redstone"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/ruby"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/sugar"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/emerald"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/ender_pearl"));
        OreTypes.GEM_TEXTURES.add(new Identifier("items/charcoal"));
    }
}
