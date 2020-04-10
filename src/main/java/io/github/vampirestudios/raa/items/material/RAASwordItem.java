package io.github.vampirestudios.raa.items.material;

import com.google.gson.JsonElement;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.effects.MaterialEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;
import java.util.Objects;

public class RAASwordItem extends SwordItem {

    private Material material;

    public RAASwordItem(Material material, Settings settings) {
        super(material.getToolMaterial(), (int) material.getToolMaterial().getSwordAttackDamage(), 1.0F, settings);
        this.material = material;
    }

    @Override
    public Text getName(ItemStack itemStack_1) {
        return new TranslatableText("text.raa.item.sword", new LiteralText(WordUtils.capitalize(material.getName())));
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
