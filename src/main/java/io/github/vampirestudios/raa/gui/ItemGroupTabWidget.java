package io.github.vampirestudios.raa.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.itemGroup.ItemTab;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;

public class ItemGroupTabWidget extends ButtonWidget
{
	private final ItemTab tab;
	public boolean isSelected = false;

	public ItemGroupTabWidget(int x, int y, ItemTab tab, PressAction onPress)
	{
		super(x, y, 22, 22, I18n.translate(tab.geTranslationKey()), onPress);

		this.tab = tab;
	}

	protected int getYImage(boolean isHovered)
	{
		return isHovered || isSelected ? 1 : 0;
	}

	public void renderButton(int mouseX, int mouseY, float delta) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		minecraftClient.getTextureManager().bindTexture(TEXTURE);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		int i = this.getYImage(this.isHovered());

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

		this.drawTexture(this.x, this.y, 0, i * height, this.width, this.height);
		this.renderBg(minecraftClient, mouseX, mouseY);

		minecraftClient.getItemRenderer().renderGuiItem(tab.getIcon(), this.x + 3, this.y + 3);
	}

	public static final Identifier TEXTURE = new Identifier(RandomlyAddingAnything.MOD_ID, "textures/gui/tabs.png");
}