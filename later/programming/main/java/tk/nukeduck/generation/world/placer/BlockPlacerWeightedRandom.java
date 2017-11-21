package tk.nukeduck.generation.world.placer;

import java.util.Random;

import net.minecraft.world.World;
import tk.nukeduck.generation.util.BlockInfo;
import tk.nukeduck.generation.util.WeightedRandom;

public class BlockPlacerWeightedRandom extends IBlockPlacer {
	private WeightedRandom<BlockInfo> possibles;
	private Object[] args;

	public BlockPlacerWeightedRandom(WeightedRandom<BlockInfo> possibles, Object... args) {
		this.possibles = possibles;
		this.setArgs(args);
	}

	public BlockPlacerWeightedRandom setArgs(Object...args) {
		this.args = args;
		return this;
	}

	@Override
	public BlockInfo getBlockPlaced(World world, int x, int y, int z, Random random) {
		return this.possibles.next(random, this.args);
	}
}
