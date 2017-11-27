package com.sammccreery.cherry.generation;

import java.util.Random;

import com.sammccreery.cherry.util.Util;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class WorldGenPatches implements IWorldGenerator {
	protected final Block block;
	protected final float frequencyMode, frequencySpread;
	protected final float densityMode, densitySpread;
	protected final int spread;

	public WorldGenPatches(Block block, float frequencyMode, float frequencySpread, float densityMode, float densitySpread, int spread) {
		this.block = block;

		this.frequencyMode = frequencyMode;
		this.frequencySpread = frequencySpread;
		this.densityMode = densityMode;
		this.densitySpread = densitySpread;

		this.spread = spread;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int trials = Math.round(Util.distributeSmooth(frequencyMode, frequencySpread, random.nextFloat()));

		for(int i = 0; i < trials; i++) {
			int x = chunkX * 16 + random.nextInt(16);
			int z = chunkZ * 16 + random.nextInt(16);
			int y = world.getTopSolidOrLiquidBlock(x, z);

			if(canPlacePatch(world, x, y, z, chunkGenerator, chunkProvider, random)) {
				placePatch(world, x, y, z, chunkGenerator, chunkProvider, random);
			}
		}
	}

	protected void placePatch(World world, int x, int y, int z, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random) {
		int trials = Math.round(Util.distributeSmooth(densityMode, densitySpread, random.nextFloat()));

		for(int i = 0; i < trials; i++) {
			x += random.nextInt(spread * 2) - spread;
			z += random.nextInt(spread * 2) - spread;
			y = world.getTopSolidOrLiquidBlock(x, z);

			if(canPlace(world, x, y, z, chunkGenerator, chunkProvider, random)) {
				place(world, x, y, z, chunkGenerator, chunkProvider, random);
			}
		}
	}

	protected boolean canPlacePatch(World world, int x, int y, int z, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random) {
		return true;
	}

	protected void place(World world, int x, int y, int z, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random) {
		world.setBlock(x, y, z, block);
	}

	protected boolean canPlace(World world, int x, int y, int z, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random) {
		return block.canPlaceBlockAt(world, x, y, z)
			&& world.getBlock(x, y, z).isReplaceable(world, x, y, z)
			&& World.doesBlockHaveSolidTopSurface(world, x, y - 1, z);
	}
}
