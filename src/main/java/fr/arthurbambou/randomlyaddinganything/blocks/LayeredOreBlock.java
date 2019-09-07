package fr.arthurbambou.randomlyaddinganything.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.loot.LootPool;
import net.minecraft.world.loot.LootSupplier;
import net.minecraft.world.loot.LootTables;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;
import net.minecraft.world.loot.context.LootContextTypes;

public class LayeredOreBlock extends OreBlock {

	private boolean complainedAboutLoot = false;
	
	public LayeredOreBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.SOLID;
	}

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