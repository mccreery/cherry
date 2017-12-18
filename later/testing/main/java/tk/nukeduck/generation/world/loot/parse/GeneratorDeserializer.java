package tk.nukeduck.generation.world.loot.parse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tk.nukeduck.generation.world.loot.parse.generator.GeneratorRange;
import tk.nukeduck.generation.world.loot.parse.generator.GeneratorStatic;
import tk.nukeduck.generation.world.loot.parse.generator.GeneratorValues;
import tk.nukeduck.generation.world.loot.parse.generator.IGenerator;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GeneratorDeserializer implements JsonDeserializer<IGenerator> {
	private static final HashMap<String, Class<? extends IGenerator>> TYPES = new HashMap<String, Class<? extends IGenerator>>();
	static {
		TYPES.put("range", GeneratorRange.class);
		TYPES.put("value", GeneratorValues.class);
	}

	@Override
	public IGenerator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if(json.isJsonPrimitive()) {
			int value;
			try {
				value = json.getAsInt();
			} catch(NumberFormatException e) {
				throw new JsonParseException("Only integer primitives are allowed for static values");
			}
			return new GeneratorStatic(new Double(value));
		}

		ParsedGenerator generator = context.deserialize(json, ParsedGenerator.class);
		ArrayList<Class<?>> paramTypes = new ArrayList<Class<?>>();
		for(Object a : generator.args) {
			paramTypes.add(a.getClass());
		}
		Class<?>[] types = paramTypes.toArray(new Class<?>[paramTypes.size()]);

		for(Entry<String, Class<? extends IGenerator>> entry : TYPES.entrySet()) {
			if(generator.generator.equals(entry.getKey())) {
				Constructor<? extends IGenerator> constructor;
				try {
					constructor = entry.getValue().getConstructor(types);
					return constructor.newInstance(generator.args);
				} catch (Exception e) {
					throw new JsonParseException("Invalid arguments passed");
				}
			}
		}
		throw new JsonParseException("Invalid generator type");
	}

	private static class ParsedGenerator {
		public String generator;
		public Object[] args;
	}
}
