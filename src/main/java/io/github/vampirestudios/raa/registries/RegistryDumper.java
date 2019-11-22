package io.github.vampirestudios.raa.registries;

import net.minecraft.block.Block;
import net.minecraft.text.LiteralText;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class RegistryDumper {

    public static void init() {
        System.out.println("Blocks");
        Registry.BLOCK.forEach(block -> {
            RegistryInformation information = new RegistryInformation(block.getName(), Registry.BLOCK.getId(block), Registry.BLOCK.getRawId(block));
            System.out.println(information.toString());
        });
        System.out.println("Items");
        Registry.ITEM.forEach(item -> {
            RegistryInformation information = new RegistryInformation(item.getName(), Registry.ITEM.getId(item), Registry.ITEM.getRawId(item));
            System.out.println(information.toString());
        });
        System.out.println("Entity types");
        Registry.ENTITY_TYPE.forEach(item -> {
            RegistryInformation information = new RegistryInformation(item.getName(), Registry.ENTITY_TYPE.getId(item), Registry.ENTITY_TYPE.getRawId(item));
            System.out.println(information.toString());
        });
    }

}
