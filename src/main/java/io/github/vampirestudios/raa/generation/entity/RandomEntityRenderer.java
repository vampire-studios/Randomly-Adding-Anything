package io.github.vampirestudios.raa.generation.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class RandomEntityRenderer extends MobEntityRenderer<RandomEntity, PlayerEntityModel<RandomEntity>> {

    public RandomEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new PlayerEntityModel<>(0.0F, true), 0.5F);
    }

    @Override
    public Identifier getTexture(RandomEntity entity) {
        /*if (this.getRenderManager().textureManager.getTexture(new Identifier("minecraft:dynamic/" + entity.getDataTracker().get(RandomEntity.serverUUID) + "_1")) != null) {
            return new Identifier("minecraft:dynamic/" + entity.getDataTracker().get(RandomEntity.serverUUID) + "_1");
        }
        BufferedImage imageBase = new TextureAssembler(entity).createTexture();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(imageBase, "png", stream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        InputStream is = new ByteArrayInputStream(stream.toByteArray());
        NativeImage base = null;
        try {
            base = NativeImage.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        NativeImageBackedTexture texture = new NativeImageBackedTexture(base);
        return this.getRenderManager().textureManager.registerDynamicTexture(entity.getDataTracker().get(RandomEntity.serverUUID), texture);*/
        return new Identifier("textures/entity/steve.png");
    }

    @Override
    protected void scale(RandomEntity livingEntity_1, MatrixStack matrixStack_1, float float_1) {
        float float_2 = 0.9375F;
        if (livingEntity_1.isBaby()) {
            float_2 = (float) ((double) float_2 * 0.5D);
            this.field_4673 = 0.25F;
        } else {
            this.field_4673 = 0.5F;
        }

        GlStateManager.scalef(float_2, float_2, float_2);
    }

}
