package tk.nukeduck.lightsaber.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import tk.nukeduck.lightsaber.client.gui.GuiRefillUnit;
import tk.nukeduck.lightsaber.client.renderer.ItemRenderCrystal;
import tk.nukeduck.lightsaber.client.renderer.ItemRenderLightsaber;
import tk.nukeduck.lightsaber.registry.LightsaberBlocks;
import tk.nukeduck.lightsaber.registry.LightsaberItems;

public class ClientProxy extends CommonProxy {
	/** Registers lightsaber renders for use when held in-hand, and crystals */
	@Override
	public void registerRenderers() {
		for(Item item : LightsaberItems.lightsabers) {
			MinecraftForgeClient.registerItemRenderer(item, ItemRenderLightsaber.instance);
		}
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(LightsaberBlocks.crystal), new ItemRenderCrystal());
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && ID == 0 && te instanceof TileEntityRefillUnit) {
			return new GuiRefillUnit(player.inventory, (TileEntityRefillUnit) te);
		}
		return null;
	}
}