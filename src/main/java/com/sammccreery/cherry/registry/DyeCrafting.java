package com.sammccreery.cherry.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DyeCrafting extends SurroundCrafting {
	public DyeCrafting(ItemStack surround, ItemStack output) {
		super(new ItemStack(Items.dye), surround, output);
	}

	@Override
	protected boolean isValidCore(ItemStack stack) {
		return stack.getItem() == this.core.getItem();
	}

	@Override
	protected ItemStack getResult(ItemStack core, ItemStack surround) {
		ItemStack output = super.getResult(core, surround);
		output.setItemDamage(15 - core.getItemDamage()); // Dye block
		return output;
	}
}
