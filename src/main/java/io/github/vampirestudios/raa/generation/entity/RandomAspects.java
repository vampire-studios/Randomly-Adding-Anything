package io.github.vampirestudios.raa.generation.entity;

import java.util.Random;

//Handles anything related to the appearance of the social villager
public class RandomAspects {

    private static Random random = new Random();

    private String hairColor;
    private String eyeColor;
    private String skinColor;
    private int hairStyle = 0;

    private RandomEntity randomEntity;

    public RandomAspects(RandomEntity randomEntity) {
        this.randomEntity = randomEntity;
        setupHair();
        setupSkin();
        setupEyes();
    }

    public RandomAspects(String hairColor, String eyeColor, String skinColor, int hairStyle) {
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.skinColor = skinColor;
        this.hairStyle = hairStyle;
    }

    private void setupHair() {
        String[] maleHairList = {"Red", "Brown", "Black", "Blonde"};
        String[] femaleHairStyle = {"Red", "Brown", "Black", "Blonde", "Pink", "Ginger"};
        int[] styleList = {1, 2, 3, 4};
        this.hairStyle = styleList[random.nextInt(styleList.length)];
        this.hairColor = femaleHairStyle[random.nextInt(femaleHairStyle.length)];
    }

    private void setupEyes() {
        String[] eyeList = {"Black", "Blue", "Brown", "Green", "Lime", "Pink", "Yellow"};
        this.eyeColor = eyeList[random.nextInt(eyeList.length)];
    }

    private void setupSkin() {
        String[] skinList = {"Light", "Medium", "Dark"};
        this.skinColor = skinList[random.nextInt(skinList.length)];
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public int getHairStyle() {
        return hairStyle;
    }

}