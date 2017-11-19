package nukeduck.snowplus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockSnowPlusStairs extends BlockStairs
{
	public BlockSnowPlusStairs(int par1, Block par2Block, int par3)
	{
		super(par1, par2Block, par3);
		Block.lightOpacity[par1] = 0;
	}
}