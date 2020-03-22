package io.github.vampirestudios.raa.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;

public class RenderingUtils {

    public static void renderLucidSky(MinecraftClient client, Identifier texture, MatrixStack matrixStack) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        client.getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        for(int i = 0; i < 6; ++i) {
            matrixStack.push();
            if (i == 1) {
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            }
            if (i == 2) {
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
            }
            if (i == 3) {
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
            }
            if (i == 4) {
                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
            }
            if (i == 5) {
                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90.0F));
            }

            Matrix4f matrix4f = matrixStack.peek().getModel();
            bufferBuilder.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
            bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).color(255, 255, 255, 255).texture(0.0F, 0.0F).next();
            bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).color(255, 255, 255, 255).texture(0.0F, 1.0F).next();
            bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).color(255, 255, 255, 255).texture(1.0F, 1.0F).next();
            bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).color(255, 255, 255, 255).texture(1.0F, 0.0F).next();
            tessellator.draw();
            matrixStack.pop();
        }

        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

}
