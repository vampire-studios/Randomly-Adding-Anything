package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

// Thanks to TelepathicGrunt and the UltraAmplified mod for this class
public class HangingRuins extends Feature<DefaultFeatureConfig> {

	public HangingRuins(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> changedBlock, Random rand, BlockPos position, DefaultFeatureConfig config) {
		//makes sure this ruins does not spawn too close to world height border.
		if (position.getY() < world.getSeaLevel() + 5) {
			return false;
		}

		BlockRotation rot = BlockRotation.values()[rand.nextInt(BlockRotation.values().length)];
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), position.getY(), position.getZ());
		BlockPos.Mutable offset = new BlockPos.Mutable();

		//makes sure there is enough solid blocks on ledge to hold this feature.
		for (int x = -5; x <= 5; x++)
		{
			for (int z = -5; z <= 5; z++)
			{
				if (Math.abs(x * z) > 9 && Math.abs(x * z) < 20)
				{
					//match rotation of structure as it rotates around 0, 0 I think.
					//The -4 is to make the check rotate the same way as structure and 
					//then we do +4 to get the actual position again.
					offset.set(x - 4, 0, z - 4).set(offset.rotate(rot));
					if (!world.getBlockState(blockpos$Mutable.add(-offset.getX() + 4, 1, -offset.getZ() + 4)).isAir()) {
						return false;
					}
				}
			}
		}

		//makes sure top won't be exposed to air
		if (shouldMoveDownOne(world, blockpos$Mutable, offset, rot))
		{
			blockpos$Mutable.move(Direction.DOWN);
		}

		//UltraAmplified.LOGGER.debug("Hanging Ruins | " + position.getX() + " " + position.getY() + " "+position.getZ());
		StructureManager templatemanager = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureManager();
		Structure template = templatemanager.getStructure(new Identifier(RandomlyAddingAnything.MOD_ID + ":hanging_ruins"));

		if (template == null)
		{
			RandomlyAddingAnything.LOGGER.warn("hanging ruins NTB does not exist!");
			return false;
		}

		StructurePlacementData placementsettings = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(rot)
				.setIgnoreEntities(false).setChunkPosition((ChunkPos) null);

		template.place(world, blockpos$Mutable.move(4, -8, 4), placementsettings, 2);

		return true;

	}


	private boolean shouldMoveDownOne(IWorld world, BlockPos.Mutable blockpos$Mutable, BlockPos.Mutable offset, BlockRotation rot) {

		//if we are on a 1 block thick ledge at any point, move down one so ruins ceiling isn't exposed 
		for (int x = -5; x <= 5; x++)
		{
			for (int z = -5; z <= 5; z++)
			{
				offset.set(x - 4, 0, z - 4).set(offset.rotate(rot));
				if (Math.abs(x * z) < 20 && !world.getBlockState(blockpos$Mutable.add(-offset.getX() + 4, 2, -offset.getZ() + 4)).isOpaque())
				{
					//world.setBlockState(blockpos$Mutable.add(-offset.getX() + 4, 2, -offset.getZ() + 4), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
					return true;
				}
			}
		}
		return false;
	}
}