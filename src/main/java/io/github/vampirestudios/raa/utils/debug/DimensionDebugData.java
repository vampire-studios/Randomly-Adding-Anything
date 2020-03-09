package io.github.vampirestudios.raa.utils.debug;

import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;

public class DimensionDebugData {

    private DimensionData dimensionData;

    public DimensionDebugData(DimensionData dimensionData) {
        this.dimensionData = dimensionData;
    }

    public void print() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n\n Dimension Information").append("\n##########################");
        stringBuilder.append("\n Dimension Name: ").append(dimensionData.getName());
        stringBuilder.append("\n Has sky: ").append(dimensionData.hasSky());
        stringBuilder.append("\n Has skylight: ").append(dimensionData.hasSkyLight());
        stringBuilder.append("\n Can sleep: ").append(dimensionData.canSleep());
        stringBuilder.append("\n Water vaporizes: ").append(dimensionData.doesWaterVaporize());
        stringBuilder.append("\n Has thick fog: ").append(dimensionData.hasThickFog());

        stringBuilder.append("\n\n Advanced Information").append("\n##########################");
        stringBuilder.append("\n Dimension Identifier: ").append(dimensionData.getId().toString());
        stringBuilder.append("\n Chunk Generator: ").append(dimensionData.getDimensionChunkGenerator().toString());
//        stringBuilder.append("\n Surface Builder: ").append(dimensionData.getNewSurfaceBuilder().toString());

        stringBuilder.append("\n\n Color Pallet Information").append("\n##########################");
        stringBuilder.append("\n Sky Color: ").append(Integer.toHexString(dimensionData.getDimensionColorPalette().getSkyColor()).replace("ff",
                ""));
        stringBuilder.append("\n Grass Color: ").append(Integer.toHexString(dimensionData.getDimensionColorPalette().getGrassColor()).replace("ff",
                ""));
        stringBuilder.append("\n Fog Color: ").append(Integer.toHexString(dimensionData.getDimensionColorPalette().getFogColor()).replace("ff",
                ""));
        stringBuilder.append("\n Foliage Color: ").append(Integer.toHexString(dimensionData.getDimensionColorPalette().getFoliageColor()).replace("ff",
                ""));
        stringBuilder.append("\n Stone Color: ").append(Integer.toHexString(dimensionData.getDimensionColorPalette().getStoneColor()).replace("ff",
                ""));

        stringBuilder.append("\n\n Texture Information").append("\n##########################");
        stringBuilder.append("\n Stone Texture: ").append(dimensionData.getTexturesInformation().getStoneTexture().toString());
        stringBuilder.append("\n Stone Bricks Texture: ").append(dimensionData.getTexturesInformation().getStoneBricksTexture().toString());
        stringBuilder.append("\n Mossy Stone Bricks Texture: ").append(dimensionData.getTexturesInformation().getMossyStoneBricksTexture().toString());
        stringBuilder.append("\n Cracked Stone Bricks Texture: ").append(dimensionData.getTexturesInformation().getCrackedStoneBricksTexture().toString());
        stringBuilder.append("\n Cobblestone Texture: ").append(dimensionData.getTexturesInformation().getCobblestoneTexture().toString());
        stringBuilder.append("\n Mossy Cobblestone Texture: ").append(dimensionData.getTexturesInformation().getMossyCobblestoneTexture().toString());
        stringBuilder.append("\n Chiseled Texture: ").append(dimensionData.getTexturesInformation().getChiseledTexture().toString());
        stringBuilder.append("\n Cracked Chiseled Texture: ").append(dimensionData.getTexturesInformation().getCrackedChiseledTexture().toString());
        stringBuilder.append("\n Mossy Chiseled Texture: ").append(dimensionData.getTexturesInformation().getMossyChiseledTexture().toString());
        stringBuilder.append("\n Polished Texture: ").append(dimensionData.getTexturesInformation().getPolishedTexture().toString());
        stringBuilder.append("\n Ice Texture: ").append(dimensionData.getTexturesInformation().getIceTexture().toString());

        System.out.print(stringBuilder);
    }

}
