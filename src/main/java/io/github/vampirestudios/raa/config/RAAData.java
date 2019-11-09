package io.github.vampirestudios.raa.config;

import blue.endless.jankson.JsonObject;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.utils.JanksonHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public abstract class RAAData {
	public static final int CURRENT_VERSION = 2;
	public static final File CONFIG_PATH = new File(FabricLoader.getInstance().getConfigDirectory(), RandomlyAddingAnything.MOD_ID);

	private final File configFile;

	protected RAAData(String fileName) {
		configFile = new File(CONFIG_PATH, fileName + ".json");
	}

	public boolean fileExists() {
		return configFile.exists();
	}

	public void load() {
		try {
			JsonObject json = JanksonHelper.getJankson().load(configFile);
			Optional<Integer> version = JanksonHelper.getOptional(json, "version", int.class);
			if(version.isPresent()) {
				for(int i = version.get(); i < CURRENT_VERSION; i++) {
					RandomlyAddingAnything.LOGGER.debug("Upgrading RAA data file \"" + configFile.toString() + "\" to version " + (i + 1) + ".");
					json = upgrade(json, i);
				}
				load(json);
				RandomlyAddingAnything.LOGGER.info("Loaded RAA data file \"" + configFile.toString() + "\".");
				if(version.get() != CURRENT_VERSION) {
					save();
					RandomlyAddingAnything.LOGGER.debug("Saved upgraded RAA file.");
				}
			} else {
				generateNewData();
				save();
			}
		} catch (Throwable e) {
			RandomlyAddingAnything.LOGGER.info("Couldn't load RAA data file \"" + configFile.toString() + "\": " + e.getMessage() + ". New data will now be generated");
			generateNewData();
			save();
		}
	}

	public void save() {
		try {
			save(new FileWriter(configFile));
		} catch (IOException e) {
			throw new RuntimeException("Couldn't save RAA data file: " + configFile.toString(), e);
		}
	}

	public abstract void generateNewData();

	protected abstract JsonObject upgrade(JsonObject json, int version);
	protected abstract void load(JsonObject jsonObject) throws RAADataLoadException;
	protected abstract void save(FileWriter fileWriter);
}
