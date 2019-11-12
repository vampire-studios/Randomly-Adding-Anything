package io.github.vampirestudios.raa.generation.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class RandomEntity extends PassiveEntity {
    public static TrackedData<String> hairColorUnified = DataTracker.registerData(RandomEntity.class, TrackedDataHandlerRegistry.STRING);
    public static TrackedData<String> eyeColorUnified = DataTracker.registerData(RandomEntity.class, TrackedDataHandlerRegistry.STRING);
    public static TrackedData<String> skinColorUnified = DataTracker.registerData(RandomEntity.class, TrackedDataHandlerRegistry.STRING);
    public static TrackedData<Integer> hairStyleUnified = DataTracker.registerData(RandomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static TrackedData<String> serverUUID = DataTracker.registerData(RandomEntity.class, TrackedDataHandlerRegistry.STRING);
    private String hairColor;
    private String eyeColor;
    private String skinColor;
    private int hairStyle = 0;

    private RandomAspects randomAspects = new RandomAspects(this);

    public RandomEntity(EntityType<? extends PassiveEntity> type, World world) {
        super(type, world);
        ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(true);
        this.setCanPickUpLoot(true);
        if (hairColor == null || hairColor.equals("")) {
            unifiedSetup();
            this.dataTracker.set(hairColorUnified, hairColor);
            this.dataTracker.set(eyeColorUnified, eyeColor);
            this.dataTracker.set(skinColorUnified, skinColor);
            this.dataTracker.set(hairStyleUnified, hairStyle);
            this.dataTracker.set(serverUUID, this.getUuidAsString());
        }
    }

    @Override
    public PassiveEntity createChild(PassiveEntity passiveEntity) {
        return null;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, ZombieEntity.class, 8.0F, 0.6D, 0.6D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, EvokerEntity.class, 12.0F, 0.8D, 0.8D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, VindicatorEntity.class, 8.0F, 0.8D, 0.8D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, VexEntity.class, 8.0F, 0.6D, 0.6D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, PillagerEntity.class, 15.0F, 0.6D, 0.6D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, IllusionerEntity.class, 12.0F, 0.6D, 0.6D));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 0.6D));
        this.goalSelector.add(9, new GoToEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(9, new WanderAroundFarGoal(this, 0.6D));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(hairColorUnified, "Brown");
        this.dataTracker.startTracking(eyeColorUnified, "Black");
        this.dataTracker.startTracking(skinColorUnified, "Light");
        this.dataTracker.startTracking(hairStyleUnified, 1);
        this.dataTracker.startTracking(serverUUID, this.getUuidAsString());
    }

    public <T> T get(TrackedData<T> key) {
        return this.dataTracker.get(key);
    }

    public <T> void set(TrackedData<T> key, T value) {
        this.dataTracker.set(key, value);
    }

    private void setupHair() {
        this.hairStyle = randomAspects.getHairStyle();
        this.hairColor = randomAspects.getHairColor();
    }

    private void setupEyes() {
        this.eyeColor = randomAspects.getEyeColor();
    }

    private void setupSkin() {
        this.skinColor = randomAspects.getSkinColor();
    }

    public boolean canImmediatelyDespawn(double double_1) {
        return false;
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte byte_1) {
        if (byte_1 == 12) {
            this.produceParticles(ParticleTypes.HEART);
        } else if (byte_1 == 13) {
            this.produceParticles(ParticleTypes.ANGRY_VILLAGER);
        } else if (byte_1 == 14) {
            this.produceParticles(ParticleTypes.HAPPY_VILLAGER);
        } else if (byte_1 == 42) {
            this.produceParticles(ParticleTypes.SPLASH);
        } else {
            super.handleStatus(byte_1);
        }
    }

    @Environment(EnvType.CLIENT)
    private void produceParticles(ParticleEffect particleParameters_1) {
        for (int int_1 = 0; int_1 < 5; ++int_1) {
            double double_1 = this.random.nextGaussian() * 0.02D;
            double double_2 = this.random.nextGaussian() * 0.02D;
            double double_3 = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(particleParameters_1, this.getX() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
                    this.getY() + 1.0D + (double) (this.random.nextFloat() * this.getHeight()),
                    this.getZ() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), double_1, double_2, double_3);
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putString("hair_color", hairColor);
        tag.putString("eye_color", eyeColor);
        tag.putString("skin_color", skinColor);
        tag.putInt("hair_style", hairStyle);
        tag.putInt("age", this.getBreedingAge());
    }

    private void unifiedSetup() {
        this.setupSkin();
        this.setupEyes();
        this.setupHair();
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.dataTracker.set(hairColorUnified, hairColor);
        this.dataTracker.set(eyeColorUnified, eyeColor);
        this.dataTracker.set(skinColorUnified, skinColor);
        this.dataTracker.set(hairStyleUnified, hairStyle);
        this.dataTracker.set(serverUUID, this.getUuidAsString());
        if (hairColor == null || hairColor.equals("")) {
            unifiedSetup();
        }
        this.setBreedingAge(tag.getInt("age"));
        this.setCanPickUpLoot(true);
    }

}