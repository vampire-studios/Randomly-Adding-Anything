package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;
import java.util.function.Function;

//Code kindly taken from Terrestria. Thank you, coderbot, Prospector, and Valoeghese!
public class VolcanoStructureFeature extends StructureFeature<DefaultFeatureConfig> {
	//
	private static final int VOLCANO_SPACING = 6;

	// How many chunks should be in between each volcano at least
	private static final int VOLCANO_SEPARATION = 3;
	private static final int SEED_MODIFIER = 0x0401C480;
	private static DimensionData dimensionData;

	public VolcanoStructureFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function, DimensionData dimensionDataIn) {
		super(function);
		dimensionData = dimensionDataIn;
	}

	protected ChunkPos getStart(ChunkGenerator<?> chunkGenerator_1, Random random_1, int chunkX, int chunkZ, int scaleX, int scaleZ) {
		chunkX += VOLCANO_SPACING * scaleX;
		chunkZ += VOLCANO_SPACING * scaleZ;

		// Adjust to grid position
		chunkX = chunkX < 0 ? chunkX - VOLCANO_SPACING + 1 : chunkX;
		chunkZ = chunkZ < 0 ? chunkZ - VOLCANO_SPACING + 1 : chunkZ;

		int finalChunkX = chunkX / VOLCANO_SPACING;
		int finalChunkZ = chunkZ / VOLCANO_SPACING;

		((ChunkRandom) random_1).setStructureSeed(chunkGenerator_1.getSeed(), finalChunkX, finalChunkZ, SEED_MODIFIER);

		// Get random position within grid area
		finalChunkX *= VOLCANO_SPACING;
		finalChunkZ *= VOLCANO_SPACING;
		finalChunkX += random_1.nextInt(VOLCANO_SPACING - VOLCANO_SEPARATION);
		finalChunkZ += random_1.nextInt(VOLCANO_SPACING - VOLCANO_SEPARATION);

		return new ChunkPos(finalChunkX, finalChunkZ);
	}

	@Override
	public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int chunkX, int chunkZ, Biome biome) {
		ChunkPos start = this.getStart(chunkGenerator, random, chunkX, chunkZ, 0, 0);

		if (chunkX == start.x && chunkZ == start.z) {
			if (biome.getCategory() == Biome.Category.OCEAN && random.nextInt(4) != 0) {
				return false;
			} else if (random.nextInt(2) != 0) {
				return false;
			}

			return chunkGenerator.hasStructure(biome, this);
		}

		return false;
	}

	public StructureFeature.StructureStartFactory getStructureStartFactory() {
		return VolcanoStructureStart::new;
	}

	public String getName() {
		return "Volcano";
	}

	public int getRadius() {
		return 12;
	}

	public static class VolcanoStructureStart extends StructureStart {
		VolcanoStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox boundingBox, int references, long baseSeed) {
			super(feature, chunkX, chunkZ, boundingBox, references, baseSeed);
		}

		public void initialize(ChunkGenerator<?> generator, StructureManager manager, int chunkX, int chunkZ, Biome biome) {
			VolcanoGenerator volcano = new VolcanoGenerator(this.random, chunkX * 16, chunkZ * 16, biome, dimensionData);

			this.children.add(volcano);
			this.setBoundingBoxFromChildren();
		}
	}
}