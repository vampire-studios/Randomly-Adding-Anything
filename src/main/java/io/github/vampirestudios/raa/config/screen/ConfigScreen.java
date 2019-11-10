package io.github.vampirestudios.raa.config.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.raa.config.GeneralConfig;
import io.github.vampirestudios.raa.config.screen.dimensions.DimensionListScreen;
import io.github.vampirestudios.raa.config.screen.materials.MaterialListScreen;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

public class ConfigScreen extends Screen {

    Screen parent;

    public ConfigScreen(Screen parent) {
        super(new TranslatableText("config.title.raa"));
        this.parent = parent;
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
        addButton(new ButtonWidget(width / 2, height / 2, 145, 20, I18n.translate("config.button.raa.generalConfig"), var1 -> {
            minecraft.openScreen(AutoConfig.getConfigScreen(GeneralConfig.class, this).get());
        }));
        addButton(new ButtonWidget(width / 2, height / 2, 145, 20, I18n.translate("config.button.raa.materialConfiguration"), var1 -> {
            minecraft.openScreen(new MaterialListScreen(this));
        }));
        addButton(new ButtonWidget(width / 2, height / 2, 145, 20, I18n.translate("config.button.raa.dimensionConfiguration"), var1 -> {
            minecraft.openScreen(new DimensionListScreen(this));
        }));
        addButton(new ButtonWidget(4, 4, 50, 20, I18n.translate("gui.back"), var1 -> minecraft.openScreen(parent)));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderDirtBackground(0);
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
