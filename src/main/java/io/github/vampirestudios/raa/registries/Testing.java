package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import io.github.vampirestudios.raa.utils.RenderingUtils;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.Dimension;

public class Testing {
    private static final Identifier LUCID_SKY = new Identifier("raa:textures/environment/lucid_sky.png");
    
    public static void renderCustomSky(MatrixStack matrixStack, MinecraftClient client, Dimension dimension) {
        if (dimension instanceof CustomDimension) {
            if (Utils.checkBitFlag(((CustomDimension)dimension).getDimensionData().getFlags(), Utils.LUCID))
                RenderingUtils.renderLucidSky(client, LUCID_SKY, matrixStack);
        }
    }

}