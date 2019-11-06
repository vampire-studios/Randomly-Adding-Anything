package io.github.vampirestudios.raa.config.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.raa.registries.Materials;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

import java.util.Objects;

public class MaterialListScreen extends Screen {

    Screen parent;
    private MaterialisationMaterialListWidget materialList;
    private MaterialisationDescriptionListWidget descriptionList;

    public MaterialListScreen(Screen parent) {
        super(new TranslatableText("config.title.raa"));
        this.parent = parent;
    }

    public static void overlayBackground(int x1, int y1, int x2, int y2, int red, int green, int blue, int startAlpha, int endAlpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        Objects.requireNonNull(MinecraftClient.getInstance()).getTextureManager().bindTexture(DrawableHelper.BACKGROUND_LOCATION);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        buffer.begin(7, VertexFormats.POSITION_UV_COLOR);
        buffer.vertex(x1, y2, 0.0D).texture(x1 / 32.0F, y2 / 32.0F).color(red, green, blue, endAlpha).next();
        buffer.vertex(x2, y2, 0.0D).texture(x2 / 32.0F, y2 / 32.0F).color(red, green, blue, endAlpha).next();
        buffer.vertex(x2, y1, 0.0D).texture(x2 / 32.0F, y1 / 32.0F).color(red, green, blue, startAlpha).next();
        buffer.vertex(x1, y1, 0.0D).texture(x1 / 32.0F, y1 / 32.0F).color(red, green, blue, startAlpha).next();
        tessellator.draw();
    }

    @Override
    public void tick() {
        super.tick();
        /*if (ConfigHelper.loading) {
            MinecraftClient.getInstance().openScreen(new MaterialisationLoadingConfigScreen(this, parent));
        }*/
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
        /*addButton(new ButtonWidget(width - 104, 4, 100, 20, I18n.translate("config.button.materialisation.install"), var1 -> {
            minecraft.openScreen(new MaterialisationInstallScreen(this));
        }));
        addButton(new ButtonWidget(59, 4, 85, 20, I18n.translate("config.button.materialisation.reload"), var1 -> {
            if (!ConfigHelper.loading) {
                MinecraftClient.getInstance().openScreen(new MaterialisationLoadingConfigScreen(this, parent));
                CompletableFuture.runAsync(ConfigHelper::loadConfig, ConfigHelper.EXECUTOR_SERVICE);
            }
        }));*/
        addButton(new ButtonWidget(4, 4, 50, 20, I18n.translate("gui.back"), var1 -> minecraft.openScreen(parent)));
        children.add(materialList = new MaterialisationMaterialListWidget(minecraft, width / 2 - 10, height - 38, 28 + 5, height - 5, DrawableHelper.BACKGROUND_LOCATION));
        children.add(descriptionList = new MaterialisationDescriptionListWidget(minecraft, width / 2 - 10, height - 38, 28 + 5, height - 5, DrawableHelper.BACKGROUND_LOCATION));
        materialList.setLeftPos(5);
        descriptionList.setLeftPos(width / 2 + 5);
        Materials.MATERIALS.forEach(material -> materialList.addItem(new MaterialisationMaterialListWidget.PackEntry(material) {
            @Override
            public void onClick() {
                descriptionList.addMaterial(MaterialListScreen.this, material);
            }
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderDirtBackground(0);
        materialList.render(mouseX, mouseY, delta);
        descriptionList.render(mouseX, mouseY, delta);
        overlayBackground(0, 0, width, 28, 64, 64, 64, 255, 255);
        overlayBackground(0, height - 5, width, height, 64, 64, 64, 255, 255);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value,
                GlStateManager.SourceFactor.ZERO.value, GlStateManager.DestFactor.ONE.value);
        GlStateManager.disableAlphaTest();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_UV_COLOR);
        buffer.vertex(0, 28 + 4, 0.0D).texture(0.0F, 1.0F).color(0, 0, 0, 0).next();
        buffer.vertex(this.width, 28 + 4, 0.0D).texture(1.0F, 1.0F).color(0, 0, 0, 0).next();
        buffer.vertex(this.width, 28, 0.0D).texture(1.0F, 0.0F).color(0, 0, 0, 255).next();
        buffer.vertex(0, 28, 0.0D).texture(0.0F, 0.0F).color(0, 0, 0, 255).next();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlphaTest();
        GlStateManager.disableBlend();
        drawCenteredString(font, title.asFormattedString(), width / 2, 10, 16777215);
        super.render(mouseX, mouseY, delta);
    }

}
