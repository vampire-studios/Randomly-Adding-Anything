package io.github.vampirestudios.raa.generation.dimensions.data;

public class DimensionTreeData {
    private DimensionFoliagePlacers foliagePlacerType;
    private DimensionTreeTypes treeType;

	private boolean plainsTrees; //This option overrides everything and only makes the trees found in the plains biome.
	private int baseHeight;
	private int foliageHeight;
	private int foliageHeightRandom;
	private int crownHeight;
	private int maxWaterDepth;
	private float chance;

	private boolean hasLeafVines;
	private boolean hasTrunkVines;
	private boolean hasCocoaBeans;
	private boolean hasPodzolUnderneath;
	private boolean hasBeehives;

	private float cocoaChance;
	private float beehiveChance;

	private int foliageRange;

	private DimensionWoodType woodType;

	public DimensionTreeData(DimensionFoliagePlacers foliagePlacerType, DimensionTreeTypes treeType, boolean plainsTrees, int baseHeight, int foliageHeight,
							 int foliageHeightRandom, int crownHeight, int maxWaterDepth, DimensionWoodType woodType, int foliageRange, float chance, boolean hasLeafVines,
							 boolean hasTrunkVines, boolean hasCocoaBeans, boolean hasPodzolUnderneath, boolean hasBeehives, float cocoaChance, float beehiveChance) {
		this.foliagePlacerType = foliagePlacerType;
		this.treeType = treeType;
		this.plainsTrees = plainsTrees;
		this.baseHeight = baseHeight;
		this.foliageHeight = foliageHeight;
		this.foliageHeightRandom = foliageHeightRandom;
		this.crownHeight = crownHeight;
		this.maxWaterDepth = maxWaterDepth;
		this.foliageRange = foliageRange;
		this.woodType = woodType;
		this.chance = chance;
		this.hasLeafVines = hasLeafVines;
		this.hasTrunkVines = hasTrunkVines;
		this.hasCocoaBeans = hasCocoaBeans;
		this.hasPodzolUnderneath = hasPodzolUnderneath;
		this.hasBeehives = hasBeehives;
		this.cocoaChance = cocoaChance;
		this.beehiveChance = beehiveChance;
	}

	public DimensionFoliagePlacers getFoliagePlacerType() {
		return foliagePlacerType;
	}

	public DimensionTreeTypes getTreeType() {
		return treeType;
	}

	public boolean isPlainsTrees() {
		return plainsTrees;
	}

	public int getBaseHeight() {
		return baseHeight;
	}

	public int getFoliageHeight() {
		return foliageHeight;
	}

	public int getFoliageHeightRandom() {
		return foliageHeightRandom;
	}

	public int getMaxWaterDepth() {
		return maxWaterDepth;
	}

	public int getFoliageRange() {
		return foliageRange;
	}

	public DimensionWoodType getWoodType() {
		return woodType;
	}

	public float getChance() {
		return chance;
	}

	public boolean hasLeafVines() {
		return hasLeafVines;
	}

	public boolean hasTrunkVines() {
		return hasTrunkVines;
	}

	public boolean hasCocoaBeans() {
		return hasCocoaBeans;
	}

	public boolean hasPodzolUnderneath() {
		return hasPodzolUnderneath;
	}

	public boolean hasBeehives() {
		return hasBeehives;
	}

	public float getCocoaChance() {
		return cocoaChance;
	}

	public float getBeehiveChance() {
		return beehiveChance;
	}

	public int getCrownHeight() {
		return crownHeight;
	}

	public static class Builder {
		private DimensionFoliagePlacers foliagePlacerType;
		private DimensionTreeTypes treeType;
		private boolean plainsTrees; //This option overrides everything and only makes the trees found in the plains biome.
		private int baseHeight;
		private int foliageHeight;
		private int foliageHeightRandom;
		private int crownHeight;
		private int maxWaterDepth;
		private float chance;

		private boolean hasLeafVines;
		private boolean hasTrunkVines;
		private boolean hasCocoaBeans;
		private boolean hasPodzolUnderneath;
		private boolean hasBeehives;

		private float cocoaChance;
		private float beehiveChance;

		private int foliageRange;

		private DimensionWoodType woodType;

		public static Builder create() {
			return new Builder();
		}

		public Builder foliagePlacerType(DimensionFoliagePlacers foliagePlacerType) {
			this.foliagePlacerType = foliagePlacerType;
			return this;
		}

		public Builder treeType(DimensionTreeTypes treeType) {
			this.treeType = treeType;
			return this;
		}

		public Builder plainsTrees(boolean plainsTrees) {
			this.plainsTrees = plainsTrees;
			return this;
		}

		public Builder baseHeight(int baseHeight) {
			this.baseHeight = baseHeight;
			return this;
		}

		public Builder foliageHeight(int foliageHeight) {
			this.foliageHeight = foliageHeight;
			return this;
		}

		public Builder foliageHeightRandom(int foliageHeightRandom) {
			this.foliageHeightRandom = foliageHeightRandom;
			return this;
		}

		public Builder crownHeight(int crownHeight) {
			this.crownHeight = crownHeight;
			return this;
		}

		public Builder maxWaterDepth(int maxWaterDepth) {
			this.maxWaterDepth = maxWaterDepth;
			return this;
		}

		public Builder foliageRange(int foliageRange) {
			this.foliageRange = foliageRange;
			return this;
		}

		public Builder woodType(DimensionWoodType woodType) {
			this.woodType = woodType;
			return this;
		}

		public Builder chance(float chance) {
			this.chance = chance;
			return this;
		}

		public Builder hasLeafVines(boolean hasLeafVines) {
			this.hasLeafVines = hasLeafVines;
			return this;
		}

		public Builder hasTrunkVines(boolean hasTrunkVines) {
			this.hasTrunkVines = hasTrunkVines;
			return this;
		}

		public Builder hasCocoaBeans(boolean hasCocoaBeans) {
			this.hasCocoaBeans = hasCocoaBeans;
			return this;
		}

		public Builder hasPodzolUnderneath(boolean hasPodzolUnderneath) {
			this.hasPodzolUnderneath = hasPodzolUnderneath;
			return this;
		}

		public Builder hasBeehives(boolean hasBeehives) {
			this.hasBeehives = hasBeehives;
			return this;
		}

		public Builder cocoaChance(float cocoaChance) {
			this.cocoaChance = cocoaChance;
			return this;
		}

		public Builder beehiveChance(float beehiveChance) {
			this.beehiveChance = beehiveChance;
			return this;
		}

		public DimensionTreeData build() {
			return new DimensionTreeData(foliagePlacerType, treeType, plainsTrees, baseHeight, foliageHeight, foliageHeightRandom, crownHeight, maxWaterDepth, woodType,
					foliageRange, chance, hasLeafVines, hasTrunkVines, hasCocoaBeans, hasPodzolUnderneath, hasBeehives, cocoaChance, beehiveChance);
		}
	}
}
