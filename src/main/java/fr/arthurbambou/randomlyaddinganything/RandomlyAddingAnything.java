package fr.arthurbambou.randomlyaddinganything;

import fr.arthurbambou.randomlyaddinganything.config.Config;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import fr.arthurbambou.randomlyaddinganything.registries.Textures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class RandomlyAddingAnything implements ModInitializer {

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier("raa", "itemgroup"), () -> new ItemStack(Items.FIREWORK_ROCKET));
	public static final String MOD_ID = "raa";
	public static final Config CONFIG = new Config();

	@Override
	public void onInitialize() {
		Textures.init();
		Materials.init();
		Materials.createMaterialResources();
	}
}