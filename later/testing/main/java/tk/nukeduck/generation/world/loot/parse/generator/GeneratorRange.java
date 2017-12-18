package tk.nukeduck.generation.world.loot.parse.generator;

import java.util.Random;

public class GeneratorRange implements IGenerator {
	private int min, max;

	public GeneratorRange(Double min, Double max) {
		this.min = min.intValue();
		this.max = max.intValue();
	}

	@Override
	public int next(Random random) {
		if(max <= min) return min;
		return random.nextInt(max - min) + min;
	}
}
