package tk.nukeduck.lightsaber.block.tileentity;

import tk.nukeduck.lightsaber.item.ItemEnergyCapsule;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnergyCapsule extends Slot {
	public SlotEnergyCapsule(IInventory playerInventory, int id, int xPos, int yPos) {
		super(playerInventory, id, xPos, yPos);
	}
	
	/** {@inheritDoc}<br/>Energy capsule slot only allows an {@code ItemEnergyCapsule}.
	 * @return {@code true} if the {@code ItemStack} given is an instane of {@code ItemEnergyCapsule}. */
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemEnergyCapsule;
	}
}