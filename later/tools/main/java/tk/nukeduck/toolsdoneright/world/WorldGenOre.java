package tk.nukeduck.toolsdoneright.world;

import java.util.Random;

import tk.nukeduck.toolsdoneright.ToolsDoneRight;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenOre implements IWorldGenerator {
	WorldGenMinable[] generators = new WorldGenMinable[] {
		new WorldGenMinable(ToolsDoneRight.rubyOre, 4),
		new WorldGenMinable(ToolsDoneRight.topazOre, 8),
		new WorldGenMinable(ToolsDoneRight.sapphireOre, 6)
	};
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == 0) {
			for (int i = 0; i < random.nextInt(5); i++) {
				generators[random.nextInt(generators.length)].generate(world, random, chunkX * 16 + random.nextInt(16), random.nextInt(20) + 5, chunkZ * 16 + random.nextInt(16));
			}
		}
	}
}