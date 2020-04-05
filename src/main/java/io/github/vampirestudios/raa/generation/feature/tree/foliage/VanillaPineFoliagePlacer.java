package io.github.vampirestudios.raa.generation.feature.tree.foliage;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class VanillaPineFoliagePlacer extends FoliagePlacer {
    private final int height;
    private final int randomHeight;

    public VanillaPineFoliagePlacer(int i, int j, int k, int l, int m, int n) {
        super(i, j, k, l, FoliagePlacerType.PINE_FOLIAGE_PLACER);
        this.height = m;
        this.randomHeight = n;
    }

    public void generate(ModifiableTestableWorld world, Random random, BranchedTreeFeatureConfig config, int trunkHeight, BlockPos pos, int foliageHeight, int radius, Set<BlockPos> leaves) {
        int i = this.offset + random.nextInt(this.randomOffset + 1);
        int j = 0;

        for(int k = foliageHeight + i; k >= i; --k) {
            this.generate(world, random, config, pos, foliageHeight, k, j, leaves);
            if (j >= 1 && k == i + 1) {
                --j;
            } else if (j < radius) {
                ++j;
            }
        }

    }

    public int getRadius(Random random, int baseHeight, BranchedTreeFeatureConfig config) {
        if (baseHeight < 0) {
            baseHeight *= -1; //make the height positive if it's negative
        }

        return this.radius + random.nextInt(this.randomRadius + 1) + random.nextInt(baseHeight + 1);
    }

    public int getHeight(Random random, int trunkHeight) {
        return this.height + random.nextInt(this.randomHeight + 1);
    }

    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, int radius) {
        return Math.abs(dx) == radius && Math.abs(dz) == radius && radius > 0;
    }

    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius) {
        return radius <= 1 ? 0 : 2;
    }

    public <T> T serialize(DynamicOps<T> ops) {
        Builder<T, T> builder = ImmutableMap.builder();
        builder.put(ops.createString("height"), ops.createInt(this.height)).put(ops.createString("height_random"), ops.createInt(this.randomHeight));
        return ops.merge(super.serialize(ops), ops.createMap(builder.build()));
    }
}
