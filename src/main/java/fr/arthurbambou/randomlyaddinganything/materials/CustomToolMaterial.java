package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomToolMaterial implements ToolMaterial {

    private Material material;
    private int durability;
    private float miningSpeed;
    private float attackDamage;
    private int miningLevel;
    private int enchantability;
    private float hoeAttackSpeed;
    private float axeAttackDamage;
    private float axeAttackSpeed;

    public CustomToolMaterial(Material material, int durability, float miningSpeed, float attackDamage, int miningLevel,
                              int enchantability, float hoeAttackSpeed, float axeAttackDamage, float axeAttackSpeed) {
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.hoeAttackSpeed = hoeAttackSpeed;
        this.axeAttackDamage = axeAttackDamage;
        this.axeAttackSpeed = axeAttackSpeed;
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
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        if (material.getOreInformation().getOreType() == OreTypes.CRYSTAL) {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", material.getName().toLowerCase() + "_crystal")));
        } else if (material.getOreInformation().getOreType() == OreTypes.GEM) {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", material.getName().toLowerCase() + "_gem")));
        } else {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", material.getName().toLowerCase() + "_crystal")));
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
}
