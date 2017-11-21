package tk.nukeduck.lightsaber.network;

import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import tk.nukeduck.lightsaber.client.gui.GuiRefillUnit;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class IORuleMessageHandler implements IMessageHandler<IORuleMessage, IMessage> {
	@Override
	public IMessage onMessage(IORuleMessage message, MessageContext ctx) {
		if(ctx.side == Side.SERVER) {
			TileEntity toSet = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
			if(toSet != null && toSet instanceof TileEntityRefillUnit) {
				TileEntityRefillUnit te = (TileEntityRefillUnit) toSet;
				int newValue = (te.ioModes[message.id] + 1) % GuiRefillUnit.getSizeModes();
				((TileEntityRefillUnit) toSet).ioModes[message.id] = (byte) newValue;
				
				ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(message.x, message.y, message.z);
			}
		}
		return null;
	}	
}