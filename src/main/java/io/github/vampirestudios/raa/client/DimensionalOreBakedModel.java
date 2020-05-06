package io.github.vampirestudios.raa.client;

import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.generation.materials.DimensionMaterial;
import io.github.vampirestudios.raa.registries.Dimensions;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class DimensionalOreBakedModel extends RAABakedModel {

    private final DimensionMaterial dimensionMaterial;

    public DimensionalOreBakedModel(DimensionMaterial material) {
        super(material);
        this.dimensionMaterial = material;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh());
    }

    private Mesh mesh() {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        Identifier diemnsionId = new Identifier(dimensionMaterial.getId().getNamespace(), dimensionMaterial.getId().getPath().split("_")[0]);
        DimensionData dimensionData = Dimensions.DIMENSIONS.get(diemnsionId);

        RenderMaterial mat = renderer.materialFinder().disableAo(0, true).blendMode(0, BlendMode.CUTOUT_MIPPED).disableDiffuse(0, false).find();
        int color = Objects.requireNonNull(dimensionData).getDimensionColorPalette().getStoneColor();
        Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX)
                .apply(dimensionData.getTexturesInformation().getStoneTexture());

        emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.EAST, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.WEST, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.NORTH, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.DOWN, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();

        if (dimensionMaterial.isGlowing()) {
            mat = renderer.materialFinder().disableDiffuse(0, true).blendMode(0, BlendMode.CUTOUT_MIPPED).emissive(0, true).find();
        } else {
            mat = renderer.materialFinder().disableDiffuse(0, true).blendMode(0, BlendMode.CUTOUT_MIPPED).find();
        }
        color = dimensionMaterial.getColor();
        sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(this.dimensionMaterial.getTexturesInformation().getOverlayTexture());

        emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.EAST, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.WEST, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.NORTH, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.DOWN, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                .material(mat)
                .spriteColor(0, color, color, color, color)
                .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();

        return builder.build();
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh());
    }

    @Override
    public Sprite getSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(this.dimensionMaterial.getTexturesInformation().getOverlayTexture());
    }

    @Override
    public ModelOverrideList getOverrides() {
        if (FabricLoader.getInstance().isModLoaded("optifabric")) {
            return ItemProxy.EMPTY;
        } else {
            return new ItemProxy();
        }
    }

    protected class ItemProxy extends ModelOverrideList {
        public ItemProxy() {
            super(null, null, null, Collections.emptyList());
        }

        @Override
        public BakedModel apply(BakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity) {
            return DimensionalOreBakedModel.this;
        }

    }
}
