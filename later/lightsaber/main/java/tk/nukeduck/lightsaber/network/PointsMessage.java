package tk.nukeduck.lightsaber.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PointsMessage implements IMessage {
	public byte progressCurrent;
	public short points;
	
	public PointsMessage() {
		this((byte) 0, (short) 0);
	}
	
	public PointsMessage(byte progress, short points) {
		this.progressCurrent = progress;
		this.points = points;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.points = buf.readShort();
		this.progressCurrent = buf.readByte();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(this.points);
		buf.writeByte(this.progressCurrent);
	}	
}