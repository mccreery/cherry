package com.sammccreery.cherry.item;

import com.sammccreery.cherry.registry.CherryBlocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSnowDoor extends ItemBlock {
	public ItemSnowDoor(Block block) {
		super(block);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(func_150936_a(world, x, y, z, side, player, stack)) {
			int meta = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
			ItemDoor.placeDoorBlock(world, x, y+1, z, meta, CherryBlocks.snowDoor);
			--stack.stackSize;
			return true;
		}
		return false;
	}

	@Override
	public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		return side == 1 && player.canPlayerEdit(x, y+1, z, side, stack) && player.canPlayerEdit(x, y+2, z, side, stack)
			&& field_150939_a.canPlaceBlockAt(world, x, y+1, z);
	}
}
