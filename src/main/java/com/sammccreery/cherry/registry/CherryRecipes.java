package com.sammccreery.cherry.registry;

import java.util.ArrayList;
import java.util.List;

import com.sammccreery.cherry.registry.ResourceMaterial.ItemType;
import com.sammccreery.cherry.util.StackUtils;
import com.sammccreery.cherry.util.UniversalName;

import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;

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

	@SuppressWarnings("unchecked")
	private void registerToolUpgrades() {
		List<IRecipe> pending = new ArrayList<IRecipe>();

		CraftingManager crafting = CraftingManager.getInstance();

		for(IRecipe recipe : (List<IRecipe>)crafting.getRecipeList()) {
			if(!(recipe instanceof ShapedRecipes)) continue;

			ItemType type = StackUtils.getToolType(recipe.getRecipeOutput());
			if(type == null) continue;

			ItemStack resource = null;

			for(ItemStack stack : ((ShapedRecipes)recipe).recipeItems) {
				if(StackUtils.isEmpty(stack) || stack.getItem() == Items.stick) {
					continue;
				} else if(!StackUtils.isEmpty(stack)) {
					resource = stack;
					break;
				}
			}

			if(resource == null) continue;
			InventoryCrafting template = type.getTemplate(resource);

			try {
				if(recipe.matches(template, null)) {
					ItemStack output = recipe.getCraftingResult(template);

					if(StackUtils.getToolType(output) == type) {
						pending.add(new ToolUpgrade(type, resource, output));
					}
				}
			} catch(NullPointerException e) {} // Recipe depends on world stuff so ignore it
		}

		for(IRecipe recipe : pending) {
			registerLocal(recipe);
		}
	}
}
