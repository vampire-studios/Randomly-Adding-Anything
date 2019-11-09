package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class DimensionData {
    private Identifier id;
    private String name;
    private int dimensionId;
    private DimensionBiomeData biomeData;
    private DimensionColorPalette dimensionColorPalette;
    private boolean hasSkyLight;
    private boolean hasSky;
    private boolean canSleep;
    private boolean waterVaporize;
    private boolean renderFog;
    private DimensionChunkGenerators dimensionChunkGenerator;
    private int flags;
    private HashMap<String, int[]> mobs;

    public DimensionData(Identifier id, String name, int dimensionId, DimensionBiomeData biomeData, DimensionColorPalette dimensionColorPalette, boolean hasSkyLight, boolean hasSky, boolean canSleep, boolean waterVaporize, boolean renderFog, DimensionChunkGenerators dimensionChunkGenerator, int flags, HashMap<String, int[]> mobs) {
    	this.id = id;
        this.name = name;
        this.dimensionId = dimensionId;
        this.biomeData = biomeData;
        this.dimensionColorPalette = dimensionColorPalette;
        this.hasSkyLight = hasSkyLight;
        this.hasSky = hasSky;
        this.canSleep = canSleep;
        this.waterVaporize = waterVaporize;
        this.renderFog = renderFog;
        this.dimensionChunkGenerator = dimensionChunkGenerator;
        this.flags = flags;
        this.mobs = mobs;
    }

    public Identifier getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public DimensionBiomeData getBiomeData() {
        return biomeData;
    }

    public DimensionColorPalette getDimensionColorPalette() {
        return dimensionColorPalette;
    }

    public boolean hasSkyLight() {
        return hasSkyLight;
    }

    public boolean hasSky() {
        return hasSky;
    }

    public boolean canSleep() {
        return canSleep;
    }

    public boolean doesWaterVaporize() {
        return waterVaporize;
    }

    public boolean shouldRenderFog() {
        return renderFog;
    }

    public DimensionChunkGenerators getDimensionChunkGenerator() {
        return dimensionChunkGenerator;
    }

    public int getFlags() {
        return flags;
    }

    public HashMap<String, int[]> getMobs() {
        return mobs;
    }

	public static class Builder {
		private Identifier id;
		private String name;
		private int dimensionId;
		private DimensionBiomeData biomeData;
		private DimensionColorPalette dimensionColorPalette;
		private boolean hasSkyLight;
		private boolean hasSky;
		private boolean canSleep;
		private boolean waterVaporize;
		private boolean renderFog;
		private DimensionChunkGenerators dimensionChunkGenerator;
		private int flags;
		HashMap<String, int[]> mobs;

		public static Builder create(Identifier id, String name) {
			Builder builder = new Builder();
			builder.id = id;
			builder.name = name;
			return builder;
		}

		@Deprecated
		public static Builder create() {
			return new Builder();
		}

		private Builder() {

		}

		public Builder id(Identifier id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder chunkGenerator(DimensionChunkGenerators dimensionChunkGenerator) {
			this.dimensionChunkGenerator = dimensionChunkGenerator;
			return this;
		}

		public Builder dimensionId(int dimensionId) {
			this.dimensionId = dimensionId;
			return this;
		}

		public Builder biome(DimensionBiomeData biomeData) {
			this.biomeData = biomeData;
			return this;
		}

		public Builder colorPalette(DimensionColorPalette dimensionColorPalette) {
			this.dimensionColorPalette = dimensionColorPalette;
			return this;
		}

		public Builder hasSkyLight(boolean hasSkyLight) {
			this.hasSkyLight = hasSkyLight;
			return this;
		}

		public Builder hasSky(boolean hasSky) {
			this.hasSky = hasSky;
			return this;
		}

		public Builder canSleep(boolean canSleep) {
			this.canSleep = canSleep;
			return this;
		}

		public Builder waterVaporize(boolean waterVaporize) {
			this.waterVaporize = waterVaporize;
			return this;
		}

		public Builder shouldRenderFog(boolean renderFog) {
			this.renderFog = renderFog;
			return this;
		}

		public Builder flags(int flags) {
			this.flags = flags;
			return this;
		}

		public Builder mobs(HashMap<String, int[]> mobs) {
			this.mobs = mobs;
			return this;
		}

		public DimensionData build() {
			return new DimensionData(id, name, dimensionId, biomeData, dimensionColorPalette, hasSkyLight, hasSky, canSleep, waterVaporize, renderFog, dimensionChunkGenerator, flags, mobs);
		}
	}
}