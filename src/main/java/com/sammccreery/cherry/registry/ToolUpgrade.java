package com.sammccreery.cherry.registry;

import java.util.List;

import com.sammccreery.cherry.registry.ResourceMaterial.ItemType;
import com.sammccreery.cherry.util.StackUtils;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ToolUpgrade extends ShapedRecipes {
	private final ItemType type;
	private final List<ItemStack> resources;

	public ToolUpgrade(ItemType type, List<ItemStack> resources, ItemStack output) {
		super(((String)type.recipe[0]).length(), type.firstStick / 3 + 1, null, output);

		this.type = type;
		this.resources = resources;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		for(int xOrigin = 0; xOrigin <= 3 - recipeWidth; xOrigin++) {
			for(int yOrigin = 0; yOrigin <= 3 - recipeHeight; yOrigin++) {
				if(this.checkMatch(inv, xOrigin, yOrigin, true) || this.checkMatch(inv, xOrigin, yOrigin, false)) {
					return true;
				}
			}
		}
		return false;
	}

	/** @param inv The inventory to check against
	 * @param xOrigin The amount of columns to offset the recipe by
	 * @param yOrigin The amount of rows to offset the recipe by
	 * @param flip {@code true} to check against the horizontal mirror of the recipe
	 * @return {@code true} if the inventory matches the recipe at the specified offset */
	private boolean checkMatch(InventoryCrafting inv, int xOrigin, int yOrigin, boolean flip) {
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				int xRelative = x - xOrigin;
				int yRelative = y - yOrigin;

				ItemStack candidate = inv.getStackInRowAndColumn(x, y);
				if(!checkSlot(flip ? xRelative : recipeWidth - xRelative - 1, yRelative, candidate)) {
					return false;
				}
			}
		}
		return true; // All items passed
	}

	/** @return {@code true} if the stack satisfies the template in the given slot ({@code x}, {@code y}) */
	private boolean checkSlot(int x, int y, ItemStack stack) {
		if(x < 0 || x >= recipeWidth || y < 0 || y >= recipeHeight) {
			return StackUtils.isEmpty(stack);
		} else {
			char c = ((String)type.recipe[y]).charAt(x);

			if(c == '#') {
				for(ItemStack resource : resources) {
					// Use what Forge uses. No questions.
					if(OreDictionary.itemMatches(resource, stack, false)) return true;
				}
				return false;

				//return ItemStack.areItemStacksEqual(stack, resource);
			} else if(c == '|' && (y*3 + x) == type.firstStick) {
				return StackUtils.getToolType(stack) == type;
			} else {
				return StackUtils.isEmpty(stack);
			}
		}
	}
}
