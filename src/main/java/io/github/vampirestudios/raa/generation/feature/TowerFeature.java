package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.enums.Attachment;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
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
    JsonConverter converter = new JsonConverter();
    Map<String, JsonConverter.StructureValues> structures = new HashMap<String, JsonConverter.StructureValues>() {{
        put("tower_base", converter.LoadStructure("tower/tower_base.json"));
        put("tower_walls", converter.LoadStructure("tower/tower_walls.json"));
        put("tower_stairs", converter.LoadStructure("tower/tower_stairs.json"));
        put("tower_ladders", converter.LoadStructure("tower/tower_ladders.json"));
        put("tower_pillar", converter.LoadStructure("tower/tower_pillar.json"));
        put("tower_roof", converter.LoadStructure("tower/tower_roof.json"));
    }};

    public TowerFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

        //Make sure the structure can spawn here
        int x_origin = pos.getX();
        int y_origin = pos.getY();
        int z_origin = pos.getZ();

        List<List<Float>> Flatness = new ArrayList<>();
        for (float x_offset = x_origin - 14; x_offset < x_origin + 14; x_offset++) {
            for (float z_offset = z_origin - 14; z_offset < z_origin + 14; z_offset++) {
                float y_offset = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, new BlockPos(x_offset, 0, z_offset)).getY();
                boolean non_spawnable = world.isAir(new BlockPos(x_offset, y_offset - 1, z_offset)) || (!world.getBlockState(new BlockPos(x_offset, y_offset - 1, z_offset)).isOpaque() && !world.getBlockState(new BlockPos(x_offset, y_offset - 2, z_offset)).isOpaque());
                if (x_offset < x_origin + 3 && z_offset < z_origin + 3) {
                    Flatness.add(Arrays.asList(x_offset, y_offset, z_offset, 0f));
                }
                for (int i = 0; i < Flatness.size(); i++) {
                    if (Math.pow(( x_offset - Flatness.get(i).get(0)) - 5.5f, 2) + Math.pow(z_offset - Flatness.get(i).get(2) - 5.5f, 2) < Math.pow(6, 2)) {
                        if (y_offset > Flatness.get(i).get(1) - 3 && y_offset <= Flatness.get(i).get(1)) {
                            if (y_offset == Flatness.get(i).get(1)) {
                                Flatness.get(i).set(3, Flatness.get(i).get(3) + 1f);
                            } else if (y_offset == Flatness.get(i).get(1) - 1) {
                                Flatness.get(i).set(3, Flatness.get(i).get(3) + 0.5f);
                            } else {
                                Flatness.get(i).set(3, Flatness.get(i).get(3) + 0.25f);
                            }
                        }
                        if (non_spawnable) {
                            Flatness.get(i).set(3, -144f);
                        }
                    }
                }
            }
        }
        float max_flatness = -1;
        int chosen = -1;
        for (int i = 0; i < Flatness.size(); i++) {
            if (Flatness.get(i).get(3) > max_flatness) {
                max_flatness = Flatness.get(i).get(3);
                chosen = i;
            }
        }
        boolean working_spawn = false;
        if (chosen != -1) {
            int x_chosen = Flatness.get(chosen).get(0).intValue();
            int y_chosen = Flatness.get(chosen).get(1).intValue();
            int z_chosen = Flatness.get(chosen).get(2).intValue();
            pos = new BlockPos(x_chosen, y_chosen, z_chosen);
            working_spawn = TrySpawning(world, pos);
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
            PlaceRoom(world, pos.add(0, -6, 0), structures, "Storage", -2);
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
                PlaceRoom(world, pos.add(0, 1 + level*7, 0), structures, room_name, 2*level + 2);
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

        return true;
    }

    public static boolean TrySpawning(IWorld world, BlockPos pos) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir()) {
            return false;
        }
        Map<Integer, Float> heights = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            heights.put(i, 0f);
        }
        int totalHeight = 0;
        Float maxFreq = 0f;
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
                        Float tempFreqs = heights.get(tempHeights.get(i)) + tempFloats.get(i);
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

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("saves/" + world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/tower_spawns.txt", true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  true;
    }

    public static void placePiece(IWorld world, BlockPos pos, int rotation, JsonConverter.StructureValues piece, int decay) {
        for (int i = 0; i < piece.getBlock_positions().size(); i++) {
            List<Integer> currBlockPos = piece.getBlock_positions().get(i);
            String currBlockType = piece.getBlock_types().get(piece.getBlock_states().get(i));
            int x = currBlockPos.get(0);
            int y = currBlockPos.get(1);
            int z = currBlockPos.get(2);

            //Rotate
            Direction direction = Rotate(((rotation + (rotation%2)*2 - 1)%4 + 4)%4, 0);
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
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse("raa:" + (world.getDimension().getType().getSuffix()).substring(4) + "_stone_bricks")).getDefaultState(), 2);
                } else if (currBlockType.equals("minecraft:ladder")) {
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse(currBlockType)).getDefaultState().with(Properties.HORIZONTAL_FACING, direction), 2);
                } else {
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse(currBlockType)).getDefaultState(), 2);
                }
            }
        }
    }

    public static void FillWindows(IWorld world, BlockPos pos, int fill) {
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

    public static void PlaceDecos(IWorld world, BlockPos pos, int rotation, List<String> blocks, List<List<Integer>> blockPos) {
        if (!world.isAir(pos.add(0, -1, 0))) {
            for (int i = 0; i < blockPos.size(); i++) {
                int x = blockPos.get(i).get(0);
                int y = blockPos.get(i).get(1);
                int z = blockPos.get(i).get(2);
                String currBlock = blocks.get(i);

                //Rotate
                int xTemp = x;
                x = (x + z)*Math.round(MathHelper.cos((float) (Math.PI / 2f * (rotation - z))));
                z = (xTemp + z)*-Math.round(MathHelper.sin((float) (Math.PI / 2f * (rotation - z))));
                Direction direction = Rotate(rotation, 0);

                if (currBlock.equals("barrel")) {
                    int dir = new Random().nextInt(3);
                    if (dir == 0 || blocks.size() == 1) {
                        direction = Direction.UP;
                    } else {
                        direction = Rotate(rotation, (dir + 3)%4);
                    }
                    world.setBlockState(pos.add(x, y, z), Blocks.BARREL.getDefaultState().with(Properties.FACING, direction), 2);
                } else if (currBlock.equals("smoker") || currBlock.equals("blast_furnace") || currBlock.equals("furnace") || currBlock.equals("wall_torch") || currBlock.equals("stonecutter")) {
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse("minecraft:" + currBlock)).getDefaultState().with(Properties.HORIZONTAL_FACING, direction), 2);
                } else if (currBlock.contains("chest")) {
                    world.setBlockState(pos.add(x, y, z), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, direction)), 2);
                    if (currBlock.equals("chest1")) {
                        LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(x, y, z), LootTables.SHIPWRECK_SUPPLY_CHEST);
                    } else if (currBlock.equals("chest2")) {
                        LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(x, y, z), LootTables.VILLAGE_WEAPONSMITH_CHEST);
                    } else {
                        LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(x, y, z), LootTables.STRONGHOLD_LIBRARY_CHEST);
                    }
                } else if (currBlock.contains("armor_stand")) {
                    Entity armorStand = new Entity(EntityType.ARMOR_STAND, world.getWorld()) {
                        @Override
                        protected void initDataTracker() { }

                        @Override
                        protected void readCustomDataFromTag(CompoundTag compoundTag) { }

                        @Override
                        protected void writeCustomDataToTag(CompoundTag compoundTag) { }

                        @Override
                        public Packet<?> createSpawnPacket() { return null; }
                    };
                    float stand_rotation = 90f;
                    if (currBlock.equals("armor_stand1")) {
                        if (rotation% 2 == 0) {
                            stand_rotation = -45f;
                        } else {
                            stand_rotation = 45f;
                        }
                    } else {
                        //Put armor on the stand
                    }
                    armorStand.setPositionAndAngles(pos.add(x, y, z), stand_rotation, 0f);
                    world.spawnEntity(armorStand);
                } else if (currBlock.equals("oak_stairs")) {
                    if (blocks.size() == 3) {
                        world.setBlockState(pos.add(x, y, z), Blocks.OAK_STAIRS.getDefaultState().with(Properties.HORIZONTAL_FACING, direction), 2);
                    } else if (x != z) {
                        world.setBlockState(pos.add(x, y, z), Blocks.OAK_STAIRS.getDefaultState().with(Properties.HORIZONTAL_FACING, direction).with(Properties.BLOCK_HALF, BlockHalf.TOP), 2);
                    } else {
                        world.setBlockState(pos.add(x, y, z), Blocks.OAK_STAIRS.getDefaultState().with(Properties.HORIZONTAL_FACING, Rotate(rotation, 2)).with(Properties.BLOCK_HALF, BlockHalf.TOP), 2);
                    }
                } else if (currBlock.equals("potted_")) {
                    String[] plants = { "white_tulip", "spruce_sapling", "red_tulip", "red_mushroom", "poppy", "pink_tulip", "oxeye_daisy", "orange_tulip",
                                        "oak_sapling", "lily_of_the_valley", "jungle_sapling", "fern", "dead_bush", "dark_oak_sapling", "dandelion", "cactus",
                                        "brown_mushroom", "blue_orchid", "birch_sapling", "bamboo", "azure_bluet", "allium", "acacia_sapling", "cornflower"};
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse("minecraft:" + currBlock + plants[new Random().nextInt(plants.length)])).getDefaultState(), 2);
                } else if (currBlock.equals("grindstone")) {
                    world.setBlockState(pos.add(x, y, z), Blocks.GRINDSTONE.getDefaultState().with(Properties.WALL_MOUNT_LOCATION, WallMountLocation.FLOOR), 2);
                } else if (currBlock.equals("bell")) {
                    world.setBlockState(pos.add(x, y, z), Blocks.BELL.getDefaultState().with(Properties.HORIZONTAL_FACING, Rotate(rotation, 2)).with(Properties.ATTACHMENT, Attachment.SINGLE_WALL), 2);
                } else if (currBlock.equals("iron_bars")) {
                    if (blocks.size() == 1) {
                        world.setBlockState(pos.add(x, y, z), Blocks.IRON_BARS.getDefaultState().with(Properties.NORTH, true).with(Properties.SOUTH, true), 2);
                    }
                    else {
                        world.setBlockState(pos.add(x, y, z), Blocks.IRON_BARS.getDefaultState(), 2);
                        if (!world.isAir(pos.add(x + 1, y, z)) || (x == z && y == 0)) {
                            world.setBlockState(pos.add(x, y, z), world.getBlockState(pos.add(x, y, z)).with(Properties.EAST, true), 2);
                        }
                        if (!world.isAir(pos.add(x - 1, y, z)) || (x == z && y == 0)) {
                            world.setBlockState(pos.add(x, y, z), world.getBlockState(pos.add(x, y, z)).with(Properties.WEST, true), 2);
                        }
                        if (!world.isAir(pos.add(x, y, z + 1)) || (x == z && y == 0)) {
                            world.setBlockState(pos.add(x, y, z), world.getBlockState(pos.add(x, y, z)).with(Properties.SOUTH, true), 2);
                        }
                        if (!world.isAir(pos.add(x, y, z - 1)) || (x == z && y == 0)) {
                            world.setBlockState(pos.add(x, y, z), world.getBlockState(pos.add(x, y, z)).with(Properties.NORTH, true), 2);
                        }
                    }
                } else {
                    world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse("minecraft:" + currBlock)).getDefaultState(), 2);
                }
            }
        }
    }

    public static void PlaceRoom(IWorld world, BlockPos pos, Map<String, JsonConverter.StructureValues> Pieces, String type, int decay) {
        //walls
        placePiece(world, pos.add(1, 0, 1), 0, Pieces.get("tower_walls"), decay + 2);
        //stairs/ladders
        if (Rands.chance(2)) {
            placePiece(world, pos, new Random().nextInt(4), Pieces.get("tower_stairs"), decay - 1);
        } else {
            placePiece(world, pos, new Random().nextInt(4), Pieces.get("tower_ladders"), decay - 1);
        }

        Map<List<String>, List<List<Integer>>> cornerItems = new HashMap<>();
        Map<List<String>, List<List<Integer>>> centerItems = new HashMap<>();
        Object[] names;
        Random rand = new Random();
        List<String> item;

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

                //Populate corner items
                cornerItems.clear();
                cornerItems.put(Arrays.asList("barrel", "barrel", "barrel", "barrel"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,0,1), Arrays.asList(1,0,0), Arrays.asList(0,1,0)));
                cornerItems.put(Arrays.asList("wall_torch"), Arrays.asList(Arrays.asList(0,1,0)));
                cornerItems.put(Arrays.asList("smoker"), Arrays.asList(Arrays.asList(0,0,0)));
                cornerItems.put(Arrays.asList("furnace"), Arrays.asList(Arrays.asList(0,0,0)));
                cornerItems.put(Arrays.asList("crafting_table"), Arrays.asList(Arrays.asList(0,0,0)));
                names = cornerItems.keySet().toArray();
                for (int i = 0; i < 4; i++) {
                    item = (List<String>) names[rand.nextInt(5)];
                    PlaceDecos(world, pos.add(3 + 7*(i/2), 0, 3 + 7*Math.round(MathHelper.sin((float) (Math.PI/3*i)))), i, item, cornerItems.get(item));
                }

                //Populate center items
                centerItems.clear();
                centerItems.put(Arrays.asList("chest1", "air"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,0,1)));
                centerItems.put(Arrays.asList("barrel"), Arrays.asList(Arrays.asList(0,0,0)));
                centerItems.put(Arrays.asList("stonecutter"), Arrays.asList(Arrays.asList(0,0,0)));
                centerItems.put(Arrays.asList("tnt"), Arrays.asList(Arrays.asList(0,0,0)));
                centerItems.put(Arrays.asList("oak_fence", "oak_pressure_plate"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,1,0)));
                names = centerItems.keySet().toArray();
                item = (List<String>) names[rand.nextInt(5)];
                PlaceDecos(world, pos.add(5, 0, 6), 3, item, centerItems.get(item));
                item = (List<String>) names[rand.nextInt(5)];
                PlaceDecos(world, pos.add(8, 0, 7), 1, item, centerItems.get(item));

                FillWindows(world, pos, 2);
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

                //Populate corner items
                cornerItems.clear();
                cornerItems.put(Arrays.asList("iron_bars","iron_bars","iron_bars","iron_bars"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,0,1), Arrays.asList(1,0,0), Arrays.asList(0,1,0)));
                cornerItems.put(Arrays.asList("oak_fence", "oak_pressure_plate"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,1,0)));
                cornerItems.put(Arrays.asList("smithing_table"), Arrays.asList(Arrays.asList(0,0,0)));
                cornerItems.put(Arrays.asList("blast_furnace"), Arrays.asList(Arrays.asList(0,0,0)));
                cornerItems.put(Arrays.asList("armor_stand1"), Arrays.asList(Arrays.asList(0,0,0)));
                names = cornerItems.keySet().toArray();
                for (int i = 0; i < 4; i++) {
                    item = (List<String>) names[rand.nextInt(5)];
                    PlaceDecos(world, pos.add(3 + 7*(i/2), 0, 3 + 7*Math.round(MathHelper.sin((float) (Math.PI/3*i)))), i, item, cornerItems.get(item));
                }

                //Populate center items
                centerItems.clear();
                centerItems.put(Arrays.asList("chest2", "air"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,0,1)));
                centerItems.put(Arrays.asList("iron_bars"), Arrays.asList(Arrays.asList(0,0,0)));
                centerItems.put(Arrays.asList("damaged_anvil"), Arrays.asList(Arrays.asList(0,0,0)));
                centerItems.put(Arrays.asList("grindstone"), Arrays.asList(Arrays.asList(0,0,0)));
                centerItems.put(Arrays.asList("armor_stand2"), Arrays.asList(Arrays.asList(0,0,0)));
                names = centerItems.keySet().toArray();
                item = (List<String>) names[rand.nextInt(5)];
                PlaceDecos(world, pos.add(5, 0, 6), 3, item, centerItems.get(item));
                item = (List<String>) names[rand.nextInt(5)];
                PlaceDecos(world, pos.add(8, 0, 7), 1, item, centerItems.get(item));

                FillWindows(world, pos, 0);
                break;
            case "Barracks":
                //Center Books/Beds
                for (int i = 0; i < 4; i++) {
                    world.setBlockState(pos.add(6 + i%2, 0, 6 + i/2), Blocks.OAK_PLANKS.getDefaultState(), 2);
                    world.setBlockState(pos.add(6 + i%2, 1, 6 + i/2), Blocks.BOOKSHELF.getDefaultState(), 2);
                }
                List<String> bed_sheets = Arrays.asList("white_carpet","red_carpet");
                List<List<Integer>> bed_pos = Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,0,1), Arrays.asList(0,1,0), Arrays.asList(0,1,1));
                for (int i = 0; i < 4; i++) {
                    int x = 5 + 3*(i/2);
                    int z = 5 + 3*Math.round(MathHelper.sin((float) (Math.PI/3*i)));
                    List<String> bed_items = Arrays.asList("oak_stairs","oak_stairs",bed_sheets.get((i+1)%2),bed_sheets.get(i%2));
                    PlaceDecos(world, pos.add(x, 0, z), (i + 1)%4, bed_items, bed_pos);
                    if (i%2 == 0) {
                        List<String> table_items = Arrays.asList("scaffolding", "oak_pressure_plate");
                        List<List<Integer>> table_pos = Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,1,0));
                        PlaceDecos(world, pos.add(x - 2*i + 2, 0, z), i, table_items, table_pos);
                    }
                }

                //Populate corner items
                cornerItems.clear();
                cornerItems.put(Arrays.asList("oak_stairs","oak_stairs","white_carpet","red_carpet"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,0,1), Arrays.asList(0,1,0), Arrays.asList(0,1,1)));
                cornerItems.put(Arrays.asList("oak_fence", "oak_pressure_plate", "oak_stairs"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,1,0), Arrays.asList(0,0,1)));
                cornerItems.put(Arrays.asList("scaffolding", "potted_"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,1,0)));
                cornerItems.put(Arrays.asList("bookshelf", "bookshelf"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,1,0)));
                cornerItems.put(Arrays.asList("armor_stand1"), Arrays.asList(Arrays.asList(0,0,0)));
                names = cornerItems.keySet().toArray();
                for (int i = 0; i < 4; i++) {
                    item = (List<String>) names[rand.nextInt(5)];
                    PlaceDecos(world, pos.add(3 + 7*(i/2), 0, 3 + 7*Math.round(MathHelper.sin((float) (Math.PI/3*i)))), i, item, cornerItems.get(item));
                }

                //Populate center items
                centerItems.clear();
                centerItems.put(Arrays.asList("chest3", "air"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,0,1)));
                centerItems.put(Arrays.asList("bell"), Arrays.asList(Arrays.asList(0,1,0)));
                centerItems.put(Arrays.asList("scaffolding", "oak_pressure_plate"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,1,0)));
                centerItems.put(Arrays.asList("scaffolding", "lantern"), Arrays.asList(Arrays.asList(0,0,0), Arrays.asList(0,1,0)));
                names = centerItems.keySet().toArray();
                item = (List<String>) names[rand.nextInt(4)];
                PlaceDecos(world, pos.add(5, 0, 6), 3, item, centerItems.get(item));
                item = (List<String>) names[rand.nextInt(4)];
                PlaceDecos(world, pos.add(8, 0, 7), 1, item, centerItems.get(item));

                FillWindows(world, pos, 1);
                break;
            case "Empty2":
                FillWindows(world, pos, 1);
                break;
            default:
                FillWindows(world, pos, 0);
                break;
        }

        //pillar
        if (Rands.chance(2)) {
            placePiece(world, pos.add(6, 0, 6), 0, Pieces.get("tower_pillar"), decay);
        }
    }

    public static Direction Rotate(int rotation, int amount) {
        if (amount > 0) {
            Direction new_dir = Rotate(rotation, amount - 1);
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