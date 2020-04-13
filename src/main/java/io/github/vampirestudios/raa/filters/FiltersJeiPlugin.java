package io.github.vampirestudios.raa.filters;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.BaseBoundsHandler;
import me.shedaniel.rei.api.DisplayHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Ocelot
 */
public class FiltersJeiPlugin implements REIPluginV0 {

    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier(RandomlyAddingAnything.MOD_ID, RandomlyAddingAnything.MOD_ID);
    }

    @Override
    public void registerBounds(DisplayHelper displayHelper) {
        BaseBoundsHandler.getInstance().registerExclusionZones(CreativeInventoryScreen.class, () -> {
            if (Filters.get().hasFilters(ItemGroup.GROUPS[((CreativeInventoryScreen)MinecraftClient.getInstance().currentScreen).getSelectedTab()])) {
                List<Rectangle> areas = new ArrayList<>();

                /* Tabs */
                areas.add(new Rectangle(MinecraftClient.getInstance().currentScreen.width - 28,
                        MinecraftClient.getInstance().currentScreen.height + 10, 56, 230));

                /* Buttons */
                for (Element child : MinecraftClient.getInstance().currentScreen.children()) {
                    if (child instanceof IconButton || child instanceof TagButton) {
                        ButtonWidget button = (ButtonWidget) child;
                        areas.add(new Rectangle(button.x, button.y, button.getWidth(), button.getHeight()));
                    }
                }

                return areas;
            }
            return Collections.emptyList();
        });
    }

}