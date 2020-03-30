package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import io.github.vampirestudios.raa.utils.RenderingUtils;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow @Final private MinecraftClient client;
    private static final Identifier LUCID_SKY = new Identifier("raa:textures/environment/lucid_sky.png");

    @Inject(method = "renderSky", at=@At("HEAD"))
    public void onRenderSky(MatrixStack matrixStack, float f, CallbackInfo info) {
        if (this.client.world.dimension instanceof CustomDimension) {
            if (Utils.checkBitFlag(((CustomDimension)this.client.world.dimension).getDimensionData().getFlags(), Utils.LUCID))
                RenderingUtils.renderLucidSky(this.client, LUCID_SKY, matrixStack);
        }
    }



}
