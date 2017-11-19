package nukeduck.craftconvenience.registry;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class SurroundCrafting implements IRecipe {
	private Item source;
	private Item ingredient;
	private Item output;

	public SurroundCrafting(Item source, Item ingredient, Item output) {
		this.source = source;
		this.ingredient = ingredient;
		this.output = output;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World worldIn) {
		boolean hasSource = false;
		boolean hasIngredient = false;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;
			if(stack.getItem().equals(this.source)) {
				hasSource = true;
			} else if(stack.getItem().equals(this.ingredient)) {
				if(hasIngredient) return false;
				hasIngredient = true;
			} else {
				return false;
			}
		}
		return hasSource && hasIngredient;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		int source = 0;
		boolean hasIngredient = false;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;
			if(stack.getItem().equals(this.source)) {
				source++;
			} else if(stack.getItem().equals(this.ingredient)) {
				if(hasIngredient) return null;
				hasIngredient = true;
			} else {
				return null;
			}
		}
		if(!hasIngredient || source == 0) return null;
		return new ItemStack(this.output, source);
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inventory) {
		ItemStack[] stacks = new ItemStack[inventory.getSizeInventory()];
		for(int i = 0; i < stacks.length; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			stacks[i] = ForgeHooks.getContainerItem(stack);
		}
		return stacks;
	}
}
