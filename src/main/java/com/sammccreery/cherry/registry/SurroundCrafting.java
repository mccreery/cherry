package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.util.StackUtils;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class SurroundCrafting implements IRecipe {
	protected ItemStack core;
	protected ItemStack surround;
	protected ItemStack output;

	public SurroundCrafting(ItemStack core, ItemStack surround, ItemStack output) {
		this.core = core;
		this.surround = surround;
		this.output = output;
	}

	protected boolean isValidCore(ItemStack stack) {
		return stack.getItem() == core.getItem() && stack.getItemDamage() == core.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, core);
	}
	protected boolean isCompatibleSurround(ItemStack surround, ItemStack stack) {
		if(surround == null) surround = this.surround;
		return stack.getItem() == surround.getItem() && stack.getItemDamage() == surround.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, surround);
	}
	protected ItemStack getResult(ItemStack core, ItemStack surround) {
		return ItemStack.copyItemStack(output);
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World worldIn) {
		return !StackUtils.isEmpty(getCraftingResult(inventory));
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		ItemStack core = null;
		ItemStack surround = null;
		int stackSize = 0;

		if(output.getItem() == Item.getItemFromBlock(Blocks.stained_glass)) {
			System.out.println("Checking potential inventory:");

			for(int y = 0, i = 0; y < 3; y++) {
				for(int x = 0; x < 3; x++, i++) {
					ItemStack stack = inventory.getStackInSlot(i);

					if(StackUtils.isEmpty(stack)) System.out.print('_');
					else if(isValidCore(stack)) System.out.print('C');
					else if(isCompatibleSurround(null, stack)) System.out.print('S');
					else System.out.print('?');
				}
				System.out.println();
			}
			System.out.flush();
		}

		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);

			if(StackUtils.isEmpty(stack)) {
				continue;
			} else if(isValidCore(stack)) {
				if(core != null) return null;
				else core = stack;
			} else if(isCompatibleSurround(surround, stack)) {
				if(surround == null) surround = stack.copy();
				++stackSize;
			} else {
				return null;
			}
		}
		if(core == null || surround == null) return null;

		ItemStack stack = getResult(core, surround);
		output.stackSize = stackSize;
		return stack;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}
}
