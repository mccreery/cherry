package nukeduck.craftconvenience.registry;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class StainCrafting implements IRecipe {
	private Block source;
	private Block output;

	public StainCrafting(Block source, Block output) {
		this.source = source;
		this.output = output;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World worldIn) {
		boolean hasDyeable = false;
		boolean hasDye = false;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;
			if(stack.getItem().equals(Item.getItemFromBlock(this.source))) {
				hasDyeable = true;
			} else if(stack.getItem().equals(Items.dye)) {
				if(hasDye) return false;
				hasDye = true;
			} else {
				return false;
			}
		}
		return hasDyeable && hasDye;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		int dyeable = 0;
		boolean hasDye = false;
		int dyeColor = 0;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;
			if(stack.getItem().equals(Item.getItemFromBlock(this.source))) {
				dyeable++;
			} else if(stack.getItem().equals(Items.dye)) {
				if(hasDye) return null;
				hasDye = true;
				dyeColor = 15 - stack.getItemDamage(); // Why's it inverted? God only knows
			} else {
				return null;
			}
		}
		if(!hasDye || dyeable == 0) return null;
		return new ItemStack(this.output, dyeable, dyeColor);
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
