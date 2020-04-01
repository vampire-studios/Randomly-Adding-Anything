package io.github.vampirestudios.raa.client.entity;

import io.github.vampirestudios.raa.entity.RandomEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RandomEntityRenderer extends MobEntityRenderer<RandomEntity, RandomEntityModel<RandomEntity>> {
    public RandomEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new RandomEntityModel<>(0.0F, false), 1);
    }

    @Override
    public Identifier getTexture(RandomEntity entity) {
        return new Identifier("textures/entity/zombie/zombie.png");
    }

    @Override
    protected void scale(RandomEntity entity, MatrixStack matrices, float amount) {
        super.scale(entity, matrices, amount);
        matrices.scale(entity.data.size, entity.data.size, entity.data.size);
    }

    @Override
    public void render(RandomEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model.setRGB(entity.data.r, entity.data.g, entity.data.b);

        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}