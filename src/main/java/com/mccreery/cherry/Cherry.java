package com.mccreery.cherry;


import com.mccreery.cherry.registry.CherryItems;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid=Cherry.MODID, name="Cherry", version="1.0")
public class Cherry {
	public static final String MODID = "cherry";

	@EventHandler
	public void init(FMLInitializationEvent e) {
		CherryItems.init();
	}
}
