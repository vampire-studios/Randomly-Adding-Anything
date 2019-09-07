package fr.arthurbambou.randomlyaddinganything.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public abstract class ArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
   protected static final Identifier SKIN = new Identifier("textures/misc/enchanted_item_glint.png");
   protected final A modelLeggings;
   protected final A modelBody;
   private float alpha = 1.0F;
   private float red = 1.0F;
   private float green = 1.0F;
   private float blue = 1.0F;
   private boolean ignoreGlint;
   private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.newHashMap();

   protected ArmorFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext_1, A bipedEntityModel_1, A bipedEntityModel_2) {
      super(featureRendererContext_1);
      this.modelLeggings = bipedEntityModel_1;
      this.modelBody = bipedEntityModel_2;
   }

   public void render(T livingEntity_1, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, float float_7) {
      this.renderArmor(livingEntity_1, float_1, float_2, float_3, float_4, float_5, float_6, float_7, EquipmentSlot.CHEST);
      this.renderArmor(livingEntity_1, float_1, float_2, float_3, float_4, float_5, float_6, float_7, EquipmentSlot.LEGS);
      this.renderArmor(livingEntity_1, float_1, float_2, float_3, float_4, float_5, float_6, float_7, EquipmentSlot.FEET);
      this.renderArmor(livingEntity_1, float_1, float_2, float_3, float_4, float_5, float_6, float_7, EquipmentSlot.HEAD);
   }

   public boolean hasHurtOverlay() {
      return false;
   }

   private void renderArmor(T livingEntity_1, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, float float_7, EquipmentSlot equipmentSlot_1) {
      ItemStack itemStack_1 = livingEntity_1.getEquippedStack(equipmentSlot_1);
      if (itemStack_1.getItem() instanceof ArmorItem) {
         ArmorItem armorItem_1 = (ArmorItem)itemStack_1.getItem();
         if (armorItem_1.getSlotType() == equipmentSlot_1) {
            A bipedEntityModel_1 = this.getArmor(equipmentSlot_1);
            this.getModel().setAttributes(bipedEntityModel_1);
            bipedEntityModel_1.method_17086(livingEntity_1, float_1, float_2, float_3);
            this.method_4170(bipedEntityModel_1, equipmentSlot_1);
            boolean boolean_1 = this.isLegs(equipmentSlot_1);
            this.bindTexture(this.getArmorTexture(armorItem_1, boolean_1));
            if (armorItem_1 instanceof DyeableArmorItem) {
               int int_1 = ((DyeableArmorItem)armorItem_1).getColor(itemStack_1);
               float float_8 = (float)(int_1 >> 16 & 255) / 255.0F;
               float float_9 = (float)(int_1 >> 8 & 255) / 255.0F;
               float float_10 = (float)(int_1 & 255) / 255.0F;
               GlStateManager.color4f(this.red * float_8, this.green * float_9, this.blue * float_10, this.alpha);
               bipedEntityModel_1.method_17088(livingEntity_1, float_1, float_2, float_4, float_5, float_6, float_7);
               this.bindTexture(this.method_4174(armorItem_1, boolean_1, "overlay"));
            }

            GlStateManager.color4f(this.red, this.green, this.blue, this.alpha);
            bipedEntityModel_1.method_17088(livingEntity_1, float_1, float_2, float_4, float_5, float_6, float_7);
            if (!this.ignoreGlint && itemStack_1.hasEnchantments()) {
               renderEnchantedGlint(this::bindTexture, livingEntity_1, bipedEntityModel_1, float_1, float_2, float_3, float_4, float_5, float_6, float_7);
            }

         }
      }
   }

   public A getArmor(EquipmentSlot equipmentSlot_1) {
      return this.isLegs(equipmentSlot_1) ? this.modelLeggings : this.modelBody;
   }

   private boolean isLegs(EquipmentSlot equipmentSlot_1) {
      return equipmentSlot_1 == EquipmentSlot.LEGS;
   }

   public static <T extends Entity> void renderEnchantedGlint(Consumer<Identifier> consumer_1, T entity_1, EntityModel<T> entityModel_1, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, float float_7) {
      float float_8 = (float)entity_1.age + float_3;
      consumer_1.accept(SKIN);
      GameRenderer gameRenderer_1 = MinecraftClient.getInstance().gameRenderer;
      gameRenderer_1.setFogBlack(true);
      GlStateManager.enableBlend();
      GlStateManager.depthFunc(514);
      GlStateManager.depthMask(false);
      float float_9 = 0.5F;
      GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);

      for(int int_1 = 0; int_1 < 2; ++int_1) {
         GlStateManager.disableLighting();
         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
         float float_10 = 0.76F;
         GlStateManager.color4f(0.38F, 0.19F, 0.608F, 1.0F);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         float float_11 = 0.33333334F;
         GlStateManager.scalef(0.33333334F, 0.33333334F, 0.33333334F);
         GlStateManager.rotatef(30.0F - (float)int_1 * 60.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.translatef(0.0F, float_8 * (0.001F + (float)int_1 * 0.003F) * 20.0F, 0.0F);
         GlStateManager.matrixMode(5888);
         entityModel_1.render(entity_1, float_1, float_2, float_4, float_5, float_6, float_7);
         GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      }

      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      GlStateManager.enableLighting();
      GlStateManager.depthMask(true);
      GlStateManager.depthFunc(515);
      GlStateManager.disableBlend();
      gameRenderer_1.setFogBlack(false);
   }

   private Identifier getArmorTexture(ArmorItem armorItem_1, boolean boolean_1) {
      return this.method_4174(armorItem_1, boolean_1, null);
   }

   private Identifier method_4174(ArmorItem armorItem_1, boolean boolean_1, String string_1) {
      Identifier identifier = new Identifier(RandomlyAddingAnything.MOD_ID, "textures/models/armor/" +
              armorItem_1.getMaterial().getName() + "_layer_" + (boolean_1 ? 2 : 1) + (string_1 == null ? "" : "_" + string_1) + ".png");
      return ARMOR_TEXTURE_CACHE.computeIfAbsent(identifier.toString(), Identifier::new);
   }

   protected abstract void method_4170(A var1, EquipmentSlot var2);

   protected abstract void method_4190(A var1);
}
