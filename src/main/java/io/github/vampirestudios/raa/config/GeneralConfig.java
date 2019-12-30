package io.github.vampirestudios.raa.config;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.namegeneration.LangEnum;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = RandomlyAddingAnything.MOD_ID)
public class GeneralConfig implements ConfigData {

    public int materialNumber = 100;
    public int dimensionNumber = 20;
    public boolean debug = false;
    public boolean regen = false;
    public boolean enableVanillaItemColoring = false;
    public LangEnum namingLanguage = LangEnum.ENGLISH;

}
