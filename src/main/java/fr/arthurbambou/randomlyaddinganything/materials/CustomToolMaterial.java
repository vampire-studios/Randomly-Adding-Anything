package fr.arthurbambou.randomlyaddinganything.materials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;

public class CustomToolMaterial implements ToolMaterial {

    private Ingredient repairIngredient;
    private int durability;
    private float miningSpeed;
    private float attackDamage;
    private int miningLevel;

    public CustomToolMaterial(Ingredient repairIngredient, int durability, float miningSpeed, float attackDamage, int miningLevel) {
        this.repairIngredient = repairIngredient;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeed() {
        return miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public int getEnchantability() {
        return ToolMaterials.GOLD.getEnchantability();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

}
