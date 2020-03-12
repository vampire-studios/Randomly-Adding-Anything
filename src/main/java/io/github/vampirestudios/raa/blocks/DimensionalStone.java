package io.github.vampirestudios.raa.blocks;

import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class DimensionalStone extends Block {
    private String dimensionName;

    public DimensionalStone(DimensionData dimensionData) {
        super(Settings.copy(Blocks.STONE).strength(Rands.randFloatRange(0.25f, 4), Rands.randFloatRange(4, 20))
                .jumpVelocityMultiplier(dimensionData.getDimensionStoneJumpHeight()));
        this.dimensionName = dimensionData.getName();
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(Registry.BLOCK.get(new Identifier(MOD_ID, dimensionName.toLowerCase() + "_cobblestone")).asItem()));
        return list;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        super.onSteppedOn(world, pos, entity);
        if (Rands.chance(20)) {
            if (entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 10, Integer.MAX_VALUE, true,
                        false, false));
                ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, Integer.MAX_VALUE, true,
                        false, false));
            }
        }
    }

    @Override
    public void onBroken(IWorld world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
    }

}
