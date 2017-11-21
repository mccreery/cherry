package tk.nukeduck.lightsaber.block.tileentity;

import org.apache.commons.lang3.ArrayUtils;

import tk.nukeduck.lightsaber.item.ItemEnergyCapsule;
import tk.nukeduck.lightsaber.item.ItemLightsaber;
import tk.nukeduck.lightsaber.util.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRefillUnit extends TileEntity implements ISidedInventory {
	public TileEntityRefillUnit() {
		this(0);
	}
	
	/** Constructor.<br/>
	 * Sets the values of {@code chargeLevelMax} and {@code chargeDelay} dependent on the mark.
	 * @param mark The mark to give this tile entity. */
	public TileEntityRefillUnit(int mark) {
		inventorySlots = new ItemStack[4];
		if(mark < Constants.REFILL_UNIT_CAPACITIES.length) {
			this.chargeLevelMax = (short) Constants.REFILL_UNIT_CAPACITIES[mark][0];
			this.chargeDelay = (byte) Constants.REFILL_UNIT_CAPACITIES[mark][1];
		}
	}
	
	/** All the slots in the tile entity. */
	public ItemStack[] inventorySlots;
	/** How much the item being held should rotate. */
	public int rotationAmount = 0;
	
	/** How many ticks between each charge. */
	public byte chargeDelay = 0;
	/** How many ticks are currently remaining until the next charge. */
	public byte untilNextCharge = 0;
	
	/** How much charge this tile entity holds. */
	public short chargeLevel = 0;
	/** How much charge this tile entity can hold as a maximum. */
	public short chargeLevelMax = 1000;
	
	/** The currently selected IO modes for each of the four slot groups. */
	public byte[] ioModes = {0, 0, 0, 0};
	
	/** Increases rotation and counts down until the next charge occurs. */
	@Override
	public void updateEntity() {
		rotationAmount += 4;
		rotationAmount %= 36000;
		
		if(untilNextCharge <= 0) {
			untilNextCharge = (byte) (chargeDelay - 1);
			
			ItemStack itemStack = getStackInSlot(0);
			if(itemStack != null) {
				itemStack.getTagCompound().setBoolean("Enabled", false);
				if(itemStack.getItemDamage() > 1 && chargeLevel > 0) {
					itemStack.setItemDamage(itemStack.getItemDamage() - 1);
					chargeLevel--;
				}
			}
			
			for(int i = 1; i < 4; i++) {
				ItemStack itemStack2 = getStackInSlot(i);
				if(itemStack2 != null && itemStack2.getItemDamage() < itemStack2.getMaxDamage() && chargeLevel < chargeLevelMax) {
					itemStack2.setItemDamage(itemStack2.getItemDamage() + 1);
					chargeLevel++;
				}
			}
		} else {
			untilNextCharge--;
		}
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
	
	public int getSizeInventory() {
		return inventorySlots.length;
	}
	
	public ItemStack getStackInSlot(int i) {
		return inventorySlots[i];
	}
	
	public ItemStack decrStackSize(int slot, int amount) {
		if(inventorySlots[slot] != null) {
			if(inventorySlots[slot].stackSize <= amount) {
				ItemStack itemstack = inventorySlots[slot];
				inventorySlots[slot] = null;
				return itemstack;
			}

			ItemStack itemstack1 = inventorySlots[slot].splitStack(amount);

			if(inventorySlots[slot].stackSize == 0) {
				inventorySlots[slot] = null;
			}

			return itemstack1;
		} else {
			return null;
		}
	}
	
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if(inventorySlots[slot] != null)
		{
			ItemStack itemstack = inventorySlots[slot];
			inventorySlots[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}
	
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		inventorySlots[slot] = itemStack;

		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}
	
	@Override
	public String getInventoryName() {
		return "container.lightsaberCharger";
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	/** Write NBT of this tile entity to the given {@code NBTTagCompound}.
	 * @param compound The compound to save data to. */
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setByte("ChargeDelay", chargeDelay);
		tagCompound.setByte("ChargeCountdown", untilNextCharge);
		tagCompound.setShort("Charge", chargeLevel);
		tagCompound.setShort("ChargeMax", chargeLevelMax);
		tagCompound.setByteArray("IOModes", ioModes);
		
		NBTTagList items = new NBTTagList();
		
		for(int i = 0; i < inventorySlots.length; i++) {
			if(inventorySlots[i] != null) {
				NBTTagCompound slot = new NBTTagCompound();
				slot.setByte("Slot", (byte)i);
				inventorySlots[i].writeToNBT(slot);
				items.appendTag(slot);
			}
		}
		
		tagCompound.setTag("Items", items);
	}
	
	/** Read NBT from the given {@code NBTTagCompound} into this tile entity's values.
	 * @param compound The compound to read data from. */
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.chargeDelay = tagCompound.getByte("ChargeDelay");
		this.untilNextCharge = tagCompound.getByte("ChargeCountdown");
		this.chargeLevel = tagCompound.getShort("Charge");
		this.chargeLevelMax = tagCompound.getShort("ChargeMax");
		this.ioModes = tagCompound.getByteArray("IOModes");
		
		NBTTagList items = tagCompound.getTagList("Items", 10);
		
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound current = items.getCompoundTagAt(i);
			inventorySlots[current.getByte("Slot")] = ItemStack.loadItemStackFromNBT(current);
		}
	}
	
	/** @param player The player trying to access the tile entity.
	 * @return {@code true} if the player is within 8 blocks of the tile entity. */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
				? player.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64.0D
				: false;
	}
	
	public void openInventory() {}
	public void closeInventory() {}
	
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		if(slot > 3) return false;
		if(slot == 0) return itemStack.getItem() instanceof ItemLightsaber;
		else return itemStack.getItem() instanceof ItemEnergyCapsule;
	}
	
	/** Slot indices which can contain Energy Capsules. */
	private int[] capsuleSlots = {1, 2, 3}; // Energy Capsule slots
	/** Slot indices which can contain chargeable items. */
	private int[] saberSlots = {0};
	/** Slot indices for all the slots in the inventory. */
	private int[] allSlots = {0, 1, 2, 3};
	/** Empty array to indicate no slot indices. */
	private int[] noSlots = {};
	
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side == 0 || side == 1) return allSlots;
		switch(this.getBlockMetadata()) {
			case 0:
				return side == 5 ? capsuleSlots : side == 4 ? saberSlots : noSlots;
			case 1:
				return side == 3 ? capsuleSlots : side == 2 ? saberSlots : noSlots;
			case 2:
				return side == 4 ? capsuleSlots : side == 5 ? saberSlots : noSlots;
			case 3:
				return side == 2 ? capsuleSlots : side == 3 ? saberSlots : noSlots;
			default:
				return noSlots;
		}
	}
	
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		int[] slots = this.getAccessibleSlotsFromSide(side);
		if(!ArrayUtils.contains(slots, slot)) return false;
		
		if((slots == allSlots || slots == capsuleSlots) && itemStack.getItem() instanceof ItemEnergyCapsule) {
			switch(ioModes[0]) {
				case 0:
					return true;
				case 1:
					return false;
				case 2:
					return itemStack.getItemDamage() == itemStack.getMaxDamage();
				case 3:
					return itemStack.getItemDamage() <= 1;
			}
		} else if((slots == allSlots || slots == saberSlots) && itemStack.getItem() instanceof ItemLightsaber) {
			switch(ioModes[2]) {
				case 0:
					return true;
				case 1:
					return false;
				case 2:
					return itemStack.getItemDamage() == itemStack.getMaxDamage();
				case 3:
					return itemStack.getItemDamage() <= 1;
			}
		}
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		int[] slots = this.getAccessibleSlotsFromSide(side);
		if(!ArrayUtils.contains(slots, slot)) return false;
		
		if((slots == allSlots || slots == capsuleSlots) && itemStack.getItem() instanceof ItemEnergyCapsule) {
			switch(ioModes[1]) {
				case 0:
					return true;
				case 1:
					return false;
				case 2:
					return itemStack.getItemDamage() == itemStack.getMaxDamage();
				case 3:
					return itemStack.getItemDamage() <= 1;
			}
		} else if((slots == allSlots || slots == saberSlots) && itemStack.getItem() instanceof ItemLightsaber) {
			switch(ioModes[3]) {
				case 0:
					return true;
				case 1:
					return false;
				case 2:
					return itemStack.getItemDamage() == itemStack.getMaxDamage();
				case 3:
					return itemStack.getItemDamage() <= 1;
			}
		}
		return false;
	}
}