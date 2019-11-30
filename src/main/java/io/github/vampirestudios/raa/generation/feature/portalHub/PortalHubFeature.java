package io.github.vampirestudios.raa.generation.feature.portalHub;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.block.enums.StairShape;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import io.github.vampirestudios.raa.utils.WorldStructureManipulation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
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
            World world2 = world.getWorld();
            BufferedWriter writer = new BufferedWriter(new FileWriter("saves/" + ((ServerWorld) world2).getSaveHandler().getWorldDir().getName() + "/data/portal_hub_spawns.txt", true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void placePiece(IWorld world, BlockPos pos, int rotation, JsonConverter.StructureValues piece, int decay) {
        PortalHubTheme theme = PortalHubThemes.PORTAL_HUB_THEMES.get(Rands.randInt(
                PortalHubThemes.PORTAL_HUB_THEMES.getIds().size()));
        assert theme != null;
        for (int i = 0; i < piece.getBlockPositions().size(); i++) {
            Vec3i currBlockPos = piece.getBlockPositions().get(i);
            String currBlockType = piece.getBlockTypes().get(piece.getBlockStates().get(i));
            Map<String, String> currBlockProp = piece.getBlockProperties().get(piece.getBlockStates().get(i));

            //Spawn blocks
            if (decay > 0 && Rands.chance(14 - decay)) {
                world.setBlockState(pos.add(x, y, z), Blocks.AIR.getDefaultState(), 2);
            } else {
                if (currBlockType.equals("minecraft:stone_bricks")) {
                    world.setBlockState(pos.add(x, y, z), theme.getBlock().getDefaultState(), 2);
                } else if (currBlockType.equals("minecraft:stone_brick_slab")) {
                    world.setBlockState(pos.add(x, y, z), theme.getSlab().getDefaultState().with(Properties.SLAB_TYPE, SlabType.valueOf(currBlockProp.get("type").toUpperCase())), 2);
                } else if (currBlockType.equals("minecraft:structure_block")) {
                    //Do nothing TODO: Remove this later!
                } else if (currBlockType.equals("minecraft:stone_brick_stairs")) {
                    world.setBlockState(pos.add(x, y, z), theme.getStairs().getDefaultState().with(Properties.BLOCK_HALF, BlockHalf.valueOf(currBlockProp.get("half").toUpperCase())).with(Properties.STAIR_SHAPE, StairShape.valueOf(currBlockProp.get("shape").toUpperCase())).with(Properties.HORIZONTAL_FACING, Direction.valueOf(currBlockProp.get("facing").toUpperCase())), 2);
                } else if (currBlockType.equals("minecraft:stone_brick_wall")) {
                    world.setBlockState(pos.add(x, y, z), theme.getWall().getDefaultState(), 2);
                } else if (currBlockType.equals("minecraft:orange_wool")) {
                    //Spawn random portal
                    List<DimensionData> dimensionDataList = new ArrayList<>();
                    Dimensions.DIMENSIONS.forEach(dimensionDataList::add);
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse("raa:" +Rands.list(dimensionDataList).getName().toLowerCase() + "_portal")).getDefaultState(), 2);
                } else if (!currBlockType.equals("minecraft:air")){
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse(currBlockType)).getDefaultState(), 2);
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