package nukeduck.craftconvenience.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import org.apache.commons.lang3.ArrayUtils;

public class ToolUpgrades implements IRecipe {
	private static final String[][] patterns = {
		{"#", "#", "B"}, {"###", " B "}, {"##", "#B"}, {"#", "B"}, {"##", " B"}
	};
	private static final Item[] woodTools = new Item[] {
		Items.wooden_sword, Items.wooden_pickaxe, Items.wooden_axe, Items.wooden_shovel, Items.wooden_hoe
	};
	private static final Item[] upgradeItems = new Item[] {
		Item.getItemFromBlock(Blocks.cobblestone), Items.iron_ingot, Items.gold_ingot, Items.diamond
	};
	private static final Item[][] upgrades = new Item[][] {
		{Items.stone_sword, Items.stone_pickaxe, Items.stone_axe, Items.stone_shovel, Items.stone_hoe},
		{Items.iron_sword, Items.iron_pickaxe, Items.iron_axe, Items.iron_shovel, Items.iron_hoe},
		{Items.golden_sword, Items.golden_pickaxe, Items.golden_axe, Items.golden_shovel, Items.golden_hoe},
		{Items.diamond_sword, Items.diamond_pickaxe, Items.diamond_axe, Items.diamond_shovel, Items.diamond_hoe}
	};
	/** A cache of all possible recipes.<br/>
	 * Access by {@code recipeCache[tier][type]}. */
	private static final ShapedRecipes[][] recipeCache = new ShapedRecipes[upgradeItems.length][patterns.length];
	static {
		for(int tier = 0; tier < upgradeItems.length; tier++) {
			for(int type = 0; type < patterns.length; type++) {
				ItemStack upgradeItem = new ItemStack(upgradeItems[tier]);
				ItemStack baseItem = new ItemStack(woodTools[type], 1, 32767);

				String[] pattern = patterns[type];
				int w = pattern[0].length();
				int h = pattern.length;

				ItemStack[] stacks = new ItemStack[w * h];
				int offset;
				for(int y = 0; y < h; y++) {
					offset = y * w;
					for(int x = 0; x < w; x++) {
						char c = pattern[y].charAt(x);
						if(c == '#') {
							stacks[offset + x] = upgradeItem;
						} else if(c == 'B') {
							stacks[offset + x] = baseItem;
						}
					}
				}
				recipeCache[tier][type] = new ShapedRecipes(w, h, stacks, null);
			}
		}
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World worldIn) {
		int tier = -1;
		int type = -1;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;

			int newType = ArrayUtils.indexOf(woodTools, stack.getItem());
			if(newType != -1) {
				if(type != -1) return false;
				type = newType;
			}
			int newTier = ArrayUtils.indexOf(upgradeItems, stack.getItem());
			if(newTier != -1) {
				if(tier == -1) {
					tier = newTier;
				} else if(tier != newTier) {
					return false;
				}
			}
		}
		if(tier == -1 || type == -1) return false;

		return this.matchesShaped(inventory, type, tier, worldIn);
	}

	public boolean matchesShaped(InventoryCrafting inventory, int type, int tier, World world) {
		return recipeCache[tier][type].matches(inventory, world);
		/*ItemStack upgradeItem = new ItemStack(upgradeItems[tier]);
		ItemStack baseItem = new ItemStack(woodTools[type], 1, 32767);

		String[] pattern = patterns[type];
		int w = pattern[0].length();
		int h = pattern.length;

		ItemStack[] stacks = new ItemStack[w * h];
		int offset;
		for(int y = 0; y < h; y++) {
			offset = y * w;
			for(int x = 0; x < w; x++) {
				char c = pattern[y].charAt(x);
				if(c == '#') {
					stacks[offset + x] = upgradeItem;
				} else if(c == 'B') {
					stacks[offset + x] = baseItem;
				}
			}
		}
		return new ShapedRecipes(w, h, stacks, null).matches(inventory, world);*/
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		int tier = -1;
		int type = -1;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;
			if(tier == -1) tier = ArrayUtils.indexOf(upgradeItems, stack.getItem());
			if(type == -1) type = ArrayUtils.indexOf(woodTools, stack.getItem());
			if(tier != -1 && type != -1) {
				break;
			}
		}

		if(this.matchesShaped(inventory, type, tier, null)) {
			return new ItemStack(upgrades[tier][type]);
		}
		return null;
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
