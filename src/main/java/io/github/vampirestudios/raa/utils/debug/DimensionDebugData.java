package io.github.vampirestudios.raa.utils.debug;

import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;

public class DimensionDebugData {

    private final DimensionData dimensionData;

    public DimensionDebugData(DimensionData dimensionData) {
        this.dimensionData = dimensionData;
    }

    public void print() {

        String stringBuilder = "\n\n Dimension Information" + "\n##########################" +
                "\n Dimension Name: " + dimensionData.getName() +
                "\n Has sky: " + dimensionData.getCustomSkyInformation().hasSky() +
                "\n Has skylight: " + dimensionData.getCustomSkyInformation().hasSkyLight() +
                "\n Can sleep: " + dimensionData.canSleep() +
                "\n Water vaporizes: " + dimensionData.doesWaterVaporize() +
                "\n Has thick fog: " + dimensionData.hasThickFog() +
                "\n\n Advanced Information" + "\n##########################" +
                "\n Dimension Identifier: " + dimensionData.getId().toString() +
                "\n Chunk Generator: " + dimensionData.getDimensionChunkGenerator().toString() +
//        stringBuilder.append("\n Surface Builder: ").append(dimensionData.getNewSurfaceBuilder().toString());

                "\n\n Color Pallet Information" + "\n##########################" +
                "\n Sky Color: " + Integer.toHexString(dimensionData.getDimensionColorPalette().getSkyColor()).replace("ff",
                "") +
                "\n Grass Color: " + Integer.toHexString(dimensionData.getDimensionColorPalette().getGrassColor()).replace("ff",
                "") +
                "\n Fog Color: " + Integer.toHexString(dimensionData.getDimensionColorPalette().getFogColor()).replace("ff",
                "") +
                "\n Foliage Color: " + Integer.toHexString(dimensionData.getDimensionColorPalette().getFoliageColor()).replace("ff",
                "") +
                "\n Stone Color: " + Integer.toHexString(dimensionData.getDimensionColorPalette().getStoneColor()).replace("ff",
                "") +
                "\n\n Texture Information" + "\n##########################" +
                "\n Stone Texture: " + dimensionData.getTexturesInformation().getStoneTexture().toString() +
                "\n Stone Bricks Texture: " + dimensionData.getTexturesInformation().getStoneBricksTexture().toString() +
                "\n Mossy Stone Bricks Texture: " + dimensionData.getTexturesInformation().getMossyStoneBricksTexture().toString() +
                "\n Cracked Stone Bricks Texture: " + dimensionData.getTexturesInformation().getCrackedStoneBricksTexture().toString() +
                "\n Cobblestone Texture: " + dimensionData.getTexturesInformation().getCobblestoneTexture().toString() +
                "\n Mossy Cobblestone Texture: " + dimensionData.getTexturesInformation().getMossyCobblestoneTexture().toString() +
                "\n Chiseled Texture: " + dimensionData.getTexturesInformation().getChiseledTexture().toString() +
                "\n Cracked Chiseled Texture: " + dimensionData.getTexturesInformation().getCrackedChiseledTexture().toString() +
                "\n Mossy Chiseled Texture: " + dimensionData.getTexturesInformation().getMossyChiseledTexture().toString() +
                "\n Polished Texture: " + dimensionData.getTexturesInformation().getPolishedTexture().toString() +
                "\n Ice Texture: " + dimensionData.getTexturesInformation().getIceTexture().toString();
        System.out.print(stringBuilder);
    }

}
