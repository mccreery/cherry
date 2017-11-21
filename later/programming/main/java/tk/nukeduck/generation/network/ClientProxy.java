package tk.nukeduck.generation.network;

import tk.nukeduck.generation.block.TileEntityMimic;
import tk.nukeduck.generation.client.CamoRenderer;
import tk.nukeduck.generation.client.ModelMimic;
import tk.nukeduck.generation.client.RenderMimic;
import tk.nukeduck.generation.client.TileEntityMimicRenderer;
import tk.nukeduck.generation.entity.EntityMimic;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
	public static int renderId;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderers() {
		renderId = RenderingRegistry.getNextAvailableRenderId();
		ISimpleBlockRenderingHandler renderer = new CamoRenderer();
		RenderingRegistry.registerBlockHandler(renderId, renderer);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMimic.class, new TileEntityMimicRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityMimic.class, new RenderMimic(new ModelMimic(), 0.5F));
	}
}
