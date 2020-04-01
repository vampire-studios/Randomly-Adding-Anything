package io.github.vampirestudios.raa.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;

@Environment(EnvType.CLIENT)
public class RandomEntityModel<T extends ZombieEntity> extends AbstractZombieModel<T> {
    private float r;
    private float g;
    private float b;

    public RandomEntityModel(float f, boolean bl) {
        this(f, 0.0F, 64, bl ? 32 : 64);
    }

    protected RandomEntityModel(float f, float g, int i, int j) {
        super(f, g, i, j);
    }

    public boolean isAttacking(T zombieEntity) {
        return zombieEntity.isAttacking();
    }

    public void setRGB(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        super.render(matrices, vertices, light, overlay, red*r, green*g, blue*b, alpha);
    }
}