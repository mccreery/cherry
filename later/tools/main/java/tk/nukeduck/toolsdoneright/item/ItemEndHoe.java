package tk.nukeduck.toolsdoneright.item;

import tk.nukeduck.toolsdoneright.ToolsDoneRight;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEndHoe extends ItemTDRHoe {
	public ItemEndHoe(ToolMaterial p_i45343_1_) {
		super(p_i45343_1_);
		// TODO Auto-generated constructor stub
	}
	
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
    	super.onItemUse(itemstack, player, world, x, y, z, side, p_77648_8_, p_77648_9_, p_77648_10_);
    	if(!world.isRemote && world.getBlock(x, y, z) instanceof IGrowable && !((IGrowable) world.getBlock(x, y, z)).func_149851_a(world, x, y, z, true)) {
    		for(ItemStack i : world.getBlock(x, y, z).getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
    			world.spawnEntityInWorld(new EntityItem(world, x + ToolsDoneRight.random.nextDouble(), y + ToolsDoneRight.random.nextDouble(), z + ToolsDoneRight.random.nextDouble(), i));
    		world.setBlockMetadataWithNotify(x, y, z, 0, 0);
    		if(!player.capabilities.isCreativeMode) itemstack.damageItem(1, player);
    		return true;
    	}
    	return false;
    }
}