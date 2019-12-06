package io.github.vampirestudios.raa.client;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.generation.materials.DimensionMaterial;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelItemPropertyOverrideList;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Random;
import java.util.function.Supplier;

public class DimensionOreBakedModel extends RAABakedModel {

    private DimensionMaterial dimensionMaterial;

    public DimensionOreBakedModel(DimensionMaterial material) {
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

        RenderMaterial mat = renderer.materialFinder().disableAo(0, false).blendMode(0, BlendMode.CUTOUT_MIPPED).disableDiffuse(0, false).find();
        int color = dimensionMaterial.getDimensionData().getDimensionColorPalette().getStoneColor();
        Sprite sprite;
        if (material.getOreInformation().getGeneratesIn() != GeneratesIn.DOES_NOT_APPEAR) {
            sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier(Registry.BLOCK.getId(material.getOreInformation()
                    .getGeneratesIn().getBlock()).getNamespace(), "block/" + Registry.BLOCK.getId(material.getOreInformation()
                    .getGeneratesIn().getBlock()).getPath()));
        } else {
            sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/oak_planks"));
        }

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

        if (material.isGlowing()) {
            mat = renderer.materialFinder().disableDiffuse(0, true).blendMode(0, BlendMode.CUTOUT_MIPPED).emissive(0, true).find();
        } else {
            mat = renderer.materialFinder().disableDiffuse(0, true).blendMode(0, BlendMode.CUTOUT_MIPPED).find();
        }
        color = material.getRGBColor();
        sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(this.material.getTexturesInformation().getOverlayTexture());

        if (material.getOreInformation().getGeneratesIn() != GeneratesIn.GRASS_BLOCK && material.getOreInformation().getGeneratesIn() != GeneratesIn.PODZOL) {
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
        } else {
            emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
        }

        return builder.build();
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh());
    }

    @Override
    public Sprite getSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(this.material.getTexturesInformation().getOverlayTexture());
    }

    @Override
    public ModelItemPropertyOverrideList getItemPropertyOverrides() {
        return new ItemProxy();
    }

    protected class ItemProxy extends ModelItemPropertyOverrideList {
        public ItemProxy() {
            super(null, null, null, Collections.emptyList());
        }

        @Override
        public BakedModel apply(BakedModel bakedModel_1, ItemStack itemStack_1, World world_1, LivingEntity livingEntity_1) {
            return DimensionOreBakedModel.this;
        }
    }
}
