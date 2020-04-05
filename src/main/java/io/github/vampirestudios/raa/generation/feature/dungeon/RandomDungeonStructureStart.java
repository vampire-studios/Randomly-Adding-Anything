package io.github.vampirestudios.raa.generation.feature.dungeon;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;

public class RandomDungeonStructureStart extends StructureStart {

    private static final Identifier CENTERS = new Identifier("raa:dungeons/centers");
    private static final Identifier CORRIDORS = new Identifier("raa:dungeons/corridors");
    private static final Identifier CROSS_SECTIONS = new Identifier("raa:dungeons/corridors/cross_sections");
    private static final Identifier ROOMS = new Identifier("raa:dungeons/rooms");
 
    RandomDungeonStructureStart(StructureFeature<?> feature, int x, int z, BlockBox box, int int_3, long seed) {
        super(feature, x, z, box, int_3, seed);
    }
 
    @Override
    public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
        StructurePoolBasedGenerator.addPieces(CENTERS, 7, RandomDungeonPiece::new, chunkGenerator, structureManager, new BlockPos(x * 16, 0, z * 16), children, random);
        setBoundingBoxFromChildren();
    }

    static {
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        CENTERS,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement("raa:dungeons/centers/center_1"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/centers/center_2"), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        CORRIDORS,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement("raa:dungeons/corridors/corridor_1"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/corridors/corridor_2"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/corridors/corridor_3"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/corridors/corridor_4"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/corridors/corridor_5"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/corridors/left_corner"), 1)
                        ),
                        StructurePool.Projection.TERRAIN_MATCHING
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        CROSS_SECTIONS,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement("raa:dungeons/corridors/cross_sections/cross_section_1"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/corridors/cross_sections/t_cross_section_right"), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        ROOMS,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_1"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_1_1"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_2"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_2_1"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_3"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_3_1"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_4"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_4_1"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_5"), 1),
                                Pair.of(new SinglePoolElement("raa:dungeons/rooms/room_5_1"), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }

}