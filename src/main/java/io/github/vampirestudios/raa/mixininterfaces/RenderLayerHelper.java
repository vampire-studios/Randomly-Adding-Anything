package io.github.vampirestudios.raa.mixininterfaces;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RenderLayers.class)
public interface RenderLayerHelper {
  @Accessor("BLOCKS")
  static Map<Block, RenderLayer> getBlockRenderLayers() {
    throw new AssertionError();
  }

  @Accessor("FLUIDS")
  static Map<Fluid, RenderLayer> getFluidRenderLayers() {
    throw new AssertionError();
  }

//  @Accessor
//  static void setDefaultBlockRenderLayer(final DefaultRenderLayer layer) {
//    throw new AssertionError();
//  }
//
//  @Accessor
//  static void setDefaultFluidRenderLayer(final DefaultRenderLayer layer) {
//    throw new AssertionError();
//  }
}