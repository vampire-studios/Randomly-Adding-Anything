package io.github.vampirestudios.raa.filters;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

/**
 * Author: MrCrayfish
 */
public class TagButton extends ButtonWidget {

    private static final Identifier TABS = new Identifier("textures/gui/container/creative_inventory/tabs.png");

    private final FilterEntry category;
    private final ItemStack stack;
    private boolean toggled;

    public TagButton(int x, int y, FilterEntry filter, PressAction pressable) {
        super(x, y, 32, 28, "", pressable);
        this.category = filter;
        this.stack = filter.getIcon();
        this.toggled = filter.isEnabled();
    }

    public FilterEntry getFilter()
    {
        return category;
    }

    @Override
    public void onPress() {
        this.toggled = !this.toggled;
        this.category.setEnabled(this.toggled);
        super.onPress();
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.getTextureManager().bindTexture(TABS);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.disableLighting();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

        int width = this.toggled ? 32 : 28;
        int textureX = 28;
        int textureY = this.toggled ? 32 : 0;
        this.drawRotatedTexture(this.x, this.y, textureX, textureY, width, 28);

        RenderSystem.enableRescaleNormal();
        DiffuseLighting.enableGuiDepthLighting();
        ItemRenderer renderer = mc.getItemRenderer();
        renderer.zOffset = 100.0F;
        renderer.renderGuiItem(this.stack, x + 8, y + 6);
        renderer.renderGuiItemOverlay(mc.textRenderer, this.stack, x + 8, y + 6);
        renderer.zOffset = 100.0F;
    }

    private void drawRotatedTexture(int x, int y, int textureX, int textureY, int width, int height) {
        float scaleX = 0.00390625F;
        float scaleY = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferbuilder.vertex(x, y + height, 0.0).texture(((float)(textureX + height) * scaleX), ((float)(textureY) * scaleY)).next();
        bufferbuilder.vertex(x + width, y + height, 0.0).texture(((float)(textureX + height) * scaleX), ((float)(textureY + width) * scaleY)).next();
        bufferbuilder.vertex(x + width, y, 0.0).texture(((float)(textureX) * scaleX), ((float)(textureY + width) * scaleY)).next();
        bufferbuilder.vertex(x, y, 0.0).texture(((float)(textureX) * scaleX), ((float)(textureY) * scaleY)).next();
        tessellator.draw();
    }

    public void updateState()
    {
        this.toggled = category.isEnabled();
    }
}