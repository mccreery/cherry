package com.sammccreery.cherry.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class InventoryFoodCanister implements IInventory {
	private String name = "Food Canister";

	private ItemStack[] inventory = new ItemStack[5];
	private final ItemStack invItem;

	public InventoryFoodCanister(ItemStack stack) {
		this.invItem = stack;

		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		readFromNBT(stack.getTagCompound());
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int arg0) {
		return inventory[arg0];
	}

	@Override
	public ItemStack decrStackSize(int arg0, int arg1) {
		ItemStack stack = getStackInSlot(arg0);
		if(stack != null) {
			if(stack.stackSize > arg1) {
				stack = stack.splitStack(arg1);
				markDirty();
			} else {
				setInventorySlotContents(arg0, null);
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int arg0) {
		ItemStack stack = getStackInSlot(arg0);
		if(stack != null) {
			setInventorySlotContents(arg0, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int arg0, ItemStack arg1) {
		this.inventory[arg0] = arg1;

		if(arg1 != null && arg1.stackSize > this.getInventoryStackLimit()) {
			arg1.stackSize = this.getInventoryStackLimit();
		}

		markDirty();
	}

	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return name.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		for(int i = 0; i < getSizeInventory(); ++i) {
			if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				inventory[i] = null;
			}
		}
		writeToNBT(invItem.getTagCompound());
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer arg0) {
		return arg0.getHeldItem() == invItem;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int arg0, ItemStack arg1) {
		return arg1.getItem() instanceof ItemFood;
	}

	public void readFromNBT(NBTTagCompound tagcompound) {
		NBTTagList items = tagcompound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for(int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");

			if(slot >= 0 && slot < getSizeInventory()) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	public void writeToNBT(NBTTagCompound tagcompound) {
		NBTTagList items = new NBTTagList();

		for(int i = 0; i < getSizeInventory(); ++i) {
			if(getStackInSlot(i) != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				getStackInSlot(i).writeToNBT(item);

				items.appendTag(item);
			}
		}
		tagcompound.setTag("ItemInventory", items);
	}
}
