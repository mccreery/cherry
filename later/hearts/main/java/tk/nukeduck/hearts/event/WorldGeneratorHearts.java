package tk.nukeduck.hearts.event;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import tk.nukeduck.hearts.HeartCrystal;
import tk.nukeduck.hearts.registry.HeartsBlocks;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorHearts implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId != 0) return;
		generateSurface(world, random, chunkX * 16, chunkZ * 16);
	}

	private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		for(int i = 0; i < HeartCrystal.config.getGenCount(); i++) {
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(HeartCrystal.config.getGenHeight());
			int z = chunkZ + random.nextInt(16);

			if(!world.getBlock(x, y, z).isReplaceable(world, x, y, z)) continue;
			if(random.nextBoolean()) continue; // Half the time, just skip

			Block ground;
			while((ground = world.getBlock(x, y - 1, z)).isReplaceable(world, x, y - 1, z)) {
				y--;
			}

			if(ground.getMaterial() == Material.rock) {
				world.setBlock(x, y, z, HeartsBlocks.crystal);
			}
		}
	}
}
