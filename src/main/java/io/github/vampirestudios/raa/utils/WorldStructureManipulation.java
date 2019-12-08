package io.github.vampirestudios.raa.utils;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;

import java.util.*;

public class WorldStructureManipulation {
    public static Vec3i CircularSpawnCheck(IWorld world, BlockPos pos, Vec3i size, float TOLERANCE) {
        //Make sure the structure can spawn here
        int xOrigin = pos.getX();
        int zOrigin = pos.getZ();
        Vec3i newPos = Vec3i.ZERO;

        List<List<Float>> flatnessList = new ArrayList<>();
        for (float xOffset = xOrigin - size.getX(); xOffset < xOrigin + size.getX(); xOffset++) {
            for (float zOffset = zOrigin - size.getZ(); zOffset < zOrigin + size.getZ(); zOffset++) {
                float yOffset = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, new BlockPos(xOffset, 0, zOffset)).getY();
                boolean nonSpawnable = yOffset < 5 || (!world.getBlockState(new BlockPos(xOffset, yOffset - 1, zOffset)).isOpaque() && !world.getBlockState(new BlockPos(xOffset, yOffset - 2, zOffset)).isOpaque()) || world.getBlockState(new BlockPos(xOffset, yOffset - 1, zOffset)).equals(Blocks.BEDROCK.getDefaultState());
                if (xOffset < xOrigin + 3 && zOffset < zOrigin + 3) {
                    flatnessList.add(Arrays.asList(xOffset, yOffset, zOffset, 0f));
                }
                for (List<Float> flatness : flatnessList) {
                    if (Math.pow((xOffset - flatness.get(0)) - (size.getX() - 3) / 2f, 2) + Math.pow(zOffset - flatness.get(2) - (size.getX() - 3) / 2f, 2) < Math.pow((size.getX() - 2) / 2f, 2)) {
                        if (yOffset > flatness.get(1) - 3 && yOffset <= flatness.get(1)) {
                            if (yOffset == flatness.get(1)) {
                                flatness.set(3, flatness.get(3) + 1f);
                            } else if (yOffset == flatness.get(1) - 1) {
                                flatness.set(3, flatness.get(3) + 0.5f);
                            } else {
                                flatness.set(3, flatness.get(3) + 0.25f);
                            }
                        }
                        if (nonSpawnable) {
                            flatness.set(3, (float) -Math.pow(size.getX(), 2));
                        }
                    }
                }
            }
        }
        float maxFlatness = -1;
        int chosen = -1;
        for (int i = 0; i < flatnessList.size(); i++) {
            if (flatnessList.get(i).get(3) > maxFlatness) {
                maxFlatness = flatnessList.get(i).get(3);
                chosen = i;
            }
        }
        if (chosen != -1) {
            int xChosen = flatnessList.get(chosen).get(0).intValue();
            int yChosen = flatnessList.get(chosen).get(1).intValue();
            int zChosen = flatnessList.get(chosen).get(2).intValue();
            newPos = trySpawning(world, new BlockPos(xChosen, yChosen, zChosen), size, TOLERANCE);
        }

        if (newPos.compareTo(Vec3i.ZERO) == 0 || newPos.getY() > 255 - size.getY()) {
            /*if (maxFlatness > 20) {
                System.out.println("---Failed to spawn! Origin Coords: " + xOrigin + "/" + yOrigin + "/" + zOrigin);
                System.out.println("New Coords: " + pos.getX() + "/" + pos.getY() + "/" + pos.getZ());
                System.out.println("Flatness: " + maxFlatness);
            }*/
            return Vec3i.ZERO;
        }

        return newPos;
    }

    private static Vec3i trySpawning(IWorld world, BlockPos pos, Vec3i size, float TOLERANCE) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir() || world.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState())) {
            return Vec3i.ZERO;
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
                if (Math.pow(xIndent - (size.getX() - 3) / 2f, 2) + Math.pow(zIndent - (size.getX() - 3) / 2f, 2) < Math.pow((size.getX() - 2) / 2f, 2)) {
                    if (!world.getBlockState(new BlockPos(pos.add(xIndent, -1, zIndent))).isOpaque() && !world.getBlockState(new BlockPos(pos.add(xIndent, -2, zIndent))).isOpaque()) {
                        return Vec3i.ZERO;
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
                        if (tempHeights.get(i) < 0 || tempHeights.get(i) > 255) {
                            return Vec3i.ZERO;
                        }
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
        int area = (int) (Math.PI*Math.pow((size.getX() - 2)/2f, 2));
        //TODO: This is where the TOLERANCE for generation is used, which ranges from 0 to 1. The lower this is, the more strict the tower generation is. Increase it for wacky generation.
        TOLERANCE = (TOLERANCE > 1f) ? 1f : Math.max(TOLERANCE, 0f);
        if (maxHeight - minHeight > 3 && maxHeight*area - totalHeight > area*((maxHeight - minHeight)/2f * TOLERANCE) && maxHeight*area - totalHeight < area*((maxHeight - minHeight)*(1 - TOLERANCE/2f))) {
            return Vec3i.ZERO;
        }

        return (pos.add(0, modeHeight - pos.getY(), 0));
    }

    public static void placeBlock(IWorld world, BlockPos pos, String block, Map<String, String> properties, int rotation) {
        //Place block
        world.setBlockState(pos, Registry.BLOCK.get(Identifier.tryParse(block)).getDefaultState(), 2);

        //Rotate stuff
        String facing = "NORTH";
        List<String> directions = Arrays.asList("FALSE", "FALSE", "FALSE", "FALSE");
        String axis = "x";
        if (properties.get("facing") != null) {
            facing = properties.get("facing");
            if (!facing.equals("UP") && !facing.equals("DOWN")) {
                facing = rotateDir(rotation, facing);
            }
        }
        if (properties.get("north") != null || properties.get("west") != null || properties.get("south") != null || properties.get("east") != null) {
            directions = rotateWall(rotation, Arrays.asList(properties.get("north"), properties.get("west"), properties.get("south"), properties.get("east")));
        }
        if (properties.get("axis") != null) {
            axis = properties.get("axis");
            axis = (rotation % 2 == 0) ? axis : (axis.equals("x")) ? "z" : (axis.equals("z")) ? "x" : axis;
        }

        //Give properties to block
        if (world.getBlockState(pos) == Registry.BLOCK.get(Identifier.tryParse(block)).getDefaultState()) { //Make sure the block you're trying to apply this stuff to *actually exists*
            if (properties.get("waterlogged") != null) {
                //TODO: ~~
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.WATERLOGGED, properties.get("waterlogged").equals("TRUE")), 2);
            } if (properties.get("type") != null) {
                if (block.equals("minecraft:chest")) {
                    //TODO: [Chests]
                    world.setBlockState(pos, world.getBlockState(pos).with(Properties.CHEST_TYPE, ChestType.valueOf(properties.get("type"))), 2);
                } else {
                    //TODO: [Slabs]
                    world.setBlockState(pos, world.getBlockState(pos).with(Properties.SLAB_TYPE, SlabType.valueOf(properties.get("type"))), 2);
                }
            } if (properties.get("half") != null) {
                //TODO: [Stairs]
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.BLOCK_HALF, BlockHalf.valueOf(properties.get("half"))), 2);
            } if (properties.get("shape") != null) {
                //TODO: [Stairs]
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.STAIR_SHAPE, StairShape.valueOf(properties.get("shape"))), 2);
            } if (properties.get("facing") != null) {
                if (block.equals("minecraft:barrel")) {
                    //TODO: Barrel
                    world.setBlockState(pos, world.getBlockState(pos).with(Properties.FACING, Direction.valueOf(facing)), 2);
                } else {
                    //TODO: [Anvils], [Chests], [Stairs], Bell, Blast_Furnace, Furnace, Grindstone, Smoker, Stonecutter,
                    world.setBlockState(pos, world.getBlockState(pos).with(Properties.HORIZONTAL_FACING, Direction.valueOf(facing)), 2);
                }
            } if (properties.get("north") != null || properties.get("west") != null || properties.get("south") != null || properties.get("east") != null) {
                //TODO: [Fences], [Walls], Iron bar
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.NORTH, (directions.get(0) != null && directions.get(0).equals("TRUE"))), 2);
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.WEST, (directions.get(1) != null && directions.get(1).equals("TRUE"))), 2);
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.SOUTH, (directions.get(2) != null && directions.get(2).equals("TRUE"))), 2);
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.EAST, (directions.get(3) != null && directions.get(3).equals("TRUE"))), 2);
            } if (properties.get("up") != null) {
                //TODO: [Walls]
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.UP, properties.get("up").equals("TRUE")), 2);
            } if (properties.get("open") != null) {
                //TODO: Barrel
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.OPEN, properties.get("open").equals("TRUE")), 2);
            } if (properties.get("snowy") != null) {
                //TODO: Grass_Block
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.SNOWY, properties.get("snowy").equals("TRUE")), 2);
            } if (properties.get("lit") != null) {
                //TODO: Blast_Furnace, Furnace, Smoker
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.LIT, properties.get("lit").equals("TRUE")), 2);
            } if (properties.get("bottom") != null) {
                //TODO: ~~
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.BOTTOM, properties.get("bottom").equals("TRUE")), 2);
            } if (properties.get("hanging") != null) {
                //TODO: Lantern
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.HANGING, properties.get("hanging").equals("TRUE")), 2);
            } if (properties.get("powered") != null) {
                //TODO: [Pressure_Plates], Bell
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.POWERED, properties.get("powered").equals("TRUE")), 2);
            } if (properties.get("unstable") != null) {
                //TODO: TNT
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.UNSTABLE, properties.get("unstable").equals("TRUE")), 2);
            } if (properties.get("face") != null) {
                //TODO: Grindstone
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.WALL_MOUNT_LOCATION, WallMountLocation.valueOf(properties.get("face"))), 2);
            } if (properties.get("distance") != null) {
                //TODO: Scaffolding
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.DISTANCE_0_7, Integer.parseInt(properties.get("distance"))), 2);
            } if (properties.get("attachment") != null) {
                //TODO: Bell
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.ATTACHMENT, Attachment.valueOf(properties.get("attachment"))), 2);
            } if (properties.get("axis") != null) {
                //TODO: Bone_Block
                world.setBlockState(pos, world.getBlockState(pos).with(Properties.AXIS, Direction.Axis.fromName(axis)), 2);
            }
        }

        //world.setBlockState(pos, StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.valueOf(dir)).with(Properties.WATERLOGGED, properties.get("waterlogged").equals("TRUE"))), 2);
    }

    public static void spawnEntity(IWorld world, BlockPos pos, String entity, Map<String, String> properties, float rotation) {
        if (entity.equals("minecraft:armor_stand")) {
            Entity armorStand = EntityType.ARMOR_STAND.create(world.getWorld());

            if (properties.get("armor") != null) {
                if (properties.get("armor").equals("ALL")) {
                    //Put armor on the stand
                    List<ItemStack> helmets = ImmutableList.of(
                            new ItemStack(Items.LEATHER_HELMET),
                            new ItemStack(Items.CHAINMAIL_HELMET),
                            new ItemStack(Items.GOLDEN_HELMET),
                            new ItemStack(Items.IRON_HELMET),
                            new ItemStack(Items.DIAMOND_HELMET)
                    );
                    List<ItemStack> chestplates = ImmutableList.of(
                            new ItemStack(Items.LEATHER_CHESTPLATE),
                            new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                            new ItemStack(Items.GOLDEN_CHESTPLATE),
                            new ItemStack(Items.IRON_CHESTPLATE),
                            new ItemStack(Items.DIAMOND_CHESTPLATE)
                    );
                    List<ItemStack> leggings = ImmutableList.of(
                            new ItemStack(Items.LEATHER_LEGGINGS),
                            new ItemStack(Items.CHAINMAIL_LEGGINGS),
                            new ItemStack(Items.GOLDEN_LEGGINGS),
                            new ItemStack(Items.IRON_LEGGINGS),
                            new ItemStack(Items.DIAMOND_LEGGINGS)
                    );
                    List<ItemStack> boots = ImmutableList.of(
                            new ItemStack(Items.LEATHER_BOOTS),
                            new ItemStack(Items.CHAINMAIL_BOOTS),
                            new ItemStack(Items.GOLDEN_BOOTS),
                            new ItemStack(Items.IRON_BOOTS),
                            new ItemStack(Items.DIAMOND_BOOTS)
                    );
                    Objects.requireNonNull(armorStand).setPositionAndAngles(pos, rotation, 0f);
                    world.spawnEntity(armorStand);
                    armorStand.equipStack(EquipmentSlot.HEAD, Rands.list(helmets));
                    armorStand.equipStack(EquipmentSlot.CHEST, Rands.list(chestplates));
                    armorStand.equipStack(EquipmentSlot.LEGS, Rands.list(leggings));
                    armorStand.equipStack(EquipmentSlot.FEET, Rands.list(boots));
                }
            } else {
                List<ItemStack> helmets = ImmutableList.of(
                        new ItemStack(Items.LEATHER_HELMET),
                        new ItemStack(Items.CHAINMAIL_HELMET),
                        new ItemStack(Items.GOLDEN_HELMET),
                        new ItemStack(Items.IRON_HELMET),
                        new ItemStack(Items.DIAMOND_HELMET)
                );
                List<ItemStack> chestplates = ImmutableList.of(
                        new ItemStack(Items.LEATHER_CHESTPLATE),
                        new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                        new ItemStack(Items.GOLDEN_CHESTPLATE),
                        new ItemStack(Items.IRON_CHESTPLATE),
                        new ItemStack(Items.DIAMOND_CHESTPLATE)
                );
                List<ItemStack> leggings = ImmutableList.of(
                        new ItemStack(Items.LEATHER_LEGGINGS),
                        new ItemStack(Items.CHAINMAIL_LEGGINGS),
                        new ItemStack(Items.GOLDEN_LEGGINGS),
                        new ItemStack(Items.IRON_LEGGINGS),
                        new ItemStack(Items.DIAMOND_LEGGINGS)
                );
                List<ItemStack> boots = ImmutableList.of(
                        new ItemStack(Items.LEATHER_BOOTS),
                        new ItemStack(Items.CHAINMAIL_BOOTS),
                        new ItemStack(Items.GOLDEN_BOOTS),
                        new ItemStack(Items.IRON_BOOTS),
                        new ItemStack(Items.DIAMOND_BOOTS)
                );
                Objects.requireNonNull(armorStand).setPositionAndAngles(pos, rotation, 0f);
                world.spawnEntity(armorStand);
                armorStand.equipStack(EquipmentSlot.HEAD, Rands.list(helmets));
                armorStand.equipStack(EquipmentSlot.CHEST, Rands.list(chestplates));
                armorStand.equipStack(EquipmentSlot.LEGS, Rands.list(leggings));
                armorStand.equipStack(EquipmentSlot.FEET, Rands.list(boots));
            }
        }
    }

    public static Vec3i rotatePos(int rotation, Vec3i pos, Vec3i size) {
        int x = pos.getX();
        int z = pos.getZ();
        if (rotation == 1) {
            int xTemp = pos.getX();
            x = size.getX() - 1 - z;
            z = xTemp;
        } else if (rotation == 2) {
            x = size.getX() - 1 - x;
            z = size.getZ() - 1 - z;
        } else if (rotation == 3) {
            int xTemp = x;
            x = z;
            z = size.getZ() - 1 - xTemp;
        }
        return (new Vec3i(x, pos.getY(), z));
    }

    private static String rotateDir(int rotation, String direction) {
        if (rotation > 0) {
            String dir = rotateDir(rotation - 1, direction);
            return (dir.equals("NORTH") ? "WEST" : (dir.equals("WEST")) ? "SOUTH" : (dir.equals("SOUTH")) ? "EAST" : "NORTH");
        } else {
            return direction;
        }
    }

    private static List<String> rotateWall(int rotation, List<String> directions) {
        if (rotation > 0) {
            List<String> dirs = rotateWall(rotation - 1, directions);
            return (Arrays.asList(dirs.get(3), dirs.get(0), dirs.get(1), dirs.get(2)));
        } else {
            return directions;
        }
    }
}