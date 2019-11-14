package io.github.vampirestudios.raa.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class WoodType {
	public static final WoodType SPRUCE = WoodTypeRegistry.registerVanilla(new WoodType("spruce", Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG));
	public static final WoodType OAK = WoodTypeRegistry.registerVanilla(new WoodType("oak", Blocks.OAK_PLANKS, Blocks.OAK_LEAVES, Blocks.OAK_LOG));
	public static final WoodType BIRCH = WoodTypeRegistry.registerVanilla(new WoodType("birch", Blocks.BIRCH_PLANKS, Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG));
	public static final WoodType JUNGLE = WoodTypeRegistry.registerVanilla(new WoodType("jungle", Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG));
	public static final WoodType ACACIA = WoodTypeRegistry.registerVanilla(new WoodType("acacia", Blocks.ACACIA_PLANKS, Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG));
	public static final WoodType DARK_OAK = WoodTypeRegistry.registerVanilla(new WoodType("dark_oak", Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG));
	public static final WoodType[] VANILLA = new WoodType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};

	protected Identifier identifier;
	private Block baseBlock;
	private Block leaves;
	private Block log;

	public WoodType(Identifier identifier) {
		this(identifier, Blocks.AIR);
	}

	public WoodType(String name, Block baseBlock) {
		this(new Identifier("minecraft", name), baseBlock);
	}

	public WoodType(String name, Block baseBlock, Block leaves, Block log) {
		this(new Identifier("minecraft", name), baseBlock, leaves, log);
	}

	public WoodType(Identifier identifier, Block baseBlock) {
		this.identifier = identifier;
		this.baseBlock = baseBlock;
	}

	public WoodType(Identifier identifier, Block baseBlock, Block leaves, Block log) {
		this.identifier = identifier;
		this.baseBlock = baseBlock;
		this.leaves = leaves;
		this.log = log;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public Block getBaseBlock() {
		return baseBlock;
	}

	public Block getLeaves() {
		return leaves;
	}

	public Block getLog() {
		return log;
	}
}
