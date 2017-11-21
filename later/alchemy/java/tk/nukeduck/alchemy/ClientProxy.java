package tk.nukeduck.alchemy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.*;

public class ClientProxy extends CommonProxy {
	public static int renderId;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderers() {
		renderId = RenderingRegistry.getNextAvailableRenderId();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlchemyPot.class, new AlchemyPotRenderer());
	}
	
	public void registerTileEntitySpecialRenderer() {
		// Nothing
	}
}