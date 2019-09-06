package fr.arthurbambou.randomlyaddinganything;

import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import fr.arthurbambou.randomlyaddinganything.registries.Textures;
import net.fabricmc.api.ModInitializer;

public class RandomlyAddingAnything implements ModInitializer {
	@Override
	public void onInitialize() {
		Textures.init();
		Materials.init();
	}
}