package tk.nukeduck.generation.block;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityProgrammer extends TileEntity implements IInventory {
	private ItemStack[] slots = new ItemStack[2];
	private Random random = new Random();
	protected String displayName;
	private static final String __OBFID = "CL_00000352";

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return this.slots[slotIndex];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		if(this.slots[slotIndex] != null) {
			ItemStack itemstack;

			if(this.slots[slotIndex].stackSize <= count) {
				itemstack = this.slots[slotIndex];
				this.slots[slotIndex] = null;
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.slots[slotIndex].splitStack(count);

				if(this.slots[slotIndex].stackSize == 0) {
					this.slots[slotIndex] = null;
				}
				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		if(this.slots[slotIndex] != null) {
			ItemStack itemstack = this.slots[slotIndex];
			this.slots[slotIndex] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack stack) {
		this.slots[slotIndex] = stack;

		if(stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.displayName : "container.programmer";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.displayName != null;
	}

	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];

		for(int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if(j >= 0 && j < this.slots.length) {
				this.slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		if(tagCompound.hasKey("CustomName", 8)) {
			this.displayName = tagCompound.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for(int i = 0; i < this.slots.length; ++i) {
			if(this.slots[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.slots[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		tagCompound.setTag("Items", nbttaglist);

		if(this.hasCustomInventoryName()) {
			tagCompound.setString("CustomName", this.displayName);
		}
	}

	public int getInventoryStackLimit() {
		 return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void openInventory() {}
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack stack) {
		return true;
	}
}
