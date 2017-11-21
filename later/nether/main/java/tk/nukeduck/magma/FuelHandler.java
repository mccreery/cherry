package tk.nukeduck.magma;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		if(fuel.getItem() instanceof ItemBlock && Block.getBlockFromItem(fuel.getItem()) != Blocks.air) {
			if(Block.getBlockFromItem(fuel.getItem()) == Magma.magma)
				return 6400;
		}
		return 0;
	}
}