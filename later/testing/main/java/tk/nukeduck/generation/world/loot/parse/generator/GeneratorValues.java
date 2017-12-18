package tk.nukeduck.generation.world.loot.parse.generator;

import java.util.ArrayList;
import java.util.Random;

public class GeneratorValues implements IGenerator {
	private int[] values;
	public GeneratorValues(ArrayList values) {
		this.values = new int[values.size()];
		for(int i = 0; i < values.size(); i++) {
			this.values[i] = ((Double) values.get(i)).intValue();
		}
	}

	@Override
	public int next(Random random) {
		return this.values[random.nextInt(this.values.length)];
	}
}
