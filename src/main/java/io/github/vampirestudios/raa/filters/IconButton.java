package io.github.vampirestudios.raa.filters;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.Identifier;

/**
 * Author: MrCrayfish
 */
@Environment(EnvType.CLIENT)
public class IconButton extends ButtonWidget {

    private Identifier iconResource;
    private int iconU;
    private int iconV;

    public IconButton(int x, int y, String message, PressAction pressable, Identifier iconResource, int iconU, int iconV) {
        super(x, y, 20, 20, message, pressable);
        this.iconResource = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    public void setIcon(Identifier iconResource, int iconU, int iconV) {
        this.iconResource = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks)
    {
        MinecraftClient.getInstance().getTextureManager().bindTexture(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        int offset = this.getYImage(this.isHovered());
        this.drawTexture(this.x, this.y, 0, 46 + offset * 20, this.width / 2, this.height);
        this.drawTexture(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + offset * 20, this.width / 2, this.height);
        if(!this.active)
        {
            RenderSystem.color4f(0.5F, 0.5F, 0.5F, 1.0F);
        }
        MinecraftClient.getInstance().getTextureManager().bindTexture(this.iconResource);
        this.drawTexture(this.x + 2, this.y + 2, this.iconU, this.iconV, 16, 16);
    }
}