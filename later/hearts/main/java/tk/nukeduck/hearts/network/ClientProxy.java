package tk.nukeduck.hearts.network;

import tk.nukeduck.hearts.block.TileEntityHeartCrystal;
import tk.nukeduck.hearts.block.TileEntityHeartLantern;
import tk.nukeduck.hearts.renderer.HeartCrystalRenderer;
import tk.nukeduck.hearts.renderer.LanternRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {
	public static int renderId;

	@Override
	public void renderInit() {
		renderId = RenderingRegistry.getNextAvailableRenderId();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeartLantern.class, new LanternRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeartCrystal.class, new HeartCrystalRenderer());
	}
}
