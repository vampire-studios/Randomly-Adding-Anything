package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.generation.entity.RandomEntity;
import io.github.vampirestudios.raa.generation.entity.RandomEntityBuilder;
import io.github.vampirestudios.raa.generation.entity.RandomEntityData;
import io.github.vampirestudios.raa.utils.Color;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.vampirelib.utils.registry.EntityRegistryBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

public class Entities {

    public static EntityType<RandomEntity> RANDOM_ENTITY;
    public static final Set<Identifier> ENTITY_IDS = new HashSet<>();
    public static final Registry<RandomEntityData> ENTITIES = new DefaultedRegistry<>("raa:entities");

    public static boolean ready = false;

    public static void generate() {
        for (int i = 0; i < RandomlyAddingAnything.CONFIG.entitiesAmount; i++) {
            Color eggOne = new Color(Rands.randIntRange(0, 255), Rands.randIntRange(0, 255), Rands.randIntRange(0, 255));
            Color eggTwo = new Color(Rands.randIntRange(0, 255), Rands.randIntRange(0, 255), Rands.randIntRange(0, 255));
            INameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialNameGenerator();

            String name;
            Identifier id;
            do {
                name = nameGenerator.generate();
                id = new Identifier(RandomlyAddingAnything.MOD_ID, nameGenerator.asId(name));
            } while (ENTITY_IDS.contains(id));
            ENTITY_IDS.add(id);

            RandomEntityData randomEntityData = RandomEntityBuilder.create()
                    .colorOne(eggOne.getColor())
                    .colorTwo(eggTwo.getColor())
                    .id(id)
                    .trackingDistance(Rands.randIntRange(0, 100))
                    .updateIntervalTicks(Rands.randIntRange(0, 100))
                    .alwaysUpdateVelocity(Rands.chance(3))
                    .sizeX(Rands.randFloatRange(0.0F, 2.0F))
                    .sizeY(Rands.randFloatRange(0.0F, 2.0F))
                    .build();

            Registry.register(ENTITIES, id, randomEntityData);
        }
        ready = true;
    }

    public static boolean isReady() {
        return ready;
    }

    public static void createEntities() {
        ENTITIES.forEach(randomEntityData -> {
            Identifier identifier = randomEntityData.getId();
            RANDOM_ENTITY = EntityRegistryBuilder
                    .<RandomEntity>createBuilder(identifier)
                    .entity(RandomEntity::new)
                    .category(EntityCategory.CREATURE)
                    .hasEgg(true)
                    .egg(randomEntityData.colorOne, randomEntityData.colorTwo)
                    .dimensions(EntityDimensions.fixed(randomEntityData.sizeX, randomEntityData.sizeY))
                    .tracker(randomEntityData.trackingDistance, randomEntityData.updateIntervalTicks, randomEntityData.alwaysUpdateVelocity)
                    .build();
        });
    }

}