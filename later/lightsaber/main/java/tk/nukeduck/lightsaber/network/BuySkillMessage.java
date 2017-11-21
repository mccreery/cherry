package tk.nukeduck.lightsaber.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class BuySkillMessage implements IMessage {
	public byte id;
	
	public BuySkillMessage() {
		this((byte) 0);
	}
	
	public BuySkillMessage(byte id) {
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readByte();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.id);
	}
}