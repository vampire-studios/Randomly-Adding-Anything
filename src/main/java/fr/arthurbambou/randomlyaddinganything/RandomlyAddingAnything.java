package fr.arthurbambou.randomlyaddinganything;

import fr.arthurbambou.randomlyaddinganything.config.Config;
import fr.arthurbambou.randomlyaddinganything.config.SavingSystem;
import fr.arthurbambou.randomlyaddinganything.materials.MaterialRecipes;
import fr.arthurbambou.randomlyaddinganything.materials.MaterialWorldSpawning;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import fr.arthurbambou.randomlyaddinganything.registries.Textures;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class RandomlyAddingAnything implements ModInitializer {

	public static final ItemGroup RAA_ORES = FabricItemGroupBuilder.build(new Identifier("raa", "ores"), () -> new ItemStack(Blocks.IRON_ORE));
	public static final ItemGroup RAA_RESOURCES = FabricItemGroupBuilder.build(new Identifier("raa", "resources"), () -> new ItemStack(Items.IRON_INGOT));
	public static final ItemGroup RAA_TOOLS = FabricItemGroupBuilder.build(new Identifier("raa", "tools"), () -> new ItemStack(Items.IRON_PICKAXE));
	public static final ItemGroup RAA_ARMOR = FabricItemGroupBuilder.build(new Identifier("raa", "armor"), () -> new ItemStack(Items.IRON_HELMET));
	public static final ItemGroup RAA_WEAPONS = FabricItemGroupBuilder.build(new Identifier("raa", "weapons"), () -> new ItemStack(Items.IRON_SWORD));
	public static final String MOD_ID = "raa";
	public static Config CONFIG;

	@Override
	public void onInitialize() {
		AutoConfig.register(Config.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(Config.class).getConfig();
		Textures.init();
		if (SavingSystem.init() || CONFIG.regen) {
			Materials.init();
			SavingSystem.createFile();
			CONFIG.regen = false;
		} else {
			SavingSystem.readFile();
			Materials.isReady = true;
		}
		Materials.createMaterialResources();
		MaterialRecipes.init();
		MaterialWorldSpawning.init();
	}
}