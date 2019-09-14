package io.github.vampirestudios.raa.config;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.namegeneration.LangEnum;
import me.sargunvohra.mcmods.autoconfig1.ConfigData;

@me.sargunvohra.mcmods.autoconfig1.annotation.Config(name = RandomlyAddingAnything.MOD_ID)
public class Config implements ConfigData {

    public int materialNumber = 100;
    public boolean debug = true;
    public boolean regen = false;
    public LangEnum namingLanguage = LangEnum.ENGLISH;

}
