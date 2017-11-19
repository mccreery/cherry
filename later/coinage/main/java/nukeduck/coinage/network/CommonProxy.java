package nukeduck.coinage.network;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import nukeduck.coinage.Constants;
import nukeduck.coinage.event.CommonEvents;
import nukeduck.coinage.gui.GuiCoinBag;
import nukeduck.coinage.inventory.ContainerCoinBag;
import nukeduck.coinage.inventory.InventoryCoinBag;

public class CommonProxy implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Constants.COIN_BAG_GUI_ID) return new ContainerCoinBag(player, player.inventory, new InventoryCoinBag(player.getHeldItem()));
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Constants.COIN_BAG_GUI_ID) return new GuiCoinBag(new ContainerCoinBag(player, player.inventory, new InventoryCoinBag(player.getHeldItem())));
		return null;
	}
	
	public FontRenderer getFontRendererCoins() {return null;}
	public void registerRenderThings() {}
	
	public void registerEvents() {
		CommonEvents events = new CommonEvents();
		MinecraftForge.EVENT_BUS.register(events);
		FMLCommonHandler.instance().bus().register(events);
	}
}
