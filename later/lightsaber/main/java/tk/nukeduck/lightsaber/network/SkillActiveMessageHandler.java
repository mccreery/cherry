package tk.nukeduck.lightsaber.network;

import tk.nukeduck.lightsaber.entity.skills.ForceSkill;
import tk.nukeduck.lightsaber.entity.skills.ServerSkillHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class SkillActiveMessageHandler implements IMessageHandler<SkillActiveMessage, IMessage> {
	@Override
	public IMessage onMessage(SkillActiveMessage message, MessageContext ctx) {
		if(ctx.side == Side.SERVER) {
			if(message.active) {
				ServerSkillHandler.instance.startSkill(ctx.getServerHandler().playerEntity, ForceSkill.fromId(message.skillId));
			} else {
				ServerSkillHandler.instance.endSkill(ctx.getServerHandler().playerEntity);
			}
		}
		return null;
	}
}