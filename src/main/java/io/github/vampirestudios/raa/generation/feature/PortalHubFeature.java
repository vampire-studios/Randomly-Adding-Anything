package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.WorldStructureManipulation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class PortalHubFeature extends Feature<DefaultFeatureConfig> {
    private JsonConverter converter = new JsonConverter();
    private Map<String, JsonConverter.StructureValues> structures = new HashMap<String, JsonConverter.StructureValues>() {{
        put("portal_hub", converter.loadStructure("portal_hub/portal_hub.json"));
    }};

    public PortalHubFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

        //Cheeky way of limiting these structures to the overworld
        if (!world.getDimension().getType().getSuffix().equals("")) {
            return true;
        }

        //Check if structure can generate in the area
        Vec3i tempPos = WorldStructureManipulation.CircularSpawnCheck(world, pos, structures.get("portal_hub").getSize(), 0.125f);
        if (tempPos.compareTo(Vec3i.ZERO) == 0) {
            return true;
        }
        pos = new BlockPos(tempPos);

        //Generate portal
        placePiece(world, pos, structures.get("portal_hub"), 0);

        //Record spawn in text file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("saves/" + world.getLevelProperties().getLevelName() + "/data/portal_hub_spawns.txt", true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }



    private static void placePiece(IWorld world, BlockPos pos, JsonConverter.StructureValues piece, int decay) {
        for (int i = 0; i < piece.getBlockPositions().size(); i++) {
            Vec3i currBlockPos = piece.getBlockPositions().get(i);
            String currBlockType = piece.getBlockTypes().get(piece.getBlockStates().get(i));
            Map<String, String> currBlockProp = piece.getBlockProperties().get(piece.getBlockStates().get(i));

            //Spawn blocks
            if (decay <= 0 || !Rands.chance(14 - decay)) {
                if (currBlockType.equals("minecraft:orange_wool")) {
                    List<DimensionData> dimensionDataList = new ArrayList<>();
                    Dimensions.DIMENSIONS.forEach(dimensionDataList::add);
                    WorldStructureManipulation.PlaceBlock(world, pos.add(currBlockPos), "raa:" + Rands.list(dimensionDataList).getName().toLowerCase() + "_portal", currBlockProp, 0);
                } else {
                    WorldStructureManipulation.PlaceBlock(world, pos.add(currBlockPos), currBlockType, currBlockProp, 0);
                }
            }
        }
    }
}