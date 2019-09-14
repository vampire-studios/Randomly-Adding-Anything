package io.github.vampirestudios.raa.material;

import io.github.vampirestudios.raa.blocks.OreBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.function.Predicate;

public interface IMaterial {

    ToolMaterial getToolMaterial();

    ArmorMaterial getArmorMaterial();

    Item.Settings getItemSettings();

    Block.Settings getBlockSettings();

    OreBlockSettings getOreBlockSettings();

    float getAttackSpeed(String name);

    float getAttackDamage(String name);

    String getName();

    Predicate<IMaterialFactory<?>> matches();

    default int getDurability() {
        return 0;
    }

    // This is a necessary evil... thanks stairs
    default BlockState getDecoBlockstate() {
        return null;
    }

}
