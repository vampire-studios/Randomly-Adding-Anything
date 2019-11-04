package io.github.vampirestudios.raa.client;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.materials.Material;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelItemPropertyOverrideList;
import net.minecraft.client.texture.Sprite;
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

        RenderMaterial mat = renderer.materialFinder().disableDiffuse(0, false).find();
        int color = 0xFFFFFFFF;
        Sprite sprite;
        if (material.getOreInformation().getGenerateIn() != GeneratesIn.DOES_NOT_APPEAR) {
            sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/" + Registry.BLOCK.getId(material.getOreInformation()
                    .getGenerateIn().getBlock()).getPath()));
        } else {
            sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/oak_planks"));
        }
        
        if (material.getOreInformation().getGenerateIn() != GeneratesIn.GRASS_BLOCK && material.getOreInformation().getGenerateIn() != GeneratesIn.PODZOL) {
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
        } else if (material.getOreInformation().getGenerateIn() == GeneratesIn.GRASS_BLOCK) {
            mat = renderer.materialFinder().disableDiffuse(0, false).blendMode(0, RenderLayer.getCutoutMipped()).find();
            Sprite sideSprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/grass_block_side"));
            Sprite sideOverlaySprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/grass_block_side_overlay"));
            Sprite topSprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/grass_block_top"));
            Sprite bottomSprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/dirt"));
            int color2 = 0xffffff;
            BlockColorProvider blockColor =  ColorProviderRegistry.BLOCK.get(material.getOreInformation().getGenerateIn().getBlock());
            if (blockColor != null) {
                color2 = 0xff000000 | blockColor.getColor(material.getOreInformation().getGenerateIn().getBlock().getDefaultState(), blockView, pos, 1);
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
        } else {
            mat = renderer.materialFinder().disableDiffuse(0, false).find();
            Sprite sideSprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/podzol_side"));
            Sprite topSprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/podzol_top"));
            Sprite bottomSprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(new Identifier("block/dirt"));

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
            mat = renderer.materialFinder().disableDiffuse(0, true).blendMode(0, RenderLayer.getCutout()).emissive(0, true).find();
        } else {
            mat = renderer.materialFinder().disableDiffuse(0, true).blendMode(0, RenderLayer.getCutout()).find();
        }
        color = material.getRGBColor();
        sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(this.material.getOreInformation().getOverlayTexture());

        if (material.getOreInformation().getGenerateIn() != GeneratesIn.GRASS_BLOCK && material.getOreInformation().getGenerateIn() != GeneratesIn.PODZOL) {
            emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV ).emit();
            emitter.square(Direction.EAST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV ).emit();
            emitter.square(Direction.WEST, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV ).emit();
            emitter.square(Direction.NORTH, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV ).emit();
            emitter.square(Direction.DOWN, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV ).emit();
            emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV ).emit();
        } else {
            emitter.square(Direction.UP, 0, 0, 1, 1, 0)
                    .material(mat)
                    .spriteColor(0, color, color, color, color)
                    .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV ).emit();
        }

        return builder.build();
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getBlockPos()));
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

    @Override
    public Sprite getSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas().getSprite(this.material.getOreInformation().getOverlayTexture());
    }

    @Override
    public ModelItemPropertyOverrideList getItemPropertyOverrides() {
        return new ItemProxy();
    }
}
