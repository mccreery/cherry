package nukeduck.functionaldecor.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDecor extends TileEntity {
	private NBTTagCompound nbt;
	public NBTTagCompound getCustomData() {
		return this.nbt;
	}

	public static final String DATA_KEY = "CustomData";

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.nbt = compound.getCompoundTag(DATA_KEY);
	}

	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag(DATA_KEY, this.nbt);
	}

	@Override
	public Packet getDescriptionPacket() {
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, this.nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		this.nbt = packet.func_148857_g();
	}
}
