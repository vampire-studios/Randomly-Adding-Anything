package fr.arthurbambou.randomlyaddinganything.world.gen.feature;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import fr.arthurbambou.randomlyaddinganything.materials.GeneratingOptions;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.*;
import java.util.function.Predicate;

public class RAAOreFeature extends Feature<DefaultFeatureConfig> {
	
	public static Clump[] SPHERES = {
			Clump.of(1), Clump.of(2), Clump.of(3), Clump.of(4), Clump.of(5), Clump.of(6), Clump.of(7), Clump.of(8), Clump.of(9)
	};
	
	public static final Predicate<Block> NATURAL_STONE = (it)->
			it==Blocks.STONE    ||
			it==Blocks.GRANITE  ||
			it==Blocks.DIORITE  ||
			it==Blocks.SAND  ||
			it==Blocks.DIRT  ||
			it==Blocks.COARSE_DIRT  ||
			it==Blocks.SANDSTONE  ||
			it==Blocks.RED_SAND  ||
			it==Blocks.RED_SANDSTONE  ||
			it==Blocks.ANDESITE;

	public Material material;

	public RAAOreFeature(Material material) {
		super(DefaultFeatureConfig::deserialize);
		this.material = material;
	}

	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random rand, BlockPos pos, DefaultFeatureConfig uselessConfig) {
		
		Chunk toGenerateIn = world.getChunk(pos);
		Biome biome = toGenerateIn.getBiome(pos);
		GeneratingOptions settings = material.getGeneratingOptions();

		for(String s : settings.biomes) {
			if (biome == Registry.BIOME.get(new Identifier(s))) {

				int clusters = settings.cluster_count;
				if (clusters<1) clusters=1;
				
				if (settings.cluster_size<=0) settings.cluster_size = 1;
				
				int blocksGenerated = 0;
				for(int i=0; i<clusters; i++) {
					int overbleed = 0; //Increase to allow ore deposits to overlap South/East chunks by this amount
					
					int radius = (int) Math.log(settings.cluster_size) + 1;
					if (radius>7) radius=7; //radius can't go past 7 without adding some overbleed
					for(int j=0; j<SPHERES.length; j++) { //find the smallest clump in our vocabulary which expresses the number of ores
						Clump clump = SPHERES[j];
						if (clump.size()>=settings.cluster_size) {
							radius = j+1;
							break;
						}
					}
					
					int clusterX = rand.nextInt(16 + overbleed - (radius*2))+radius;
					int clusterZ = rand.nextInt(16 + overbleed - (radius*2))+radius;
					int heightRange = settings.max_height-settings.min_height; if (heightRange<1) heightRange=1;
					int clusterY = rand.nextInt(heightRange)+settings.min_height;
					
					clusterX += toGenerateIn.getPos().getStartX();
					clusterZ += toGenerateIn.getPos().getStartZ();
					
					int generatedThisCluster = generateVeinPartGaussianClump(s, world, clusterX, clusterY, clusterZ, settings.cluster_size, radius, ImmutableSet.of(Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState()), 85, rand);
					blocksGenerated += generatedThisCluster;
					System.out.println("    Generated "+generatedThisCluster+" out of "+settings.cluster_size+" expected.");
				}
				
				System.out.println("    Generated "+blocksGenerated+" in "+clusters+" clusters out of "+settings.cluster_size+"*"+clusters+"="+(settings.cluster_size*clusters));
			} else {
				System.out.println("    skipping "+s+" here.");
			}
		}
		
		return false;
	}

	protected int generateVeinPartGaussianClump(String resourceName, IWorld world, int x, int y, int z, int clumpSize, int radius, Set<BlockState> states, int density, Random rand) {
		int radIndex = radius-1;
		Clump clump = (radIndex<SPHERES.length) ? SPHERES[radIndex].copy() : Clump.of(radius);
		
		//int rad2 = radius * radius;
		BlockState[] blocks = states.toArray(new BlockState[0]);
		int replaced = 0;
		for(int i=0; i<clump.size(); i++) {
			if (clump.isEmpty()) break;
			BlockPos pos = clump.removeGaussian(rand, x, y, z);
			if (replace(world, pos.getX(), pos.getY(), pos.getZ(), resourceName, blocks, rand)) {
				replaced++;
				if (replaced>=clumpSize) return replaced;
			}
		}
		
		return replaced;
	}
	
	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param states fallback states to use if there is no replacer for natural stone
	 * @param rand
	 * @return
	 */
	public boolean replace(IWorld world, int x, int y, int z, String resource, BlockState[] states, Random rand) {
		BlockPos pos = new BlockPos(x, y, z);
		BlockState toReplace = world.getBlockState(pos);
		/*HashMap<String, String> replacementSpecs = OregenResourceListener.getConfig().replacements.get(resource);
		if (replacementSpecs!=null) {
			//System.out.println("Activating replacementSpecs for resource "+resource);
			for(Map.Entry<String, String> entry : replacementSpecs.entrySet()) {
				if (test(toReplace.getBlock(), entry.getKey())) {
					BlockState replacement = getBlockState(entry.getValue(), rand);
					if (replacement==null) continue;
					
					world.setBlockState(pos, replacement, 3);
					return true;
				}
			}
			return false; //There are replacements defined for this resource, but none could be applied.
		} else {
			if (!NATURAL_STONE.test(toReplace.getBlock())) return false; //Fixes surface copper
			
			BlockState replacement = states[rand.nextInt(states.length)];
			world.setBlockState(pos, replacement, 3);
			return true;
		}*/
		if (!NATURAL_STONE.test(toReplace.getBlock())) return false; //Fixes surface copper

		BlockState replacement = states[rand.nextInt(states.length)];
		world.setBlockState(pos, replacement, 3);
		return true;
	}
	
	public boolean test(Block block, String spec) {
		if (spec.startsWith("#")) {
			Tag<Block> tag = BlockTags.getContainer().get(new Identifier(spec.substring(1)));
			if (tag==null) return false;
			return tag.contains(block);
		} else {
			Block b = Registry.BLOCK.get(new Identifier(spec));
			if (b==Blocks.AIR) return false;
			return block==b;
		}
	}
	
	public BlockState getBlockState(String spec, Random rnd) {
		if (spec.startsWith("#")) {
			Tag<Block> tag = BlockTags.getContainer().get(new Identifier(spec.substring(1)));
			if (tag==null) return null;
			return tag.getRandom(rnd).getDefaultState();
		} else {
			Block b = Registry.BLOCK.get(new Identifier(spec));
			if (b==Blocks.AIR) return null;
			return b.getDefaultState();
		}
	}
}