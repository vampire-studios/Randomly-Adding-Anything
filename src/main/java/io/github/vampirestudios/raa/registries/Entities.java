package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.config.EntitiesConfig;
import io.github.vampirestudios.raa.entity.EntityData;
import io.github.vampirestudios.raa.entity.RandomEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.ArrayList;
import java.util.List;

public class Entities {
    public static SimpleRegistry<EntityType<?>> RANDOM_ZOMBIES = new SimpleRegistry<>();

    public static void init() {
        //register each entity
        for (EntityData entity : EntitiesConfig.ENTITIES) {
            RANDOM_ZOMBIES.add(EntitiesConfig.ENTITIES.getId(entity), Registry.register(
                    Registry.ENTITY_TYPE,
                    EntitiesConfig.ENTITIES.getId(entity),
                    FabricEntityTypeBuilder.create(EntityCategory.AMBIENT,
                            (e, w) -> new RandomEntity(EntitiesConfig.ENTITIES.getId(entity), w)
                    ).size(EntityDimensions.fixed(1, 2)).build()
            ));
        }
    }
}