package tk.nukeduck.generation.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tk.nukeduck.generation.BlockProgram;
import tk.nukeduck.generation.ItemDisc;

public class ContainerProgrammer extends Container {
	protected final TileEntityProgrammer programmer;
	public final Slot discInput, discOutput;

	public ContainerProgrammer(IInventory player, TileEntityProgrammer programmer) {
		this.programmer = programmer;
		int i, j;

		this.discInput = new Slot(programmer, 0, 174, 11) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemDisc;
			}
		};
		this.discInput.setBackgroundIcon(ItemDisc.background);
		this.addSlotToContainer(this.discInput);

		this.addSlotToContainer(this.discOutput = new Slot(programmer, 1, 224, 11) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});

		for(i = 0; i < 3; ++i) {
			for(j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 148 + i * 18));
			}
		}

		for(i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 206));
		}
	}

	public void write() {
		BlockProgram program = this.programmer.program;
		ItemStack stack = this.discInput.getStack();
		ItemDisc disc = (ItemDisc) stack.getItem();

		disc.writeProgram(stack, program);
	}

	public boolean canWrite() {
		if(!this.discInput.getHasStack()) return false;

		ItemStack stack = this.discInput.getStack();
		ItemDisc disc = (ItemDisc) stack.getItem();
		if(disc == null || !(disc instanceof ItemDisc)) return false;

		return disc.hasSufficientSpace(stack, this.programmer.program.length);
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
