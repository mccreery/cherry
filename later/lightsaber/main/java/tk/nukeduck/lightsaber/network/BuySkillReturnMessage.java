package tk.nukeduck.lightsaber.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class BuySkillReturnMessage implements IMessage {
	public short points;
	public byte id;
	
	public BuySkillReturnMessage() {
		this.id = 0;
		this.points = 0;
	}
	
	public BuySkillReturnMessage(byte id, short points) {
		this.id = id;
		this.points = points;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readByte();
		this.points = buf.readShort();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.id);
		buf.writeShort(this.points);
	}
}