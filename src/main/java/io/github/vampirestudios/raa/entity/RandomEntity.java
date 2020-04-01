package io.github.vampirestudios.raa.entity;

import io.github.vampirestudios.raa.config.EntitiesConfig;
import io.github.vampirestudios.raa.registries.Entities;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

// currently just a recolored zombie
public class RandomEntity extends ZombieEntity {
    public final EntityData data;

    public RandomEntity(Identifier dataID, World world) {
        super((EntityType<? extends ZombieEntity>) Entities.RANDOM_ZOMBIES.get(dataID), world);

        this.data = EntitiesConfig.ENTITIES.get(dataID);

        if (data.runFromSun) {
            this.goalSelector.add(2, new AvoidSunlightGoal(this));
            this.goalSelector.add(3, new EscapeSunlightGoal(this, data.attackSpeed));
        }

        this.goalSelector.add(2, new ZombieAttackGoal(this, data.attackSpeed, false));
        this.goalSelector.add(6, new MoveThroughVillageGoal(this, data.attackSpeed, true, 4, this::canBreakDoors));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge(ZombifiedPiglinEntity.class));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new FollowTargetGoal<>(this, AbstractTraderEntity.class, false));
        this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(5, new FollowTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
    }

    @Override
    protected void initCustomGoals() {
        //TODO: proper entity data storage
    }
}
