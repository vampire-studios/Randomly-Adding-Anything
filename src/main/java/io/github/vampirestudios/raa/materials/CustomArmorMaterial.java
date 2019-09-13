package io.github.vampirestudios.raa.materials;

import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.api.enums.OreTypes;
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
    private final OreTypes oreTypes;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final float toughness;
    private final int horseArmorBonus;

    public CustomArmorMaterial(String name, OreTypes oreTypes, int int_1, int[] ints_1, int int_2, float float_1, int horseArmorBonus) {
        this.name = name;
        this.oreTypes = oreTypes;
        this.durabilityMultiplier = int_1;
        this.protectionAmounts = ints_1;
        this.enchantability = int_2;
        this.toughness = float_1;
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
        if (oreTypes == OreTypes.CRYSTAL) {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", name.toLowerCase() + "_crystal")));
        } else if (oreTypes == OreTypes.GEM) {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", name.toLowerCase() + "_gem")));
        } else {
            return Ingredient.ofItems(Registry.ITEM.get(new Identifier("raa", name.toLowerCase() + "_crystal")));
        }
    }

}
