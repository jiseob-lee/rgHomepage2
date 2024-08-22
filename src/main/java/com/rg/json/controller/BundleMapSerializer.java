package com.rg.json.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BundleMapSerializer implements JsonSerializer<Map<String, String>> {

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(BundleMapSerializer.class);
	private final Logger LOGGER = LogManager.getLogger(BundleMapSerializer.class);

	@Override
	public JsonElement serialize(final Map<String, String> bundleMap, final Type typeOfSrc,
			final JsonSerializationContext context) {
		final JsonObject resultJson = new JsonObject();

		for (final String key : bundleMap.keySet()) {
			try {
				createFromBundleKey(resultJson, key, bundleMap.get(key));
			} catch (final IOException e) {
				LOGGER.error("Bundle map serialization exception: ", e);
			}
		}

		return resultJson;
	}

	public JsonObject createFromBundleKey(final JsonObject resultJson, final String key, final String value)
			throws IOException {
		if (!key.contains(".")) {
			resultJson.addProperty(key, value);

			return resultJson;
		}

		final String currentKey = firstKey(key);
		if (currentKey != null) {
			final String subRightKey = key.substring(currentKey.length() + 1, key.length());
			final JsonObject childJson = getJsonIfExists(resultJson, currentKey);

			resultJson.add(currentKey, createFromBundleKey(childJson, subRightKey, value));
		}

		return resultJson;
	}

	private String firstKey(final String fullKey) {
		final String[] splittedKey = fullKey.split("\\.");

		return (splittedKey.length != 0) ? splittedKey[0] : fullKey;
	}

	private JsonObject getJsonIfExists(final JsonObject parent, final String key) {
		if (parent == null) {
			LOGGER.warn("Parent json parameter is null!");
			return null;
		}

		if (parent.get(key) != null && !(parent.get(key) instanceof JsonObject)) {
			throw new IllegalArgumentException("Invalid key \'" + key + "\' for parent: " + parent
					+ "\nKey can not be JSON object and property or array in one time");
		}

		if (parent.getAsJsonObject(key) != null) {
			return parent.getAsJsonObject(key);
		} else {
			return new JsonObject();
		}
	}
}