package com.sammccreery.cherry;

import com.sammccreery.cherry.event.Events;
import com.sammccreery.cherry.registry.CherryBlocks;
import com.sammccreery.cherry.registry.CherryGeneration;
import com.sammccreery.cherry.registry.CherryItems;
import com.sammccreery.cherry.registry.Registry;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod(modid=Cherry.MODID, useMetadata=true)
public class Cherry {
	public static final String MODID = "cherry";

	@EventHandler
	public void init(FMLInitializationEvent e) {
		Registry.addRegister(Item.class, new CherryItems());
		Registry.addRegister(Block.class, new CherryBlocks());
		Registry.addRegister(Object.class, new Events());
		Registry.addRegister(IWorldGenerator.class, new CherryGeneration());
		Registry.initAll();
	}
}
