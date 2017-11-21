package tk.nukeduck.lightsaber.network;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PointsMessageHandler implements IMessageHandler<PointsMessage, IMessage> {
	@Override
	public IMessage onMessage(PointsMessage message, MessageContext ctx) {
		if(ctx.side == Side.CLIENT) {
			ExtendedPropertiesForceSkills skills = ExtendedPropertiesForceSkills.get(Lightsaber.mc.thePlayer);
			skills.points = message.points;
			skills.progressCurrent = message.progressCurrent;
		}
		return null;
	}	
}