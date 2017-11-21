package tk.nukeduck.lightsaber.network;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class ManaMessageHandler implements IMessageHandler<ManaMessage, IMessage> {
	@Override
	public IMessage onMessage(ManaMessage message, MessageContext ctx) {
		if(ctx.side == Side.CLIENT) {
			ExtendedPropertiesForceSkills.get(Lightsaber.mc.thePlayer).currentMana = message.mana;
			ExtendedPropertiesForceSkills.get(Lightsaber.mc.thePlayer).maxMana = message.maxMana;
			System.out.println("Mana is now " + message.mana);
		}
		return null;
	}
}