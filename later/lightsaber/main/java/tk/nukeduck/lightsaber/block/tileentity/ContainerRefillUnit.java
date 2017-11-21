package tk.nukeduck.lightsaber.block.tileentity;

import tk.nukeduck.lightsaber.item.ItemEnergyCapsule;
import tk.nukeduck.lightsaber.item.ItemLightsaber;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRefillUnit extends Container {
	private TileEntityRefillUnit tileEntity;
	private short chargeLevel, chargeLevelMax;
	private byte chargeDelay, chargeCountdown;
	private byte[] ioModes = {0, 0, 0, 0};
	
	/** Constructor.<br/>
	 * Sets the tile entity, adds slots and adds crafters.
	 * @param inventory The inventory of the player who opened the container.
	 * @param tileEntity The tile entity of the refill unit opened. */
	public ContainerRefillUnit(InventoryPlayer inventory, TileEntityRefillUnit tileEntity) {
		this.tileEntity = tileEntity;
		
		addSlotToContainer(new Slot(tileEntity, 0, 116, 50) {
			@Override
			public boolean isItemValid(ItemStack itemStack) {
				return itemStack.getItem() instanceof ItemLightsaber;
			}
		});
		
		addSlotToContainer(new SlotEnergyCapsule(tileEntity, 1, 26, 46));
		addSlotToContainer(new SlotEnergyCapsule(tileEntity, 2, 49, 53));
		addSlotToContainer(new SlotEnergyCapsule(tileEntity, 3, 72, 46));
		
		for(int i = 0; i < 3; i++) {
			for(int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}
		
		for(int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(inventory, j, 8 + j * 18, 142));
		}
		
		for(int i = 0; i < this.crafters.size(); ++i) {
			ICrafting iCrafting = (ICrafting)this.crafters.get(i);
			addCraftingToCrafters(iCrafting);
		}
	}
	
	/** Sends updates to the {@code ICrafting} specified for all values.
	 * @param iCrafting the {@code ICrafting} to send updates to. */
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {
		super.addCraftingToCrafters(iCrafting);
		iCrafting.sendProgressBarUpdate(this, 0, this.tileEntity.chargeLevel);
		iCrafting.sendProgressBarUpdate(this, 1, this.tileEntity.chargeLevelMax);
		iCrafting.sendProgressBarUpdate(this, 2, this.tileEntity.chargeDelay);
		iCrafting.sendProgressBarUpdate(this, 3, this.tileEntity.untilNextCharge);
	}
	
	/** Sends updates to all {@code ICrafting} instances when values have changed. */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i) {
			ICrafting iCrafting = (ICrafting)this.crafters.get(i);
			
			if(this.chargeLevel != this.tileEntity.chargeLevel) {
				iCrafting.sendProgressBarUpdate(this, 0, this.tileEntity.chargeLevel);
			}
			if(this.chargeLevelMax != this.tileEntity.chargeLevelMax) {
				iCrafting.sendProgressBarUpdate(this, 1, this.tileEntity.chargeLevelMax);
			}
			if(this.chargeDelay != this.tileEntity.chargeDelay) {
				iCrafting.sendProgressBarUpdate(this, 2, this.tileEntity.chargeDelay);
			}
			if(this.chargeCountdown != this.tileEntity.untilNextCharge) {
				iCrafting.sendProgressBarUpdate(this, 3, this.tileEntity.untilNextCharge);
			}
		}
		
		this.chargeLevel = this.tileEntity.chargeLevel;
		this.chargeLevelMax = this.tileEntity.chargeLevelMax;
		this.chargeDelay = this.tileEntity.chargeDelay;
		this.chargeCountdown = this.tileEntity.untilNextCharge;
	}
	
	/** Syncs the value given at the specified ID with this container's tile entity.
	 * @param id The ID of the variable to update (e.g. {@code 0} for {@code chargeLevel}).
	 * @param value The value to set. */
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		switch(id) {
			case 0:
				this.tileEntity.chargeLevel = (short) value;
				break;
			case 1:
				this.tileEntity.chargeLevelMax = (short) value;
				break;
			case 2:
				this.tileEntity.chargeDelay = (byte) value;
				break;
			case 3:
				this.tileEntity.untilNextCharge = (byte) value;
				break;
		}
	}
	
	/** @param player The player to check for.
	 * @return {@code true} if the given player is able to access the tile entity. */
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotnumber) {
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(slotnumber);
		
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if(slotnumber < 4) {
				if(!mergeItemStack(itemstack1, 4, 40, true)) {
					return null;
				}
			} else if (slotnumber > 3 && slotnumber < 40) {
				if(itemstack.getItem() instanceof ItemLightsaber) {
					if(!mergeItemStack(itemstack1, 0, 1, false)) {
						if(slotnumber < 31) {
							if(!mergeItemStack(itemstack1, 31, 40, false)) {
								return null;
							}
						} else {
							if(!mergeItemStack(itemstack1, 4, 31, false)) {
								return null;
							}
						}
					}
				} else if(itemstack.getItem() instanceof ItemEnergyCapsule) {
					if(!mergeItemStack(itemstack1, 1, 4, false)) {
						if(slotnumber < 31) {
							if(!mergeItemStack(itemstack1, 31, 40, false)) {
								return null;
							}
						} else {
							if(!mergeItemStack(itemstack1, 4, 31, false)) {
								return null;
							}
						}
					}
				} else {
					if(slotnumber < 31) {
						if(!mergeItemStack(itemstack1, 31, 40, false)) {
							return null;
						}
					} else {
						if(!mergeItemStack(itemstack1, 4, 31, false)) {
							return null;
						}
					}
				}
			}
			
			if(itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			
			slot.onPickupFromSlot(player, itemstack);
		}
		return itemstack;
	}
}