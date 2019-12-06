package io.github.vampirestudios.raa.mixininterfaces;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RenderLayers.class)
public interface RenderLayerMaps {
  @Accessor("BLOCKS")
  static Map<Block, RenderLayer> blocks() {
    throw new AssertionError();
  }
  
  @Accessor("FLUIDS")
  static Map<Fluid, RenderLayer> fluids() {
    throw new AssertionError();
  }
}