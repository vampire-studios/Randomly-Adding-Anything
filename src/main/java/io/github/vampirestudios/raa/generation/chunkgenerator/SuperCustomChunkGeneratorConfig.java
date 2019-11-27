package io.github.vampirestudios.raa.generation.chunkgenerator;

import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;

public class SuperCustomChunkGeneratorConfig extends ChunkGeneratorConfig {

	public final int seaLevel;
	
	public final boolean enableLargeSampler;
	public final double largeSamplerAmplitude;
	
	public final double noiseStretch;

	private SuperCustomChunkGeneratorConfig(int seaLevel, boolean enableLargeSampler, double largeSamplerAmplitude, double noiseStretch) {
		this.seaLevel = seaLevel;
		
		this.enableLargeSampler = enableLargeSampler;
		this.largeSamplerAmplitude = largeSamplerAmplitude;
		this.noiseStretch = noiseStretch;
	}

	public static class Builder {
		private int seaLevel = 63;
		
		private boolean enableLargeSampler = false;
		private double largeSamplerAmplitude = 0.0D;
		
		private double noiseStretch = 0.59D;

		public Builder seaLevel(int seaLevel) {
			this.seaLevel = seaLevel;
			return this;
		}
		
		public Builder enableLargeSampler(double amplitude) {
			this.enableLargeSampler = true;
			this.largeSamplerAmplitude = amplitude;
			return this;
		}
		
		public Builder noiseStretch(double stretch) {
			this.noiseStretch = stretch;
			return this;
		}

		public SuperCustomChunkGeneratorConfig build() {
			return new SuperCustomChunkGeneratorConfig(seaLevel, enableLargeSampler, largeSamplerAmplitude, noiseStretch * 100D);
		}
	}
}
