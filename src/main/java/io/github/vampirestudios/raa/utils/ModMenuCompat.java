package io.github.vampirestudios.raa.utils;

import io.github.prospector.modmenu.api.ModMenuApi;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.config.screen.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;

import java.util.function.Function;

public class ModMenuCompat implements ModMenuApi {

    @Override
    public String getModId() {
        return RandomlyAddingAnything.MOD_ID;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return ConfigScreen::new;
    }

}