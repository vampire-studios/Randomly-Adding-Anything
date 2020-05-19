package io.github.vampirestudios.raa.client.render;

import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.utils.Color;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.class_5294;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public class RAASkyProperties {

    public static void init() {
        for (DimensionData dimensionData : Dimensions.DIMENSIONS) {
            RAADimensionSkyProperties skyProperties = new RAADimensionSkyProperties(dimensionData);
            class_5294.field_24609.put(Registry.DIMENSION_TYPE.get(dimensionData.getId()), skyProperties);
        }
    }


    public static class RAADimensionSkyProperties extends class_5294 {
        private DimensionData dimensionData;

        protected RAADimensionSkyProperties(DimensionData dimensionData) {
            super(dimensionData.getCloudHeight(), false, dimensionData.getCustomSkyInformation().hasSky());
            this.dimensionData = dimensionData;
        }

        @Override
        public Vec3d method_28112(Vec3d fogColor, float f) {
            if (Utils.checkBitFlag(this.dimensionData.getFlags(), Utils.LUCID)) {
            return fogColor.multiply(0.15000000596046448D);
        }
        int fogColor2 = this.dimensionData.getDimensionColorPalette().getFogColor();
        int[] rgbColor = Color.intToRgb(fogColor2);
        return new Vec3d(rgbColor[0] / 255.0, rgbColor[1] / 255.0, rgbColor[2] / 255.0);
        }

        @Override
        public boolean method_28110(int i, int j) {
            return this.dimensionData.hasThickFog();
        }

        @Override
        public boolean method_28113() {
            return !dimensionData.getDimensionChunkGenerator().equals(DimensionChunkGenerators.FLOATING) &&
                !dimensionData.getDimensionChunkGenerator().equals(DimensionChunkGenerators.LAYERED_FLOATING) &&
                !dimensionData.getDimensionChunkGenerator().equals(DimensionChunkGenerators.PRE_CLASSIC_FLOATING);
        }
    }
}
