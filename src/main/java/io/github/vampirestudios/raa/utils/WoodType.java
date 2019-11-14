package io.github.vampirestudios.raa.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class WoodType {
	public static final WoodType SPRUCE = WoodTypeRegistry.registerVanilla(new WoodType("spruce", Blocks.SPRUCE_PLANKS));
	public static final WoodType OAK = WoodTypeRegistry.registerVanilla(new WoodType("oak", Blocks.OAK_PLANKS));
	public static final WoodType BIRCH = WoodTypeRegistry.registerVanilla(new WoodType("birch", Blocks.BIRCH_PLANKS));
	public static final WoodType JUNGLE = WoodTypeRegistry.registerVanilla(new WoodType("jungle", Blocks.JUNGLE_PLANKS));
	public static final WoodType ACACIA = WoodTypeRegistry.registerVanilla(new WoodType("acacia", Blocks.ACACIA_PLANKS));
	public static final WoodType DARK_OAK = WoodTypeRegistry.registerVanilla(new WoodType("dark_oak", Blocks.DARK_OAK_PLANKS));
	public static final WoodType[] VANILLA = new WoodType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};

	protected Identifier identifier;
	public Block baseBlock;

	public WoodType(Identifier identifier) {
		this(identifier, Blocks.AIR);
	}

	public WoodType(String name, Block baseBlock) {
		this(new Identifier("minecraft", name), baseBlock);
	}

	public WoodType(Identifier identifier, Block baseBlock) {
		this.identifier = identifier;
		this.baseBlock = baseBlock;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

}
