package io.github.vampirestudios.raa.material.factory;

import io.github.vampirestudios.raa.material.IMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

public class ArmorFactory implements IItemMaterialFactory {

	private EquipmentSlot type;

	public ArmorFactory(EquipmentSlot type) {
		this.type = type;
	}

	@Override
	public Item create(IMaterial material, String modid) {
		return new ArmorItem(material.getArmorMaterial(), type, material.getItemSettings().maxDamage(material.getDurability()))/*.setRegistryName(modid, material.getName() + getSuffix())*/;
	}

	@Override
	public String getSuffix() {
		return "_" + getName();
	}

	@Override
	public String getName() {
		switch (type) {
			case FEET:
				return "boots";
			case LEGS:
				return "leggings";
			case CHEST:
				return "chestplate";
			default:
				return "helmet";
		}
	}
}