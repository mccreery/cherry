package tk.nukeduck.generation.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import tk.nukeduck.generation.util.NBTConstants;

public class TileEntityMimic extends TileEntity implements IInventory {
	private TileEntityChest chest;

	public TileEntityMimic() {
		this.chest = new TileEntityChest();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.chest.readFromNBT(compound);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList list = compound.getTagList(NBTConstants.CHEST_KEY, 10);
		this.writeChestToNBT(list);
		compound.setTag(NBTConstants.CHEST_KEY, list);
	}

	public void writeChestToNBT(NBTTagList list) {
		for(int i = 0; i < this.chest.getSizeInventory(); i++) {
			if(this.getStackInSlot(i) != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				this.chest.getStackInSlot(i).writeToNBT(compound);
				list.appendTag(compound);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return this.chest.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return this.chest.getStackInSlot(slotIndex);
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		return this.chest.decrStackSize(slotIndex, count);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		return this.chest.getStackInSlotOnClosing(slotIndex);
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack stack) {
		this.chest.setInventorySlotContents(slotIndex, stack);
	}

	@Override
	public String getInventoryName() {
		return this.chest.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.chest.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return this.chest.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.chest.isUseableByPlayer(player);
	}

	@Override
	public void openInventory() {
		this.chest.openInventory();
	}

	@Override
	public void closeInventory() {
		this.chest.openInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack stack) {
		return this.chest.isItemValidForSlot(slotIndex, stack);
	}
}
