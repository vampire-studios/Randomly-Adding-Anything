package fr.arthurbambou.randomlyaddinganything.mixins.client;

import fr.arthurbambou.randomlyaddinganything.client.ArmorBipedFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher_1, PlayerEntityModel<AbstractClientPlayerEntity> entityModel_1, float float_1) {
        super(entityRenderDispatcher_1, entityModel_1, float_1);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;)V", at=@At("RETURN"))
    private void onInit(EntityRenderDispatcher entityRenderDispatcher, CallbackInfo callbackInfoReturnable) {
        this.addFeature(new ArmorBipedFeatureRenderer<>(this, new BipedEntityModel<>(0.5F), new BipedEntityModel<>(1.0F)));
    }

    @Override
    protected Identifier getTexture(AbstractClientPlayerEntity var1) {
        return null;
    }

}