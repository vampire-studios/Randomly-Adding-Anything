package io.github.vampirestudios.raa.items.material;

import io.github.vampirestudios.raa.api.buckets.BucketItemRegistry;
import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;

public class RAABucketItem extends BucketItem {
    private Material material;
    private Fluid fluidLazy;

    public RAABucketItem(Fluid fluid, Material material) {
        super(fluid, new Item.Settings().group(ItemGroup.MISC));
        this.material = material;
        this.fluidLazy = fluid;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        HitResult hitResult = rayTrace(world, user, this.fluidLazy == Fluids.EMPTY ? RayTraceContext.FluidHandling.SOURCE_ONLY : RayTraceContext.FluidHandling.NONE);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else if (hitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);
            if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, itemStack)) {
                BlockState blockState;
                if (this.fluidLazy == Fluids.EMPTY) {
                    blockState = world.getBlockState(blockPos);
                    if (blockState.getBlock() instanceof FluidDrainable) {
                        Fluid fluid = ((FluidDrainable)blockState.getBlock()).tryDrainFluid(world, blockPos, blockState);
                        if (fluid != Fluids.EMPTY) {
                            user.incrementStat(Stats.USED.getOrCreateStat(this));
                            user.playSound(fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                            ItemStack itemStack2 = this.getFilledStack(itemStack, user, BucketItemRegistry.BUCKET_ITEMS.get(material.getId()).getBucketItemFromFluid(fluid));
                            if (!world.isClient) {
                                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, new ItemStack(BucketItemRegistry.BUCKET_ITEMS.get(material.getId()).getBucketItemFromFluid(fluid)));
                            }

                            return TypedActionResult.success(itemStack2);
                        }
                    }

                    return TypedActionResult.fail(itemStack);
                } else {
                    blockState = world.getBlockState(blockPos);
                    BlockPos blockPos3 = blockState.getBlock() instanceof FluidFillable && this.fluidLazy == Fluids.WATER ? blockPos : blockPos2;
                    if (this.placeFluid(user, world, blockPos3, blockHitResult)) {
                        this.onEmptied(world, itemStack, blockPos3);
                        if (user instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, blockPos3, itemStack);
                        }

                        user.incrementStat(Stats.USED.getOrCreateStat(this));
                        return TypedActionResult.success(this.getEmptiedStack(itemStack, user));
                    } else {
                        return TypedActionResult.fail(itemStack);
                    }
                }
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
    }

    protected ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        return !player.abilities.creativeMode ? new ItemStack(BucketItemRegistry.BUCKET_ITEMS.get(material.getId()).getBucketItemFromFluid(Fluids.EMPTY)) : stack;
    }

    private ItemStack getFilledStack(ItemStack stack, PlayerEntity player, Item filledBucket) {
        if (player.abilities.creativeMode) {
            return stack;
        } else {
            stack.decrement(1);
            if (stack.isEmpty()) {
                return new ItemStack(filledBucket);
            } else {
                if (!player.inventory.insertStack(new ItemStack(filledBucket))) {
                    player.dropItem(new ItemStack(filledBucket), false);
                }

                return stack;
            }
        }
    }

    @Override
    public Text getName(ItemStack stack) {
        if (this.fluidLazy == Fluids.EMPTY) {
            return new TranslatableText("text.raa.item.bucket.empty", WordUtils.capitalize(this.material.getName()));
        } else {
            return new TranslatableText("text.raa.item.bucket.liquid", WordUtils.capitalize(this.material.getName()),
                    new TranslatableText("block." + Registry.FLUID.getId(this.fluidLazy).toString().replace(":",".")));
        }
    }
}
