package io.github.vampirestudios.raa.config;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.JanksonHelper;

import java.io.FileWriter;

public class MaterialsData extends RAAData {
	public MaterialsData(String fileName) {
		super(fileName);
	}

	@Override
	public void generateNewData() {
		Materials.init();
	}

	@Override
	protected JsonObject upgrade(JsonObject json, int version) {
		JsonArray materialsArray = JanksonHelper.require(json, "materials", JsonArray.class);
		switch(version) {
			case 0:
				JanksonHelper.iterateArrayObjects(materialsArray, jsonObject -> {
					jsonObject.putDefault("color", JanksonHelper.require(jsonObject, "rgb", int.class), null);
					jsonObject.remove("rgb");
				});
				break;
			case 1:
				JanksonHelper.iterateArrayObjects(materialsArray, jsonObject -> {

				});
				break;
			default:
				break;
		}
		return json;
	}

	@Override
	protected void load(JsonObject jsonObject) throws RAADataLoadException {
		/*JsonArray materialsArray = JanksonHelper.require(jsonObject, "materials", JsonArray.class);

		for(int i = 0; i < materialsArray.size(); i++) {
			try {
				JsonObject materialJson = JanksonHelper.require(materialsArray, i, JsonObject.class);

				// TODO

			} catch (Exception e) {
				RandomlyAddingAnything.LOGGER.warn("Found incompatible entry in RAA material data's array!");
				e.printStackTrace();
				RandomlyAddingAnything.LOGGER.warn("Skipping entry...");
			}
		}*/
		Material[] materials = JanksonHelper.getJankson().fromJson(jsonObject, Material[].class);
	}

	@Override
	protected void save(FileWriter fileWriter) {
		/*JsonElement materialsArray = JanksonHelper.getJankson().toJson(Materials.MATERIALS.stream().toArray(Material[]::new));
		JsonObject main = new JsonObject();
		main.put("version", new JsonPrimitive(CURRENT_VERSION));
		main.put("materials", materialsArray);
		fileWriter.write(main.toJson(true, false));*/
		Gson gson = new GsonBuilder().create();
		gson.toJson(Materials.MATERIALS.stream().toArray(Material[]::new), fileWriter);
	}
}
