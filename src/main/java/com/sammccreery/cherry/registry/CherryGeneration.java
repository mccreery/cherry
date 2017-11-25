package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.generation.WorldGenOres;
import com.sammccreery.cherry.util.UniversalName;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class CherryGeneration extends Registry<IWorldGenerator> {
	@Override
	public IWorldGenerator registerLocal(IWorldGenerator obj, UniversalName name) {
		GameRegistry.registerWorldGenerator(obj, 0);
		return obj;
	}

	@Override
	public void init() {
		registerLocal(new WorldGenOres());
	}
}
