package tk.nukeduck.generation.world.placer;

import java.util.Random;

import tk.nukeduck.generation.util.BlockInfo;
import net.minecraft.world.World;

public class BlockPlacerRandom extends IBlockPlacer {
	private BlockInfo[] possibles;
	public BlockPlacerRandom(BlockInfo... possibles) {
		this.possibles = possibles;
	}

	@Override
	public BlockInfo getBlockPlaced(World world, int x, int y, int z, Random random) {
		return this.possibles[random.nextInt(this.possibles.length)];
	}
}
