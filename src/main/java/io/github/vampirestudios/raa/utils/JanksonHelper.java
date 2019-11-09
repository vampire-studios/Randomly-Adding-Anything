package io.github.vampirestudios.raa.utils;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.cottonmc.jankson.JanksonFactory;
import io.github.vampirestudios.raa.RandomlyAddingAnything;

import java.util.Optional;
import java.util.function.Consumer;

public class JanksonHelper {
	private static final Jankson JANKSON = JanksonFactory.builder().build();

	public static Jankson getJankson() {
		return JANKSON;
	}

	public static <T> Optional<T> getOptional(JsonObject jsonObject, String key, Class<T> clazz) {
		if(jsonObject == null)
			return Optional.empty();
		return Optional.ofNullable(jsonObject.get(clazz, key));
	}

	public static <T> Optional<T> getOptional(JsonArray jsonArray, int index, Class<T> clazz) {
		if(jsonArray == null)
			return Optional.empty();
		return Optional.ofNullable(jsonArray.get(clazz, index));
	}

	public static <T> T require(JsonObject jsonObject, String key, Class<T> clazz) {
		return getOptional(jsonObject, key, clazz).orElseThrow(() -> new JsonParseException("Couldn't get field \"" + key + "\" on json object as " + clazz.getSimpleName() + ": " + jsonObject.toString()));
	}

	public static <T> T require(JsonArray jsonArray, int index, Class<T> clazz) {
		return getOptional(jsonArray, index, clazz).orElseThrow(() -> new JsonParseException("Couldn't get data at index " + index + " on json array as " + clazz.getSimpleName() + ": " + jsonArray.toString()));
	}

	public static void iterateArrayObjects(JsonArray jsonArray, Consumer<JsonObject> consumer) {
		for(int i = 0; i < jsonArray.size(); i++) {
			try {
				consumer.accept(require(jsonArray, i, JsonObject.class));
			} catch (Throwable e) {
				RandomlyAddingAnything.LOGGER.warn("Exception in json array iterator. Skipping entry...");
				e.printStackTrace();
			}
		}
	}
}
