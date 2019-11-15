package io.github.vampirestudios.raa.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class WoodType {
	public static final WoodType SPRUCE = WoodTypeRegistry.registerVanilla(new WoodType("spruce", Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG));
	public static final WoodType OAK = WoodTypeRegistry.registerVanilla(new WoodType("oak", Blocks.OAK_LEAVES, Blocks.OAK_LOG));
	public static final WoodType BIRCH = WoodTypeRegistry.registerVanilla(new WoodType("birch", Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG));
	public static final WoodType JUNGLE = WoodTypeRegistry.registerVanilla(new WoodType("jungle", Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG));
	public static final WoodType ACACIA = WoodTypeRegistry.registerVanilla(new WoodType("acacia", Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG));
	public static final WoodType DARK_OAK = WoodTypeRegistry.registerVanilla(new WoodType("dark_oak", Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG));
	public static final WoodType[] VANILLA = new WoodType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};

	protected Identifier identifier;
	private Block leaves;
	private Block log;

	public WoodType(String name, Block leaves, Block log) {
		this(new Identifier(name), leaves, log);
	}

	public WoodType(Identifier identifier, Block leaves, Block log) {
		this.identifier = identifier;
		this.leaves = leaves;
		this.log = log;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public Block getLeaves() {
		return leaves;
	}

	public Block getLog() {
		return log;
	}

}