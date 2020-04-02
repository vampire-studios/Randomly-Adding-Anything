package io.github.vampirestudios.raa.config;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.namegeneration.LangEnum;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = RandomlyAddingAnything.MOD_ID)
public class GeneralConfig implements ConfigData {

    @Comment("Amount of materials to generate")
    public int materialGenAmount = 40;
    @Comment("Amount of unique materials to generate per dimension")
    public int dimensionMaterialGenAmount = 10;
    @Comment("Amount of dimensions to generate")
    public int dimensionGenAmount = 50;
    @Comment("Amount of entities to generate")
    public int entityGenAmount = 20;
    @Comment("Mostly for us developers")
    public boolean debug = false;
    @Comment("If this is set to true materials will regenerate each time you restart your game, you can also just delete the raa folder in the configs folder")
    public boolean regenConfigs = false;
    @Comment("The name the materials should generate in")
    public LangEnum namingLanguage = LangEnum.ENGLISH;
    @Comment("If portal hubs should spawn naturally, if set to false you need to make your own ones in creative")
    public boolean shouldSpawnPortalHub = true;

}
