package com.sammccreery.cherry.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.sammccreery.cherry.registry.ResourceMaterial.ItemType;
import com.sammccreery.cherry.util.StackUtils;
import com.sammccreery.cherry.util.UniversalName;

import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CherryRecipes extends Registry<IRecipe> {
	@Override
	public IRecipe registerLocal(IRecipe obj, UniversalName name) {
		GameRegistry.addRecipe(obj);
		return obj;
	}

	@Override
	public void init() {
		registerToolUpgrades();
	}

	@Override
	public boolean isEventValid(FMLEvent e) {
		return e instanceof FMLPostInitializationEvent;
	}

	public static final int OREDICT_STICK = OreDictionary.getOreID("stickWood");

	@SuppressWarnings("unchecked")
	private void registerToolUpgrades() {
		List<IRecipe> pending = new ArrayList<IRecipe>();

		CraftingManager crafting = CraftingManager.getInstance();

		for(IRecipe recipe : (List<IRecipe>)crafting.getRecipeList()) {
			Object[] recipeItems = null;

			if(recipe instanceof ShapedRecipes) {
				recipeItems = ((ShapedRecipes)recipe).recipeItems;
			} else if(recipe instanceof ShapedOreRecipe) {
				recipeItems = ((ShapedOreRecipe)recipe).getInput();
			}
			if(recipeItems == null) continue;

			ItemType type = StackUtils.getToolType(recipe.getRecipeOutput());
			if(type == null) continue;

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
							pending.add(new ToolUpgrade(type, resources, output));
							break;
						}
					}
				} catch(NullPointerException e) {} // Recipe depends on world stuff so ignore it
			}
		}

		for(IRecipe recipe : pending) {
			registerLocal(recipe);
		}
	}
}
