package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.registries.Materials;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomArmorMaterial implements ArmorMaterial {

    private final String name;
    private final OreType oreType;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final float toughness;
    private final int horseArmorBonus;

    public CustomArmorMaterial(String name, OreType oreType, int durabilityMultiplier, int[] protectionAmounts, int enchantability, float toughness, int horseArmorBonus) {
        this.name = name;
        this.oreType = oreType;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.toughness = toughness;
        this.horseArmorBonus = horseArmorBonus;
    }

    public int getDurability(EquipmentSlot equipmentSlot_1) {
        return Materials.BASE_DURABILITY[equipmentSlot_1.getEntitySlotId()] * this.durabilityMultiplier;
    }

    public int getProtectionAmount(EquipmentSlot equipmentSlot_1) {
        return this.protectionAmounts[equipmentSlot_1.getEntitySlotId()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Environment(EnvType.CLIENT)
    public String getName() {
        return "raa";
    }

    public float getToughness() {
        return this.toughness;
    }

    public int getHorseArmorBonus() {
        return horseArmorBonus;
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

}
