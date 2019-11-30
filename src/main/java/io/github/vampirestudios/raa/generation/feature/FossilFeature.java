package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.WorldStructureManipulation;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class FossilFeature extends Feature<DefaultFeatureConfig> {
    private JsonConverter converter = new JsonConverter();
    private Map<String, JsonConverter.StructureValues> structures = new HashMap<String, JsonConverter.StructureValues>() {{
        put("fossil1", converter.loadStructure("fossils/fossil01.json"));
        put("fossil2", converter.loadStructure("fossils/fossil02.json"));
        put("fossil3", converter.loadStructure("fossils/fossil03.json"));
        put("fossil4", converter.loadStructure("fossils/fossil04.json"));
    }};

    public FossilFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (pos.getY() < 9 || !world.getBlockState(pos.add(0, -1, 0)).isOpaque() || world.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState()))
            return true;

        int yChosen = new Random().nextInt(25) + 4;
        while (pos.getY() - yChosen < 5) {
            yChosen = new Random().nextInt(25) + 4;
        }
        pos.add(0, -yChosen, 0);
        JsonConverter.StructureValues fossilChosen = structures.get("fossil" + (new Random().nextInt(structures.size()) + 1));
        int rotation = new Random().nextInt(4);
        for (int i = 0; i < fossilChosen.getBlockPositions().size(); i++) {
            if (!Rands.chance(6)) {
                Vec3i currBlockPos = fossilChosen.getBlockPositions().get(i);
                String currBlockType = fossilChosen.getBlockTypes().get(fossilChosen.getBlockStates().get(i));
                Map<String, String> currBlockProp = fossilChosen.getBlockProperties().get(fossilChosen.getBlockStates().get(i));

                currBlockPos = WorldStructureManipulation.rotatePos(rotation, currBlockPos, fossilChosen.getSize());

                WorldStructureManipulation.PlaceBlock(world, pos.add(currBlockPos), currBlockType, currBlockProp, rotation);
            }
        }

        try {
            World world2 = world.getWorld();
            BufferedWriter writer = new BufferedWriter(new FileWriter("saves/" + ((ServerWorld) world2).getSaveHandler().getWorldDir().getName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/fossil_spawns.txt", true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}