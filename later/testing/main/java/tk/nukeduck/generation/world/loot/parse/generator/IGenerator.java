package tk.nukeduck.generation.world.loot.parse.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Random;

import sun.reflect.annotation.AnnotationType;

public interface IGenerator {
	public int next(Random random);
}
