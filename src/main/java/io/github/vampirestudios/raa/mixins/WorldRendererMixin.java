package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.registries.Testing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow @Final private MinecraftClient client;
    @Shadow private ClientWorld world;

    @Inject(method = "renderSky", at = @At("HEAD"))
    public void onRenderSkyPre(MatrixStack matrixStack, float f, CallbackInfo info) {
        Testing.renderCustomSky(matrixStack, client, this.world.getDimension());
    }

}
