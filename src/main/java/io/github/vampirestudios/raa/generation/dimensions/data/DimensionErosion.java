package io.github.vampirestudios.raa.generation.dimensions.data;

public class DimensionErosion {
	private int octaves = 4;
	private double frequency = 128.0;
	private double amplitude_high = 6.0;
	private double amplitude_low = 8.0;

	private double base_noise = 0.1;
	private double threshold = 0.0;

	public DimensionErosion(int octaves, double frequency, double amplitude_high, double amplitude_low, double base_noise, double threshold) {
		this.octaves = octaves;
		this.frequency = frequency;
		this.amplitude_high = amplitude_high;
		this.amplitude_low = amplitude_low;
		this.base_noise = base_noise;
		this.threshold = threshold;
	}

	public int getOctaves() {
		return octaves;
	}

	public double getFrequency() {
		return frequency;
	}

	public double getAmplitudeHigh() {
		return amplitude_high;
	}

	public double getAmplitudeLow() {
		return amplitude_low;
	}

	public double getBaseNoise() {
		return base_noise;
	}

	public double getThreshold() {
		return threshold;
	}

	public static class Builder {
		private int octaves = 4;
		private double frequency = 128.0;
		private double amplitude_high = 6.0;
		private double amplitude_low = 8.0;

		private double base_noise = 0.1;
		private double threshold = 0.0;

		public static Builder create() {
			return new Builder();
		}

		public Builder octaves(int octaves) {
			this.octaves = octaves;
			return this;
		}

		public Builder frequency(double frequency) {
			this.frequency = frequency;
			return this;
		}

		public Builder amplitude_high(double amplitude_high) {
			this.amplitude_high = amplitude_high;
			return this;
		}

		public Builder amplitude_low(double amplitude_low) {
			this.amplitude_low = amplitude_low;
			return this;
		}

		public Builder base_noise(double base_noise) {
			this.base_noise = base_noise;
			return this;
		}

		public Builder threshold(double threshold) {
			this.threshold = threshold;
			return this;
		}

		public DimensionErosion build() {
			return new DimensionErosion(octaves, frequency, amplitude_high, amplitude_low, base_noise, threshold);
		}

	}

}