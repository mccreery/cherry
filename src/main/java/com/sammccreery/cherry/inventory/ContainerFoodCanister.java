package com.sammccreery.cherry.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ContainerFoodCanister extends Container {
	public final InventoryFoodCanister inventory;

	private static final int INV_START = 5, /*INV_END = 31, HOTBAR_START = 32, */HOTBAR_END = 40;

	public ContainerFoodCanister(EntityPlayer par1Player, InventoryPlayer inventoryPlayer, InventoryFoodCanister inventoryItem) {
		this.inventory = inventoryItem;
		int i;

		// These slots
		for(i = 0; i < 5; ++i) {
			this.addSlotToContainer(new SlotFood(this.inventory, i, 44 + (18 * i), 20));
		}

		// Inventory slots
		for(i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
			}
		}

		// Hotbar
		for(i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 109));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// If item is in our custom Inventory or armor slot
			if(par2 < INV_START) {
				// try to place in player inventory / action bar
				if(!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			// Item is in inventory / hotbar, try to place in custom inventory or armor slots
			else {
				// Check that the item is the right type
				if(itemstack1.getItem() instanceof ItemFood) {
					// Try to merge into your custom inventory slots
					// We use 'InventoryItem.INV_SIZE' instead of INV_START just in case
					// you also add armor or other custom slots
					if (!this.mergeItemStack(itemstack1, 0, 5, false)) {
						return null;
					}
				}
			}

			if(itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
			return null;
		}
		return super.slotClick(slot, button, flag, player);
	}
}
