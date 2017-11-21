package tk.nukeduck.generation.world.placer;

import java.util.Random;

import tk.nukeduck.generation.util.BlockInfo;
import net.minecraft.world.World;

public class BlockPlacerStatic extends IBlockPlacer {
	private BlockInfo blockInfo;
	public BlockPlacerStatic(BlockInfo blockInfo) {
		this.blockInfo = blockInfo;
	}

	@Override
	public BlockInfo getBlockPlaced(World world, int x, int y, int z, Random random) {
		return this.blockInfo;
	}
}
