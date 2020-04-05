package io.github.vampirestudios.raa.config.screen.dimensions;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionBiomeData;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionColorPalette;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
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
    public int addItem(io.github.vampirestudios.raa.config.screen.dimensions.RAADimensionDescriptionListWidget.Entry item) {
        return super.addItem(item);
    }

    public void addMaterial(DimensionListScreen og, DimensionData dimensionData) {
        this.data = dimensionData;
        clearItems();
        addItem(new TitleMaterialOverrideEntry(og, dimensionData, new LiteralText(WordUtils.capitalizeFully(dimensionData.getName())).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        DimensionColorPalette colorPalette = dimensionData.getDimensionColorPalette();
        addItem(new TextEntry(new TranslatableText("config.text.raa.identifier", dimensionData.getId().toString())));

        addItem(new TextEntry(new TranslatableText("config.text.raa.hasSky", new TranslatableText("config.text.raa.boolean.value." + dimensionData.getCustomSkyInformation().hasSky()))));
        addItem(new TextEntry(new TranslatableText("config.text.raa.hasSkyLight", new TranslatableText("config.text.raa.boolean.value." + dimensionData.getCustomSkyInformation().hasSkyLight()))));
        addItem(new TextEntry(new TranslatableText("config.text.raa.canSleep", new TranslatableText("config.text.raa.boolean.value." + dimensionData.canSleep()))));
        addItem(new TextEntry(new TranslatableText("config.text.raa.waterVaporize", new TranslatableText("config.text.raa.boolean.value." + dimensionData.doesWaterVaporize()))));
        addItem(new TextEntry(new TranslatableText("config.text.raa.renderFog", new TranslatableText("config.text.raa.boolean.value." + dimensionData.hasThickFog()))));

        //determine formatting colors
        //the numbers will have to change when more dangerous dimensions are added
        Formatting difficultyFormatting = Formatting.GREEN;
        if (dimensionData.getDifficulty() > 2) difficultyFormatting = Formatting.YELLOW;
        if (dimensionData.getDifficulty() > 6) difficultyFormatting = Formatting.RED;
        if (dimensionData.getDifficulty() > 10) difficultyFormatting = Formatting.DARK_RED;
        addItem(new TextEntry(new TranslatableText("config.text.raa.difficulty", new LiteralText(dimensionData.getDifficulty() + "").formatted(difficultyFormatting))));

        addItem(new TitleEntry(new TranslatableText("config.title.raa.advancedInformation").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        addItem(new TextEntry(new TranslatableText("config.text.raa.chunkGenerator", WordUtils.capitalizeFully(dimensionData.getDimensionChunkGenerator().toString().replace("_", " ").toLowerCase()))));

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
            if (Utils.checkBitFlag(flags, Utils.LUCID))
                addItem(new TextEntryWithTooltip(new TranslatableText("config.text.raa.flags.lucid").formatted(Formatting.DARK_RED), "config.tooltip.raa.lucid", og));
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

        if (dimensionData.getCustomSkyInformation().hasSky()) {
            addItem(new ColorEntry("config.text.raa.skyColor", colorPalette.getSkyColor()));
        }
        addItem(new ColorEntry("config.text.raa.grassColor", colorPalette.getGrassColor()));
        addItem(new ColorEntry("config.text.raa.fogColor", colorPalette.getFogColor()));
        addItem(new ColorEntry("config.text.raa.foliageColor", colorPalette.getFoliageColor()));
        addItem(new ColorEntry("config.text.raa.stoneColor", colorPalette.getStoneColor()));
        addItem(new ColorEntry("config.text.raa.waterColor", dimensionData.getBiomeData().get(0).getWaterColor()));

        for (DimensionBiomeData biomeData : dimensionData.getBiomeData()) {
            addItem(new TitleEntry(new LiteralText(WordUtils.capitalizeFully(biomeData.getId().getPath().replace("_", " ")))));
        }
    }

    public static class ColorEntry extends io.github.vampirestudios.raa.config.screen.dimensions.RAADimensionDescriptionListWidget.Entry {
        private final String s;
        private final int color;

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

    public static class TitleMaterialOverrideEntry extends RAADimensionDescriptionListWidget.Entry {
        protected String s;
        private final ButtonWidget overrideButton;

        public TitleMaterialOverrideEntry(DimensionListScreen og, DimensionData material, Text text) {
            this.s = text.asFormattedString();
            String btnText = I18n.translate("config.button.raa.edit");
            overrideButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(btnText) + 10, 20, btnText,
                    widget -> openClothConfigForMaterial(og, material));
        }

        private static void openClothConfigForMaterial(DimensionListScreen og, DimensionData dimensionData) {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(og)
                    .setTitle(I18n.translate("config.title.raa.config_specific", WordUtils.capitalizeFully(dimensionData.getName())));
            ConfigCategory category = builder.getOrCreateCategory("null"); // The name is not required if we only have 1 category in Cloth Config 1.8+
            ConfigEntryBuilder eb = builder.entryBuilder();
            category.addEntry(
                    eb.startStrField("config.field.raa.identifier", dimensionData.getId().getPath())
                            .setDefaultValue(dimensionData.getId().getPath())
                            .setSaveConsumer(dimensionData::setId)
                            .setErrorSupplier(str -> {
                                if (str.toLowerCase().equals(str))
                                    return Optional.empty();
                                return Optional.of(I18n.translate("config.error.raa.identifier.no.caps"));
                            })
                            .requireRestart()
                            .build()
            );
            category.addEntry(
                    eb.startStrField("config.field.raa.name", dimensionData.getName())
                            .setDefaultValue(dimensionData.getName())
                            .setSaveConsumer(dimensionData::setName)
                            .build()
            );

            SubCategoryBuilder misc = eb.startSubCategory(I18n.translate("config.title.raa.misc")).setExpanded(false);
            misc.add(
                    eb.startBooleanToggle("config.field.raa.canSleep", dimensionData.canSleep())
                            .setDefaultValue(dimensionData.canSleep())
                            .setSaveConsumer(dimensionData::setCanSleep)
                            .build()
            );
            misc.add(
                    eb.startBooleanToggle("config.field.raa.doesWaterVaporize", dimensionData.doesWaterVaporize())
                            .setDefaultValue(dimensionData.doesWaterVaporize())
                            .setSaveConsumer(dimensionData::setWaterVaporize)
                            .build()
            );
            misc.add(
                    eb.startBooleanToggle("config.field.raa.shouldRenderFog", dimensionData.hasThickFog())
                            .setDefaultValue(dimensionData.hasThickFog())
                            .setSaveConsumer(dimensionData::setRenderFog)
                            .build()
            );
            misc.add(
                    eb.startFloatField("config.field.raa.stoneHardness", dimensionData.getStoneHardness())
                            .setDefaultValue(dimensionData.getStoneHardness())
                            .setSaveConsumer(dimensionData::setStoneHardness)
                            .build()
            );
            misc.add(
                    eb.startFloatField("config.field.raa.stoneResistance", dimensionData.getStoneResistance())
                            .setDefaultValue(dimensionData.getStoneResistance())
                            .setSaveConsumer(dimensionData::setStoneResistance)
                            .build()
            );
            category.addEntry(misc.build());

            SubCategoryBuilder colors = eb.startSubCategory(I18n.translate("config.title.raa.colors")).setExpanded(false);
            if (dimensionData.getCustomSkyInformation().hasSky()) {
                colors.add(
                        eb.startAlphaColorField("config.field.raa.skyColor", dimensionData.getDimensionColorPalette().getSkyColor())
                                .setDefaultValue(dimensionData.getDimensionColorPalette().getSkyColor())
                                .setSaveConsumer(integer -> dimensionData.getDimensionColorPalette().setSkyColor(integer))
                                .build()
                );
            }
            colors.add(
                    eb.startAlphaColorField("config.field.raa.fogColor", dimensionData.getDimensionColorPalette().getFogColor())
                            .setDefaultValue(dimensionData.getDimensionColorPalette().getFogColor())
                            .setSaveConsumer(integer -> dimensionData.getDimensionColorPalette().setFogColor(integer))
                            .build()
            );
            colors.add(
                    eb.startAlphaColorField("config.field.raa.stoneColor", dimensionData.getDimensionColorPalette().getStoneColor())
                            .setDefaultValue(dimensionData.getDimensionColorPalette().getStoneColor())
                            .setSaveConsumer(integer -> dimensionData.getDimensionColorPalette().setStoneColor(integer))
                            .build()
            );
            category.addEntry(colors.build());

            SubCategoryBuilder astralBody = eb.startSubCategory(I18n.translate("config.title.raa.astralBody")).setExpanded(false);
            astralBody.add(
                    eb.startBooleanToggle("config.field.raa.hasSky", dimensionData.getCustomSkyInformation().hasSky())
                            .setDefaultValue(dimensionData.getCustomSkyInformation().hasSky())
                            .setSaveConsumer(dimensionData.getCustomSkyInformation()::setHasSky)
                            .build()
            );
            astralBody.add(
                    eb.startBooleanToggle("config.field.raa.hasSkyLight", dimensionData.getCustomSkyInformation().hasSkyLight())
                            .setDefaultValue(dimensionData.getCustomSkyInformation().hasSkyLight())
                            .setSaveConsumer(dimensionData.getCustomSkyInformation()::setHasSkyLight)
                            .build()
            );
            astralBody.add(
                    eb.startBooleanToggle("config.field.raa.hasCustomSun", dimensionData.getCustomSkyInformation().hasCustomSun())
                            .setDefaultValue(dimensionData.getCustomSkyInformation().hasCustomSun())
                            .setSaveConsumer(dimensionData.getCustomSkyInformation()::shouldHaveCustomSun)
                            .build()
            );
            astralBody.add(
                    eb.startBooleanToggle("config.field.raa.hasCustomMoon", dimensionData.getCustomSkyInformation().hasCustomMoon())
                            .setDefaultValue(dimensionData.getCustomSkyInformation().hasCustomMoon())
                            .setSaveConsumer(dimensionData.getCustomSkyInformation()::shouldHaveCustomMoon)
                            .build()
            );
            if (dimensionData.getCustomSkyInformation().hasCustomSun()) {
                astralBody.add(
                        eb.startAlphaColorField("config.field.raa.sunColor", dimensionData.getCustomSkyInformation().getSunTint())
                                .setDefaultValue(dimensionData.getCustomSkyInformation().getSunTint())
                                .setSaveConsumer(integer -> dimensionData.getCustomSkyInformation().setSunTint(integer))
                                .build()
                );
                astralBody.add(
                        eb.startFloatField("config.field.raa.sunSize", dimensionData.getCustomSkyInformation().getSunSize())
                                .setDefaultValue(dimensionData.getCustomSkyInformation().getSunSize())
                                .setSaveConsumer(size -> dimensionData.getCustomSkyInformation().setSunSize(size))
                                .build()
                );
            }
            if (dimensionData.getCustomSkyInformation().hasCustomMoon()) {
                astralBody.add(
                        eb.startAlphaColorField("config.field.raa.moonColor", dimensionData.getCustomSkyInformation().getMoonTint())
                                .setDefaultValue(dimensionData.getCustomSkyInformation().getMoonTint())
                                .setSaveConsumer(integer -> dimensionData.getCustomSkyInformation().setMoonTint(integer))
                                .build()
                );
                astralBody.add(
                        eb.startFloatField("config.field.raa.moonSize", dimensionData.getCustomSkyInformation().getMoonSize())
                                .setDefaultValue(dimensionData.getCustomSkyInformation().getMoonSize())
                                .setSaveConsumer(size -> dimensionData.getCustomSkyInformation().setMoonSize(size))
                                .build()
                );
            }
            category.addEntry(astralBody.build());

            SubCategoryBuilder gravity = eb.startSubCategory(I18n.translate("config.title.raa.gravity")).setExpanded(false);
            gravity.add(
                    eb.startBooleanToggle("config.field.raa.hasCustomGravity", dimensionData.hasCustomGravity())
                            .setDefaultValue(dimensionData.hasCustomGravity())
                            .setSaveConsumer(dimensionData::shouldHaveCustomGravity)
                            .build()
            );
            if (dimensionData.hasCustomGravity()) {
                gravity.add(
                        eb.startFloatField("config.field.raa.gravity", dimensionData.getGravity())
                                .setDefaultValue(dimensionData.getGravity())
                                .setSaveConsumer(dimensionData::setGravity)
                                .build()
                );
            }
            category.addEntry(gravity.build());

            SubCategoryBuilder biomes = eb.startSubCategory(I18n.translate("config.title.raa.biomes")).setExpanded(false);
            for (DimensionBiomeData biomeData : dimensionData.getBiomeData()) {
                SubCategoryBuilder biomeDataSubCategory = eb.startSubCategory(I18n.translate("config.title.raa.biomeData", WordUtils.capitalizeFully(biomeData.getId()
                        .getPath().replace("_", " ")))).setExpanded(false);
                biomeDataSubCategory.add(
                        eb.startStrField("config.field.raa.biomeData.name", WordUtils.capitalizeFully(biomeData.getId().getPath().replace("_", " ")))
                                .setDefaultValue(WordUtils.capitalizeFully(biomeData.getId().getPath().replace("_", " ")))
                                .setSaveConsumer(biomeData::setName)
                                .build()
                );
                biomeDataSubCategory.add(
                        eb.startStrField("config.field.raa.biomeData.surfaceBuilder",
                                biomeData.getSurfaceBuilder().toString())
                                .setDefaultValue(biomeData.getSurfaceBuilder().toString())
                                .setSaveConsumer(biomeData::setSurfaceBuilder)
                                .setErrorSupplier(str -> {
                                    if (str.toLowerCase().equals(str))
                                        return Optional.empty();
                                    return Optional.of(I18n.translate("config.error.raa.identifier.no_caps"));
                                })
                                .build()
                );
                biomeDataSubCategory.add(
                        eb.startStrField("config.field.raa.biomeData.surfaceBuilderConfig",
                                Utils.fromConfigToIdentifier(biomeData.getSurfaceConfig()).toString())
                                .setDefaultValue(Utils.fromConfigToIdentifier(biomeData.getSurfaceConfig()).toString())
                                .setSaveConsumer(biomeData::setSurfaceConfig)
                                .setErrorSupplier(str -> {
                                    if (str.toLowerCase().equals(str))
                                        return Optional.empty();
                                    return Optional.of(I18n.translate("config.error.raa.identifier.no_caps"));
                                })
                                .build()
                );
                biomeDataSubCategory.add(
                        eb.startFloatField("config.field.raa.biomeData.depth", biomeData.getDepth())
                                .setDefaultValue(biomeData.getDepth())
                                .setSaveConsumer(biomeData::setDepth)
                                .setErrorSupplier(str -> {
                                    if (str >= 0.0F)
                                        return Optional.empty();
                                    return Optional.of(I18n.translate("config.error.raa.depth_under_zero"));
                                })
                                .build()
                );
                biomeDataSubCategory.add(
                        eb.startFloatField("config.field.raa.biomeData.scale", biomeData.getScale())
                                .setDefaultValue(biomeData.getScale())
                                .setSaveConsumer(biomeData::setScale)
                                .setErrorSupplier(str -> {
                                    if (str >= 0.0F)
                                        return Optional.empty();
                                    return Optional.of(I18n.translate("config.error.raa.depth_under_zero"));
                                })
                                .build()
                );
                biomeDataSubCategory.add(
                        eb.startFloatField("config.field.raa.biomeData.temperature", biomeData.getTemperature())
                                .setDefaultValue(biomeData.getTemperature())
                                .setSaveConsumer(biomeData::setTemperature)
                                .setErrorSupplier(str -> {
                                    if (str >= 0.0F)
                                        return Optional.empty();
                                    return Optional.of(I18n.translate("config.error.raa.depth_under_zero"));
                                })
                                .build()
                );
                biomeDataSubCategory.add(
                        eb.startFloatField("config.field.raa.biomeData.downfall", biomeData.getDownfall())
                                .setDefaultValue(biomeData.getDownfall())
                                .setSaveConsumer(biomeData::setDownfall)
                                .setErrorSupplier(str -> {
                                    if (str >= 0.0F)
                                        return Optional.empty();
                                    return Optional.of(I18n.translate("config.error.raa.depth_under_zero"));
                                })
                                .build()
                );
                biomeDataSubCategory.add(
                        eb.startAlphaColorField("config.field.raa.waterColor", biomeData.getWaterColor())
                        .setDefaultValue(biomeData.getWaterColor())
                        .setSaveConsumer(biomeData::setWaterColor)
                        .build()
                );
                biomeDataSubCategory.add(
                        eb.startAlphaColorField("config.field.raa.foliageColor", biomeData.getFoliageColor())
                                .setDefaultValue(biomeData.getFoliageColor())
                                .setSaveConsumer(biomeData::setFoliageColor)
                                .build()
                );
                biomeDataSubCategory.add(
                        eb.startAlphaColorField("config.field.raa.waterColor", biomeData.getGrassColor())
                                .setDefaultValue(biomeData.getGrassColor())
                                .setSaveConsumer(biomeData::setGrassColor)
                                .build()
                );
                biomes.add(biomeDataSubCategory.build());
            }
            category.addEntry(biomes.build());

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

    public static class TextEntryWithTooltip extends io.github.vampirestudios.raa.config.screen.dimensions.RAADimensionDescriptionListWidget.Entry {
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

    public static class TextEntry extends io.github.vampirestudios.raa.config.screen.dimensions.RAADimensionDescriptionListWidget.Entry {
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

    public static class TitleEntry extends io.github.vampirestudios.raa.config.screen.dimensions.RAADimensionDescriptionListWidget.Entry {
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

    public static class EmptyEntry extends io.github.vampirestudios.raa.config.screen.dimensions.RAADimensionDescriptionListWidget.Entry {
        private final int height;

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

    public static abstract class Entry extends DynamicElementListWidget.ElementEntry<io.github.vampirestudios.raa.config.screen.dimensions.RAADimensionDescriptionListWidget.Entry> {

    }

}