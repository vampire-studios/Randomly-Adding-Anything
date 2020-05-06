package io.github.vampirestudios.raa.config.screen.materials;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.raa.config.screen.ConfigScreen;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MaterialListScreen extends Screen {

    Screen parent;
    String tooltip = null;
    private RAAMaterialListWidget materialList;
    private RAAMaterialDescriptionListWidget descriptionList;

    public MaterialListScreen(Screen parent) {
        super(new TranslatableText("config.title.raa.material"));
        this.parent = parent;
    }

    @Override
    public boolean keyPressed(int int_1, int int_2, int int_3) {
        if (int_1 == 256) {
            client.openScreen(parent);
            return true;
        }
        return super.keyPressed(int_1, int_2, int_3);
    }

    @Override
    protected void init() {
        super.init();
        addButton(new ButtonWidget(4, 4, 50, 20, new TranslatableText("gui.back"), var1 -> client.openScreen(parent)));
        children.add(materialList = new RAAMaterialListWidget(client, width / 2 - 10, height,
                28 + 5, height - 5, BACKGROUND_TEXTURE));
        children.add(descriptionList = new RAAMaterialDescriptionListWidget(client, width / 2 - 10, height,
                28 + 5, height - 5, BACKGROUND_TEXTURE));
        materialList.setLeftPos(5);
        descriptionList.setLeftPos(width / 2 + 5);
        List<Material> materials = new ArrayList<>();
        for (Material material : Materials.MATERIALS) materials.add(material);
        materials.sort(Comparator.comparing(material -> WordUtils.capitalizeFully(material.getName()), String::compareToIgnoreCase));
        for (Material material : materials) {
            materialList.addItem(new RAAMaterialListWidget.MaterialEntry(material) {
                @Override
                public void onClick() {
                    descriptionList.addMaterial(MaterialListScreen.this, material);
                }

                @Override
                public boolean isSelected(Material material) {
                    return descriptionList.material == material;
                }
            });
        }
        if (!materials.isEmpty()) materialList.addItem(new RAAMaterialListWidget.EmptyEntry(10));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        tooltip = null;
        renderDirtBackground(0);
        materialList.render(matrices, mouseX, mouseY, delta);
        descriptionList.render(matrices, mouseX, mouseY, delta);
        ConfigScreen.overlayBackground(0, 0, width, 28, 64, 64, 64, 255, 255);
        ConfigScreen.overlayBackground(0, height - 5, width, height, 64, 64, 64, 255, 255);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA.field_22545, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.field_22528,
                GlStateManager.SrcFactor.ZERO.field_22545, GlStateManager.DstFactor.ONE.field_22528
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
        drawCenteredString(matrices, textRenderer, title.asString(), width / 2, 10, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
//        if (tooltip != null)
//            renderTooltip(Arrays.asList(tooltip.split("\n")), mouseX, mouseY);
    }

}
