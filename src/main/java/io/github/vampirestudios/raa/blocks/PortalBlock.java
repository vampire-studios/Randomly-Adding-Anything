package io.github.vampirestudios.raa.blocks;

import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class PortalBlock extends Block {
    private DimensionType dimensionType;
    private DimensionData dimensionData;

    public PortalBlock(DimensionData  dimensionData, DimensionType dimensionType) {
        super(Block.Settings.of(Material.PORTAL));
        this.dimensionType = dimensionType;
        this.dimensionData = dimensionData;
    }

    @Override
    public Text getName() {
        return new TranslatableText("text.raa.block.portal" , new LiteralText(dimensionData.getName()));
    }

    @Override
    public ActionResult onUse(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
        if (!world_1.isClient) {
            System.out.println("Space1");
            if ((int) playerEntity_1.getPos().getX() == blockPos_1.up().down().getX() && (int) playerEntity_1.getPos().getZ() == blockPos_1.up().down().getZ() && (int) playerEntity_1.getPos().getY() == blockPos_1.up().down().getY() + 1) {
                System.out.println("Space");
                if (playerEntity_1.dimension != this.dimensionType) {
                    playerEntity_1.changeDimension(this.dimensionType);
                }
            }
        }
        return super.onUse(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
    }
}
