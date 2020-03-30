//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.raa.generation.surface.vanilla_variants;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

public class DarkBadlandsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final BlockState GRAY_TERRACOTTA;
    private static final BlockState WHITE_TERRACOTTA;
    private static final BlockState BLACK_TERRACOTTA;
    private static final BlockState BROWN_TERRACOTTA;
    private static final BlockState CYAN_TERRACOTTA;
    private static final BlockState LIGHT_GRAY_TERRACOTTA;

    static {
        GRAY_TERRACOTTA = Blocks.GRAY_TERRACOTTA.getDefaultState();
        WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
        BLACK_TERRACOTTA = Blocks.BLACK_TERRACOTTA.getDefaultState();
        BROWN_TERRACOTTA = Blocks.BROWN_TERRACOTTA.getDefaultState();
        CYAN_TERRACOTTA = Blocks.CYAN_TERRACOTTA.getDefaultState();
        LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
    }

    protected BlockState[] layerBlocks;
    protected long seed;
    protected OctaveSimplexNoiseSampler heightCutoffNoise;
    protected OctaveSimplexNoiseSampler heightNoise;
    protected OctaveSimplexNoiseSampler layerNoise;

    public DarkBadlandsSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function) {
        super(function);
    }

    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        int n = x & 15;
        int o = z & 15;
        BlockState whiteTerracotta = WHITE_TERRACOTTA;
        BlockState underMaterial = biome.getSurfaceConfig().getUnderMaterial();
        int i = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean b = Math.cos(noise / 3.0D * 3.141592653589793D) > 0.0D;
        int i1 = -1;
        boolean notSureWhatThisIs = false;
        int r = 0;
        Mutable mutable = new Mutable();

        for (int y = height; y >= 0; --y) {
            if (r < 15) {
                mutable.set(n, y, o);
                BlockState blockState5 = chunk.getBlockState(mutable);
                if (blockState5.isAir()) {
                    i1 = -1;
                } else if (blockState5.getBlock() == defaultBlock.getBlock()) {
                    if (i1 == -1) {
                        notSureWhatThisIs = false;
                        if (i <= 0) {
                            whiteTerracotta = Blocks.AIR.getDefaultState();
                            underMaterial = defaultBlock;
                        } else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                            whiteTerracotta = WHITE_TERRACOTTA;
                            underMaterial = biome.getSurfaceConfig().getUnderMaterial();
                        }

                        if (y < seaLevel && (whiteTerracotta == null || whiteTerracotta.isAir())) {
                            whiteTerracotta = defaultFluid;
                        }

                        i1 = i + Math.max(0, y - seaLevel);
                        if (y >= seaLevel - 1) {
                            if (y > seaLevel + 3 + i) {
                                BlockState state;
                                if (y >= 64 && y <= 127) {
                                    if (b) {
                                        state = WHITE_TERRACOTTA;
                                    } else {
                                        state = this.calculateLayerBlockState(x, y, z);
                                    }
                                } else {
                                    state = GRAY_TERRACOTTA;
                                }

                                chunk.setBlockState(mutable, state, false);
                            } else {
                                chunk.setBlockState(mutable, biome.getSurfaceConfig().getTopMaterial(), false);
                                notSureWhatThisIs = true;
                            }
                        } else {
                            chunk.setBlockState(mutable, underMaterial, false);
                            Block block = underMaterial.getBlock();
                            if (block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA || block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA || block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA || block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA || block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA || block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA || block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA || block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA) {
                                chunk.setBlockState(mutable, GRAY_TERRACOTTA, false);
                            }
                        }
                    } else if (i1 > 0) {
                        --i1;
                        if (notSureWhatThisIs) {
                            chunk.setBlockState(mutable, GRAY_TERRACOTTA, false);
                        } else {
                            chunk.setBlockState(mutable, this.calculateLayerBlockState(x, y, z), false);
                        }
                    }

                    ++r;
                }
            }
        }

    }

    public void initSeed(long seed) {
        if (this.seed != seed || this.layerBlocks == null) {
            this.initLayerBlocks(seed);
        }

        if (this.seed != seed || this.heightCutoffNoise == null || this.heightNoise == null) {
            ChunkRandom chunkRandom = new ChunkRandom(seed);
            this.heightCutoffNoise = new OctaveSimplexNoiseSampler(chunkRandom, IntStream.of(3, 0));
            this.heightNoise = new OctaveSimplexNoiseSampler(chunkRandom, IntStream.of(0, 0));
        }

        this.seed = seed;
    }

    protected void initLayerBlocks(long seed) {
        this.layerBlocks = new BlockState[64];
        Arrays.fill(this.layerBlocks, WHITE_TERRACOTTA);
        ChunkRandom chunkRandom = new ChunkRandom(seed);
        this.layerNoise = new OctaveSimplexNoiseSampler(chunkRandom, IntStream.of(0, 0));

        int i;
        for (i = 0; i < 64; ++i) {
            i += chunkRandom.nextInt(5) + 1;
            if (i < 64) {
                this.layerBlocks[i] = GRAY_TERRACOTTA;
            }
        }

        i = chunkRandom.nextInt(4) + 2;

        int o;
        int t;
        int y;
        int z;
        for (o = 0; o < i; ++o) {
            t = chunkRandom.nextInt(3) + 1;
            y = chunkRandom.nextInt(64);

            for (z = 0; y + z < 64 && z < t; ++z) {
                this.layerBlocks[y + z] = BLACK_TERRACOTTA;
            }
        }

        o = chunkRandom.nextInt(4) + 2;

        int w;
        for (t = 0; t < o; ++t) {
            y = chunkRandom.nextInt(3) + 2;
            z = chunkRandom.nextInt(64);

            for (w = 0; z + w < 64 && w < y; ++w) {
                this.layerBlocks[z + w] = BROWN_TERRACOTTA;
            }
        }

        t = chunkRandom.nextInt(4) + 2;

        for (y = 0; y < t; ++y) {
            z = chunkRandom.nextInt(3) + 1;
            w = chunkRandom.nextInt(64);

            for (int x = 0; w + x < 64 && x < z; ++x) {
                this.layerBlocks[w + x] = CYAN_TERRACOTTA;
            }
        }

        y = chunkRandom.nextInt(3) + 3;
        z = 0;

        for (w = 0; w < y; ++w) {
            z += chunkRandom.nextInt(16) + 4;

            for (int ac = 0; z + ac < 64 && ac < 1; ++ac) {
                this.layerBlocks[z + ac] = WHITE_TERRACOTTA;
                if (z + ac > 1 && chunkRandom.nextBoolean()) {
                    this.layerBlocks[z + ac - 1] = LIGHT_GRAY_TERRACOTTA;
                }

                if (z + ac < 63 && chunkRandom.nextBoolean()) {
                    this.layerBlocks[z + ac + 1] = LIGHT_GRAY_TERRACOTTA;
                }
            }
        }

    }

    protected BlockState calculateLayerBlockState(int x, int y, int z) {
        int i = (int) Math.round(this.layerNoise.sample((double) x / 512.0D, (double) z / 512.0D, false) * 2.0D);
        return this.layerBlocks[(y + i + 64) % 64];
    }
}
