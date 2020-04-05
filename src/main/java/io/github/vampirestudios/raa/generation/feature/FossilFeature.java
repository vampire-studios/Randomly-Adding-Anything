package io.github.vampirestudios.raa.generation.feature;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.JsonConverter;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import io.github.vampirestudios.raa.utils.WorldStructureManipulation;
import net.minecraft.block.Blocks;
import net.minecraft.class_5138;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class FossilFeature extends Feature<DefaultFeatureConfig> {
    private JsonConverter converter = new JsonConverter();
    private Map<String, JsonConverter.StructureValues> structures;

    public FossilFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, class_5138 class_5138, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        JsonObject fossil1 = null;
        JsonObject fossil2 = null;
        JsonObject fossil3 = null;
        JsonObject fossil4 = null;
        JsonObject fossil5 = null;
        JsonObject fossil6 = null;
        JsonObject fossil7 = null;
        try {
            Resource towerBasePath = MinecraftClient.getInstance().getServer().getDataManager().getResource(new Identifier("raa:structures/fossils/fossil01.json"));
            fossil1 = new Gson().fromJson(new InputStreamReader(towerBasePath.getInputStream()), JsonObject.class);
            JsonObject finalTowerBase = fossil1;

            Resource towerWallsPath = MinecraftClient.getInstance().getServer().getDataManager().getResource(new Identifier("raa:structures/fossils/fossil02.json"));
            fossil2 = new Gson().fromJson(new InputStreamReader(towerWallsPath.getInputStream()), JsonObject.class);
            JsonObject finalTowerWalls = fossil2;

            Resource towerStairsPath = MinecraftClient.getInstance().getServer().getDataManager().getResource(new Identifier("raa:structures/fossils/fossil03.json"));
            fossil3 = new Gson().fromJson(new InputStreamReader(towerStairsPath.getInputStream()), JsonObject.class);
            JsonObject finalTowerStairs = fossil3;

            Resource towerLaddersPath = MinecraftClient.getInstance().getServer().getDataManager().getResource(new Identifier("raa:structures/fossils/fossil04.json"));
            fossil4 = new Gson().fromJson(new InputStreamReader(towerLaddersPath.getInputStream()), JsonObject.class);
            JsonObject finalTowerLadders = fossil4;

            Resource towerPillarPath = MinecraftClient.getInstance().getServer().getDataManager().getResource(new Identifier("raa:structures/fossils/fossil05.json"));
            fossil5 = new Gson().fromJson(new InputStreamReader(towerPillarPath.getInputStream()), JsonObject.class);
            JsonObject finalTowerPillar = fossil5;

            Resource fossil6Path = MinecraftClient.getInstance().getServer().getDataManager().getResource(new Identifier("raa:structures/fossils/fossil06.json"));
            fossil6 = new Gson().fromJson(new InputStreamReader(fossil6Path.getInputStream()), JsonObject.class);
            JsonObject finalFossil6 = fossil6;

            Resource fossil7Path = MinecraftClient.getInstance().getServer().getDataManager().getResource(new Identifier("raa:structures/fossils/fossil07.json"));
            fossil7 = new Gson().fromJson(new InputStreamReader(fossil7Path.getInputStream()), JsonObject.class);
            JsonObject finalFossil7 = fossil7;

            structures = new HashMap<String, JsonConverter.StructureValues>() {{
                put("fossil1", converter.loadStructure(finalTowerBase));
                put("fossil2", converter.loadStructure(finalTowerWalls));
                put("fossil3", converter.loadStructure(finalTowerStairs));
                put("fossil4", converter.loadStructure(finalTowerLadders));
                put("fossil5", converter.loadStructure(finalTowerPillar));
                put("fossil6", converter.loadStructure(finalFossil6));
                put("fossil7", converter.loadStructure(finalFossil7));
            }};
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fossil1 == null || fossil2 == null || fossil3 == null || fossil4 == null || fossil5 == null || fossil6 == null) {
            System.out.println("Can't get the file");
            return true;
        }

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

        Utils.createSpawnsFile("fossil", world, pos);

        return true;
    }
}