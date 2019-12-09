package io.github.vampirestudios.raa.utils.noise;

public enum NoiseType {
	SIMPLEX(OpenSimplexNoise.class)/*,
	WORLEY(WorleyNoise.class),
	PERLIN(PerlinNoise.class),
	VALUE(ValueNoise.class),
	CUBIC(CubicNoise.class)*/;

	public final Class<? extends Noise> noiseClass;

	NoiseType(Class<? extends Noise> noiseClass) {
		this.noiseClass = noiseClass;
	}

}