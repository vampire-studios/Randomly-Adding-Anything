package io.github.vampirestudios.raa.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public abstract class RandomEntityModel<T extends Entity> extends Model {
   public float handSwingProgress;
   public boolean riding;
   public boolean child;

   protected RandomEntityModel() {
      this(RenderLayer::getEntityCutoutNoCull);
   }

   protected RandomEntityModel(Function<Identifier, RenderLayer> function) {
      super(function);
      this.child = true;
   }

   public abstract void setAngles(T entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch);

   public void animateModel(T entity, float limbAngle, float limbDistance, float tickDelta) {
   }

   public void copyStateTo(RandomEntityModel<T> copy) {
      copy.handSwingProgress = this.handSwingProgress;
      copy.riding = this.riding;
      copy.child = this.child;
   }

}