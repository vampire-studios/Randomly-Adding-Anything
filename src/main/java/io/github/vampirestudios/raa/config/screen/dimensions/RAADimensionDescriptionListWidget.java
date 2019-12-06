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
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
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
import java.util.Map;
import java.util.Optional;

public class RAADimensionDescriptionListWidget extends DynamicElementListWidget<RAADimensionDescriptionListWidget.Entry> {

    DimensionData data;

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
        this.data = dimensionData;
        clearItems();
        addItem(new TitleMaterialOverrideEntry(og, dimensionData, new LiteralText(WordUtils.capitalizeFully(dimensionData.getName())).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        DimensionColorPalette colorPalette = dimensionData.getDimensionColorPalette();
//        addItem(new TitleEntry(new LiteralText(WordUtils.capitalizeFully(dimensionData.getName())).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        addItem(new TextEntry(new TranslatableText("config.text.raa.identifier", dimensionData.getId().toString())));

        addItem(new TextEntry(new TranslatableText("config.text.raa.hasSky", new TranslatableText("config.text.raa.boolean.value." + dimensionData.hasSky()))));
        addItem(new TextEntry(new TranslatableText("config.text.raa.hasSkyLight", new TranslatableText("config.text.raa.boolean.value." + dimensionData.hasSkyLight()))));
        addItem(new TextEntry(new TranslatableText("config.text.raa.canSleep", new TranslatableText("config.text.raa.boolean.value." + dimensionData.canSleep()))));
        addItem(new TextEntry(new TranslatableText("config.text.raa.waterVaporize", new TranslatableText("config.text.raa.boolean.value." + dimensionData.doesWaterVaporize()))));
        addItem(new TextEntry(new TranslatableText("config.text.raa.renderFog", new TranslatableText("config.text.raa.boolean.value." + dimensionData.shouldRenderFog()))));

        //determine formatting colors
        //the numbers will have to change when more dangerous dimensions are added
        Formatting difficultyFormatting = Formatting.GREEN;
        if (dimensionData.getDifficulty() > 2) difficultyFormatting = Formatting.YELLOW;
        if (dimensionData.getDifficulty() > 6) difficultyFormatting = Formatting.RED;
        if (dimensionData.getDifficulty() > 10) difficultyFormatting = Formatting.DARK_RED;
        addItem(new TextEntry(new TranslatableText("config.text.raa.difficulty", new LiteralText(dimensionData.getDifficulty() + "").formatted(difficultyFormatting))));

        if (dimensionData.getFlags() != 0) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.flags").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            int flags = dimensionData.getFlags();
            if (Utils.checkBitFlag(flags, Utils.LUSH))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.lush").formatted(Formatting.GREEN), "config.tooltip.raa.lush", og));
            if (Utils.checkBitFlag(flags, Utils.CIVILIZED))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.civilized").formatted(Formatting.DARK_GREEN), "config.tooltip.raa.civilized", og));
            if (Utils.checkBitFlag(flags, Utils.ABANDONED))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.abandoned").formatted(Formatting.GRAY), "config.tooltip.raa.abandoned", og));
            if (Utils.checkBitFlag(flags, Utils.DEAD))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.dead").formatted(Formatting.DARK_GRAY), "config.tooltip.raa.dead", og));
            if (Utils.checkBitFlag(flags, Utils.DRY))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.dry").formatted(Formatting.YELLOW), "config.tooltip.raa.dry", og));
            if (Utils.checkBitFlag(flags, Utils.TECTONIC))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.tectonic").formatted(Formatting.DARK_GRAY), "config.tooltip.raa.tectonic", og));
            if (Utils.checkBitFlag(flags, Utils.MOLTEN))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.molten").formatted(Formatting.YELLOW), "config.tooltip.raa.molten", og));
            if (Utils.checkBitFlag(flags, Utils.CORRUPTED))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.corrupted").formatted(Formatting.DARK_RED), "config.tooltip.raa.corrupted", og));
        }

        if (dimensionData.getCivilizationInfluences().size() > 0) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.civs").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            for (Map.Entry<String, Double> pair : dimensionData.getCivilizationInfluences().entrySet()) {
                if (pair.getValue() != 1.0) {
                    addItem(new TextEntry(new TranslatableText("config.text.raa.civs.var", pair.getKey(), new DecimalFormat("##.00").format(pair.getValue() * 100))));
                } else {
                    addItem(new TextEntry(new TranslatableText("config.text.raa.civs.var.home", pair.getKey(), new DecimalFormat("##.00").format(pair.getValue() * 100))));
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
        addItem(new ColorEntry("config.text.raa.waterColor", dimensionData.getBiomeData().get(0).getWaterColor()));
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

        @SuppressWarnings("deprecation")
        private static void openClothConfigForMaterial(DimensionListScreen og, DimensionData material) {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(og)
                    .setTitle(I18n.translate("config.title.raa.config_specific", WordUtils.capitalizeFully(material.getName())));
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

            //TODO: refactor this with array support
            /*category.addEntry(
                    eb.startStrField("config.field.raa.name", material.getName())
                            .setDefaultValue(material.getName())
                            .setSaveConsumer(material::setName)
                            .build()
            );*/
//            SubCategoryBuilder biomeData = eb.startSubCategory("config.title.raa.biomeData").setExpended(false);
//            biomeData.add(
//                    eb.startStrField("config.field.raa.biomeData.id", material.getBiomeData().getId().getPath())
//                            .setDefaultValue(material.getBiomeData().getId().getPath())
//                            .setSaveConsumer(str -> material.getBiomeData().setId(new Identifier(RandomlyAddingAnything.MOD_ID, str)))
//                            .build()
//            );
//            biomeData.add(
//                    eb.startStrField("config.field.raa.biomeData.name", material.getBiomeData().getName())
//                            .setDefaultValue(material.getBiomeData().getName())
//                            .setSaveConsumer(material.getBiomeData()::setName)
//                            .build()
//            );
//            biomeData.add(
//                    eb.startIntField("config.field.raa.biomeData.surfaceBuilderVariantChance",
//                            material.getBiomeData().getSurfaceBuilderVariantChance())
//                            .setDefaultValue(material.getBiomeData().getSurfaceBuilderVariantChance())
//                            .setSaveConsumer(material.getBiomeData()::setSurfaceBuilderVariantChance)
//                            .build()
//            );
//            biomeData.add(
//                    eb.startFloatField("config.field.raa.biomeData.depth", material.getBiomeData().getDepth())
//                            .setDefaultValue(material.getBiomeData().getDepth())
//                            .setSaveConsumer(material.getBiomeData()::setDepth)
//                            .build()
//            );
//            biomeData.add(
//                    eb.startFloatField("config.field.raa.biomeData.scale", material.getBiomeData().getScale())
//                            .setDefaultValue(material.getBiomeData().getScale())
//                            .setSaveConsumer(material.getBiomeData()::setScale)
//                            .build()
//            );
//            biomeData.add(
//                    eb.startFloatField("config.field.raa.biomeData.temperature", material.getBiomeData().getTemperature())
//                            .setDefaultValue(material.getBiomeData().getTemperature())
//                            .setSaveConsumer(material.getBiomeData()::setTemperature)
//                            .build()
//            );
//            biomeData.add(
//                    eb.startFloatField("config.field.raa.biomeData.downfall", material.getBiomeData().getDownfall())
//                            .setDefaultValue(material.getBiomeData().getDownfall())
//                            .setSaveConsumer(material.getBiomeData()::setDownfall)
//                            .build()
//            );
//            category.addEntry(biomeData.build());

            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.hasSky", material.hasSky())
                            .setDefaultValue(material.hasSky())
                            .setSaveConsumer(material::setHasSky)
                            .build()
            );
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.hasSkyLight", material.hasSkyLight())
                            .setDefaultValue(material.hasSkyLight())
                            .setSaveConsumer(material::setHasSkyLight)
                            .build()
            );
            // TODO Fix this
//            if (material.hasSky()) {
//                category.addEntry(
//                        eb.startStrField("config.field.raa.skyColor", Integer.toHexString(material.getDimensionColorPalette().getSkyColor()).replaceFirst("ff", ""))
//                                .setDefaultValue(Integer.toHexString(material.getDimensionColorPalette().getSkyColor()).replaceFirst("ff", ""))
//                                .setSaveConsumer(str -> material.getDimensionColorPalette().setSkyColor(Integer.decode("0x" + str)))
//                                .setErrorSupplier(str -> {
//                                    try {
//                                        Integer.decode("0x" + str);
//                                        return Optional.empty();
//                                    } catch (Exception e) {
//                                        return Optional.of(I18n.translate("config.error.raa.invalid.color"));
//                                    }
//                                })
//                                .build()
//                );
//            }
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.canSleep", material.canSleep())
                            .setDefaultValue(material.canSleep())
                            .setSaveConsumer(material::setCanSleep)
                            .build()
            );
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.doesWaterVaporize", material.doesWaterVaporize())
                            .setDefaultValue(material.doesWaterVaporize())
                            .setSaveConsumer(material::setWaterVaporize)
                            .build()
            );
            category.addEntry(
                    eb.startBooleanToggle("config.field.raa.shouldRenderFog", material.shouldRenderFog())
                            .setDefaultValue(material.shouldRenderFog())
                            .setSaveConsumer(material::setRenderFog)
                            .build()
            );
            builder.setSavingRunnable(RandomlyAddingAnything.DIMENSIONS_CONFIG::overrideFile);
            MinecraftClient.getInstance().openScreen(builder.build());
        }

        @Override
        public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
            RenderSystem.pushMatrix();
            RenderSystem.scalef(1.4F, 1.4F, 1.4F);
            MinecraftClient.getInstance().textRenderer.drawWithShadow(s, x / 1.4f, (y + 5) / 1.4f, 16777215);
            RenderSystem.popMatrix();
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

    public static class TextEntryWithTooltip extends Entry {
        protected String s;
        protected String tooltip;
        protected DimensionListScreen screen;

        public TextEntryWithTooltip(Text text, String tooltip, DimensionListScreen screen) {
            this.s = text.asFormattedString();
            this.tooltip = I18n.hasTranslation(tooltip) ? I18n.translate(tooltip) : null;
            this.screen = screen;
        }

        @Override
        public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(s, x, y, 16777215);
            if (tooltip != null && mouseX >= x && mouseY >= y && mouseX <= x + MinecraftClient.getInstance().textRenderer.getStringWidth(s) && mouseY <= y + getItemHeight())
                screen.tooltip = tooltip;
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

    public static class TextEntry extends Entry {
        protected String s;

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