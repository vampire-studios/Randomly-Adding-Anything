package io.github.vampirestudios.raa.generation.feature.labyrint;

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

public class LabyrintStructureStart extends StructureStart {

    private static final Identifier CENTERS = new Identifier("raa:labyrint/centers");
    private static final Identifier HALLWAYS = new Identifier("raa:labyrint/hallways");
    private static final Identifier SECTIONS = new Identifier("raa:labyrint/hallways/sections");
    private static final Identifier ROOMS = new Identifier("raa:labyrint/rooms");
 
    LabyrintStructureStart(StructureFeature<?> feature, int x, int z, BlockBox box, int int_3, long seed) {
        super(feature, x, z, box, int_3, seed);
    }
 
    @Override
    public void init(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
        StructurePoolBasedGenerator.addPieces(CENTERS, 7, LabyrintPiece::new, chunkGenerator, structureManager, new BlockPos(x * 16, 0, z * 16), children, random);
        setBoundingBoxFromChildren();
    }

    static {
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        CENTERS,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement("raa:labyrint/centers/center_1", ImmutableList.of(), StructurePool.Projection.RIGID), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        HALLWAYS,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/blindway", ImmutableList.of(), StructurePool.Projection.RIGID), 1),
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/corner_hallway", ImmutableList.of(), StructurePool.Projection.RIGID), 1),
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/hallway_1", ImmutableList.of(), StructurePool.Projection.RIGID), 1),
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/hallway_2", ImmutableList.of(), StructurePool.Projection.RIGID), 1),
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/hallway_3", ImmutableList.of(), StructurePool.Projection.RIGID), 1),
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/hallway_w_door", ImmutableList.of(), StructurePool.Projection.RIGID), 1),
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/hallway_w_door_2", ImmutableList.of(), StructurePool.Projection.RIGID), 1),
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/lootable_blindway", ImmutableList.of(), StructurePool.Projection.RIGID), 1)
                        ),
                        StructurePool.Projection.TERRAIN_MATCHING
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        SECTIONS,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/sections/cross_section_hallway", ImmutableList.of(), StructurePool.Projection.RIGID), 1),
                                Pair.of(new SinglePoolElement("raa:labyrint/hallways/sections/t_section_hallway", ImmutableList.of(), StructurePool.Projection.RIGID), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        ROOMS,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement("raa:labyrint/rooms/room_1", ImmutableList.of(), StructurePool.Projection.RIGID), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }

}