package fr.arthurbambou.randomlyaddinganything.materials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class CustomArmorMaterial implements ArmorMaterial {

    private Ingredient repairIngredient;
    private String name;

    public CustomArmorMaterial(Ingredient repairIngredient, String name) {
        this.repairIngredient = repairIngredient;
        this.name = name;
    }

    @Override
    public int getDurability(EquipmentSlot var1) {
        return ArmorMaterials.LEATHER.getDurability(var1);
    }

    @Override
    public int getProtectionAmount(EquipmentSlot var1) {
        return ArmorMaterials.LEATHER.getProtectionAmount(var1);
    }

    @Override
    public int getEnchantability() {
        return ArmorMaterials.LEATHER.getEnchantability();
    }

    @Override
    public SoundEvent getEquipSound() {
        return ArmorMaterials.LEATHER.getEquipSound();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return ArmorMaterials.LEATHER.getToughness();
    }

}
