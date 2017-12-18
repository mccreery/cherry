package tk.nukeduck.generation.block;

import tk.nukeduck.generation.ItemDisc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ContainerProgrammer extends Container {
	protected final TileEntityProgrammer programmer;
	private final Slot discInput, discOutput;

	public ContainerProgrammer(IInventory player, TileEntityProgrammer programmer) {
		this.programmer = programmer;
		int i, j;

		this.discInput = new Slot(programmer, 0, 177, 8) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemDisc;
			}
		};
		this.discInput.setBackgroundIcon(ItemDisc.background);
		this.addSlotToContainer(this.discInput);
		this.addSlotToContainer(this.discOutput = new Slot(programmer, 1, 225, 8) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});

		for(i = 0; i < 3; ++i) {
			for(j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 137 + i * 18));
			}
		}

		for(i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 195));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotIndex);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(slotIndex < 2) {
				if(!this.mergeItemStack(itemstack1, 2, 38, true)) {
					return null;
				}
			} else/* if(!this.mergeItemStack(itemstack1, 0, 2, false))*/ {
				//return null;
				if(this.discInput.isItemValid(itemstack1)) {
					if(!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else {
					if(slotIndex < 29) {
						if(!this.mergeItemStack(itemstack1, 29, 38, false)) {
							return null;
						}
					} else {
						if(!this.mergeItemStack(itemstack1, 2, 29, false)) {
							return null;
						}
					}
				}
			}

			if(itemstack1.stackSize == 0) {
				slot.putStack((ItemStack)null);
			} else {
				slot.onSlotChanged();
			}

			if(itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}
}
