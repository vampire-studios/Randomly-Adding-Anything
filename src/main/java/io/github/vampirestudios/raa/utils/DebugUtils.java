package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.vampirelib.utils.Color;

public class DebugUtils {

    public static void dimensionDebug(DimensionData dimensionData) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("\nDimension Information")
//        .append("##########################");
        int[] fog = Color.intToRgb(dimensionData.getDimensionColorPalette().getFogColor());
        int[] grass = Color.intToRgb(dimensionData.getDimensionColorPalette().getGrassColor());
        int[] foliage = Color.intToRgb(dimensionData.getDimensionColorPalette().getFoliageColor());
        int[] sky = Color.intToRgb(dimensionData.getDimensionColorPalette().getSkyColor());
        int[] stone = Color.intToRgb(dimensionData.getDimensionColorPalette().getStoneColor());
        String text =
                "\n\nTechnical Dimension Information" +
                        "\n##########################" +
                        "\n   Name : " + dimensionData.getName() +
                        "\n   Biome Name : " + dimensionData.getBiomeData().get(0).getName() +
                        "\n   Dimension ID : " + dimensionData.getDimensionId() +
                        "\n\nDimension Design Information" +
                        "\n##########################" +
                        "\n   Fog Color : " + fog[0] + "," + fog[1] + "," + fog[2] +
                        "\n   Grass Color : " + grass[0] + "," + grass[1] + "," + grass[2] +
                        "\n   Foliage Color : " + foliage[0] + "," + foliage[1] + "," + foliage[2] +
                        "\n   Sky Color : " + sky[0] + "," + sky[1] + "," + sky[2] +
                        "\n   Stone Color : " + stone[0] + "," + stone[1] + "," + stone[2] +
                        "\n   Has Skylight : " + dimensionData.hasSkyLight() +
                        "\n   Has Sky : " + dimensionData.hasSky() +
                        "\n   Can Sleep : " + dimensionData.canSleep() + "\n";
        System.out.println(text);
    }

    public static void materialDebug(Material material, Color color) {
        String text =
                "\n\nTechnical Material Information" +
                        "\n##########################" +
                        "\nName : " + material.getName() +
                        "\nGenerate in : " + material.getOreInformation().getGeneratesIn().getIdentifier().toString() +
                        "\nOre Type : " + material.getOreInformation().getOreType().name().toLowerCase() +
                        "\n\nMaterial Design Information" +
                        "\n##########################" +
                        "\nMaterial Color : " + color.getRed() + "," + color.getGreen() + "," + color.getBlue() +
                        "\nOverlay Texture : " + material.getTexturesInformation().getOverlayTexture().toString() +
                        "\nResource Item Texture : " + material.getTexturesInformation().getResourceItemTexture().toString() +
                        "\nHas Armor : " + material.hasArmor() +
                        "\nHas Weapons : " + material.hasWeapons() +
                        "\nHas Tools : " + material.hasTools() +
                        "\nIs Glowing : " + material.isGlowing() +
                        "\nHas Ore Flower : " + material.hasOreFlower() +
                        "\nHas Food : " + material.hasFood() + "\n";
        System.out.println(text);
    }

}
