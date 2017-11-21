package tk.nukeduck.lightsaber.block.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrystal extends TileEntity {
	private byte density, rotation, offsetX, offsetZ;
	
	/** Constructor. Creates the default tile entity. */
	public TileEntityCrystal() {
		this(0, 0, 0, 0);
	}
	
	/** Constructor. Creates a tile entity with the given parameters.
	 * @param density The density of shards of crystal within the block.
	 * @param rotation The rotation of the model.
	 * @param offsetX An offset to render the block at on the X axis.
	 * @param offsetZ An offset to render the block at on the Z axis. */
	public TileEntityCrystal(int density, int rotation, int offsetX, int offsetZ) {
		this.setDensity((byte) density);
		this.setRotation((byte) rotation);
		this.setOffsets((byte) offsetX, (byte) offsetZ);
	}
	
	/** Has no effect, as the tile entity doesn't update. */
	@Override
	public void updateEntity() {}
	
	/** Write NBT of this tile entity to the given {@code NBTTagCompound}.
	 * @param compound The compound to save data to. */
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setByte("Density", getDensity());
		compound.setByte("Rotation", getRotation());
		
		compound.setByte("OffsetX", offsetX);
		compound.setByte("OffsetZ", offsetZ);
	}
	
	/** Read NBT from the given {@code NBTTagCompound} into this tile entity's values.
	 * @param compound The compound to read data from. */
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.setDensity(compound.getByte("Density"));
		this.setRotation(compound.getByte("Rotation"));
		
		this.setOffsets(compound.getByte("OffsetX"), compound.getByte("OffsetZ"));
	}
	
	/** @return The density of this crystal's shards. */
	public byte getDensity() {
		return density;
	}

	/** Sets the density of this crystal's shards.
	 * @param density The density to set. */
	public void setDensity(byte density) {
		this.density = density;
	}
	
	/** @return The rotation of this crystal's model. */
	public byte getRotation() {
		return rotation;
	}
	
	/** Sets the rotation of this crystal's model. */
	public void setRotation(byte rotation) {
		this.rotation = rotation;
	}
	
	/** @return The X offset of this crystal's model. */
	public byte getOffsetX() {
		return offsetX;
	}
	
	/** @return The Z offset of this crystal's model. */
	public byte getOffsetZ() {
		return offsetZ;
	}
	
	/** Sets the offsets on the X and Z axes of this crystal's model.
	 * @param offsetX The X offset to set.
	 * @param offsetZ The Z offset to set. */
	public void setOffsets(byte offsetX, byte offsetZ) {
		this.offsetX = offsetX;
		this.offsetZ = offsetZ;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
	readFromNBT(packet.func_148857_g());
	}
}