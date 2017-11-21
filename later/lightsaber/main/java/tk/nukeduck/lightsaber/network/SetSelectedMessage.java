package tk.nukeduck.lightsaber.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class SetSelectedMessage implements IMessage {
	public int index;
	public byte id;
	
	public SetSelectedMessage() {
		this(-1, (byte) -1);
	}
	
	public SetSelectedMessage(int index, byte id) {
		this.index = index;
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.index = buf.readInt();
		this.id = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.index);
		buf.writeByte(this.id);
	}
}