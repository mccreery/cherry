package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.util.UniversalName;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.IRecipe;

public class CherryRecipes extends Registry<IRecipe> {
	@Override
	public IRecipe registerLocal(IRecipe obj, UniversalName name) {
		GameRegistry.addRecipe(obj);
		return obj;
	}

	@Override
	public void init() {}
}
