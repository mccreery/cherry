package tk.nukeduck.generation.world;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class IndexedItemStack {
	private int index;
	private ItemStack stack;

	public IndexedItemStack(int index) {
		this(index, null);
	}
	public IndexedItemStack(ItemStack stack) {
		this(0, stack);
	}
	public IndexedItemStack(int index, ItemStack stack) {
		this.index = index;
		this.stack = stack;
	}

	public IndexedItemStack setIndex(int index) {
		this.index = index;
		return this;
	}
	public IndexedItemStack setStack(ItemStack stack) {
		this.stack = stack;
		return this;
	}

	public int getIndex() {
		return this.index;
	}
	public ItemStack getStack() {
		return this.stack;
	}
	public int getItemCount() {
		return this.getStack().stackSize;
	}
	public int getItemMeta() {
		return this.getStack().getItemDamage();
	}
	public NBTTagCompound getItemTag() {
		return this.getStack().getTagCompound();
	}
}
