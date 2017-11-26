package com.sammccreery.cherry.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryUtils {
	/** Attempts to add the whole stack to the inventory
	 * @return The remaining stack size */
	public static int storeStack(IInventory inv, ItemStack stack) {
		if(isEmpty(stack)) return 0;

		// Stack until the inventory runs out of space
		while(stack.stackSize > fillSlot(inv, stack));
		return stack.stackSize;
	}

	/** Store as many items as possible in a single slot
	 * @return The remaining stack size */
	private static int fillSlot(IInventory inv, ItemStack stack) {
		if(isEmpty(stack)) return 0;

		if(stack.isStackable()) {
			int slot = firstStackableSlot(inv, stack);
			ItemStack dest;

			if(slot == -1) {
				slot = firstEmptySlot(inv);
				if(slot == -1) return stack.stackSize;

				// Place empty stack in slot
				dest = ItemStack.copyItemStack(stack);
				dest.stackSize = 0;
				inv.setInventorySlotContents(slot, dest);
			} else {
				// Use existing stack
				dest = inv.getStackInSlot(slot);
			}

			// Decide how much to store
			int transfer = Math.min(maxSize(inv, dest) - dest.stackSize, stack.stackSize);

			stack.stackSize -= transfer;
			dest.stackSize += transfer;
		} else {
			int slot = firstEmptySlot(inv);

			if(slot != -1) {
				inv.setInventorySlotContents(slot, ItemStack.copyItemStack(stack));
				stack.stackSize = 0;
			}
		}
		return stack.stackSize;
	}

	/*** @return The first slot in {@code inv} whose stack satisfies {@link #isEmpty(ItemStack)} */
	public static int firstEmptySlot(IInventory inv) {
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			if(isEmpty(inv.getStackInSlot(i))) {
				return i;
			}
		}
		return -1;
	}

	/** @return The first slot that the given {@link ItemStack} can be stacked into */
	public static int firstStackableSlot(IInventory inv, ItemStack stack) {
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack dest = inv.getStackInSlot(i);

			if(canStack(stack, dest) && dest.stackSize < maxSize(inv, dest)) {
				return i;
			}
		}
		return -1;
	}

	/** @return {@code true} if the effective stack size of {@code stack} is 0
	 * @see #stackSize(ItemStack) */
	public static boolean isEmpty(ItemStack stack) {
		return stackSize(stack) == 0;
	}
	/** @return The effective stack size.<br>
	 * If the stack is null or invalid, the effective size is 0 */
	public static int stackSize(ItemStack stack) {
		if(stack == null || stack.getItem() == null || stack.stackSize == 0
				|| stack.isItemStackDamageable() && stack.getItemDamage() > stack.getMaxDamage()) {
			return 0;
		} else {
			return stack.stackSize;
		}
	}

	/** @return The maximum stack size for the given stack in the given inventory */
	public static int maxSize(IInventory inv, ItemStack stack) {
		int size = inv != null ? inv.getInventoryStackLimit() : 64;

		if(stack != null && stack.getMaxStackSize() < size) {
			size = stack.getMaxStackSize();
		}
		return size;
	}

	/** @return {@code true} if the two {@link ItemStack}s are compatible to be stacked */
	public static boolean canStack(ItemStack src, ItemStack dest) {
		return src != null && dest != null
			&& src.getItem() == dest.getItem()
			&& dest.isStackable()
			&& (!dest.getHasSubtypes() || src.getItemDamage() == dest.getItemDamage())
			&& ItemStack.areItemStackTagsEqual(src, dest);
	}
}
