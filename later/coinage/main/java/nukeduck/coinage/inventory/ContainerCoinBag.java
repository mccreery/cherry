package nukeduck.coinage.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import nukeduck.coinage.item.SlotCoin;

public class ContainerCoinBag extends Container {
	public final InventoryCoinBag inventory;
	
	private static final int INV_START = InventoryCoinBag.SIZE, INV_END = INV_START + 26,
		HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerCoinBag(EntityPlayer player, InventoryPlayer inventory, InventoryCoinBag coinInventory) {
		this.inventory = coinInventory;
		int i;
		
		for(i = 0; i < InventoryCoinBag.SIZE; i++) {
			this.addSlotToContainer(new SlotCoin(this.inventory, i, 26 + 18 * (i % 5), 20 + 18 * (int) (i / 5)));
		}
		
		for (i = 0; i < 3; i++) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 69 + i * 18));
			}
		}
		
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 127));
		}
	}
	
	/*public void writeToNBT() {
		if(!parentItem.hasTagCompound()) {
			parentItem.setTagCompound(new NBTTagCompound());
		}
		((InventoryCoinBag) inventory).writeToNBT(parentItem.getTagCompound());
	}*/
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);
		
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(par2 < INV_START) { // In the hotbar
				if(!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}
				
				slot.onSlotChange(itemstack1, itemstack);
			} else {
				if(this.getSlot(0).isItemValid(itemstack1)) {
					if(!this.mergeItemStack(itemstack1, 0, 10, false)) {
						return null;
					}
				} else if(par2 >= INV_START && par2 < HOTBAR_START) {
					if(!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return null;
					}
				} else if(par2 >= HOTBAR_START && par2 < HOTBAR_END + 1) {
					if(!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
			}
			
			if(itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			
			if(itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		//this.needsUpdate = true;
		return itemstack;
	}
	
	@Override
	public ItemStack slotClick(int slotID, int buttonPressed, int flag, EntityPlayer player) {
		if(slotID >= 0 && getSlot(slotID) != null && getSlot(slotID).getStack() == player.getHeldItem()) return null;
		return super.slotClick(slotID, buttonPressed, flag, player);
	}
}
