package tk.nukeduck.generation.world.loot.parse;

import java.lang.reflect.Type;

import tk.nukeduck.generation.world.loot.parse.JsonObjectDeserializer.Wrapper;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class JsonObjectDeserializer implements JsonDeserializer<Wrapper> {
	@Override
	public Wrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return new Wrapper(json.getAsJsonObject());
	}

	public static class Wrapper {
		public JsonObject json;

		public Wrapper(JsonObject json) {
			this.json = json;
		}
	}
}
