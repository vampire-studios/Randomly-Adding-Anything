package io.github.vampirestudios.raa.generation.dimensions.data;

public class DimensionTreeData {
    private DimensionFoliagePlacers foliagePlacerType;
    private DimensionTreeTypes treeType;

	private boolean plainsTrees; //This option overrides everything and only makes the trees found in the plains biome.
	private int baseHeight;
	private int foliageHeight;
	private int foliageHeightRandom;
	private int maxWaterDepth;
	private float chance;

	private int foliageRange;

	private DimensionWoodType woodType;

	public DimensionTreeData(DimensionFoliagePlacers foliagePlacerType, DimensionTreeTypes treeType, boolean plainsTrees, int baseHeight, int foliageHeight, int foliageHeightRandom, int maxWaterDepth, DimensionWoodType woodType, int foliageRange, float chance) {
		this.foliagePlacerType = foliagePlacerType;
		this.treeType = treeType;
		this.plainsTrees = plainsTrees;
		this.baseHeight = baseHeight;
		this.foliageHeight = foliageHeight;
		this.foliageHeightRandom = foliageHeightRandom;
		this.maxWaterDepth = maxWaterDepth;
		this.foliageRange = foliageRange;
		this.woodType = woodType;
		this.chance = chance;
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

	public static class Builder {
		private DimensionFoliagePlacers foliagePlacerType;
		private DimensionTreeTypes treeType;
		private boolean plainsTrees; //This option overrides everything and only makes the trees found in the plains biome.
		private int baseHeight;
		private int foliageHeight;
		private int foliageHeightRandom;
		private int maxWaterDepth;
		private float chance;

		private int foliageRange;

		private DimensionWoodType woodType;

		public static Builder create() {
			return new Builder();
		}

		public Builder setFoliagePlacerType(DimensionFoliagePlacers foliagePlacerType) {
			this.foliagePlacerType = foliagePlacerType;
			return this;
		}

		public Builder setTreeType(DimensionTreeTypes treeType) {
			this.treeType = treeType;
			return this;
		}

		public Builder setPlainsTrees(boolean plainsTrees) {
			this.plainsTrees = plainsTrees;
			return this;
		}

		public Builder setBaseHeight(int baseHeight) {
			this.baseHeight = baseHeight;
			return this;
		}

		public Builder setFoliageHeight(int foliageHeight) {
			this.foliageHeight = foliageHeight;
			return this;
		}

		public Builder setFoliageHeightRandom(int foliageHeightRandom) {
			this.foliageHeightRandom = foliageHeightRandom;
			return this;
		}

		public Builder setMaxWaterDepth(int maxWaterDepth) {
			this.maxWaterDepth = maxWaterDepth;
			return this;
		}

		public Builder setFoliageRange(int foliageRange) {
			this.foliageRange = foliageRange;
			return this;
		}

		public Builder setWoodType(DimensionWoodType woodType) {
			this.woodType = woodType;
			return this;
		}

		public Builder setChance(float chance) {
			this.chance = chance;
			return this;
		}

		public DimensionTreeData build() {
			return new DimensionTreeData(foliagePlacerType, treeType, plainsTrees, baseHeight, foliageHeight, foliageHeightRandom, maxWaterDepth, woodType, foliageRange, chance);
		}
	}
}
