package tk.nukeduck.lightsaber.item;

import java.util.List;

import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemChargeable extends Item {
	public ItemChargeable(int max) {
		this();
		this.setMaxChargeLevel(max);
	}
	
	public ItemChargeable() {
		super();
		this.setNoRepair();
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
		if(itemStack.getItemDamage() <= 0) itemStack.setItemDamage(1);
	}
	
	/**
	 * Sets the maximum level this item can be charged to.
	 * @param max Maximum charge level.
	 * @return This item.
	 */
	public Item setMaxChargeLevel(int max) {
		return this.setMaxDamage(max + 1);
	}
	
	/** @return The maximum level this item can be charged to. */
	public int getMaxChargeLevel() {
		return this.getMaxDamage() - 1;
	}
	
	/**
	 * @return Whether or not the given {@link ItemStack} has no charge.
	 * @param itemStack The itemstack to check.
	 */
	public boolean isEmpty(ItemStack itemStack) {
		return itemStack.getItemDamage() >= this.getMaxDamage();
	}
	
	/**
	 * @return Whether or not the given {@link ItemStack} has full charge.
	 * @param itemStack The itemstack to check.
	 */
	public boolean isFull(ItemStack itemStack) {
		return itemStack.getItemDamage() <= 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advancedInfo) {
		list.add((this.getMaxDamage() - itemStack.getItemDamage()) + "/" + this.getMaxChargeLevel() + Strings.translate(Strings.ENERGY_SYMBOL));
	}
	
	/**
	 * @return The full/empty prefix applicable to the given {@link ItemStack}. If the stack is neither empty nor full, an empty string is returned.
	 * @param itemStack The itemstack to check.
	 */
	public String getItemNamePrefix(ItemStack itemStack) {
		return itemStack.getItemDamage() == this.getMaxDamage()
			? Strings.translate(Strings.EMPTY) + Strings.SPACE
			: itemStack.getItemDamage() == 1
				? Strings.translate(Strings.FULL) + Strings.SPACE
				: "";
	}
}