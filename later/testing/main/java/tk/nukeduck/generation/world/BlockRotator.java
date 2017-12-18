package tk.nukeduck.generation.world;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockRotator {
	private static final HashMap<Block, IBlockRotator> rotators = new HashMap<Block, IBlockRotator>();

	public static final void register(Block block, IBlockRotator rotator) {
		rotators.put(block, rotator);
	}

	public static final void handleRotation(World world, int x, int y, int z, ForgeDirection direction) {
		Block b = world.getBlock(x, y, z);
		for(Block block : rotators.keySet()) {
			if(block.equals(b)) {
				world.setBlockMetadataWithNotify(x, y, z, rotators.get(block).rotate(world, b, direction), 2);
				break;
			}
		}
	}
}
