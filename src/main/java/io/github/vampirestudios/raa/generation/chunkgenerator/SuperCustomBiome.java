package io.github.vampirestudios.raa.generation.chunkgenerator;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

/**
 * Biome for use with {@link SuperCustomChunkGenerator}
 */
public abstract class SuperCustomBiome extends Biome {
	public final double stretch, variationBelow, variationAbove;
	public final int octaves;
	
	protected SuperCustomBiome(Properties properties) {
		super(properties);

		stretch = properties.stretch;
		variationAbove = properties.variationAbove;
		variationBelow = properties.variationBelow;
		
		octaves = properties.octaves;
	}
	/**
	 * Biome settings for use with the {@link SuperCustomBiome}
	 */
	public static class Properties extends Biome.Settings {
		private double stretch, variationBelow, variationAbove = 0.0D;
		private int octaves = 4;

		public Properties() {
			super();
		}

		/**
		 * @param octaves octaves to use. value between 1 and 6
		 */
		public Properties heightSettings(int octaves, double stretch, double variationAbove, double variationBelow) {
			this.octaves = octaves;
			this.stretch = stretch;
			this.variationAbove = variationAbove;
			this.variationBelow = variationBelow;

			this.depth(0.0F);
			this.scale(0.0F); // possibly convert between custom values and vanilla values for vanilla compat?

			return this;
		}

		@Override
		@Deprecated
		public Biome.Settings depth(float depth) {
			return super.depth(depth);
		}

		@Override
		@Deprecated
		public Biome.Settings scale(float scale) {
			return super.scale(scale);
		}

		@Override
		public Properties temperature(float temperature) {
			super.temperature(temperature);
			return this;
		}

		@Override
		public Properties downfall(float downfall) {
			super.downfall(downfall);
			return this;
		}

		@Override
		public Properties precipitation(Biome.Precipitation precip) {
			super.precipitation(precip);
			return this;
		}

		@Override
		public <SC extends SurfaceConfig> Properties configureSurfaceBuilder(SurfaceBuilder<SC> surfaceBuilder, SC surfaceConfig) {
			super.configureSurfaceBuilder(surfaceBuilder, surfaceConfig);
			return this;
		}

		@Override
		public Properties category(Biome.Category category) {
			super.category(category);
			return this;
		}

		public Properties waterColor(int waterColor) {
			super.waterColor(waterColor);
			return this;
		}

		public Properties waterFogColor(int waterFogColor) {
			super.waterFogColor(waterFogColor);
			return this;
		}

		public Properties parent(String parent) {
			super.parent(parent);
			return this;
		}
	}
}
