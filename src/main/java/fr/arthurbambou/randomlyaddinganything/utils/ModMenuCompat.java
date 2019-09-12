/*
package fr.arthurbambou.randomlyaddinganything.utils;

import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import fr.arthurbambou.randomlyaddinganything.config.Config;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.minecraft.client.gui.screen.Screen;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public String getModId() {
        return RandomlyAddingAnything.MOD_ID;
    }

    @Override
    public Optional<Supplier<Screen>> getConfigScreen(Screen screen) {
        return Optional.of(AutoConfig.getConfigScreen(Config.class, screen));
    }
}
*/
