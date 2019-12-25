package io.github.vampirestudios.raa.generation.surface.config;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

public class CustomTernarySurfaceConfig implements SurfaceConfig {
    private final BlockState topMaterial;
    private final BlockState middleMaterial;
    private final BlockState underMaterial;
    private final BlockState underwaterMaterial;

    public CustomTernarySurfaceConfig(BlockState topMaterial, BlockState middleMaterial, BlockState underMaterial, BlockState underwaterMaterial) {
        this.topMaterial = topMaterial;
        this.middleMaterial = middleMaterial;
        this.underMaterial = underMaterial;
        this.underwaterMaterial = underwaterMaterial;
    }

    public BlockState getTopMaterial() {
        return this.topMaterial;
    }

    public BlockState getMiddleMaterial() {
        return middleMaterial;
    }

    public BlockState getUnderMaterial() {
        return this.underMaterial;
    }

    public BlockState getUnderwaterMaterial() {
        return this.underwaterMaterial;
    }

    public static CustomTernarySurfaceConfig deserialize(Dynamic<?> dynamic) {
        BlockState blockState = dynamic.get("top_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        BlockState blockState2 = dynamic.get("top_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        BlockState blockState3 = dynamic.get("under_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        BlockState blockState4 = dynamic.get("underwater_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        return new CustomTernarySurfaceConfig(blockState, blockState2, blockState4, blockState4);
    }
}
