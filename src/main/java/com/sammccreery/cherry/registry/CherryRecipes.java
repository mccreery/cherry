package com.sammccreery.cherry.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.ArrayUtils;

import com.sammccreery.cherry.registry.ResourceMaterial.ItemType;
import com.sammccreery.cherry.util.Name;
import com.sammccreery.cherry.util.Name.Format;
import com.sammccreery.cherry.util.Names;
import com.sammccreery.cherry.util.StackUtils;

import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CherryRecipes extends Registry<IRecipe> {
	@Override
	public IRecipe registerLocal(IRecipe obj, Name name) {
		GameRegistry.addRecipe(obj);
		return obj;
	}

	@Override
	public void init() {
		RecipeSorter.register(Names.TOOL_UPGRADES.format(Format.SNAKE, true), ToolUpgrade.class, Category.SHAPED, "after:minecraft:shapeless");
		RecipeSorter.register(Names.SURROUND_CRAFTING.format(Format.SNAKE, true), SurroundCrafting.class, Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register(Names.DYE_CRAFTING.format(Format.SNAKE, true), DyeCrafting.class, Category.SHAPELESS, "after:minecraft:shapeless");

		GameRegistry.addShapelessRecipe(new ItemStack(CherryItems.heartShard, 3), CherryBlocks.heartCrystal);

		GameRegistry.addRecipe(new ItemStack(CherryBlocks.heartLantern),
			"GCG", "GHG", "GGG", 'G', Blocks.glass, 'C', Blocks.hardened_clay, 'H', CherryBlocks.heartCrystal);
		GameRegistry.addRecipe(new ItemStack(CherryBlocks.heartCrystal),
			"GCG", "GHG", "GGG", 'G', Blocks.glass, 'C', Blocks.stained_hardened_clay, 'H', CherryBlocks.heartCrystal);

		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.cobblestone_wall, 1, 1), Blocks.cobblestone_wall, Item.getItemFromBlock(Blocks.vine));
		GameRegistry.addRecipe(new ItemStack(Blocks.trapped_chest), "WWW", "WHW", "WWW", 'W', Blocks.planks, 'H', Blocks.tripwire_hook);
		GameRegistry.addRecipe(new ItemStack(Items.painting), "SSS", "SPS", "SSS", 'S', Items.stick, 'P', Items.paper);
		GameRegistry.addRecipe(new ItemStack(Items.repeater), "R R", "TRT", "SSS", 'R', Items.redstone, 'T', Items.stick, 'S', Blocks.stone);
		GameRegistry.addRecipe(new ItemStack(Items.carrot_on_a_stick), "  T", " TS", "TCS", 'T', Items.stick, 'S', Items.string, 'C', Items.carrot);

		// Slime blocks aren't in the game yet
		/*registerLocal(new SurroundCrafting(new ItemStack(Blocks.piston), new ItemStack(Blocks.slime_block), new ItemStack(Blocks.sticky_piston)));
		registerLocal(new SurroundCrafting(new ItemStack(Items.blaze_powder), new ItemStack(Blocks.slime_block), new ItemStack(Items.magma_cream)));*/

		// All recipes past this point replace vanilla ones - remove them now
		postProcess();

		GameRegistry.addRecipe(new ItemStack(Items.glass_bottle, 3), "G G", " G ", 'G', Blocks.glass_pane);
		GameRegistry.addRecipe(new ItemStack(Items.glass_bottle, 8), "G G", " G ", 'G', Blocks.glass);

		// Rabbit stew isn't in the game yet
		//GameRegistry.addShapelessRecipe(new ItemStack(Items.rabbit_stew), Items.baked_potato, Items.cooked_rabbit, Items.bowl);

		GameRegistry.addShapelessRecipe(new ItemStack(Items.cookie, 6), Items.wheat, new ItemStack(Items.dye, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.book, 2), Items.paper, Items.paper, Items.paper, Items.leather);

		registerLocal(new DyeCrafting(new ItemStack(Blocks.hardened_clay), new ItemStack(Blocks.stained_hardened_clay)));
		registerLocal(new DyeCrafting(new ItemStack(Blocks.glass), new ItemStack(Blocks.stained_glass)));
		registerLocal(new DyeCrafting(new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.stained_glass_pane)));
	}

	@Override
	public boolean isEventValid(FMLEvent e) {
		return e instanceof FMLPostInitializationEvent;
	}

	public static final int OREDICT_STICK = OreDictionary.getOreID("stickWood");

	private void postProcess() {
		@SuppressWarnings("unchecked")
		ListIterator<IRecipe> iterator = (ListIterator<IRecipe>)CraftingManager.getInstance().getRecipeList().listIterator();

		while(iterator.hasNext()) {
			IRecipe recipe = iterator.next();

			checkToolUpgrade(iterator, recipe);
			if(recipe != null) checkRemove(iterator, recipe);
			if(recipe != null) replaceStairs(iterator, recipe);
		}
	}

	private void checkRemove(ListIterator<IRecipe> iterator, IRecipe recipe) {
		if(StackUtils.isEmpty(recipe.getRecipeOutput())) return;
		Item item = recipe.getRecipeOutput().getItem();

		if(item instanceof ItemBlock) {
			Block block = Block.getBlockFromItem(item);

			if(block == Blocks.stained_hardened_clay
					|| block == Blocks.stained_glass
					|| block == Blocks.stained_glass_pane) {
				iterator.remove();
			}
		} else if(item == Items.glass_bottle
				// || item == Items.rabbit_stew (Not in the game)
				|| item == Items.cookie
				|| item == Items.book) {
			iterator.remove();
		}
	}

	private void replaceStairs(ListIterator<IRecipe> iterator, IRecipe recipe) {
		if(!(recipe instanceof ShapedRecipes)) return;
		ShapedRecipes shaped = (ShapedRecipes)recipe;
		ItemStack output = recipe.getRecipeOutput();

		if(!(Block.getBlockFromItem(output.getItem()) instanceof BlockStairs) || output.stackSize != 4) {
			return;
		}

		ItemStack resource = null;
		for(int y = 0, i = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++, i++) {
				if(x <= y) {
					if(StackUtils.isEmpty(shaped.recipeItems[i]) || resource != null && !ItemStack.areItemStacksEqual(shaped.recipeItems[i], resource)) {
						return;
					} else {
						resource = shaped.recipeItems[i];
					}
				} else if(!StackUtils.isEmpty(shaped.recipeItems[i])) {
					return;
				}
			}
		}

		iterator.remove();
		iterator.add(new ShapedRecipes(2, 2, new ItemStack[] {
			resource, null, resource, resource
		}, output));
	}

	@SuppressWarnings("unchecked")
	private void checkToolUpgrade(ListIterator<IRecipe> iterator, IRecipe recipe) {
		Object[] recipeItems = null;

		if(recipe instanceof ShapedRecipes) {
			recipeItems = ((ShapedRecipes)recipe).recipeItems;
		} else if(recipe instanceof ShapedOreRecipe) {
			recipeItems = ((ShapedOreRecipe)recipe).getInput();
		}
		if(recipeItems == null) return;

		ItemType type = StackUtils.getToolType(recipe.getRecipeOutput());
		if(type == null) return;

		List<ItemStack> resources = new ArrayList<ItemStack>();

		resourceFound:
		for(Object obj : recipeItems) {
			// Handle oredict
			resources.clear();

			if(obj instanceof Collection) {
				resources.addAll((Collection<ItemStack>)obj);
			} else if(obj instanceof ItemStack) {
				resources.add((ItemStack)obj);
			}

			for(ItemStack stack : resources) {
				if(!StackUtils.isEmpty(stack)) {
					int[] ids = OreDictionary.getOreIDs(stack);

					// Not a stick, we found ourselves a set of resources
					if(!ArrayUtils.contains(ids, OREDICT_STICK)) {
						break resourceFound;
					}
				}
			}
		}

		// Check for any one of the recipes being registered
		for(ItemStack resource : resources) {
			InventoryCrafting template = type.getTemplate(resource);

			try {
				if(recipe.matches(template, null)) {
					ItemStack output = recipe.getCraftingResult(template);

					if(StackUtils.getToolType(output) == type) {
						iterator.add(new ToolUpgrade(type, resources, output));
						break;
					}
				}
			} catch(NullPointerException e) {} // Recipe depends on world stuff so ignore it
		}
	}
}
