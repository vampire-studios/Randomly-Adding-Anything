package io.github.vampirestudios.raa.generation.dimensions.data;

public class DimensionCustomSkyInformation {

    private boolean hasSkyLight;
    private boolean hasSky;

    private boolean customSun;
    private float sunSize;
    private int sunTint;

    private boolean customMoon;
    private float moonSize;
    private int moonTint;

    public DimensionCustomSkyInformation(boolean hasSky, boolean hasSkyLight, boolean customSun, float sunSize, int sunTint, boolean customMoon, float moonSize, int moonTint) {
        this.hasSky = hasSky;
        this.hasSkyLight = hasSkyLight;
        this.customSun = customSun;
        this.sunSize = sunSize;
        this.sunTint = sunTint;
        this.customMoon = customMoon;
        this.moonSize = moonSize;
        this.moonTint = moonTint;
    }

    public boolean hasSkyLight() {
        return hasSkyLight;
    }

    public void setHasSkyLight(boolean hasSkyLight) {
        this.hasSkyLight = hasSkyLight;
    }

    public boolean hasSky() {
        return hasSky;
    }

    public void setHasSky(boolean hasSky) {
        this.hasSky = hasSky;
    }

    public boolean hasCustomSun() {
        return customSun;
    }

    public void shouldHaveCustomSun(boolean customSun) {
        this.customSun = customSun;
    }

    public float getSunSize() {
        return sunSize;
    }

    public void setSunSize(float sunSize) {
        this.sunSize = sunSize;
    }

    public int getSunTint() {
        return sunTint;
    }

    public void setSunTint(int sunTint) {
        this.sunTint = sunTint;
    }

    public boolean hasCustomMoon() {
        return customMoon;
    }

    public void shouldHaveCustomMoon(boolean customMoon) {
        this.customMoon = customMoon;
    }

    public float getMoonSize() {
        return moonSize;
    }

    public void setMoonSize(float moonSize) {
        this.moonSize = moonSize;
    }

    public int getMoonTint() {
        return moonTint;
    }

    public void setMoonTint(int moonTint) {
        this.moonTint = moonTint;
    }

    public static class Builder {

        private boolean hasSkyLight;
        private boolean hasSky;

        private boolean customSun;
        private float sunSize;
        private int sunTint;

        private boolean customMoon;
        private float moonSize;
        private int moonTint;

        public static Builder create() {
            return new Builder();
        }

        public Builder hasSkyLight(boolean hasSkyLight) {
            this.hasSkyLight = hasSkyLight;
            return this;
        }

        public Builder hasSky(boolean hasSky) {
            this.hasSky = hasSky;
            return this;
        }

        public Builder customSun(boolean customSun) {
            this.customSun = customSun;
            return this;
        }

        public Builder sunSize(float sunSize) {
            this.sunSize = sunSize;
            return this;
        }

        public Builder sunTint(int sunTint) {
            this.sunTint = sunTint;
            return this;
        }

        public Builder customMoon(boolean customMoon) {
            this.customMoon = customMoon;
            return this;
        }

        public Builder moonSize(float moonSize) {
            this.moonSize = moonSize;
            return this;
        }

        public Builder moonTint(int moonTint) {
            this.moonTint = moonTint;
            return this;
        }

        public DimensionCustomSkyInformation build() {
            return new DimensionCustomSkyInformation(hasSky, hasSkyLight, customSun, sunSize, sunTint, customMoon, moonSize, moonTint);
        }

    }

}
