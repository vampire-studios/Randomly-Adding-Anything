package fr.arthurbambou.randomlyaddinganything.blocks;

import fr.arthurbambou.randomlyaddinganything.materials.Material;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.loot.LootPool;
import net.minecraft.world.loot.LootSupplier;
import net.minecraft.world.loot.LootTables;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;
import net.minecraft.world.loot.context.LootContextTypes;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LayeredOreBlock extends OreBlock {

	private boolean complainedAboutLoot = false;
	private Material material;
	
	public LayeredOreBlock(Material material, Settings settings) {
		super(settings);
		this.material = material;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	protected int getExperienceWhenMined(Random random_1) {
		return MathHelper.nextInt(random_1, material.getOreInformation().getMinXPAmount(), material.getOreInformation().getMinXPAmount());
	}

	public void onStacksDropped(BlockState blockState_1, World world_1, BlockPos blockPos_1, ItemStack itemStack_1) {
		super.onStacksDropped(blockState_1, world_1, blockPos_1, itemStack_1);
		if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, itemStack_1) == 0) {
			int int_1 = this.getExperienceWhenMined(world_1.random);
			if (int_1 > 0) {
				this.dropExperience(world_1, blockPos_1, int_1);
			}
		}

	}

	/*@Environment(EnvType.CLIENT)
	public int getBlockBrightness(BlockState blockState_1, ExtendedBlockView extendedBlockView_1, BlockPos blockPos_1) {
		if (material.isGlowing()) {
			return 15728880;
		} else {
			return 0;
		}
	}*/

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		//EARLY DETECTION OF BUSTED LOOT TABLES:
		Identifier tableId = this.getDropTableId();
		
		if (tableId == LootTables.EMPTY) {
			return Collections.emptyList();
		} else {
			LootContext context = builder.put(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
			ServerWorld world = context.getWorld();
			LootSupplier lootSupplier = world.getServer().getLootManager().getSupplier(tableId);
			
			List<ItemStack> result = lootSupplier.getDrops(context);
			if (result.isEmpty()) {
				//This might not be good. Confirm:
				
				if (lootSupplier instanceof FabricLootSupplier) {
					List<LootPool> pools = ((FabricLootSupplier)lootSupplier).getPools();
					if (pools.isEmpty()) {
						//Yup. Somehow we got a loot pool that just never drops anything.
						if (!complainedAboutLoot) {
							System.out.println("Loot pool '"+tableId+"' doesn't seem to be able to drop anything. Supplying the ore block instead. Please report this to the Cotton team!");
							complainedAboutLoot = true;
						}
						result.add(new ItemStack(this.asItem()));
					}
				}
			}
			
			return result;
		}
	}
}