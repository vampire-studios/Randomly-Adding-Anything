package io.github.vampirestudios.raa.config.screen.dimensions;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.dimensions.DimensionColorPalette;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.utils.Utils;
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
import net.minecraft.util.Pair;
import org.apache.commons.lang3.text.WordUtils;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RAADimensionDescriptionListWidget extends DynamicElementListWidget<RAADimensionDescriptionListWidget.Entry> {

    public RAADimensionDescriptionListWidget(MinecraftClient client, int width, int height, int top, int bottom, Identifier backgroundLocation) {
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

    public void addMaterial(DimensionListScreen og, DimensionData dimensionData) {
        clearItems();
//        addItem(new TitleMaterialOverrideEntry(og, dimensionData, new LiteralText(WordUtils.capitalizeFully(dimensionData.getName())).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        DimensionColorPalette colorPalette = dimensionData.getDimensionColorPalette();
        addItem(new TitleEntry(new LiteralText(WordUtils.capitalizeFully(dimensionData.getName())).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        addItem(new TextEntry(new TranslatableText("config.text.raa.identifier").formatted(Formatting.GRAY)
                .append(new TranslatableText("config.text.raa.var",  dimensionData.getId()).formatted(Formatting.WHITE))));
        //sky
        if (dimensionData.hasSky())
            addItem(new TextEntry(new TranslatableText("config.text.raa.hasSky").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.hasSky()).formatted(Formatting.GREEN))));
        else
            addItem(new TextEntry(new TranslatableText("config.text.raa.hasSky").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.hasSky()).formatted(Formatting.RED))));
        //sky light
        if (dimensionData.hasSkyLight())
            addItem(new TextEntry(new TranslatableText("config.text.raa.hasSkyLight").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.hasSkyLight()).formatted(Formatting.GREEN))));
        else
            addItem(new TextEntry(new TranslatableText("config.text.raa.hasSkyLight").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.hasSkyLight()).formatted(Formatting.RED))));
        //sleep
        if (dimensionData.canSleep())
            addItem(new TextEntry(new TranslatableText("config.text.raa.canSleep").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.canSleep()).formatted(Formatting.GREEN))));
        else
            addItem(new TextEntry(new TranslatableText("config.text.raa.canSleep").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.canSleep()).formatted(Formatting.RED))));
        //water vaporizes
        if (dimensionData.doesWaterVaporize())
            addItem(new TextEntry(new TranslatableText("config.text.raa.waterVaporize").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.doesWaterVaporize()).formatted(Formatting.GREEN))));
        else
            addItem(new TextEntry(new TranslatableText("config.text.raa.waterVaporize").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.doesWaterVaporize()).formatted(Formatting.RED))));
        //fog
        if (dimensionData.shouldRenderFog())
            addItem(new TextEntry(new TranslatableText("config.text.raa.renderFog").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.shouldRenderFog()).formatted(Formatting.GREEN))));
        else
            addItem(new TextEntry(new TranslatableText("config.text.raa.renderFog").formatted(Formatting.GRAY)
                    .append(new TranslatableText("config.text.raa.var",  dimensionData.shouldRenderFog()).formatted(Formatting.RED))));

        //determine formatting colors
        //the numbers will have to change when more dangerous dimensions are added
        Formatting difficultyFormatting = Formatting.GREEN;
        if (dimensionData.getDifficulty() > 2) difficultyFormatting = Formatting.YELLOW;
        if (dimensionData.getDifficulty() > 6) difficultyFormatting = Formatting.RED;
        if (dimensionData.getDifficulty() > 10) difficultyFormatting = Formatting.DARK_RED;
        addItem(new TextEntry(new TranslatableText("config.text.raa.difficulty").formatted(Formatting.GRAY)
                .append(new TranslatableText("config.text.raa.var",  dimensionData.getDifficulty()).formatted(difficultyFormatting))));

        if (dimensionData.getFlags() != 0) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.flags").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            int flags = dimensionData.getFlags();
            if (Utils.checkBitFlag(flags, Utils.LUSH)) addItem(new TextEntry(new TranslatableText("config.text.raa.flags.lush", dimensionData.shouldRenderFog()).formatted(Formatting.GREEN)));
            if (Utils.checkBitFlag(flags, Utils.CIVILIZED)) addItem(new TextEntry(new TranslatableText("config.text.raa.flags.civilized", dimensionData.shouldRenderFog()).formatted(Formatting.DARK_GREEN)));
            if (Utils.checkBitFlag(flags, Utils.ABANDONED)) addItem(new TextEntry(new TranslatableText("config.text.raa.flags.abandoned", dimensionData.shouldRenderFog()).formatted(Formatting.GRAY)));
            if (Utils.checkBitFlag(flags, Utils.DEAD)) addItem(new TextEntry(new TranslatableText("config.text.raa.flags.dead", dimensionData.shouldRenderFog()).formatted(Formatting.DARK_GRAY)));
            if (Utils.checkBitFlag(flags, Utils.DRY)) addItem(new TextEntry(new TranslatableText("config.text.raa.flags.dry", dimensionData.shouldRenderFog()).formatted(Formatting.YELLOW)));
            if (Utils.checkBitFlag(flags, Utils.TECTONIC)) addItem(new TextEntry(new TranslatableText("config.text.raa.flags.tectonic", dimensionData.shouldRenderFog()).formatted(Formatting.DARK_GRAY)));
            if (Utils.checkBitFlag(flags, Utils.MOLTEN)) addItem(new TextEntry(new TranslatableText("config.text.raa.flags.molten", dimensionData.shouldRenderFog()).formatted(Formatting.YELLOW)));
            if (Utils.checkBitFlag(flags, Utils.CORRUPTED)) addItem(new TextEntry(new TranslatableText("config.text.raa.flags.corrupted", dimensionData.shouldRenderFog()).formatted(Formatting.DARK_RED)));
        }

        if (dimensionData.getCivilizationInfluences().size() > 0) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.civs").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            for (Map.Entry<String, Double> pair : dimensionData.getCivilizationInfluences().entrySet()) {
                if (pair.getValue() != 1.0) {
                    addItem(new TextEntry(new TranslatableText("config.text.raa.var", pair.getKey()).formatted(Formatting.GRAY)
                            .append(": ").formatted(Formatting.GRAY)
                            .append(new TranslatableText("config.text.raa.var", new DecimalFormat("##.00").format(pair.getValue() * 100)).formatted(Formatting.WHITE)
                                    .append("%").formatted(Formatting.WHITE))));
                } else {
                    addItem(new TextEntry(new TranslatableText("config.text.raa.var", pair.getKey()).formatted(Formatting.GRAY)
                            .append(": ").formatted(Formatting.GRAY)
                            .append(new TranslatableText("config.text.raa.var", new DecimalFormat("##.00")
                                    .format(pair.getValue() * 100)).formatted(Formatting.WHITE)
                            .append("%").formatted(Formatting.WHITE)
                            .append(" (").formatted(Formatting.WHITE)
                            .append(new TranslatableText("config.text.raa.civs.home")).formatted(Formatting.WHITE)
                            .append(")").formatted(Formatting.WHITE))));
                }
            }
        }

        addItem(new TitleEntry(new TranslatableText("config.title.raa.colors").formatted(Formatting.UNDERLINE, Formatting.BOLD)));

        if (dimensionData.hasSky()) {
            addItem(new ColorEntry("config.text.raa.skyColor", colorPalette.getSkyColor()));
        }
        addItem(new ColorEntry("config.text.raa.grassColor", colorPalette.getGrassColor()));
        addItem(new ColorEntry("config.text.raa.fogColor", colorPalette.getFogColor()));
        addItem(new ColorEntry("config.text.raa.foliageColor", colorPalette.getFoliageColor()));
        addItem(new ColorEntry("config.text.raa.stoneColor", colorPalette.getStoneColor()));
        addItem(new ColorEntry("config.text.raa.waterColor", dimensionData.getBiomeData().getWaterColor()));

        /*if (dimensionData.hasTools()) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.tools").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            addItem(new TextEntry(new TranslatableText("config.text.raa.enchantability", dimensionData.getToolMaterial().getEnchantability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.durability", dimensionData.getToolMaterial().getDurability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.mining_level", dimensionData.getToolMaterial().getMiningLevel())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.tool_speed", df.format(dimensionData.getToolMaterial().getMiningSpeed()))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.attack_damage", df.format(dimensionData.getToolMaterial().getAttackDamage()))));
        }
        if (dimensionData.hasWeapons()) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.weapons").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            addItem(new TextEntry(new TranslatableText("config.text.raa.enchantability", dimensionData.getToolMaterial().getEnchantability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.durability", dimensionData.getToolMaterial().getDurability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.mining_level", dimensionData.getToolMaterial().getMiningLevel())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.tool_speed", df.format(dimensionData.getToolMaterial().getMiningSpeed()))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.attack_damage", df.format(dimensionData.getToolMaterial().getAttackDamage()))));
        }*/
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
            int i = MinecraftClient.getInstance().textRenderer.drawWithShadow(Formatting.GRAY.toString() + I18n.translate(s) + Formatting.WHITE.toString() + I18n.translate("#" + Integer.toHexString(color).replace("ff", "")), x, y, 16777215);
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

        public TitleMaterialOverrideEntry(DimensionListScreen og, DimensionData material, Text text) {
            this.s = text.asFormattedString();
            String btnText = I18n.translate("config.button.raa.edit");
            overrideButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(btnText) + 10, 20, btnText, widget -> {
                openClothConfigForMaterial(og, material);
            });
        }

        private static void openClothConfigForMaterial(DimensionListScreen og, DimensionData material) {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(new DimensionListScreen(og))
                    .setTitle(I18n.translate("config.title.raa.material", WordUtils.capitalizeFully(material.getName())));
            ConfigCategory category = builder.getOrCreateCategory("null"); // The name is not required if we only have 1 category in Cloth Config 1.8+
            ConfigEntryBuilder eb = builder.entryBuilder();
            category.addEntry(
                    eb.startStrField("config.field.raa.identifier", material.getId().getPath())
                            .setDefaultValue(material.getId().getPath())
                            .setSaveConsumer(material::setId)
                            .setErrorSupplier(str -> {
                                if (str.toLowerCase().equals(str))
                                    return Optional.empty();
                                return Optional.of(I18n.translate("config.error.raa.identifier.no.caps"));
                            })
                            .build()
            );
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.hasSky", material.hasSky())
                            .setDefaultValue(material.hasSky())
                            .build()
            );
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.hasSkyLight", material.hasSkyLight())
                            .setDefaultValue(material.hasSkyLight())
                            .build()
            );
            if (material.hasSky()) {
                category.addEntry(
                        eb.startStrField("config.field.raa.skyColor", Integer.toHexString(material.getDimensionColorPalette().getSkyColor()).replace("ff", ""))
                                .setDefaultValue(Integer.toHexString(material.getDimensionColorPalette().getSkyColor()).replace("ff", ""))
                                .build()
                );
            }
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.canSleep", material.canSleep())
                            .setDefaultValue(material.canSleep())
                            .build()
            );
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.doesWaterVaporize", material.doesWaterVaporize())
                            .setDefaultValue(material.doesWaterVaporize())
                            .build()
            );
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.shouldRenderFog", material.shouldRenderFog())
                            .setDefaultValue(material.shouldRenderFog())
                            .build()
            );
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