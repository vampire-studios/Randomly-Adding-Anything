package io.github.vampirestudios.raa.config.screen.dimensions;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.Dimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DimensionListScreen extends Screen {

    Screen parent;
    private RAADimensionListWidget dimensionList;
    private RAADimensionDescriptionListWidget descriptionList;
    private static Identifier background;

    public DimensionListScreen(Screen parent) {
        super(new TranslatableText("config.title.raa"));
        this.parent = parent;
        background = DrawableHelper.BACKGROUND_LOCATION;
    }

    public static void overlayBackground(int x1, int y1, int x2, int y2, int red, int green, int blue, int startAlpha, int endAlpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        MinecraftClient.getInstance().getTextureManager().bindTexture(background);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        buffer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        buffer.vertex(x1, y2, 0.0D).texture(x1 / 32.0F, y2 / 32.0F).color(red, green, blue, endAlpha).next();
        buffer.vertex(x2, y2, 0.0D).texture(x2 / 32.0F, y2 / 32.0F).color(red, green, blue, endAlpha).next();
        buffer.vertex(x2, y1, 0.0D).texture(x2 / 32.0F, y1 / 32.0F).color(red, green, blue, startAlpha).next();
        buffer.vertex(x1, y1, 0.0D).texture(x1 / 32.0F, y1 / 32.0F).color(red, green, blue, startAlpha).next();
        tessellator.draw();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean keyPressed(int int_1, int int_2, int int_3) {
        if (int_1 == 256 && this.shouldCloseOnEsc()) {
            minecraft.openScreen(parent);
            return true;
        }
        return super.keyPressed(int_1, int_2, int_3);
    }

    @Override
    protected void init() {
        super.init();
        addButton(new ButtonWidget(4, 4, 50, 20, I18n.translate("gui.back"), var1 -> minecraft.openScreen(parent)));
        children.add(dimensionList = new RAADimensionListWidget(minecraft, width / 2 - 10, height,
                28 + 5, height - 5, background));
        children.add(descriptionList = new RAADimensionDescriptionListWidget(minecraft, width / 2 - 10, height,
                28 + 5, height - 5, background));
        dimensionList.setLeftPos(5);
        descriptionList.setLeftPos(width / 2 + 5);
        List<DimensionData> materials = new ArrayList<>();
        for (DimensionData material : Dimensions.DIMENSIONS) materials.add(material);
        materials.sort(Comparator.comparing(material -> WordUtils.capitalizeFully(material.getName()), String::compareToIgnoreCase));
        for (DimensionData material : materials) {
            dimensionList.addItem(new RAADimensionListWidget.DimensionEntry(material) {
                @Override
                public void onClick() {
                    descriptionList.addMaterial(DimensionListScreen.this, material);
                }
            });
        }
        if (!materials.isEmpty()) dimensionList.addItem(new RAADimensionListWidget.EmptyEntry(10));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderDirtBackground(0);
        dimensionList.render(mouseX, mouseY, delta);
        descriptionList.render(mouseX, mouseY, delta);
        overlayBackground(0, 0, width, 28, 64, 64, 64, 255, 255);
        overlayBackground(0, height - 5, width, height, 64, 64, 64, 255, 255);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value,
                                       GlStateManager.SourceFactor.ZERO.value, GlStateManager.DestFactor.ONE.value
        );
        RenderSystem.disableAlphaTest();
        RenderSystem.shadeModel(7425);
        RenderSystem.disableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        buffer.vertex(0, 28 + 4, 0.0D).texture(0.0F, 1.0F).color(0, 0, 0, 0).next();
        buffer.vertex(this.width, 28 + 4, 0.0D).texture(1.0F, 1.0F).color(0, 0, 0, 0).next();
        buffer.vertex(this.width, 28, 0.0D).texture(1.0F, 0.0F).color(0, 0, 0, 255).next();
        buffer.vertex(0, 28, 0.0D).texture(0.0F, 0.0F).color(0, 0, 0, 255).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7424);
        RenderSystem.enableAlphaTest();
        RenderSystem.disableBlend();
        drawCenteredString(font, title.asFormattedString(), width / 2, 10, 16777215);
        super.render(mouseX, mouseY, delta);
    }

}