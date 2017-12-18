package tk.nukeduck.generation.world.loot.parse.generator;

import java.util.Random;

public class GeneratorStatic implements IGenerator {
	private int value;

	public GeneratorStatic(Double value) {
		this.value = value.intValue();
	}

	public GeneratorStatic setValue(int value) {
		this.value = value;
		return this;
	}
	public int getValue() {
		return this.value;
	}

	@Override
	public int next(Random random) {
		return this.getValue();
	}
}
