package tk.nukeduck.generation.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import tk.nukeduck.generation.world.structure.IStructure.Dimension;

public class WeightedRandom<T> {
	/** Map containing links between values and their weight indices. */
	private HashMap<T, Float> weights = new HashMap<T, Float>();

	/** Removes all mappings from the generator.
	 * @return The weighted random instance */
	public WeightedRandom clear() {
		this.weights.clear();
		return this;
	}

	/** Adds or updates a mapping between the given value and the given weight.
	 * @param value The value to add or update
	 * @param weight The weight to assign. Use any value {@code <= 0.0F} for an impossible result<br/>
	 * There is no upper limit, weights are calculated cumulatively
	 * @return The weighted random instance */
	public WeightedRandom set(T value, float weight) {
		this.weights.put(value, weight);
		return this;
	}

	/** Removes any given mapping, if applicable.
	 * @param value The value to remove
	 * @return {@code true} if a mapping existed, and hence was removed */
	public boolean remove(T value) {
		return this.weights.remove(value) != null;
	}

	/** Looks up the next random item drawn from this weighted random's sequence.<br/>
	 * The resulting object is chosen by cumulative weight.
	 * @param args Arguments for {@link #includeValue(Object, Object...)} to filter out any non-applicable objects
	 * @return A randomly-chosen weighted random value from this weighted random's values
	 * @see Random#nextFloat() */
	public T next(Random random, Object... args) {
		Set<T> values = new HashSet<T>(this.weights.keySet());

		Iterator<T> it = values.iterator();
		while(it.hasNext()) {
			T next = it.next();
			if(!this.includeValue(next, args)) {
				it.remove();
			}
		}

		float totalWeight = 0.0F;
		for(T value : values) {
			totalWeight += weights.get(value);
		}
		float choice = random.nextFloat() * totalWeight;

		float cumulativeWeight = 0.0F;
		for(T value : values) {
			if(choice < (cumulativeWeight += this.weights.get(value))) {
				return value;
			}
		}
		return null;
	}

	/** Provides masking for applicable objects.<br/>
	 * Objects that shouldn't be included given a call to {@link #next}
	 * @param value The value to be filtered
	 * @param args Arguments used to determine that the value is valid
	 * @return {@code true} if the value should be included in the list of possible results */
	protected boolean includeValue(T value, Object... args) {
		return true;
	}
}
