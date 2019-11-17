package io.github.vampirestudios.raa.items;

import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

public class RAAArmorItem extends DyeableArmorItem {

    private Material material;
    private EquipmentSlot equipmentSlot_1;

    public RAAArmorItem(Material material, EquipmentSlot equipmentSlot_1, Settings item$Settings_1) {
        super(material.getArmorMaterial(), equipmentSlot_1, item$Settings_1);
        this.material = material;
        this.equipmentSlot_1 = equipmentSlot_1;
    }

    @Override
    public int getColor(ItemStack stack) {
        return ((RAAArmorItem) stack.getItem()).material.getRGBColor();
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.armor_" + this.equipmentSlot_1.getName(), new LiteralText(WordUtils.capitalize(material.getName())));
    }

}
