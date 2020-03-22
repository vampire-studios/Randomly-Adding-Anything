package io.github.vampirestudios.raa.config;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.namegeneration.LangEnum;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = RandomlyAddingAnything.MOD_ID)
public class GeneralConfig implements ConfigData {

    @Comment("Amount of materials to generate")
    public int materialNumber = 100;
    @Comment("Amount of materials to generate per dimension")
    public int dimensionMaterials = 10;
    @Comment("Amount of dimensions to generate")
    public int dimensionNumber = 50;
    @Comment("Mostly for us developers")
    public boolean debug = false;
    @Comment("If this is set to true materials will regenerate each time you restart your game")
    public boolean regen = false;
    @Comment("The name the materials should generate in")
    public LangEnum namingLanguage = LangEnum.ENGLISH;
    @Comment("If portal hubs should spawn naturally, if set to false you need to make your own ones in creative")
    public boolean shouldSpawnPortalHub = true;

}
