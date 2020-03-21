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
        BlockState topMaterial = dynamic.get("top_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        BlockState middleMaterial = dynamic.get("middle_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        BlockState underMaterial = dynamic.get("under_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        BlockState underwaterMaterial = dynamic.get("underwater_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        return new CustomTernarySurfaceConfig(topMaterial, middleMaterial, underMaterial, underwaterMaterial);
    }
}
