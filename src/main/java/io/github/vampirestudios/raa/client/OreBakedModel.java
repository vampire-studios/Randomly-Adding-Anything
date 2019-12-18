package io.github.vampirestudios.raa.client;

import io.github.vampirestudios.raa.api.RAARegisteries;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.CustomTargets;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
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
import net.minecraft.client.color.block.BlockColorProvider;
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
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class OreBakedModel extends RAABakedModel {

    public OreBakedModel(Material material) {
        super(material);
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh(blockView, pos));
    }

    private Mesh mesh(BlockRenderView blockView, BlockPos pos) {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        RenderMaterial mat = renderer.materialFinder().disableAo(0, false).blendMode(0, BlendMode.CUTOUT_MIPPED).disableDiffuse(0, false).find();
        int color = 0xFFFFFFFF;
        Sprite sprite;
        if (material.getOreInformation().getTargetId() != CustomTargets.DOES_NOT_APPEAR.getId()) {
            sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier(Registry.BLOCK.getId(Objects.
                    requireNonNull(RAARegisteries.TARGET_REGISTRY.get(material.getOreInformation().getTargetId())).getBlock()).getNamespace(), "block/" +
                    Registry.BLOCK.getId(Objects.requireNonNull(RAARegisteries.TARGET_REGISTRY.get(material.getOreInformation().getTargetId())).getBlock()).getPath()));
        } else {
            sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/oak_planks"));
        }

        if (material.getOreInformation().getTargetId() != CustomTargets.GRASS_BLOCK.getId() && material.getOreInformation().getTargetId() != CustomTargets.PODZOL.getId() &&
                material.getOreInformation().getTargetId() != CustomTargets.SANDSTONE.getId() && material.getOreInformation().getTargetId() != CustomTargets.RED_SANDSTONE.getId()) {
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
        } else if (material.getOreInformation().getTargetId() == CustomTargets.GRASS_BLOCK.getId()) {
            mat = renderer.materialFinder().disableDiffuse(0, false).blendMode(0, BlendMode.CUTOUT_MIPPED).find();
            Sprite sideSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/grass_block_side"));
            Sprite sideOverlaySprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/grass_block_side_overlay"));
            Sprite topSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/grass_block_top"));
            Sprite bottomSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/dirt"));
            int color2 = 0xffffff;
            BlockColorProvider blockColor = ColorProviderRegistry.BLOCK.get(Objects.requireNonNull(RAARegisteries.TARGET_REGISTRY.get(material.getOreInformation().getTargetId())).getBlock());
            if (blockColor != null) {
                color2 = 0xff000000 | blockColor.getColor(Objects.requireNonNull(RAARegisteries.TARGET_REGISTRY.get(material.getOreInformation().getTargetId())).getBlock().getDefaultState(), blockView, pos, 1);
            }

            emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.EAST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.WEST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.NORTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.DOWN, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, bottomSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color2, color2, color2, color2)
                    .spriteBake(0, topSprite, MutableQuadView.BAKE_LOCK_UV).emit();

            emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color2, color2, color2, color2)
                    .spriteBake(0, sideOverlaySprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.EAST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color2, color2, color2, color2)
                    .spriteBake(0, sideOverlaySprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.WEST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color2, color2, color2, color2)
                    .spriteBake(0, sideOverlaySprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.NORTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color2, color2, color2, color2)
                    .spriteBake(0, sideOverlaySprite, MutableQuadView.BAKE_LOCK_UV).emit();
        } else if (material.getOreInformation().getTargetId() == CustomTargets.SANDSTONE.getId()) {
            mat = renderer.materialFinder().disableDiffuse(0, false).blendMode(0, BlendMode.CUTOUT_MIPPED).find();
            Sprite sideSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/sandstone"));
            Sprite topSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/sandstone_top"));
            Sprite bottomSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/sandstone_bottom"));
            int color2 = 0xffffff;

            emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.EAST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.WEST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.NORTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.DOWN, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, bottomSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color2, color2, color2, color2)
                    .spriteBake(0, topSprite, MutableQuadView.BAKE_LOCK_UV).emit();
        } else if (material.getOreInformation().getTargetId() == CustomTargets.RED_SANDSTONE.getId()) {
            mat = renderer.materialFinder().disableDiffuse(0, false).blendMode(0, BlendMode.CUTOUT_MIPPED).find();
            Sprite sideSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/red_sandstone"));
            Sprite topSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/red_sandstone_top"));
            Sprite bottomSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/red_sandstone_bottom"));
            int color2 = 0xffffff;

            emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.EAST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.WEST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.NORTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.DOWN, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, bottomSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color2, color2, color2, color2)
                    .spriteBake(0, topSprite, MutableQuadView.BAKE_LOCK_UV).emit();
        } else {
            mat = renderer.materialFinder().disableDiffuse(0, false).blendMode(0, BlendMode.CUTOUT_MIPPED).find();
            Sprite sideSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/podzol_side"));
            Sprite topSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/podzol_top"));
            Sprite bottomSprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("block/dirt"));

            emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.EAST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.WEST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.NORTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sideSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.DOWN, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, bottomSprite, MutableQuadView.BAKE_LOCK_UV).emit();
            emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, topSprite, MutableQuadView.BAKE_LOCK_UV).emit();
        }

        if (material.isGlowing()) {
            mat = renderer.materialFinder().disableDiffuse(0, true).blendMode(0, BlendMode.CUTOUT_MIPPED).emissive(0, true).find();
        } else {
            mat = renderer.materialFinder().disableDiffuse(0, true).blendMode(0, BlendMode.CUTOUT_MIPPED).find();
        }
        color = material.getRGBColor();
        sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(this.material.getTexturesInformation().getOverlayTexture());

        if (material.getOreInformation().getTargetId() != CustomTargets.GRASS_BLOCK.getId() && material.getOreInformation().getTargetId() != CustomTargets.PODZOL.getId()) {
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
        context.meshConsumer().accept(mesh(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getBlockPos()));
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
            return OreBakedModel.this;
        }
    }
}
