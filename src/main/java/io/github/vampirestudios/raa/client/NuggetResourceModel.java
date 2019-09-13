package io.github.vampirestudios.raa.client;

import io.github.vampirestudios.raa.materials.Material;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.indigo.renderer.helper.GeometryHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Random;
import java.util.function.Supplier;

public class NuggetResourceModel extends ItemBakedModel {

    private Identifier identifier;

    public NuggetResourceModel(Identifier identifier, Material material) {
        super(material);
        this.identifier = identifier;
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        BakedModelManager bakedModelManager = MinecraftClient.getInstance().getBakedModelManager();
        context.fallbackConsumer().accept(bakedModelManager.getModel(new ModelIdentifier(new Identifier(identifier.getNamespace(), identifier.getPath().replace("item/","") + "1"), "inventory")));
        int color = material.getRGBColor();
        context.pushTransform(quad -> {
            quad.nominalFace(GeometryHelper.lightFace(quad));
            quad.spriteColor(0, color, color, color, color);
            quad.spriteBake(0, getSprite(), MutableQuadView.BAKE_LOCK_UV);
            return true;
        });
        final QuadEmitter emitter = context.getEmitter();
        BakedModel model = bakedModelManager.getModel(new ModelIdentifier(new Identifier(identifier.getNamespace(), identifier.getPath().replace("item/","") + "1"), "inventory"));
        model.getQuads(null,null,randomSupplier.get()).forEach(bakedQuad -> {
            emitter.fromVanilla(bakedQuad.getVertexData(), 0, false);
            emitter.emit();
        });
        context.popTransform();
    }

    @Override
    public Sprite getSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas().getSprite(material.getNuggetTexture());
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelHelper.DEFAULT_ITEM_TRANSFORMS;
    }
}
