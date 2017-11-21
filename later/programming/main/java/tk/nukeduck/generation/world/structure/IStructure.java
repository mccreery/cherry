package tk.nukeduck.generation.world.structure;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class IStructure {
	private Dimension dimension;
	public IStructure(Dimension dimension) {
		this.dimension = dimension;
	}

	public boolean isValidWorld(Dimension dimension) {
		return dimension == this.dimension;
	}

	public abstract boolean canGenerate(World world, int x, int y, int z);
	public abstract void generate(World world, int x, int y, int z, Random random);
	public abstract float getWeight(BiomeGenBase biome);

	public enum Dimension {
		NETHER(-1),
		OVERWORLD(0),
		END(1);

		private int id;
		Dimension(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}

		public static Dimension fromId(int id) {
			switch(id) {
				case -1: return NETHER;
				case 0: return OVERWORLD;
				case 1: return END;
				default: return OVERWORLD;
			}
		}
	}
}
