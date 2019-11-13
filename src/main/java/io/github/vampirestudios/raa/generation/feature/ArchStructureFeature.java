package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
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
public class ArchStructureFeature extends StructureFeature<DefaultFeatureConfig> {

	private static final int ARCH_SPACING = 5;

	// How many chunks should be in between each canyon arch at least
	private static final int ARCH_SEPARATION = 3;
	private static final int SEED_MODIFIER = 0x0401C480;

	public ArchStructureFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
		super(function);
	}

	protected ChunkPos getStart(ChunkGenerator<?> chunkGenerator_1, Random random_1, int chunkX, int chunkZ, int scaleX, int scaleZ) {
		chunkX += ARCH_SPACING * scaleX;
		chunkZ += ARCH_SPACING * scaleZ;

		// Adjust to grid position
		chunkX = chunkX < 0 ? chunkX - ARCH_SPACING + 1 : chunkX;
		chunkZ = chunkZ < 0 ? chunkZ - ARCH_SPACING + 1 : chunkZ;

		int finalChunkX = chunkX / ARCH_SPACING;
		int finalChunkZ = chunkZ / ARCH_SPACING;

		((ChunkRandom) random_1).setStructureSeed(chunkGenerator_1.getSeed(), finalChunkX, finalChunkZ, SEED_MODIFIER);

		// Get random position within grid area
		finalChunkX *= ARCH_SPACING;
		finalChunkZ *= ARCH_SPACING;
		finalChunkX += random_1.nextInt(ARCH_SPACING - ARCH_SEPARATION);
		finalChunkZ += random_1.nextInt(ARCH_SPACING - ARCH_SEPARATION);

		return new ChunkPos(finalChunkX, finalChunkZ);
	}

	@Override
	public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int chunkX, int chunkZ, Biome biome) {
		ChunkPos start = this.getStart(chunkGenerator, random, chunkX, chunkZ, 0, 0);

		if (chunkX == start.x && chunkZ == start.z) {
			if (biome.getCategory() == Biome.Category.OCEAN) {
				return false;
			}

			return chunkGenerator.hasStructure(biome, this);
		}

		return false;
	}

	public StructureFeature.StructureStartFactory getStructureStartFactory() {
		return ArchStructureFeature.CanyonArchStructureStart::new;
	}

	public String getName() {
		return "canyon_arch";
	}

	public int getRadius() {
		return 12;
	}

	public static class CanyonArchStructureStart extends StructureStart {
		CanyonArchStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox boundingBox, int references, long baseSeed) {
			super(feature, chunkX, chunkZ, boundingBox, references, baseSeed);
		}

		public void initialize(ChunkGenerator<?> generator, StructureManager manager, int chunkX, int chunkZ, Biome biome) {
			ArchGenerator canyonArch = new ArchGenerator(this.random, chunkX * 16, chunkZ * 16, biome);

			this.children.add(canyonArch);
			this.setBoundingBoxFromChildren();
		}
	}
}