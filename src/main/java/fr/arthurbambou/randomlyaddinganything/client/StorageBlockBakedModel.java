package fr.arthurbambou.randomlyaddinganything.client;

import fr.arthurbambou.randomlyaddinganything.materials.Material;
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
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelItemPropertyOverrideList;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ExtendedBlockView;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Random;
import java.util.function.Supplier;

public class StorageBlockBakedModel extends RAABakedModel {

    public StorageBlockBakedModel(Material material) {
        super(material);
    }

    @Override
    public void emitBlockQuads(ExtendedBlockView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh());
    }

    private Mesh mesh() {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        RenderMaterial mat = renderer.materialFinder().disableDiffuse(0, false).find();
        int color = material.getRGBColor();
        Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(this.material.getStorageBlockTexture());

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

    protected class ItemProxy extends ModelItemPropertyOverrideList {
        public ItemProxy() {
            super(null, null, null, Collections.emptyList());
        }

        @Override
        public BakedModel apply(BakedModel bakedModel_1, ItemStack itemStack_1, World world_1, LivingEntity livingEntity_1) {
            return StorageBlockBakedModel.this;
        }
    }

    @Override
    public ModelItemPropertyOverrideList getItemPropertyOverrides() {
        return new ItemProxy();
    }
}
