package fr.arthurbambou.randomlyaddinganything.items;

import fr.arthurbambou.randomlyaddinganything.materials.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class RAASwordItem extends SwordItem {

    private Material material;

    public RAASwordItem(Material material, Settings settings) {
        super(material.getToolMaterial(), material.getToolMaterial().getSwordAttackSpeed(), material.getToolMaterial().getSwordAttackDamage(), settings);
        this.material = material;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.sword", new LiteralText(material.getName()));
    }

}
