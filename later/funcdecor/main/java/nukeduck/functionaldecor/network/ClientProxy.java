package nukeduck.functionaldecor.network;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import nukeduck.functionaldecor.block.DecorBlocks;
import nukeduck.functionaldecor.block.TileEntityDecor;
import nukeduck.functionaldecor.client.ItemRendererDecor;
import nukeduck.functionaldecor.client.TileEntityDecorRenderer;

public class ClientProxy extends CommonProxy {
	@Override
	public void initRenders() {
		TileEntityDecorRenderer renderer = new TileEntityDecorRenderer();
		ItemRendererDecor itemRenderer = new ItemRendererDecor(renderer, new TileEntityDecor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecor.class, renderer);

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(DecorBlocks.alarmClock), itemRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(DecorBlocks.lamp), itemRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(DecorBlocks.peripheral), itemRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(DecorBlocks.recycleBin), itemRenderer);
	}
}
