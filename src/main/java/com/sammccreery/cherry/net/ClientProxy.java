package com.sammccreery.cherry.net;

import com.sammccreery.cherry.block.TileEntityHeartCrystal;
import com.sammccreery.cherry.block.TileEntityHeartLantern;
import com.sammccreery.cherry.client.HeartCrystalRenderer;
import com.sammccreery.cherry.client.LanternRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy implements CherryProxy {
	public static int renderId;

	@Override
	public void init() {
		renderId = RenderingRegistry.getNextAvailableRenderId();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeartCrystal.class, new HeartCrystalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeartLantern.class, new LanternRenderer());
	}
}
