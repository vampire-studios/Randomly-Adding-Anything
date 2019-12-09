package io.github.vampirestudios.raa.generation.feature.portalHub;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.WorldStructureManipulation;
import net.minecraft.server.world.ServerWorld;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
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
            String path;
            World world2 = world.getWorld();
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) path = "saves/" + ((ServerWorld) world2).getSaveHandler().getWorldDir().getName() + "/data/portal_hub_spawns.txt";
            else path = world.getLevelProperties().getLevelName() + "/data/portal_hub_spawns.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void placePiece(IWorld world, BlockPos pos, JsonConverter.StructureValues piece, int decay) {
        int themeNum = Rands.randInt(PortalHubThemes.PORTAL_HUB_THEMES.getIds().size());
        PortalHubTheme theme = PortalHubThemes.PORTAL_HUB_THEMES.get(themeNum);
        assert theme != null;
        for (int i = 0; i < piece.getBlockPositions().size(); i++) {
            Vec3i currBlockPos = piece.getBlockPositions().get(i);
            String currBlockType = piece.getBlockTypes().get(piece.getBlockStates().get(i));
            Map<String, String> currBlockProp = piece.getBlockProperties().get(piece.getBlockStates().get(i));

            if (decay <= 0 || !Rands.chance(14 - decay)) {
                switch (currBlockType) {
                    case "minecraft:stone_bricks":
                        WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), Registry.BLOCK.getId(theme.getBlock()).toString(), currBlockProp, 0);
                        break;
                    case "minecraft:stone_brick_slab":
                        WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), Registry.BLOCK.getId(theme.getSlab()).toString(), currBlockProp, 0);
                        break;
                    case "minecraft:stone_brick_stairs":
                        WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), Registry.BLOCK.getId(theme.getStairs()).toString(), currBlockProp, 0);
                        break;
                    case "minecraft:stone_brick_wall":
                        if (themeNum < 14) {
                            WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), Registry.BLOCK.getId(theme.getWall()).toString(), currBlockProp, 0);
                        } else if (themeNum < 16) {
                            WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), Registry.BLOCK.getId(theme.getWall()).toString(), new HashMap<>(), 0);
                        } else {
                            currBlockProp.remove("up");
                            WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), Registry.BLOCK.getId(theme.getWall()).toString(), currBlockProp, 0);
                        }
                        break;
                    case "minecraft:orange_wool":
                        List<DimensionData> dimensionDataList = new ArrayList<>();
                        Dimensions.DIMENSIONS.forEach(dimensionDataList::add);
                        WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), "raa:" + Rands.list(dimensionDataList).getName().toLowerCase() + "_portal", currBlockProp, 0);
                        break;
                    default:
                        WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), currBlockType, currBlockProp, 0);
                        break;
                }
            }
        }
    }
}