package io.github.vampirestudios.raa.config.screen.materials;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.materials.Material;
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
import net.minecraft.entity.EquipmentSlot;
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

    Material material;

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
    public int addItem(io.github.vampirestudios.raa.config.screen.materials.RAAMaterialDescriptionListWidget.Entry item) {
        return super.addItem(item);
    }

    public void clearItemsPublic() {
        clearItems();
    }

    public void addMaterial(MaterialListScreen og, Material material) {
        this.material = material;
        clearItems();
        addItem(new TitleMaterialOverrideEntry(og, material, new LiteralText(WordUtils.capitalizeFully(material.getName())).formatted(Formatting.UNDERLINE, Formatting.BOLD)));
        DecimalFormat df = new DecimalFormat("#.##");
        addItem(new ColorEntry("config.text.raa.color", material.getColor()));
        addItem(new TextEntry(new TranslatableText("config.text.raa.identifier", material.getId().toString())));
//        addItem(new TextEntry(new TranslatableText("config.text.raa.targetIdentifier", material.getOreInformation().getTargetId().toString())));
        addItem(new TextEntry(new TranslatableText("config.text.raa.targetBlock", WordUtils.capitalizeFully(material.getOreInformation().getTargetId().getPath().replace("_", " ")))));
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
            addItem(new TextEntry(new TranslatableText("config.text.raa.attack_damage", df.format(material.getToolMaterial().getAttackDamage()))));
        }
        if (material.hasArmor()) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.armor").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            addItem(new TextEntry(new TranslatableText("config.text.raa.enchantability", material.getArmorMaterial().getEnchantability())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.helmetDurability", material.getArmorMaterial().getDurability(EquipmentSlot.HEAD))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.chestplateDurability", material.getArmorMaterial().getDurability(EquipmentSlot.CHEST))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.leggingsDurability", material.getArmorMaterial().getDurability(EquipmentSlot.LEGS))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.bootsDurability", material.getArmorMaterial().getDurability(EquipmentSlot.FEET))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.horseArmorBonus", material.getArmorMaterial().getHorseArmorBonus())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.equipmentSound", material.getArmorMaterial().getEquipSound().getId())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.repairItem", WordUtils.capitalizeFully(Utils.addSuffixToPath(material.getId(), material.getArmorMaterial().getOreType().getSuffix()).getPath().replace("_", " ")))));
            addItem(new TextEntry(new TranslatableText("config.text.raa.toughness", material.getArmorMaterial().getToughness())));
        }
        if (material.hasFood()) {
            addItem(new TitleEntry(new TranslatableText("config.title.raa.food").formatted(Formatting.UNDERLINE, Formatting.BOLD)));
            addItem(new TextEntry(new TranslatableText("config.text.raa.hunger", material.getFoodData().getHunger())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.saturationModifier", material.getFoodData().getSaturationModifier())));
            addItem(new TextEntry(new TranslatableText("config.text.raa.alwaysEdible", material.getFoodData().isAlwaysEdible() ? "Yes": "No")));
            addItem(new TextEntry(new TranslatableText("config.text.raa.meat", material.getFoodData().isMeat() ? "Yes": "No")));
            addItem(new TextEntry(new TranslatableText("config.text.raa.snack", material.getFoodData().isSnack() ? "Yes": "No")));
        }
    }

    public static class ColorEntry extends io.github.vampirestudios.raa.config.screen.materials.RAAMaterialDescriptionListWidget.Entry {
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

    public static class TitleMaterialOverrideEntry extends io.github.vampirestudios.raa.config.screen.materials.RAAMaterialDescriptionListWidget.Entry {
        protected String s;
        private ButtonWidget overrideButton;

        public TitleMaterialOverrideEntry(MaterialListScreen og, Material material, Text text) {
            this.s = text.asFormattedString();
            String btnText = I18n.translate("config.button.raa.edit");
            overrideButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(btnText) + 10, 20, btnText, widget -> {
                openClothConfigForMaterial(og, material);
            });
        }

        @SuppressWarnings("deprecation")
        private static void openClothConfigForMaterial(MaterialListScreen og, Material material) {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(og)
                    .setTitle(I18n.translate("config.title.raa.config_specific", WordUtils.capitalizeFully(material.getName())));
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
                SubCategoryBuilder tools = eb.startSubCategory("config.title.raa.tools").setExpended(false);
                tools.add(
                        eb.startIntField("config.field.raa.enchantability", material.getToolMaterial().getEnchantability())
                                .setDefaultValue(material.getToolMaterial().getEnchantability())
                                .setSaveConsumer(material.getToolMaterial()::setEnchantability)
                                .setMin(0)
                                .build()
                );
                tools.add(
                        eb.startIntField("config.field.raa.durability", material.getToolMaterial().getDurability())
                                .setDefaultValue(material.getToolMaterial().getDurability())
                                .setSaveConsumer(material.getToolMaterial()::setDurability)
                                .setMin(1)
                                .build()
                );
                tools.add(
                        eb.startIntField("config.field.raa.mining_level", material.getToolMaterial().getMiningLevel())
                                .setDefaultValue(material.getToolMaterial().getMiningLevel())
                                .setSaveConsumer(material.getToolMaterial()::setMiningLevel)
                                .setMin(0)
                                .build()
                );
                tools.add(
                        eb.startFloatField("config.field.raa.tool_speed", material.getToolMaterial().getMiningSpeed())
                                .setDefaultValue(material.getToolMaterial().getMiningSpeed())
                                .setSaveConsumer(material.getToolMaterial()::setMiningSpeed)
                                .setMin(0)
                                .build()
                );
                tools.add(
                        eb.startFloatField("config.field.raa.attack_damage", material.getToolMaterial().getAttackDamage())
                                .setDefaultValue(material.getToolMaterial().getAttackDamage())
                                .setSaveConsumer(material.getToolMaterial()::setAttackDamage)
                                .build()
                );
                category.addEntry(tools.build());
            }
            if (material.hasWeapons()) {
                SubCategoryBuilder weapons = eb.startSubCategory("config.title.raa.weapons").setExpended(false);
                weapons.add(
                        eb.startIntField("config.field.raa.enchantability", material.getToolMaterial().getEnchantability())
                                .setDefaultValue(material.getToolMaterial().getEnchantability())
                                .setSaveConsumer(material.getToolMaterial()::setEnchantability)
                                .setMin(0)
                                .build()
                );
                weapons.add(
                        eb.startIntField("config.field.raa.durability", material.getToolMaterial().getDurability())
                                .setDefaultValue(material.getToolMaterial().getDurability())
                                .setSaveConsumer(material.getToolMaterial()::setDurability)
                                .setMin(1)
                                .build()
                );
                weapons.add(
                        eb.startFloatField("config.field.raa.attack_damage", material.getToolMaterial().getAttackDamage())
                                .setDefaultValue(material.getToolMaterial().getAttackDamage())
                                .setSaveConsumer(material.getToolMaterial()::setAttackDamage)
                                .build()
                );
                category.addEntry(weapons.build());
            }
            builder.setSavingRunnable(RandomlyAddingAnything.MATERIALS_CONFIG::overrideFile);
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

    public static class TextEntry extends io.github.vampirestudios.raa.config.screen.materials.RAAMaterialDescriptionListWidget.Entry {
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

    public static class TitleEntry extends io.github.vampirestudios.raa.config.screen.materials.RAAMaterialDescriptionListWidget.Entry {
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

    public static class EmptyEntry extends io.github.vampirestudios.raa.config.screen.materials.RAAMaterialDescriptionListWidget.Entry {
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

    public static abstract class Entry extends DynamicElementListWidget.ElementEntry<io.github.vampirestudios.raa.config.screen.materials.RAAMaterialDescriptionListWidget.Entry> {

    }

}