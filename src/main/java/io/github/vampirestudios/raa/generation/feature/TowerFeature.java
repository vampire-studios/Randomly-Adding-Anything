package io.github.vampirestudios.raa.generation.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.WorldStructureManipulation;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class TowerFeature extends Feature<DefaultFeatureConfig> {
    private JsonConverter converter = new JsonConverter();
    private Map<String, JsonConverter.StructureValues> structures = new HashMap<String, JsonConverter.StructureValues>() {{
        put("tower_base", converter.loadStructure("tower/tower_base.json"));
        put("tower_walls", converter.loadStructure("tower/tower_walls.json"));
        put("tower_stairs", converter.loadStructure("tower/tower_stairs.json"));
        put("tower_ladders", converter.loadStructure("tower/tower_ladders.json"));
        put("tower_pillar", converter.loadStructure("tower/tower_pillar.json"));
        put("tower_roof", converter.loadStructure("tower/tower_roof.json"));
    }};

    public TowerFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

        //Check if structure can generate in the area
        Vec3i size = structures.get("tower_base").getSize();
        Vec3i tempPos = WorldStructureManipulation.CircularSpawnCheck(world, pos, new Vec3i(size.getX(), 9, size.getZ()), 0.25f);
        if (tempPos.compareTo(Vec3i.ZERO) == 0) {
            return true;
        }
        pos = new BlockPos(tempPos);

        //Generate basement
        if (pos.getY() > 10 && Rands.chance(3)) {
            placePiece(world, pos.add(0, -7, 0), 0, structures.get("tower_base"), 0);
            placeRoom(world, pos.add(0, -6, 0), structures, "Storage", -2);
        }
        placePiece(world, pos, 0, structures.get("tower_base"), 0);

        int level;
        int last_floor = -1;
        int level_chance = new Random().nextInt(20);
        for (level = 0; level_chance < 24 - 7*level; level++) {
            if (pos.getY() + 7*level < 248) {
                String room_name;
                int room_num = new Random().nextInt(3);
                while (room_num == last_floor) {
                    room_num = new Random().nextInt(3);
                }
                last_floor = room_num;
                if (room_num == 0) {
                    room_name = "Armory";
                } else if (room_num == 1) {
                    room_name = "Barracks";
                } else if (room_num == 2 && Rands.chance(2)) {
                    room_name = "Empty2";
                } else {
                    room_name = "Empty";
                }
                placeRoom(world, pos.add(0, 1 + level*7, 0), structures, room_name, 2*level + 2);
            }
            else { break; }
        }

        placePiece(world, pos.add(0, 1 + level*7, 0), 0, structures.get("tower_roof"), 2*level + 4);

        //Place in the door
        List<Integer> Open_windows = Arrays.asList(0,0,0,0);
        int max = 0;
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (world.isAir(pos.add(MathHelper.floor(6.5f + 6.5f*MathHelper.cos((float) Math.PI/2*i)), j, MathHelper.floor(6.5f + 6.5f*MathHelper.sin((float) Math.PI/2*i))))) {
                    Open_windows.set(i, Open_windows.get(i) + 1);
                }
                if (world.isAir(pos.add(MathHelper.ceil(6.5f + 6.5f*MathHelper.cos((float) Math.PI/2*i)), j, MathHelper.ceil(6.5f + 6.5f*MathHelper.sin((float) Math.PI/2*i))))) {
                    Open_windows.set(i, Open_windows.get(i) + 1);
                }
            }
            if (Open_windows.get(i) > max) {
                max = Open_windows.get(i);
                index = i;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                world.setBlockState(pos.add(MathHelper.floor(6.5f + 5.5f*MathHelper.cos((float) Math.PI/2*index)), 4 - i, MathHelper.floor(6.5f + 5.5f*MathHelper.sin((float) Math.PI/2*index))), Registry.BLOCK.get(Identifier.tryParse("raa:" + (world.getDimension().getType().getSuffix()).substring(4) + "_stone_bricks")).getDefaultState(), 2);
                world.setBlockState(pos.add(MathHelper.ceil(6.5f + 5.5f*MathHelper.cos((float) Math.PI/2*index)), 4 - i, MathHelper.ceil(6.5f + 5.5f*MathHelper.sin((float) Math.PI/2*index))), Registry.BLOCK.get(Identifier.tryParse("raa:" + (world.getDimension().getType().getSuffix()).substring(4) + "_stone_bricks")).getDefaultState(), 2);
            } else {
                world.setBlockState(pos.add(MathHelper.floor(6.5f + 5.5f*MathHelper.cos((float) Math.PI/2*index)), 4 - i, MathHelper.floor(6.5f + 5.5f*MathHelper.sin((float) Math.PI/2*index))), Blocks.AIR.getDefaultState(), 2);
                world.setBlockState(pos.add(MathHelper.ceil(6.5f + 5.5f*MathHelper.cos((float) Math.PI/2*index)), 4 - i, MathHelper.ceil(6.5f + 5.5f*MathHelper.sin((float) Math.PI/2*index))), Blocks.AIR.getDefaultState(), 2);
            }
        }

        //Record spawn in text file
        try {
            String path;
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) path = "saves/" + world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/tower_spawns.txt";
            else path = world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/tower_spawns.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static void placePiece(IWorld world, BlockPos pos, int rotation, JsonConverter.StructureValues piece, int decay) {
        for (int i = 0; i < piece.getBlockPositions().size(); i++) {
            Vec3i currBlockPos = piece.getBlockPositions().get(i);
            String currBlockType = piece.getBlockTypes().get(piece.getBlockStates().get(i));
            Map<String, String> currBlockProp = piece.getBlockProperties().get(piece.getBlockStates().get(i));

            //Rotate
            currBlockPos = WorldStructureManipulation.rotatePos(rotation, currBlockPos, piece.getSize());

            //Spawn blocks
            if (currBlockType.equals("minecraft:air") || (decay > 0 && Rands.chance(14 - decay))) {
                WorldStructureManipulation.PlaceBlock(world, pos.add(currBlockPos), "minecraft:air", new HashMap<>(), rotation);
            } else {
                if (currBlockType.equals("minecraft:stone_bricks")) {
                    WorldStructureManipulation.PlaceBlock(world, pos.add(currBlockPos), "raa:" + (world.getDimension().getType().getSuffix()).substring(4) + "_stone_bricks", currBlockProp, rotation);
                } else if (currBlockType.equals("minecraft:ladder")) {
                    WorldStructureManipulation.PlaceBlock(world, pos.add(currBlockPos), currBlockType, currBlockProp, 4 - rotation);
                } else {
                    WorldStructureManipulation.PlaceBlock(world, pos.add(currBlockPos), currBlockType, currBlockProp, rotation);
                }
            }
        }
    }

    private static void fillWindows(IWorld world, BlockPos pos, int fill) {
        //Fill windows part-way if outside or all the way if next to blocks
        for (int i = 0; i < 4; i++) {
            float xPart = 6.5f - 5.5f * MathHelper.cos((float) (Math.PI / 2 * i));
            float zPart = 6.5f - 5.5f * MathHelper.sin((float) (Math.PI / 2 * i));
            float xPart2 = 6.5f - 6.5f * MathHelper.cos((float) (Math.PI / 2 * i));
            float zPart2 = 6.5f - 6.5f * MathHelper.sin((float) (Math.PI / 2 * i));

            for (int j = 2; j < 4; j++) {
                if (!world.isAir(pos.add(MathHelper.floor(xPart2), j, MathHelper.floor(zPart2))) || (fill + 2 == j) || fill == 2) {
                    world.setBlockState(pos.add(MathHelper.floor(xPart), j, MathHelper.floor(zPart)), Registry.BLOCK.get(Identifier.tryParse("raa:" + (world.getDimension().getType().getSuffix()).substring(4) + "_stone_bricks")).getDefaultState(), 2);
                }
                if (!world.isAir(pos.add(MathHelper.ceil(xPart2), j, MathHelper.ceil(zPart2))) || (fill + 2 == j) || fill == 2) {
                    world.setBlockState(pos.add(MathHelper.ceil(xPart), j, MathHelper.ceil(zPart)), Registry.BLOCK.get(Identifier.tryParse("raa:" + (world.getDimension().getType().getSuffix()).substring(4) + "_stone_bricks")).getDefaultState(), 2);
                }
            }
        }
    }

    private static void placeDecoration(IWorld world, BlockPos pos, int rotation, List<String> blocks, List<Vec3i> blockPos, List<Map<String, String>> blockProps) {
        if (!world.isAir(pos.add(0, -1, 0))) {
            for (int i = 0; i < blockPos.size(); i++) {
                String currBlock = blocks.get(i);
                Vec3i currPos = blockPos.get(i);
                Map<String, String> currProps = blockProps.get(i);
                int x = currPos.getX();
                int z = currPos.getZ();
                int y = currPos.getY();
                int chest_type = 0;

                //Rotate
                int xTemp = x;
                x = (x + z) * Math.round(MathHelper.cos((float) (Math.PI / 2f * (rotation - z))));
                z = (xTemp + z) * -Math.round(MathHelper.sin((float) (Math.PI / 2f * (rotation - z))));
                currPos = new Vec3i(x, currPos.getY(), z);

                //Spawn entity
                if (currBlock.equals("armor_stand")) {
                    float stand_rotation = 90f;
                    if (currProps.get("armor") == null) {
                        if (rotation % 2 == 0) {
                            stand_rotation = -45f;
                        } else {
                            stand_rotation = 45f;
                        }
                    }
                    WorldStructureManipulation.SpawnEntity(world, pos.add(currPos), "minecraft:" + currBlock, currProps, stand_rotation);

                    //Spawn block
                } else {
                    if (currBlock.equals("barrel") && blocks.size() != 1) {
                        int rotate = new Random().nextInt(3);
                        currProps.put("facing", (rotate == 0) ? "SOUTH" : (rotate == 1) ? "EAST" : "UP");
                    } else if (currBlock.equals("potted_")) {
                        String[] plants = {"white_tulip", "spruce_sapling", "red_tulip", "red_mushroom", "poppy", "pink_tulip", "oxeye_daisy", "orange_tulip",
                                "oak_sapling", "lily_of_the_valley", "jungle_sapling", "fern", "dead_bush", "dark_oak_sapling", "dandelion", "cactus",
                                "brown_mushroom", "blue_orchid", "birch_sapling", "bamboo", "azure_bluet", "allium", "acacia_sapling", "cornflower"};
                        currBlock += plants[new Random().nextInt(plants.length)];
                    } else if (currBlock.equals("iron_bars")) {
                        if (blocks.size() == 1) {
                            currProps.put("west", "TRUE");
                            currProps.put("east", "TRUE");
                        } else if (x == z && y == 0) {
                            currProps.put("north", "TRUE");
                            currProps.put("west", "TRUE");
                            currProps.put("south", "TRUE");
                            currProps.put("east", "TRUE");
                        } else {
                            currProps.put("north", "TRUE");
                            currProps.put("west", "TRUE");
                        }
                    } else if (currBlock.contains("chest")) {
                        if (currBlock.equals("chest1")) {
                            chest_type = 1;
                        } else if (currBlock.equals("chest2")) {
                            chest_type = 2;
                        } else {
                            chest_type = 3;
                        }
                        currBlock = "chest";
                    }

                    WorldStructureManipulation.PlaceBlock(world, pos.add(currPos), "minecraft:" + currBlock, currProps, rotation);

                    //Chest loot
                    if (chest_type == 1) {
                        LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(x, y, z), LootTables.SHIPWRECK_SUPPLY_CHEST);
                    } else if (chest_type == 2) {
                        LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(x, y, z), LootTables.VILLAGE_WEAPONSMITH_CHEST);
                    } else if (chest_type == 3){
                        if (Rands.chance(5)) {
                            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(x, y, z), LootTables.SIMPLE_DUNGEON_CHEST);
                        } else if (Rands.chance(8)) {
                            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(x, y, z), LootTables.STRONGHOLD_LIBRARY_CHEST);
                        } else {
                            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(x, y, z), LootTables.VILLAGE_CARTOGRAPHER_CHEST);
                        }
                    }
                }
            }
        }
    }

    private static void placeRoom(IWorld world, BlockPos pos, Map<String, JsonConverter.StructureValues> pieces, String type, int decay) {
        //walls
        placePiece(world, pos.add(1, 0, 1), 0, pieces.get("tower_walls"), decay + 2);
        //stairs/ladders
        if (Rands.chance(2)) {
            placePiece(world, pos, new Random().nextInt(4), pieces.get("tower_stairs"), decay - 1);
        } else {
            placePiece(world, pos, new Random().nextInt(4), pieces.get("tower_ladders"), decay - 1);
        }

        //Populate corner items
        String cornerBlocksString = "barrel, barrel, barrel, barrel; wall_torch; smoker; furnace; crafting_table; " +
                "iron_bars, iron_bars, iron_bars, iron_bars; oak_fence, oak_pressure_plate; smithing_table; blast_furnace; armor_stand; " +
                "oak_stairs, oak_stairs, white_carpet, red_carpet; oak_fence, oak_pressure_plate, oak_stairs; scaffolding, potted_; bookshelf, bookshelf; armor_stand";
        String cornerPosString =    "0 0 0, 0 0 1, 1 0 0, 0 1 0; 0 1 0; 0 0 0; 0 0 0; 0 0 0; " +
                "0 0 0, 0 0 1, 1 0 0, 0 1 0; 0 0 0, 0 1 0; 0 0 0; 0 0 0; 0 0 0; " +
                "0 0 0, 0 0 1, 0 1 0, 0 1 1; 0 0 0, 0 1 0, 0 0 1; 0 0 0, 0 1 0; 0 0 0, 0 1 0; 0 0 0";
        String cornerPropsString =  "facing:UP, facing:UP, facing:UP, facing:UP; facing:SOUTH; facing:SOUTH; facing:SOUTH; NULL; " +
                "NULL, NULL, NULL, NULL; NULL, NULL; NULL; facing:SOUTH; NULL; " +
                "facing:NORTH half:TOP shape:STRAIGHT, facing:SOUTH half:TOP shape:STRAIGHT, NULL, NULL; NULL, NULL, facing:SOUTH half:BOTTOM shape:STRAIGHT; distance:0, NULL; NULL, NULL; NULL";

        //Populate center items
        String centerBlocksString = "chest1, air; barrel; stonecutter; tnt; oak_fence, oak_pressure_plate; " +
                "chest2, air; iron_bars; damaged_anvil; grindstone; armor_stand; " +
                "chest3, air; bell; scaffolding, oak_pressure_plate; scaffolding, lantern";
        String centerPosString =    "0 0 0, 0 0 1; 0 0 0; 0 0 0; 0 0 0; 0 0 0, 0 1 0; " +
                "0 0 0, 0 0 1; 0 0 0; 0 0 0; 0 0 0; 0 0 0; " +
                "0 0 0, 0 0 1; 0 1 0; 0 0 0, 0 1 0; 0 0 0, 0 1 0";
        String centerPropsString =  "facing:SOUTH type:SINGLE, NULL; facing:UP; facing:SOUTH; NULL; NULL, NULL; " +
                "facing:SOUTH type:SINGLE, NULL; north:TRUE south:TRUE; facing:WEST; face:FLOOR facing:WEST; armor:ALL; " +
                "facing:SOUTH type:SINGLE, NULL; attachment:SINGLE_WALL facing:NORTH; distance:0, NULL; distance:0, NULL";

        List<List<String>> cornerBlocks = new ArrayList<>();
        List<List<Vec3i>> cornerPos = new ArrayList<>();
        List<List<Map<String, String>>> cornerProps = new ArrayList<>();
        InitializeDecos(cornerBlocksString, cornerPosString, cornerPropsString, cornerBlocks, cornerPos, cornerProps);

        List<List<String>> centerBlocks = new ArrayList<>();
        List<List<Vec3i>> centerPos = new ArrayList<>();
        List<List<Map<String, String>>> centerProps = new ArrayList<>();
        InitializeDecos(centerBlocksString, centerPosString, centerPropsString, centerBlocks, centerPos, centerProps);

        Random rand = new Random();
        int randIndex;

        //Populate room based on type
        switch (type) {
            case "Storage":
                //Center barrels
                for (int i = 0; i < 8; i++) {
                    world.setBlockState(pos.add(6 + i%2, 0, 5 + i/2), Blocks.BARREL.getDefaultState().with(Properties.FACING, Direction.UP), 2);
                    if (i % 6 / 2 != 0) {
                        world.setBlockState(pos.add(6 + i%2, 1, 5 + i/2), Blocks.BARREL.getDefaultState().with(Properties.FACING, Direction.UP), 2);
                    }
                }
                world.setBlockState(pos.add(5, 0, 7), Blocks.BARREL.getDefaultState().with(Properties.FACING, Direction.UP), 2);
                world.setBlockState(pos.add(8, 0, 6), Blocks.BARREL.getDefaultState().with(Properties.FACING, Direction.UP), 2);

                //Storage corners
                for (int i = 0; i < 4; i++) {
                    randIndex = rand.nextInt(5);
                    placeDecoration(world, pos.add(3 + 7*(i/2), 0, 3 + 7*Math.round(MathHelper.sin((float) (Math.PI/3*i)))), i, cornerBlocks.get(randIndex), cornerPos.get(randIndex), cornerProps.get(randIndex));
                }

                //Storage center
                randIndex = rand.nextInt(5);
                placeDecoration(world, pos.add(5, 0, 6), 3, centerBlocks.get(randIndex), centerPos.get(randIndex), centerProps.get(randIndex));
                randIndex = rand.nextInt(5);
                placeDecoration(world, pos.add(8, 0, 7), 1, centerBlocks.get(randIndex), centerPos.get(randIndex), centerProps.get(randIndex));

                fillWindows(world, pos, 2);
                break;
            case "Armory":
                //Center iron bars
                for (int i = 0; i < 4; i++) {
                    world.setBlockState(pos.add(6 + i%2, 0, 5 + i/2*3), Blocks.IRON_BARS.getDefaultState().with(Properties.WEST, true).with(Properties.EAST, true), 2);
                }
                world.setBlockState(pos.add(5, 0, 5), Blocks.IRON_BARS.getDefaultState().with(Properties.SOUTH, true).with(Properties.EAST, true), 2);
                world.setBlockState(pos.add(5, 0, 8), Blocks.IRON_BARS.getDefaultState().with(Properties.NORTH, true).with(Properties.EAST, true), 2);
                world.setBlockState(pos.add(8, 0, 5), Blocks.IRON_BARS.getDefaultState().with(Properties.SOUTH, true).with(Properties.WEST, true), 2);
                world.setBlockState(pos.add(8, 0, 8), Blocks.IRON_BARS.getDefaultState().with(Properties.NORTH, true).with(Properties.WEST, true), 2);
                world.setBlockState(pos.add(5, 0, 7), Blocks.IRON_BARS.getDefaultState().with(Properties.NORTH, true).with(Properties.SOUTH, true), 2);
                world.setBlockState(pos.add(8, 0, 6), Blocks.IRON_BARS.getDefaultState().with(Properties.NORTH, true).with(Properties.SOUTH, true), 2);

                //Armory corners
                for (int i = 0; i < 4; i++) {
                    randIndex = rand.nextInt(5) + 5;
                    placeDecoration(world, pos.add(3 + 7*(i/2), 0, 3 + 7*Math.round(MathHelper.sin((float) (Math.PI/3*i)))), i, cornerBlocks.get(randIndex), cornerPos.get(randIndex), cornerProps.get(randIndex));
                }

                //Armory center
                randIndex = rand.nextInt(5) + 5;
                placeDecoration(world, pos.add(5, 0, 6), 3, centerBlocks.get(randIndex), centerPos.get(randIndex), centerProps.get(randIndex));
                randIndex = rand.nextInt(5) + 5;
                placeDecoration(world, pos.add(8, 0, 7), 1, centerBlocks.get(randIndex), centerPos.get(randIndex), centerProps.get(randIndex));

                fillWindows(world, pos, 0);
                break;
            case "Barracks":
                //Center Books/Beds
                for (int i = 0; i < 4; i++) {
                    world.setBlockState(pos.add(6 + i%2, 0, 6 + i/2), Blocks.OAK_PLANKS.getDefaultState(), 2);
                    world.setBlockState(pos.add(6 + i%2, 1, 6 + i/2), Blocks.BOOKSHELF.getDefaultState(), 2);
                }
                List<String> bed_sheets = Arrays.asList("white_carpet","red_carpet");
                List<Vec3i> bed_pos = Arrays.asList(Vec3i.ZERO, new Vec3i(0,0,1), new Vec3i(0,1,0), new Vec3i(0,1,1));
                List<Map<String, String>> bed_props = Arrays.asList(ImmutableMap.of("facing", "NORTH" ,"half", "TOP", "shape", "STRAIGHT"), ImmutableMap.of("facing", "SOUTH" ,"half", "TOP", "shape", "STRAIGHT"), new HashMap<>(), new HashMap<>());
                for (int i = 0; i < 4; i++) {
                    int x = 5 + 3*(i/2);
                    int z = 5 + 3*Math.round(MathHelper.sin((float) (Math.PI/3*i)));
                    List<String> bed_items = Arrays.asList("oak_stairs","oak_stairs",bed_sheets.get((i+1)%2),bed_sheets.get(i%2));
                    placeDecoration(world, pos.add(x, 0, z), (i + 1)%4, bed_items, bed_pos, bed_props);
                    if (i%2 == 0) {
                        List<String> table_items = Arrays.asList("scaffolding", "oak_pressure_plate");
                        List<Vec3i> table_pos = Arrays.asList(Vec3i.ZERO, new Vec3i(0,1,0));
                        List<Map<String, String>> table_props = Arrays.asList(ImmutableMap.of("distance", "0"), new HashMap<>());
                        placeDecoration(world, pos.add(x - 2*i + 2, 0, z), i, table_items, table_pos, table_props);
                    }
                }

                //Barracks corners
                for (int i = 0; i < 4; i++) {
                    randIndex = rand.nextInt(5) + 10;
                    placeDecoration(world, pos.add(3 + 7*(i/2), 0, 3 + 7*Math.round(MathHelper.sin((float) (Math.PI/3*i)))), i, cornerBlocks.get(randIndex), cornerPos.get(randIndex), cornerProps.get(randIndex));
                }

                //Barracks center
                randIndex = rand.nextInt(4) + 10;
                placeDecoration(world, pos.add(5, 0, 6), 3, centerBlocks.get(randIndex), centerPos.get(randIndex), centerProps.get(randIndex));
                randIndex = rand.nextInt(4) + 10;
                placeDecoration(world, pos.add(8, 0, 7), 1, centerBlocks.get(randIndex), centerPos.get(randIndex), centerProps.get(randIndex));

                fillWindows(world, pos, 1);
                break;
            case "Empty2":
                fillWindows(world, pos, 1);
                break;
            default:
                fillWindows(world, pos, 0);
                break;
        }

        //pillar
        if (Rands.chance(2)) {
            placePiece(world, pos.add(6, 0, 6), 0, pieces.get("tower_pillar"), decay);
        }
    }

    private static void InitializeDecos(String blocksString, String posString, String propsString, List<List<String>> blocks, List<List<Vec3i>> pos, List<List<Map<String, String>>> props) {
        List<String> temp1;
        temp1 = Arrays.asList(blocksString.split("; "));
        for (String i : temp1) {
            blocks.add(Arrays.asList(i.split(", ")));
        }

        temp1 = Arrays.asList(posString.split("; "));
        for (String i : temp1) {
            List<Vec3i> temp2 = new ArrayList<>();
            String[] temp3 = i.split(", ");
            for (String j : temp3) {
                List<String> temp4 = Arrays.asList(j.split(" "));
                temp2.add(new Vec3i(Integer.parseInt(temp4.get(0)), Integer.parseInt(temp4.get(1)), Integer.parseInt(temp4.get(2))));
            }
            pos.add(temp2);
        }

        temp1 = Arrays.asList(propsString.split("; "));
        for (String i : temp1) {
            List<Map<String, String>> temp2 = new ArrayList<>();
            String[] temp3 = i.split(", ");
            for (String j : temp3) {
                Map<String, String> temp4 = new HashMap<>();
                if (!j.equals("NULL")) {
                    String[] temp5 = j.split(" ");
                    for (String k : temp5) {
                        List<String> temp6 = Arrays.asList(k.split(":"));
                        temp4.put(temp6.get(0), temp6.get(1));
                    }
                }
                temp2.add(temp4);
            }
            props.add(temp2);
        }
    }
}