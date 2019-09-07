package fr.arthurbambou.randomlyaddinganything.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class ArmorBipedFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends ArmorFeatureRenderer<T, M, A> {
   public ArmorBipedFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext_1, A bipedEntityModel_1, A bipedEntityModel_2) {
      super(featureRendererContext_1, bipedEntityModel_1, bipedEntityModel_2);
   }

   protected void method_4170(A bipedEntityModel_1, EquipmentSlot equipmentSlot_1) {
      this.method_4190(bipedEntityModel_1);
      switch(equipmentSlot_1) {
      case HEAD:
         bipedEntityModel_1.head.visible = true;
         bipedEntityModel_1.headwear.visible = true;
         break;
      case CHEST:
         bipedEntityModel_1.body.visible = true;
         bipedEntityModel_1.rightArm.visible = true;
         bipedEntityModel_1.leftArm.visible = true;
         break;
      case LEGS:
         bipedEntityModel_1.body.visible = true;
         bipedEntityModel_1.rightLeg.visible = true;
         bipedEntityModel_1.leftLeg.visible = true;
         break;
      case FEET:
         bipedEntityModel_1.rightLeg.visible = true;
         bipedEntityModel_1.leftLeg.visible = true;
      }

   }

   protected void method_4190(A bipedEntityModel_1) {
      bipedEntityModel_1.setVisible(false);
   }
}
