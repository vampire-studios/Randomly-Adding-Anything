package io.github.vampirestudios.raa.generation.feature.labyrint;

import io.github.vampirestudios.raa.registries.Features;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class LabyrintPiece extends PoolStructurePiece {

    LabyrintPiece(StructureManager structureManager, StructurePoolElement poolElement, BlockPos pos, int i, BlockRotation rotation, BlockBox box) {
        super(Features.LABYRINT_PIECE, structureManager, poolElement, pos, i, rotation, box);
    }
 
    public LabyrintPiece(StructureManager manager, CompoundTag tag) {
        super(manager, tag, Features.LABYRINT_PIECE);
    }

}