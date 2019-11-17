package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomToolMaterial implements ToolMaterial {

    private transient Identifier materialId;
    private transient OreType oreType;
    private int durability;
    private float miningSpeed;
    private float attackDamage;
    private int miningLevel;
    private int enchantability;
    private float hoeAttackSpeed;
    private float axeAttackDamage;
    private float axeAttackSpeed;
    private float swordAttackDamage;

    public CustomToolMaterial(Identifier materialId, OreType oreType, int durability, float miningSpeed, float attackDamage, int miningLevel,
                              int enchantability, float hoeAttackSpeed, float axeAttackDamage, float axeAttackSpeed, float swordAttackDamage) {
        this.materialId = materialId;
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

    public static CustomToolMaterial generate(Identifier materialId, OreType oreType, int miningLevel) {
        return new CustomToolMaterial(materialId, oreType,
                Rands.randIntRange(15, 2000), Rands.randFloat(4.0F) + 1.5F,
                Rands.randFloat(3.0F), miningLevel,
                Rands.randIntRange(2, 10), Rands.randFloat(4.0F),
                Rands.randFloat(3.0F), Rands.randFloat(0.8F),
                Rands.randFloat(5.0F));
    }

    public void setMaterialId(Identifier materialId) {
        this.materialId = materialId;
    }

    public void setOreType(OreType oreType) {
        this.oreType = oreType;
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
        return Ingredient.ofItems(Registry.ITEM.get(Utils.appendToPath(materialId, oreType.getSuffix())));
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
