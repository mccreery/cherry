package com.sammccreery.cherry.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sammccreery.cherry.registry.ResourceMaterial;
import com.sammccreery.cherry.registry.ResourceMaterial.ItemType;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenOres implements IWorldGenerator {
	private WorldGenMinable[] generators;

	public WorldGenOres() {
		List<WorldGenMinable> generators = new ArrayList<WorldGenMinable>();

		for(ResourceMaterial material : ResourceMaterial.values()) {
			if(!material.skipResource()) {
				generators.add(new WorldGenMinable(material.getBlock(ItemType.ORE), material.veinSize));
			}
		}
		this.generators = generators.toArray(new WorldGenMinable[generators.size()]);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId != 0) return;

		for(int i = 0; i < random.nextInt(5); i++) {
			int x = chunkX * 16 + random.nextInt(16);
			int y = random.nextInt(20) + 5;
			int z = chunkZ * 16 + random.nextInt(16);

			generators[random.nextInt(generators.length)].generate(world, random, x, y, z);
		}
	}
}
