package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
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
    JsonConverter converter = new JsonConverter();
    Map<String, JsonConverter.StructureValues> structures = new HashMap<String, JsonConverter.StructureValues>() {{
        put("fossil1", converter.LoadStructure("fossils/fossil01.json"));
    }};

    public FossilFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (pos.getY() < 5 || !world.getBlockState(pos.add(0, -1, 0)).isOpaque())
            return true;

        int yChosen = new Random().nextInt(25);
        while (pos.getY() - yChosen < 5) {
            yChosen = new Random().nextInt(25);
        }
        JsonConverter.StructureValues fossilChosen = structures.get("fossil" + Integer.toString(new Random().nextInt(structures.size()) + 1));
        int rotation = new Random().nextInt(4);
        for (int i = 0; i < fossilChosen.getBlock_positions().size(); i++) {
            if (!Rands.chance(6)) {
                List<Integer> currBlockPos = fossilChosen.getBlock_positions().get(i);
                String currBlockType = fossilChosen.getBlock_types().get(fossilChosen.getBlock_states().get(i));
                int x = currBlockPos.get(0);
                int y = currBlockPos.get(1);
                int z = currBlockPos.get(2);

                //Rotate
                if (rotation == 1) {
                    int temp_x = x;
                    x = fossilChosen.getSize().get(0) - 1 - z;
                    z = temp_x;
                } else if (rotation == 2) {
                    x = fossilChosen.getSize().get(0) - 1 - x;
                    z = fossilChosen.getSize().get(2) - 1 - z;
                } else if (rotation == 3) {
                    int temp_x = x;
                    x = z;
                    z = fossilChosen.getSize().get(2) - 1 - temp_x;
                }

                world.setBlockState(pos.add(x, y, z), Registry.BLOCK.get(Identifier.tryParse(currBlockType)).getDefaultState(), 2);
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("saves/" + world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/fossil_spawns.txt", true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
