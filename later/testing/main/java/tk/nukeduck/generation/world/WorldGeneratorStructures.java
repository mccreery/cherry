package tk.nukeduck.generation.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import tk.nukeduck.generation.util.WeightedRandom;
import tk.nukeduck.generation.world.structure.IStructure;
import tk.nukeduck.generation.world.structure.IStructure.Dimension;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorStructures implements IWorldGenerator {
	private ArrayList<IStructure> structures = new ArrayList<IStructure>();
	public WorldGeneratorStructures addStructure(IStructure structure) {
		this.structures.add(structure);
		return this;
	}

	private HashMap<BiomeGenBase, WeightedRandom<IStructure>> generatorCache = new HashMap<BiomeGenBase, WeightedRandom<IStructure>>();

	public void clearCache() {
		this.generatorCache.clear();
	}

	public WeightedRandom<IStructure> getGenerator(BiomeGenBase biome) {
		if(generatorCache.containsKey(biome)) return generatorCache.get(biome);

		WeightedRandom<IStructure> generator = new WeightedRandom<IStructure>() {
			@Override
			protected boolean includeValue(IStructure value, Object... args) {
				if(args.length == 0 || !(args[0] instanceof Dimension)) return false;
				return value.isValidWorld((Dimension) args[0]);
			}
		};
		for(IStructure structure : this.structures) {
			generator.set(structure, structure.getWeight(biome));
		}
		generatorCache.put(biome, generator);
		return generator;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int totalStructures = 1;
		while(totalStructures > 0) {
			int x = chunkX * 16 + random.nextInt(16);
			int y = random.nextInt(world.getActualHeight());
			int z = chunkZ * 16 + random.nextInt(16);

			if(this.generate(world, x, y, z, random)) {
				totalStructures--;
				System.out.println("Finally generated");
			}
		}
	}

	public boolean generate(World world, int x, int y, int z, Random random) {
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		Dimension dimension = Dimension.fromId(world.provider.dimensionId);

		IStructure structure = this.getGenerator(biome).next(random, dimension);

		if(structure.canGenerate(world, x, y, z)) {
			structure.generate(world, x, y, z, random);
			return true;
		} else {
			return false;
		}
	}
}
