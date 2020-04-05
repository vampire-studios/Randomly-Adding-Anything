package io.github.vampirestudios.raa.blocks;

import io.github.vampirestudios.raa.api.dimension.PlayerPlacementHandlers;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PortalBlock extends Block {

//    public static final BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    private final DimensionData dimensionData;
    private final DimensionType dimensionType;

    public PortalBlock(DimensionType dimensionType, DimensionData dimensionData) {
        super(Block.Settings.of(Material.STONE).strength(8.0f, 80.f).nonOpaque());
        this.dimensionData = dimensionData;
        this.dimensionType = dimensionType;
//        this.setDefaultState(this.getDefaultState().with(ACTIVATED, false));
    }

    @Override
    public ActionResult onUse(BlockState blockState_1, World world_1, BlockPos pos, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
        if (!Objects.requireNonNull(world_1).isClient) {
            /*if (blockState_1.get(ACTIVATED)) {

            }

            if (!blockState_1.get(ACTIVATED) && playerEntity_1.getMainHandStack().getItem() == Registry.ITEM.get(Utils.addSuffixToPath(dimensionData.getId(), "_portal_key"))) {
                world_1.setBlockState(pos, blockState_1.with(ACTIVATED, true));
            }*/
            BlockPos playerPos = playerEntity_1.getBlockPos();
            if (playerPos.getX() == pos.getX() && playerPos.getZ() == pos.getZ() && playerPos.getY() == pos.getY() + 1) {
                if (playerEntity_1.world.dimension.getType() == dimensionType) {
                    // coming from our custom dimension
                    FabricDimensions.teleport(playerEntity_1, DimensionType.OVERWORLD, PlayerPlacementHandlers.OVERWORLD.getEntityPlacer());
                } else {
                    // going to our custom dimension
                    FabricDimensions.teleport(playerEntity_1, dimensionType, null);
                }
            }

        }
        return super.onUse(blockState_1, world_1, pos, playerEntity_1, hand_1, blockHitResult_1);
    }

    /*@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }*/

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(state.getBlock().asItem()));
        return list;
    }

}
