package tk.nukeduck.lightsaber.network;

import io.netty.buffer.ByteBuf;
import tk.nukeduck.lightsaber.util.Constants;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class SkillActiveMessage implements IMessage {
	public byte skillId;
	public boolean active;
	
	public SkillActiveMessage() {
		this((byte) Constants.NULL_ID, false);
	}
	
	public SkillActiveMessage(byte skillId, boolean active) {
		this.skillId = skillId;
		this.active = active;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.skillId = buf.readByte();
		this.active = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.skillId);
		buf.writeBoolean(this.active);
	}
}