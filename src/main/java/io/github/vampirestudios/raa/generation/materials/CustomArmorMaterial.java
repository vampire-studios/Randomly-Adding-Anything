package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
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

    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final float toughness;
    private final int horseArmorBonus;
    private transient Identifier materialId;
    private transient OreType oreType;

    public CustomArmorMaterial(Identifier materialId, OreType oreType, int durabilityMultiplier, int[] protectionAmounts, int enchantability, float toughness, int horseArmorBonus) {
        this.materialId = materialId;
        this.oreType = oreType;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.toughness = toughness;
        this.horseArmorBonus = horseArmorBonus;
    }

    public static CustomArmorMaterial generate(Identifier materialId, OreType oreType) {
        return new CustomArmorMaterial(
                materialId, oreType, Rands.randIntRange(2, 50),
                new int[]{Rands.randIntRange(1, 6), Rands.randIntRange(1, 10),
                        Rands.randIntRange(2, 12), Rands.randIntRange(1, 6)},
                Rands.randIntRange(7, 30),
                (Rands.chance(4) ? Rands.randFloat(4.0F) : 0.0F),
                Rands.randInt(30)
        );
    }

    public void setMaterialId(Identifier materialId) {
        this.materialId = materialId;
    }

    public void setOreType(OreType oreType) {
        this.oreType = oreType;
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
        return Ingredient.ofItems(Registry.ITEM.get(Utils.appendToPath(materialId, oreType.getSuffix())));
    }

}
