package tk.nukeduck.generation.world.loot.parse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tk.nukeduck.generation.world.loot.parse.condition.ConditionBiome;
import tk.nukeduck.generation.world.loot.parse.condition.ConditionDimension;
import tk.nukeduck.generation.world.loot.parse.condition.ICondition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ConditionDeserializer implements JsonDeserializer<ICondition> {
	private static final HashMap<String, Class<? extends ICondition>> TYPES = new HashMap<String, Class<? extends ICondition>>();
	static {
		TYPES.put("biome", ConditionBiome.class);
		TYPES.put("dimension", ConditionDimension.class);
	}

	@Override
	public ICondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		ParsedCondition condition = context.deserialize(json, ParsedCondition.class);
		ArrayList<Class<?>> paramTypes = new ArrayList<Class<?>>();
		for(Object a : condition.args) {
			paramTypes.add(a.getClass());
		}
		Class<?>[] types = paramTypes.toArray(new Class<?>[paramTypes.size()]);

		for(Entry<String, Class<? extends ICondition>> entry : TYPES.entrySet()) {
			if(condition.type.equals(entry.getKey())) {
				Constructor<? extends ICondition> constructor;
				try {
					constructor = entry.getValue().getConstructor(types);
					return constructor.newInstance(condition.args);
				} catch (Exception e) {
					throw new JsonParseException("Invalid arguments passed");
				}
			}
		}
		throw new JsonParseException("Invalid condition type");
	}

	private static class ParsedCondition {
		public String type;
		public Object[] args;
	}
}
