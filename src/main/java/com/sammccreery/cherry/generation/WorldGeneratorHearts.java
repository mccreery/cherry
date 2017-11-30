package com.sammccreery.cherry.generation;

import java.util.Random;

import com.sammccreery.cherry.registry.CherryBlocks;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorHearts implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId != 0) return;
		generateSurface(world, random, chunkX * 16, chunkZ * 16);
	}

	private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		for(int i = 0; i < /*HeartCrystal.config.getGenCount()*/5; i++) { // TODO config
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(16/*HeartCrystal.config.getGenHeight() TODO*/);
			int z = chunkZ + random.nextInt(16);

			if(!world.getBlock(x, y, z).isReplaceable(world, x, y, z)) continue;
			if(random.nextBoolean()) continue; // Half the time, just skip

			Block ground;
			while((ground = world.getBlock(x, y - 1, z)).isReplaceable(world, x, y - 1, z)) {
				y--;
			}

			if(ground.getMaterial() == Material.rock) {
				world.setBlock(x, y, z, CherryBlocks.heartCrystal);
			}
		}
	}
}
