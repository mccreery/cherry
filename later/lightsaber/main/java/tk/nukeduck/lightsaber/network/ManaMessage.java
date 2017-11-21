package tk.nukeduck.lightsaber.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class ManaMessage implements IMessage {
	public short mana, maxMana;
	
	public ManaMessage() {
		this((short) 0, (short) 0);
	}
	
	public ManaMessage(short mana, short maxMana) {
		this.mana = mana;
		this.maxMana = maxMana;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.mana = buf.readShort();
		this.maxMana = buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(this.mana);
		buf.writeShort(this.maxMana);
	}
}