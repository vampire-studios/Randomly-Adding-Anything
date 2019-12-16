package io.github.vampirestudios.raa.generation.chunkgenerator.config;

import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import supercoder79.simplexterrain.api.noise.NoiseType;
import supercoder79.simplexterrain.world.postprocess.PostProcessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomOverworldChunkGeneratorConfig extends ChunkGeneratorConfig {
    private boolean addDetailNoise = true;

    private boolean sacrificeAccuracyForSpeed = true;

    private NoiseType noiseGenerator = NoiseType.SIMPLEX;

    private List<PostProcessors> postProcessors = new ArrayList<>(Arrays.asList(PostProcessors.RIVERS, PostProcessors.SIMPLEX_CAVES));

    private int baseHeight = 100;

    private int baseOctaveAmount = 11;
    private int detailOctaveAmount = 2;
    private int scaleOctaveAmount = 2;
    private int peaksOctaveAmount = 2;

    private double baseNoiseFrequencyCoefficient = 0.75;
    private double baseNoiseSamplingFrequency = 1;

    private double detailAmplitudeHigh = 2;
    private double detailAmplitudeLow = 4;
    private double detailFrequency = 20;

    private double scaleAmplitudeHigh = 0.2;
    private double scaleAmplitudeLow = 0.09;
    private double scaleFrequencyExponent = 10;

    private double peaksFrequency = 750;
    private double peaksSampleOffset = -0.33;
    private double peaksAmplitude = 280;

    private double detailNoiseThreshold = 0.0;
    private double scaleNoiseThreshold = -0.02;

    private int lowlandStartHeight = 68;
    private int midlandStartHeight = 90;
    private int highlandStartHeight = 140;
    private int toplandStartHeight = 190;

    private int biomeScaleAmount = 7;

    private int seaLevel = 63;

    public boolean isDetailNoiseEnabled() {
        return addDetailNoise;
    }

    public void shouldAddDetailNoise(boolean addDetailNoise) {
        this.addDetailNoise = addDetailNoise;
    }

    public boolean isSacrificeAccuracyForSpeedEnabled() {
        return sacrificeAccuracyForSpeed;
    }

    public void shouldSacrificeAccuracyForSpeed(boolean sacrificeAccuracyForSpeed) {
        this.sacrificeAccuracyForSpeed = sacrificeAccuracyForSpeed;
    }

    public NoiseType getNoiseGenerator() {
        return noiseGenerator;
    }

    public void setNoiseGenerator(NoiseType noiseGenerator) {
        this.noiseGenerator = noiseGenerator;
    }

    public List<PostProcessors> getPostProcessors() {
        return postProcessors;
    }

    public void addPostProcessor(PostProcessors postProcessor) {
        this.postProcessors.add(postProcessor);
    }

    public int getBaseHeight() {
        return baseHeight;
    }

    public void setBaseHeight(int baseHeight) {
        this.baseHeight = baseHeight;
    }

    public int getBaseOctaveAmount() {
        return baseOctaveAmount;
    }

    public void setBaseOctaveAmount(int baseOctaveAmount) {
        this.baseOctaveAmount = baseOctaveAmount;
    }

    public int getDetailOctaveAmount() {
        return detailOctaveAmount;
    }

    public void setDetailOctaveAmount(int detailOctaveAmount) {
        this.detailOctaveAmount = detailOctaveAmount;
    }

    public int getScaleOctaveAmount() {
        return scaleOctaveAmount;
    }

    public void setScaleOctaveAmount(int scaleOctaveAmount) {
        this.scaleOctaveAmount = scaleOctaveAmount;
    }

    public int getPeaksOctaveAmount() {
        return peaksOctaveAmount;
    }

    public void setPeaksOctaveAmount(int peaksOctaveAmount) {
        this.peaksOctaveAmount = peaksOctaveAmount;
    }

    public double getBaseNoiseFrequencyCoefficient() {
        return baseNoiseFrequencyCoefficient;
    }

    public void setBaseNoiseFrequencyCoefficient(double baseNoiseFrequencyCoefficient) {
        this.baseNoiseFrequencyCoefficient = baseNoiseFrequencyCoefficient;
    }

    public double getBaseNoiseSamplingFrequency() {
        return baseNoiseSamplingFrequency;
    }

    public void setBaseNoiseSamplingFrequency(double baseNoiseSamplingFrequency) {
        this.baseNoiseSamplingFrequency = baseNoiseSamplingFrequency;
    }

    public double getDetailAmplitudeHigh() {
        return detailAmplitudeHigh;
    }

    public void setDetailAmplitudeHigh(double detailAmplitudeHigh) {
        this.detailAmplitudeHigh = detailAmplitudeHigh;
    }

    public double getDetailAmplitudeLow() {
        return detailAmplitudeLow;
    }

    public void setDetailAmplitudeLow(double detailAmplitudeLow) {
        this.detailAmplitudeLow = detailAmplitudeLow;
    }

    public double getDetailFrequency() {
        return detailFrequency;
    }

    public void setDetailFrequency(double detailFrequency) {
        this.detailFrequency = detailFrequency;
    }

    public double getScaleAmplitudeHigh() {
        return scaleAmplitudeHigh;
    }

    public void setScaleAmplitudeHigh(double scaleAmplitudeHigh) {
        this.scaleAmplitudeHigh = scaleAmplitudeHigh;
    }

    public double getScaleAmplitudeLow() {
        return scaleAmplitudeLow;
    }

    public void setScaleAmplitudeLow(double scaleAmplitudeLow) {
        this.scaleAmplitudeLow = scaleAmplitudeLow;
    }

    public double getScaleFrequencyExponent() {
        return scaleFrequencyExponent;
    }

    public void setScaleFrequencyExponent(double scaleFrequencyExponent) {
        this.scaleFrequencyExponent = scaleFrequencyExponent;
    }

    public double getPeaksFrequency() {
        return peaksFrequency;
    }

    public void setPeaksFrequency(double peaksFrequency) {
        this.peaksFrequency = peaksFrequency;
    }

    public double getPeaksSampleOffset() {
        return peaksSampleOffset;
    }

    public void setPeaksSampleOffset(double peaksSampleOffset) {
        this.peaksSampleOffset = peaksSampleOffset;
    }

    public double getPeaksAmplitude() {
        return peaksAmplitude;
    }

    public void setPeaksAmplitude(double peaksAmplitude) {
        this.peaksAmplitude = peaksAmplitude;
    }

    public double getDetailNoiseThreshold() {
        return detailNoiseThreshold;
    }

    public void setDetailNoiseThreshold(double detailNoiseThreshold) {
        this.detailNoiseThreshold = detailNoiseThreshold;
    }

    public double getScaleNoiseThreshold() {
        return scaleNoiseThreshold;
    }

    public void setScaleNoiseThreshold(double scaleNoiseThreshold) {
        this.scaleNoiseThreshold = scaleNoiseThreshold;
    }

    public int getLowlandStartHeight() {
        return lowlandStartHeight;
    }

    public void setLowlandStartHeight(int lowlandStartHeight) {
        this.lowlandStartHeight = lowlandStartHeight;
    }

    public int getMidlandStartHeight() {
        return midlandStartHeight;
    }

    public void setMidlandStartHeight(int midlandStartHeight) {
        this.midlandStartHeight = midlandStartHeight;
    }

    public int getHighlandStartHeight() {
        return highlandStartHeight;
    }

    public void setHighlandStartHeight(int highlandStartHeight) {
        this.highlandStartHeight = highlandStartHeight;
    }

    public int getToplandStartHeight() {
        return toplandStartHeight;
    }

    public void setToplandStartHeight(int toplandStartHeight) {
        this.toplandStartHeight = toplandStartHeight;
    }

    public int getBiomeScaleAmount() {
        return biomeScaleAmount;
    }

    public void setBiomeScaleAmount(int biomeScaleAmount) {
        this.biomeScaleAmount = biomeScaleAmount;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public int getMinY() {
        return 0;
    }

}
