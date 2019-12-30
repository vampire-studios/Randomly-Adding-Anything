package io.github.vampirestudios.raa.generation.dimensions.data;

import io.github.vampirestudios.vampirelib.utils.registry.WoodType;

public enum DimensionWoodType {
	OAK(WoodType.OAK),
	BIRCH(WoodType.BIRCH),
	SPRUCE(WoodType.SPRUCE),
	JUNGLE(WoodType.JUNGLE),
	ACACIA(WoodType.ACACIA),
	DARK_OAK(WoodType.DARK_OAK);

	public WoodType woodType;

	DimensionWoodType(WoodType woodType) {
		this.woodType = woodType;
	}
}
