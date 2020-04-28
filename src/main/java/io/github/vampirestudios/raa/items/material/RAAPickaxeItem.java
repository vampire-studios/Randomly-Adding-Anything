package io.github.vampirestudios.raa.items.material;

import com.google.gson.JsonElement;
import com.ibm.icu.text.MessageFormat;
import io.github.vampirestudios.raa.effects.MaterialEffects;
import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;
import java.util.Objects;

public class RAAPickaxeItem extends PickaxeItem {

    private Material material;

    public RAAPickaxeItem(Material material, ToolMaterial toolMaterial_1, int int_1, float float_1, Settings item$Settings_1) {
        super(toolMaterial_1, int_1, float_1, item$Settings_1);
        this.material = material;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        MessageFormat format = new MessageFormat(new TranslatableText("text.raa.item.pickaxe").asString());
        Object[] data = {WordUtils.capitalize(material.getName()), WordUtils.uncapitalize(material.getName()),
                WordUtils.uncapitalize(material.getName()).charAt(0), WordUtils.uncapitalize(material.getName()).charAt(material.getName().length() - 1)};
        return new LiteralText(format.format(data));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = Objects.requireNonNull(target).world;
        if (!world.isClient()) {
            for (Map.Entry<MaterialEffects, JsonElement> effect : material.getSpecialEffects().entrySet()) {
                effect.getKey().apply(world, target, attacker, effect.getValue());
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
