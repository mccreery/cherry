package com.sammccreery.cherry;

import com.sammccreery.cherry.event.Events;
import com.sammccreery.cherry.registry.CherryItems;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid=Cherry.MODID, useMetadata=true)
public class Cherry {
	public static final String MODID = "cherry";

	@EventHandler
	public void init(FMLInitializationEvent e) {
		CherryItems.init();
		MinecraftForge.EVENT_BUS.register(new Events());
	}
}
