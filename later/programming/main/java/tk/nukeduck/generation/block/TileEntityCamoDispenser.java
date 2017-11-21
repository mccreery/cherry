package tk.nukeduck.generation.block;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityDispenser;

public class TileEntityCamoDispenser extends TileEntityDispenser {
	private ItemStack itemIn = null;
	private Block block = null;
	private byte metadata = 0;

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.readBlock(compound);
		if(compound.hasKey("ItemIn")) {
			this.itemIn = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("ItemIn"));
		} else {
			this.itemIn = null;
		}
	}

	private boolean writing = false;

	public void writeToNBT(NBTTagCompound compound) {
		this.writing = true;
		super.writeToNBT(compound);
		this.writeBlock(compound);
		if(this.itemIn != null) {
			NBTTagCompound compoundItemIn = new NBTTagCompound();
			this.itemIn.writeToNBT(compoundItemIn);
			compound.setTag("ItemIn", compoundItemIn);
		}
		this.writing = false;
	}

	public void writeBlock(NBTTagCompound compound) {
		if(this.block == null) compound.setInteger("Block", -1); 
		else compound.setInteger("Block", Block.getIdFromBlock(this.block));
		compound.setByte("Metadata", this.metadata);
	}

	public void readBlock(NBTTagCompound compound) {
		if(compound.getInteger("Block") == -1) this.block = null;
		else this.block = Block.getBlockById(compound.getInteger("Block"));
		this.metadata = compound.getByte("Metadata");
	}

	public TileEntityCamoDispenser setBlock(Block block, int metadata) {
		return this.setBlock(new ItemStack(Item.getItemFromBlock(block), 1, metadata), block, metadata);
	}
	public TileEntityCamoDispenser setBlock(Block block, byte metadata) {
		return this.setBlock(new ItemStack(Item.getItemFromBlock(block), 1, metadata), block, metadata);
	}
	public TileEntityCamoDispenser setBlock(ItemStack stack, Block block, int metadata) {
		return this.setBlock(stack, block, (byte) metadata);
	}
	public TileEntityCamoDispenser setBlock(ItemStack stack, Block block, byte metadata) {
		this.itemIn = stack;
		this.block = block;
		this.metadata = metadata;
		return this;
	}

	public boolean hasItem() {
		return this.itemIn != null;
	}
	public boolean hasBlock() {
		return this.block != null;
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeBlock(tagCompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tagCompound);
	}

	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readBlock(pkt.func_148857_g());
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean hasCustomInventoryName() {
		return !writing || this.field_146020_a != null;
	}

	@Override
	public String getInventoryName() {
		return this.field_146020_a != null ? this.field_146020_a : I18n.format("container.camoDispenser", new Object[] {!this.hasItem() ? I18n.format("container.camoDispenser.empty", new Object[0]) : this.getItem().getDisplayName()});
	}

	public ItemStack getItem() {
		return this.itemIn;
	}
	public Block getBlock() {
		return this.block;
	}
	public int getMetadata() {
		return this.metadata;
	}
}
