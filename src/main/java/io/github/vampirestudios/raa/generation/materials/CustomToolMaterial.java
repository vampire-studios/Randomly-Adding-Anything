package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.enums.OreType;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomToolMaterial implements ToolMaterial {

    private String name;
    private OreType oreType;
    private int durability;
    private float miningSpeed;
    private float attackDamage;
    private int miningLevel;
    private int enchantability;
    private float hoeAttackSpeed;
    private float axeAttackDamage;
    private float axeAttackSpeed;
    private float swordAttackDamage;

    public CustomToolMaterial(String name, OreType oreType, int durability, float miningSpeed, float attackDamage, int miningLevel,
                              int enchantability, float hoeAttackSpeed, float axeAttackDamage, float axeAttackSpeed, float swordAttackDamage) {
        this.name = name;
        this.oreType = oreType;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.hoeAttackSpeed = hoeAttackSpeed;
        this.axeAttackDamage = axeAttackDamage;
        this.axeAttackSpeed = axeAttackSpeed;
        this.swordAttackDamage = swordAttackDamage;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Deprecated
    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public float getMiningSpeed() {
        return miningSpeed;
    }

    @Deprecated
    public void setMiningSpeed(float miningSpeed) {
        this.miningSpeed = miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Deprecated
    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Deprecated
    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Deprecated
    public void setEnchantability(int enchantability) {
        this.enchantability = enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        if (oreType == OreType.CRYSTAL) {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", name.toLowerCase() + "_crystal")));
        } else if (oreType == OreType.GEM) {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", name.toLowerCase() + "_gem")));
        } else {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", name.toLowerCase() + "_crystal")));
        }
    }

    public float getHoeAttackSpeed() {
        return hoeAttackSpeed;
    }

    public float getAxeAttackDamage() {
        return axeAttackDamage;
    }

    public float getAxeAttackSpeed() {
        return axeAttackSpeed;
    }

    public float getSwordAttackDamage() {
        return swordAttackDamage;
    }

}
