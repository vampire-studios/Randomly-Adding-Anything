package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.WorldStructureManipulation;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
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
        put("fossil5", converter.loadStructure("fossils/fossil05.json"));
        put("fossil6", converter.loadStructure("fossils/fossil06.json"));
        put("fossil7", converter.loadStructure("fossils/fossil07.json"));
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

                WorldStructureManipulation.placeBlock(world, pos.add(currBlockPos), currBlockType, currBlockProp, rotation);
            }
        }

        try {
            String path;
            World world2 = world.getWorld();
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) path = "saves/" + ((ServerWorld) world2).getSaveHandler().getWorldDir().getName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/fossil_spawns.txt";
            else path = world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/fossil_spawns.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}