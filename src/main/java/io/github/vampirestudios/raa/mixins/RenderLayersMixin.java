package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.mixininterfaces.DefaultRenderLayer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderLayers.class)
abstract class RenderLayersMixin {

  private static DefaultRenderLayer defaultBlockRenderLayer = RenderLayer::getSolid;
  private static DefaultRenderLayer defaultFluidRenderLayer = RenderLayer::getSolid;

  @Redirect(method = "getBlockLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getSolid()Lnet/minecraft/client/render/RenderLayer;"))
  private static RenderLayer getDefaultBlockRenderLayer(final RenderLayer layer) {
    return defaultBlockRenderLayer.get();
  }

  @Redirect(method = "getFluidLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getSolid()Lnet/minecraft/client/render/RenderLayer;"))
  private static RenderLayer getDefaultFluidRenderLayer(final RenderLayer layer) {
    return defaultFluidRenderLayer.get();
  }

}