package nukeduck.coinage.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.sun.org.apache.xml.internal.security.utils.I18n;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemCoinBagLockable extends ItemCoinBag {
	public ItemCoinBagLockable() {
		super();
	}
	
	@Override
	protected boolean isLockable() {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		if(this.isLocked(stack)) tooltip.add(StatCollector.translateToLocalFormatted("tooltip.locked", stack.getTagCompound().getCompoundTag("PlayerLock").getString("DisplayName")));
		tooltip.add(ChatFormatting.ITALIC + "" + ChatFormatting.DARK_GRAY + StatCollector.translateToLocal(this.isLocked(stack) ? "tooltip.unlock" : "tooltip.lock"));
	}
}
