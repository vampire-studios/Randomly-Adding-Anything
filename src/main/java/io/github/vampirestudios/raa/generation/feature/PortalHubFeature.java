package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.*;
import java.util.function.Function;

public class PortalHubFeature extends Feature<DefaultFeatureConfig> {
    JsonConverter converter = new JsonConverter();
    Map<String, JsonConverter.StructureValues> structures = new HashMap<String, JsonConverter.StructureValues>() {{
        put("portal_hub", converter.loadStructure("portal_hub/portal_hub.json"));
    }};

    public PortalHubFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

        //Make sure the structure can spawn here
        int x_origin = pos.getX();
        int y_origin = pos.getY();
        int z_origin = pos.getZ();

        List<List<Float>> flatnessList = new ArrayList<>();
        for (float x_offset = x_origin - 14; x_offset < x_origin + 14; x_offset++) {
            for (float z_offset = z_origin - 14; z_offset < z_origin + 14; z_offset++) {
                float y_offset = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, new BlockPos(x_offset, 0, z_offset)).getY();
                boolean non_spawnable = world.isAir(new BlockPos(x_offset, y_offset - 1, z_offset)) || (!world.getBlockState(new BlockPos(x_offset, y_offset - 1, z_offset)).isOpaque() && !world.getBlockState(new BlockPos(x_offset, y_offset - 2, z_offset)).isOpaque());
                if (x_offset < x_origin + 3 && z_offset < z_origin + 3) {
                    flatnessList.add(Arrays.asList(x_offset, y_offset, z_offset, 0f));
                }
                for (List<Float> flatness : flatnessList) {
                    if (Math.pow((x_offset - flatness.get(0)) - 5.5f, 2) + Math.pow(z_offset - flatness.get(2) - 5.5f, 2) < Math.pow(6, 2)) {
                        if (y_offset > flatness.get(1) - 3 && y_offset <= flatness.get(1)) {
                            if (y_offset == flatness.get(1)) {
                                flatness.set(3, flatness.get(3) + 1f);
                            } else if (y_offset == flatness.get(1) - 1) {
                                flatness.set(3, flatness.get(3) + 0.5f);
                            } else {
                                flatness.set(3, flatness.get(3) + 0.25f);
                            }
                        }
                        if (non_spawnable) {
                            flatness.set(3, -144f);
                        }
                    }
                }
            }
        }
        float max_flatness = -1;
        int chosen = -1;
        for (int i = 0; i < flatnessList.size(); i++) {
            if (flatnessList.get(i).get(3) > max_flatness) {
                max_flatness = flatnessList.get(i).get(3);
                chosen = i;
            }
        }
        boolean working_spawn = false;
        if (chosen != -1) {
            int x_chosen = flatnessList.get(chosen).get(0).intValue();
            int y_chosen = flatnessList.get(chosen).get(1).intValue();
            int z_chosen = flatnessList.get(chosen).get(2).intValue();
            pos = new BlockPos(x_chosen, y_chosen, z_chosen);
            working_spawn = trySpawning(world, pos);
        }

        if (!working_spawn || pos.getY() > 247) {
            /*if (max_flatness > 20) {
                System.out.println("Failed to spawn! Origin Coords: " + x_origin + "/" + y_origin + "/" + z_origin);
                System.out.println("New Coords: " + pos.getX() + "/" + pos.getY() + "/" + pos.getZ());
                System.out.println("Flatness: " + max_flatness);
            }*/
            return true;
        }

        //Generate basement
        if (pos.getY() > 10 && Rands.chance(3)) {
            placePiece(world, pos.add(0, -7, 0), 0, structures.get("tower_base"), 0);
        }
        placePiece(world, pos, 0, structures.get("portal_hub"), 0);
        return true;
    }

    public static boolean trySpawning(IWorld world, BlockPos pos) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir()) {
            return false;
        }
        Map<Integer, Float> heights = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            heights.put(i, 0f);
        }
        int totalHeight = 0;
        float maxFreq = 0f;
        int maxHeight = 0;
        int modeHeight = 0;
        int minHeight = 256;
        for (int xIndent = 0; xIndent < 12; xIndent++) {
            for (int zIndent = 0; zIndent < 12; zIndent++) {
                if (Math.pow(xIndent - 5.5f, 2) + Math.pow(zIndent - 5.5f, 2) < Math.pow(6, 2)) {
                    if (!world.getBlockState(new BlockPos(pos.add(xIndent, -1, zIndent))).isOpaque() && !world.getBlockState(new BlockPos(pos.add(xIndent, -2, zIndent))).isOpaque()) {
                        return false;
                    }

                    int tempHeight = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos.add(xIndent, 0, zIndent)).getY();
                    if (tempHeight < minHeight) {
                        minHeight = tempHeight;
                    }
                    if (tempHeight > maxHeight) {
                        maxHeight = tempHeight;
                    }
                    totalHeight += tempHeight;

                    List<Integer> tempHeights = Arrays.asList(tempHeight, tempHeight - 1, tempHeight - 2);
                    List<Float> tempFloats = Arrays.asList(1f, 0.5f, 0.25f);
                    for (int i = 0; i < 3; i++) {
                        float tempFreqs = heights.get(tempHeights.get(i)) + tempFloats.get(i);
                        heights.put(tempHeights.get(i), tempFreqs);
                        if (tempFreqs > maxFreq) {
                            maxFreq = tempFreqs;
                            modeHeight = tempHeights.get(i);
                        }
                    }
                }
            }
        }
        float TOLERANCE = 0.25f; //This is the tolerance for tower generation, ranging from 0 to 1. The lower this is, the more strict the tower generation is. Increase it for wacky generation.
        if (maxHeight - minHeight > 3 && maxHeight*112 - totalHeight > 112*((maxHeight - minHeight)/2f * TOLERANCE) && maxHeight*112 - totalHeight < 112*((maxHeight - minHeight)*(1 - TOLERANCE/2f))) {
            return false;
        }

        pos = pos.add(0, modeHeight - pos.getY(), 0);

        /*try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("saves/" + world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/tower_spawns.txt", true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return  true;
    }

    public static void placePiece(IWorld world, BlockPos pos, int rotation, JsonConverter.StructureValues piece, int decay) {
        for (int i = 0; i < piece.getBlockPositions().size(); i++) {
            List<Integer> currBlockPos = piece.getBlockPositions().get(i);
            String currBlockType = piece.getBlockTypes().get(piece.getBlockStates().get(i));
            int x = currBlockPos.get(0);
            int y = currBlockPos.get(1);
            int z = currBlockPos.get(2);

            //Rotate
            Direction direction = rotate(((rotation + (rotation%2)*2 - 1)%4 + 4)%4, 0);
            if (rotation == 1) {
                int temp_x = x;
                x = piece.getSize().get(0) - 1 - z;
                z = temp_x;
            } else if (rotation == 2) {
                x = piece.getSize().get(0) - 1 - x;
                z = piece.getSize().get(2) - 1 - z;
            } else if (rotation == 3){
                int temp_x = x;
                x = z;
                z = piece.getSize().get(2) - 1 - temp_x;
            }

            //Spawn blocks
            if (decay > 0 && Rands.chance(14 - decay)) {
                world.setBlockState(pos.add(x, y, z), Blocks.AIR.getDefaultState(), 2);
            }
            else {
                if (currBlockType.equals("minecraft:stone_bricks")) {
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse(currBlockType)).getDefaultState(), 2);
                } else if (currBlockType.equals("minecraft:ladder")) {
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse(currBlockType)).getDefaultState().with(Properties.HORIZONTAL_FACING, direction), 2);
                } else {
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse(currBlockType)).getDefaultState(), 2);
                }
            }
        }
    }

    public static Direction rotate(int rotation, int amount) {
        if (amount > 0) {
            Direction new_dir = rotate(rotation, amount - 1);
            if (new_dir == Direction.WEST) {
                return Direction.SOUTH;
            } else if (new_dir == Direction.SOUTH) {
                return Direction.EAST;
            } else if (new_dir == Direction.EAST) {
                return Direction.NORTH;
            } else {
                return Direction.WEST;
            }
        } else {
            if (rotation == 0) {
                return Direction.SOUTH;
            } else if (rotation == 1) {
                return Direction.EAST;
            } else if (rotation == 2) {
                return Direction.NORTH;
            } else {
                return Direction.WEST;
            }
        }
    }
}