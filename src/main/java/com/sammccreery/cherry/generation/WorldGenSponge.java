package com.sammccreery.cherry.generation;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGenSponge extends WorldGenPatches {
	public WorldGenSponge() {
		super(Blocks.sponge, 0.2f, 0.8f, 8.0f, 4.0f, 4);
	}

	@Override
	protected boolean canPlacePatch(World world, int x, int y, int z, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random) {
		return y < 48 && world.getBiomeGenForCoords(x, z) instanceof BiomeGenOcean;
	}

	@Override
	protected void place(World world, int x, int y, int z, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random) {
		int height = 1 + random.nextInt(3);

		for(int i = 0; i < height; i++, y++) {
			world.setBlock(x, y, z, block);

			int move = random.nextInt(4);
			if((move & 1) != 0) {
				x += (move & 2) - 1;
			} else {
				z += (move & 2) - 1;
			}
		}
	}

	@Override
	protected boolean canPlace(World world, int x, int y, int z, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random) {
		return super.canPlace(world, x, y, z, chunkGenerator, chunkProvider, random) &&
			world.getBlock(x, y, z).getMaterial() == Material.water;
	}
}
