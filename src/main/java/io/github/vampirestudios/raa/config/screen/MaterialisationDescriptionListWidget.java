package io.github.vampirestudios.raa.config.screen;

import io.github.vampirestudios.raa.generation.materials.Material;
import me.shedaniel.clothconfig2.gui.widget.DynamicElementListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class MaterialisationDescriptionListWidget extends DynamicElementListWidget<MaterialisationDescriptionListWidget.Entry> {
    public MaterialisationDescriptionListWidget(MinecraftClient client, int width, int height, int top, int bottom, Identifier backgroundLocation) {
        super(client, width, height, top, bottom, backgroundLocation);
    }

    @Override
    public int getItemWidth() {
        return width - 11;
    }

    @Override
    protected int getScrollbarPosition() {
        return left + width - 6;
    }

    @Override
    public int addItem(Entry item) {
        return super.addItem(item);
    }

    public void clearItemsPublic() {
        clearItems();
    }

    public void addPack(Material materialsPack) {
        clearItems();
        addItem(new TextEntry(new LiteralText(materialsPack.getName()).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        addItem(new EmptyEntry(5));
        addItem(new TextEntry(new TranslatableText("config.text.raa.identifier", materialsPack.toString()).formatted(Formatting.GRAY)));
    }

    public void addMaterial(MaterialListScreen og, Material partMaterial) {
        clearItems();
        addItem(new TitleMaterialOverrideEntry(og, partMaterial, new TranslatableText(WordUtils.capitalizeFully(partMaterial.getName())).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        DecimalFormat df = new DecimalFormat("#.##");
        addItem(new ColorEntry(I18n.translate("config.text.raa.color"), partMaterial.getRGBColor()));
        addItem(new TextEntry(new TranslatableText("config.text.raa.identifier", partMaterial.getName()).formatted(Formatting.GRAY)));
        if (partMaterial.hasTools()) {
            addItem(new TextEntry(I18n.translate("config.text.raa.enchantability", partMaterial.getToolMaterial().getEnchantability())));
            addItem(new TextEntry(I18n.translate("config.text.raa.durability", partMaterial.getToolMaterial().getDurability())));
            addItem(new TextEntry(I18n.translate("config.text.raa.mining_level", partMaterial.getToolMaterial().getMiningLevel())));
            addItem(new TextEntry(I18n.translate("config.text.raa.tool_speed", df.format(partMaterial.getToolMaterial().getMiningSpeed()))));
            addItem(new TextEntry(I18n.translate("config.text.raa.attack_damage", df.format(partMaterial.getToolMaterial().getAttackDamage()))));
        }
//        addItem(new TextEntry(I18n.translate("config.text.raa.tool_speed_multiplier", df.format(partMaterial.getToolMaterial().brea()))));
//        addItem(new TextEntry(I18n.translate("config.text.raa.durability_multiplier", df.format(partMaterial.getDurabilityMultiplier()))));
    }

    public int darkerColor(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color) & 0xFF;
        int a = (color >> 24) & 0xFF;
        return ((a & 0xFF) << 24) |
                ((Math.max((int) (r * 0.7), 0) & 0xFF) << 16) |
                ((Math.max((int) (g * 0.7), 0) & 0xFF) << 8) |
                ((Math.max((int) (b * 0.7), 0) & 0xFF));
    }

    public static class ColorEntry extends Entry {
        private String s;
        private int color;

        public ColorEntry(String s, int color) {
            this.s = s;
            this.color = color;
        }

        @Override
        public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
            int i = MinecraftClient.getInstance().textRenderer.drawWithShadow(s, x, y, 16777215);
            fillGradient(i + 1, y + 1, i + 1 + entryHeight, y + 1 + entryHeight, color, color);
        }

        @Override
        public int getItemHeight() {
            return 11;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }
    }

    public static class TitleMaterialOverrideEntry extends Entry {
        protected String s;
        private ButtonWidget overrideButton;

        public TitleMaterialOverrideEntry(MaterialListScreen og, Material partMaterial, Text text) {
            this.s = text.asFormattedString();
            String btnText = I18n.translate("config.button.raa.create_override");
            /*overrideButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(btnText) + 10, 20, btnText, widget -> {
                MinecraftClient.getInstance().openScreen(new MaterialisationCreateOverrideNameScreen(og, MinecraftClient.getInstance().currentScreen, partMaterial));
            });*/
        }

        @Override
        public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(s, x, y + 10, 16777215);
//            overrideButton.x = x + entryWidth - overrideButton.getWidth();
//            overrideButton.y = y;
//            overrideButton.render(mouseX, mouseY, delta);
        }

        @Override
        public int getItemHeight() {
            return 21;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }
    }

    public static class TextEntry extends Entry {
        protected String s;

        public TextEntry(String s) {
            this.s = s;
        }

        public TextEntry(Text text) {
            this.s = text.asFormattedString();
        }

        @Override
        public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(s, x, y, 16777215);
        }

        @Override
        public int getItemHeight() {
            return 11;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }
    }

    public static class EmptyEntry extends Entry {
        private int height;

        public EmptyEntry(int height) {
            this.height = height;
        }

        @Override
        public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {

        }

        @Override
        public int getItemHeight() {
            return height;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }
    }

    public static abstract class Entry extends DynamicElementListWidget.ElementEntry<Entry> {

    }
}