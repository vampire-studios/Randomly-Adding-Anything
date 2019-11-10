package io.github.vampirestudios.raa.config.screen.materials;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.raa.config.screen.dimensions.DimensionListScreen;
import io.github.vampirestudios.raa.generation.materials.Material;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
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
import java.util.Optional;

public class RAAMaterialDescriptionListWidget extends DynamicElementListWidget<RAAMaterialDescriptionListWidget.Entry> {

    public RAAMaterialDescriptionListWidget(MinecraftClient client, int width, int height, int top, int bottom, Identifier backgroundLocation) {
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

    public void addMaterial(DimensionListScreen og, Material material) {
        clearItems();
        addItem(new TitleMaterialOverrideEntry(og, material, new LiteralText(WordUtils.capitalizeFully(material.getName())).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        DecimalFormat df = new DecimalFormat("#.##");
        addItem(new ColorEntry("config.text.raa.color", material.getRGBColor()));
        addItem(new TextEntry(new TranslatableText("config.text.raa.identifier", material.getId().toString()).formatted(Formatting.GRAY)));
        if (material.hasTools()) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.tools").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            addItem(new TextEntry(new TranslatableText("config.text.raa.enchantability", material.getToolMaterial().getEnchantability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.durability", material.getToolMaterial().getDurability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.mining_level", material.getToolMaterial().getMiningLevel())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.tool_speed", df.format(material.getToolMaterial().getMiningSpeed()))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.attack_damage", df.format(material.getToolMaterial().getAttackDamage()))));
        }
        if (material.hasWeapons()) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.weapons").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            addItem(new TextEntry(new TranslatableText("config.text.raa.enchantability", material.getToolMaterial().getEnchantability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.durability", material.getToolMaterial().getDurability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.mining_level", material.getToolMaterial().getMiningLevel())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.tool_speed", df.format(material.getToolMaterial().getMiningSpeed()))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.attack_damage", df.format(material.getToolMaterial().getAttackDamage()))));
        }
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
            int i = MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(s, "#" + Integer.toHexString(color).replace("ff", "")), x, y, 16777215);
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

        public TitleMaterialOverrideEntry(DimensionListScreen og, Material material, Text text) {
            this.s = text.asFormattedString();
            String btnText = I18n.translate("config.button.raa.edit");
            overrideButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(btnText) + 10, 20, btnText, widget -> {
                openClothConfigForMaterial(og, material);
            });
        }

        private static void openClothConfigForMaterial(DimensionListScreen og, Material material) {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(new MaterialListScreen(og))
                    .setTitle(I18n.translate("config.title.raa.material", WordUtils.capitalizeFully(material.getName())));
            ConfigCategory category = builder.getOrCreateCategory("null"); // The name is not required if we only have 1 category in Cloth Config 1.8+
            ConfigEntryBuilder eb = builder.entryBuilder();
            category.addEntry(
                    eb.startStrField("config.field.raa.identifier", material.getName())
                            .setDefaultValue(material.getName())
                            .setSaveConsumer(material::setName)
                            .setErrorSupplier(str -> {
                                if (str.toLowerCase().equals(str))
                                    return Optional.empty();
                                return Optional.of(I18n.translate("config.error.raa.identifier.no.caps"));
                            })
                            .build()
            );
            if (material.hasTools()) {
                category.addEntry(
                        eb.startIntField("config.field.raa.enchantability", material.getToolMaterial().getEnchantability())
                                .setDefaultValue(material.getToolMaterial().getEnchantability())
                                .setSaveConsumer(i -> material.getToolMaterial().setEnchantability(i))
                                .setMin(0)
                                .build()
                );
                category.addEntry(
                        eb.startIntField("config.field.raa.durability", material.getToolMaterial().getDurability())
                                .setDefaultValue(material.getToolMaterial().getDurability())
                                .setSaveConsumer(i -> material.getToolMaterial().setDurability(i))
                                .setMin(1)
                                .build()
                );
                category.addEntry(
                        eb.startIntField("config.field.raa.mining_level", material.getToolMaterial().getMiningLevel())
                                .setDefaultValue(material.getToolMaterial().getMiningLevel())
                                .setSaveConsumer(i -> material.getToolMaterial().setMiningLevel(i))
                                .setMin(0)
                                .build()
                );
                category.addEntry(
                        eb.startFloatField("config.field.raa.tool_speed", material.getToolMaterial().getMiningSpeed())
                                .setDefaultValue(material.getToolMaterial().getMiningSpeed())
                                .setSaveConsumer(i -> material.getToolMaterial().setMiningSpeed(i))
                                .setMin(0)
                                .build()
                );
                category.addEntry(
                        eb.startFloatField("config.field.raa.attack_damage", material.getToolMaterial().getAttackDamage())
                                .setDefaultValue(material.getToolMaterial().getAttackDamage())
                                .setSaveConsumer(i -> material.getToolMaterial().setAttackDamage(i))
                                .build()
                );
            }
            if (material.hasWeapons()) {

            }
            builder.setSavingRunnable(RandomlyAddingAnything.MATERIALS_CONFIG::save);
            MinecraftClient.getInstance().openScreen(builder.build());
        }

        @Override
        public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
            RenderSystem.pushMatrix();
            RenderSystem.scalef(1.4F, 1.4F, 1.4F);
            x = 175;
            y = 20;
            MinecraftClient.getInstance().textRenderer.drawWithShadow(s, x, y + 10, 16777215);
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            RenderSystem.popMatrix();
            x = 245;
            y = 37;
            overrideButton.x = x + entryWidth - overrideButton.getWidth();
            overrideButton.y = y;
            overrideButton.render(mouseX, mouseY, delta);
        }

        @Override
        public int getItemHeight() {
            return 21;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.singletonList(overrideButton);
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

    public static class TitleEntry extends Entry {
        protected String s;

        public TitleEntry(String s) {
            this.s = s;
        }

        public TitleEntry(Text text) {
            this.s = text.asFormattedString();
        }

        @Override
        public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(s, x, y + 10, 16777215);
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