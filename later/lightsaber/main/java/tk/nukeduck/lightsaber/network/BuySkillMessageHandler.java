package tk.nukeduck.lightsaber.network;

import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.entity.skills.ForceSkill;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class BuySkillMessageHandler implements IMessageHandler<BuySkillMessage, IMessage> {
	@Override
	public IMessage onMessage(BuySkillMessage message, MessageContext ctx) {
		if(ctx.side == Side.SERVER) {
			ExtendedPropertiesForceSkills skills = (ExtendedPropertiesForceSkills) ExtendedPropertiesForceSkills.get(ctx.getServerHandler().playerEntity);
			ForceSkill skill = ForceSkill.skills[message.id];
			ForceSkill parentSkill = skill.getParent();
			short cost = skill.getCost();
			
			if(skills.points >= cost && !skills.skillsAttained.get(skill) && parentSkill != null && skills.skillsAttained.get(parentSkill)) {
				skills.skillsAttained.put(skill, true);
				skills.points -= cost;
				skills.sendManaMessage(ctx.getServerHandler().playerEntity);
				
				return new BuySkillReturnMessage(message.id, skills.points);
			}
		}
		return null;
	}
}