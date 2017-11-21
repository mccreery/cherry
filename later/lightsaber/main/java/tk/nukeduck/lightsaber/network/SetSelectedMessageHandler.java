package tk.nukeduck.lightsaber.network;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.client.gui.tome.GuiSkillTree;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.entity.skills.ForceSkill;
import tk.nukeduck.lightsaber.util.Constants;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class SetSelectedMessageHandler implements IMessageHandler<SetSelectedMessage, IMessage> {
	@Override
	public IMessage onMessage(SetSelectedMessage message, MessageContext ctx) {
		if(ctx.side == Side.SERVER) {
			ExtendedPropertiesForceSkills skills = (ExtendedPropertiesForceSkills) ExtendedPropertiesForceSkills.get(ctx.getServerHandler().playerEntity);
			if(skills.skillsAttained.get(ForceSkill.fromId(message.id))) {
				for(int i = 0; i < Constants.MAX_SELECTED; i++) {
					if(skills.selected[i] != null && skills.selected[i].getId() == message.id) {
						skills.selected[i] = null;
					}
				}
				skills.selected[message.index] = ForceSkill.fromId(message.id);
				return new SetSelectedMessage(message.index, message.id);
			}
		} else {
			ExtendedPropertiesForceSkills skills = ExtendedPropertiesForceSkills.get(Lightsaber.mc.thePlayer);
			for(int i = 0; i < Constants.MAX_SELECTED; i++) {
				if(skills.selected[i] != null && skills.selected[i].getId() == message.id) {
					skills.selected[i] = null;
				}
			}
			skills.selected[message.index] = ForceSkill.fromId(message.id);
			
			if(Lightsaber.mc.currentScreen instanceof GuiSkillTree) {
				GuiSkillTree gui = ((GuiSkillTree) Lightsaber.mc.currentScreen);
				gui.updateSelected(message.index, ForceSkill.fromId(message.id));
			}
		}
		return null;
	}
}