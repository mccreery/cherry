package tk.nukeduck.lightsaber.network;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.client.gui.tome.GuiSkillTree;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.entity.skills.ForceSkill;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class BuySkillReturnMessageHandler implements IMessageHandler<BuySkillReturnMessage, IMessage> {
	@Override
	public IMessage onMessage(BuySkillReturnMessage message, MessageContext ctx) {
		if(ctx.side == Side.CLIENT) {
			ExtendedPropertiesForceSkills skills = ExtendedPropertiesForceSkills.get(Lightsaber.mc.thePlayer);
			skills.points = message.points;
			skills.skillsAttained.put(ForceSkill.skills[message.id], true);
			
			if(Lightsaber.mc.currentScreen instanceof GuiSkillTree) {
				GuiSkillTree gui = ((GuiSkillTree) Lightsaber.mc.currentScreen);
				gui.enable(ForceSkill.skills[message.id]);
				gui.updatePoints(message.points);
			}
		}
		return null;
	}
}