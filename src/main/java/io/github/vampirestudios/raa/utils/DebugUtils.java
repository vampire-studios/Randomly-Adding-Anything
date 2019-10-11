package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.generation.materials.Material;

public class DebugUtils {

    public static void dimensionDebug(DimensionData dimensionData, Color fogColor, Color grassColor, Color foliageColor, Color skyColor, Color stoneColor) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("\nDimension Information")
//        .append("##########################");
        String text =
                "\n\nTechnical Dimension Information" +
                "\n##########################" +
                "\n   Name : " + dimensionData.getName() +
                "\n   Biome Name : " + dimensionData.getBiomeData().getName() +
                "\n   Dimension ID : " + dimensionData.getDimensionId() +
                "\n\nDimension Design Information" +
                "\n##########################" +
                "\n   Fog Color : " + fogColor.getRed() + "," + fogColor.getGreen() + "," + fogColor.getBlue() +
                "\n   Grass Color : " + grassColor.getRed() + "," + grassColor.getGreen() + "," + grassColor.getBlue() +
                "\n   Foliage Color : " + foliageColor.getRed() + "," + foliageColor.getGreen() + "," + foliageColor.getBlue() +
                "\n   Sky Color : " + skyColor.getRed() + "," + skyColor.getGreen() + "," + skyColor.getBlue() +
                "\n   Stone Color : " + stoneColor.getRed() + "," + stoneColor.getGreen() + "," + stoneColor.getBlue() +
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
                "\nGenerate in : " + material.getOreInformation().getGenerateIn().name().toLowerCase() +
                "\nOre Type : " + material.getOreInformation().getOreType().name().toLowerCase() +
                "\n\nMaterial Design Information" +
                "\n##########################" +
                "\nMaterial Color : " + color.getRed() + "," + color.getGreen() + "," + color.getBlue() +
                "\nOverlay Texture : " + material.getOreInformation().getOverlayTexture().toString() +
                "\nResource Item Texture : " + material.getResourceItemTexture().toString() +
                "\nHas Armor : " + material.hasArmor() +
                "\nHas Weapons : " + material.hasWeapons() +
                "\nHas Tools : " + material.hasTools() +
                "\nIs Glowing : " + material.isGlowing() +
                "\nHas Ore Flower : " + material.hasOreFlower() +
                "\nHas Food : " + material.hasFood() + "\n";
        System.out.println(text);
    }

}
