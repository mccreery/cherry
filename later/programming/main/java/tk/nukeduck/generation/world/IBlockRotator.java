package tk.nukeduck.generation.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IBlockRotator<T extends Block> {
	public int rotate(World world, T block, ForgeDirection direction);
}
