package io.github.vampirestudios.raa.generation.feature.portalHub;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public class PortalHubThemes {

    public static final Registry<PortalHubTheme> PORTAL_HUB_THEMES = new DefaultedRegistry<>("raa:portal_hub_themes");

    public static final PortalHubTheme STONE_BRICKS = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "stone_bricks"), new PortalHubTheme(Blocks.STONE_BRICKS, Blocks.STONE_BRICK_WALL, Blocks.STONE_BRICK_SLAB, Blocks.STONE_BRICK_STAIRS));
    public static final PortalHubTheme BRICKS = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "bricks"), new PortalHubTheme(Blocks.BRICKS, Blocks.BRICK_WALL, Blocks.BRICK_SLAB, Blocks.BRICK_STAIRS));
    public static final PortalHubTheme END_STONE_BRICKS = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "end_stone_bricks"), new PortalHubTheme(Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_WALL, Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICK_STAIRS));
    public static final PortalHubTheme SANDSTONE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "sandstone"), new PortalHubTheme(Blocks.SANDSTONE, Blocks.SANDSTONE_WALL, Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE_STAIRS));
    public static final PortalHubTheme PRISMARINE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "prismarine"), new PortalHubTheme(Blocks.PRISMARINE, Blocks.PRISMARINE_WALL, Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_STAIRS));
    public static final PortalHubTheme GRANITE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "granite"), new PortalHubTheme(Blocks.GRANITE, Blocks.GRANITE_WALL, Blocks.GRANITE_SLAB, Blocks.GRANITE_STAIRS));
    public static final PortalHubTheme DIORITE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "diorite"), new PortalHubTheme(Blocks.DIORITE, Blocks.DIORITE_WALL, Blocks.DIORITE_SLAB, Blocks.DIORITE_STAIRS));
    public static final PortalHubTheme ANDESITE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "andesite"), new PortalHubTheme(Blocks.ANDESITE, Blocks.ANDESITE_WALL, Blocks.ANDESITE_SLAB, Blocks.ANDESITE_STAIRS));
    public static final PortalHubTheme NETHER_BRICKS = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "nether_bricks"), new PortalHubTheme(Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_WALL, Blocks.NETHER_BRICK_SLAB, Blocks.NETHER_BRICK_STAIRS));
    public static final PortalHubTheme RED_NETHER_BRICKS = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "red_nether_bricks"), new PortalHubTheme(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_WALL, Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICK_STAIRS));
    public static final PortalHubTheme COBBLESTONE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "cobblestone"), new PortalHubTheme(Blocks.COBBLESTONE, Blocks.COBBLESTONE_WALL, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE_STAIRS));
    public static final PortalHubTheme MOSSY_COBBLESTONE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "mossy_cobblestone"), new PortalHubTheme(Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_STAIRS));
    public static final PortalHubTheme MOSSY_STONE_BRICKS = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "mossy_stone_bricks"), new PortalHubTheme(Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_STAIRS));
    public static final PortalHubTheme RED_SANDSTONE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "red_sandstone"), new PortalHubTheme(Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_WALL, Blocks.RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE_STAIRS));

    public static final PortalHubTheme QUARTZ = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "quartz"), new PortalHubTheme(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR, Blocks.QUARTZ_SLAB, Blocks.QUARTZ_STAIRS));
    public static final PortalHubTheme SMOOTH_QUARTZ = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "smooth_quartz"), new PortalHubTheme(Blocks.SMOOTH_QUARTZ, Blocks.QUARTZ_PILLAR, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_QUARTZ_STAIRS));

    public static final PortalHubTheme ACACIA = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "acacia"), new PortalHubTheme(Blocks.ACACIA_PLANKS, Blocks.ACACIA_FENCE, Blocks.ACACIA_SLAB, Blocks.ACACIA_STAIRS));
    public static final PortalHubTheme BIRCH = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "birch"), new PortalHubTheme(Blocks.BIRCH_PLANKS, Blocks.BIRCH_FENCE, Blocks.BIRCH_SLAB, Blocks.BIRCH_STAIRS));
    public static final PortalHubTheme DARK_OAK = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "dark_oak"), new PortalHubTheme(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE, Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_STAIRS));
    public static final PortalHubTheme JUNGLE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "jungle"), new PortalHubTheme(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_FENCE, Blocks.JUNGLE_SLAB, Blocks.JUNGLE_STAIRS));
    public static final PortalHubTheme OAK = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "oak"), new PortalHubTheme(Blocks.OAK_PLANKS, Blocks.OAK_FENCE, Blocks.OAK_SLAB, Blocks.OAK_STAIRS));
    public static final PortalHubTheme SPRUCE = registerTheme(new Identifier(RandomlyAddingAnything.MOD_ID, "spruce"), new PortalHubTheme(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_FENCE, Blocks.SPRUCE_SLAB, Blocks.SPRUCE_STAIRS));

    private static PortalHubTheme registerTheme(Identifier name, PortalHubTheme theme) {
        return Registry.register(PORTAL_HUB_THEMES, name, theme);
    }

}
