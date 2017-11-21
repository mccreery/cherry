package tk.nukeduck.generation.world.placer;

import java.util.Random;

import tk.nukeduck.generation.util.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class IBlockPlacer {
	public abstract BlockInfo getBlockPlaced(World world, int x, int y, int z, Random random);

	public boolean setBlock(World world, int x, int y, int z, Random random) {
		BlockInfo info = this.getBlockPlaced(world, x, y, z, random);
		if(info == null || info.getBlock() == null) return true;

		return world.setBlock(x, y, z, info.getBlock(), info.getMetadata(), 2);
	}
}
