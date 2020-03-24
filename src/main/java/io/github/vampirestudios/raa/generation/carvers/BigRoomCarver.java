package io.github.vampirestudios.raa.generation.carvers;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class BigRoomCarver extends RAACarver<ProbabilityConfig> {
    public BigRoomCarver(DimensionData data) {
        super(ProbabilityConfig::deserialize, data);
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig carverConfig) {
        //positions
        double x = (chunkX* 16) + random.nextInt(16);
        double z = (chunkZ* 16) + random.nextInt(16);
        double y = random.nextInt(256);

        //modifiers
        double xVelocity = random.nextDouble() - 0.5;
        double zVoleocity = random.nextDouble() - 0.5;
        double yOffset = random.nextInt(5) - 2; // [-2, 2]
        if (yOffset == 0) yOffset = 1; // 0 offset caves look bad

        //yaw & pitch
        double yaw = 4 + (random.nextDouble()*4);
        double pitch = yaw / 2;
        double cmod = 1;

        int bigIndex = random.nextInt(128) + 64; // some caves won't become massive

        for (int i = 0; i < 128; i++) {
            //calculate per-section modifiers
            double xDirectionMod = (random.nextDouble() - 0.5);
            double zDirectionMod = (random.nextDouble() - 0.5);
            double yOffsetMod = (random.nextDouble() - 0.5);
            double yawOffset = (random.nextDouble() - 0.5);

            y -= (yOffset + yOffsetMod); //lower the carving region
            x += xVelocity;
            z += zVoleocity;
            //velocity application
            xVelocity += xDirectionMod;
            if (xVelocity > 1.5) xVelocity = 1.5;
            if (xVelocity < -1.5)  xVelocity = -1.5;

            zVoleocity += zDirectionMod;
            if (zVoleocity > 1.5) zVoleocity = 1.5;
            if (zVoleocity < -1.5)  zVoleocity = -1.5;

            //yaw modification
            yaw += yawOffset;
            if (yaw < 1.5) yaw = 1.5; // min cave size
            yaw *= cmod;
//            System.out.println(yaw);
//            if (yaw > 30) {
//                yaw = 30;
//            }
            pitch = yaw / 3;
//            if (cmod > 1) {
//                cmod+= 0.003; //increase cmod every time
//            }

            if (i == bigIndex) { //big room
                cmod = 1.01;
            }

            this.carveRegion(chunk,
                    posToBiome,
                    random.nextInt(),
                    seaLevel,
                    mainChunkX,
                    mainChunkZ,
                    x,
                    y,
                    z,
                    yaw,
                    pitch,
                    carvingMask);
        }

        return true;
    }

    @Override
    public boolean shouldCarve(Random random, int chunkX, int chunkZ, ProbabilityConfig config) {
        return random.nextFloat() <= config.probability;
    }

    @Override
    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return false;
    }
}
