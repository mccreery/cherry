package com.sammccreery.cherry.item;

import com.sammccreery.cherry.util.StackUtil;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ItemEndHoe extends ItemHoe {
	public ItemEndHoe(ToolMaterial material) {
		super(material);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float dx, float dy, float dz) {
		// TODO maybe change to on block break, makes more sense?

		if(super.onItemUse(stack, player, world, x, y, z, side, dx, dy, dz)) {
			return true;
		} else if(world.isRemote || !player.canPlayerEdit(x, y, z, 0, stack)) {
			return false;
		}
		Block block = world.getBlock(x, y, z);

		if(block instanceof IGrowable && !((IGrowable)block).func_149851_a(world, x, y, z, true)) { // Block is a fully grown crop
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlock(x, y, z, Blocks.air); // Remove previous crop (dangerous)
			boolean replanted = false;

			for(ItemStack drop : block.getDrops(world, x, y, z, meta, 0)) {
				if(!replanted && drop.getItem() instanceof IPlantable &&
						drop.getItem().onItemUse(drop, player, world, x, y - 1, z, 1, 0.5F, 1.0F, 0.5F)) {
					replanted = true;
				}

				// TODO handle overflow
				StackUtil.storeStack(player.getInventoryEnderChest(), drop);
			}
			stack.damageItem(1, player);
			return true;
		}
		return false;
	}
}
