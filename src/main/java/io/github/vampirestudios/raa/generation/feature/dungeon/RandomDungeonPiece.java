package io.github.vampirestudios.raa.generation.feature.dungeon;

import io.github.vampirestudios.raa.registries.Features;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class RandomDungeonPiece extends PoolStructurePiece {
 
    RandomDungeonPiece(StructureManager structureManager_1, StructurePoolElement structurePoolElement_1, BlockPos blockPos_1, int int_1, BlockRotation blockRotation_1, BlockBox mutableIntBoundingBox_1) {
        super(Features.DUNGEON_PIECE, structureManager_1, structurePoolElement_1, blockPos_1, int_1, blockRotation_1, mutableIntBoundingBox_1);
    }
 
    public RandomDungeonPiece(StructureManager manager, CompoundTag tag) {
        super(manager, tag, Features.DUNGEON_PIECE);
    }

}