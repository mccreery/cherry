package tk.nukeduck.walljump;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSpecialGodPick extends ItemSpecial {
	public ItemSpecialGodPick() {
		super(Items.diamond_pickaxe);
	}
	
	@Override
	public ItemStack item() {
		return setUnbreakable(addEnchantment(new ItemStack(this.baseItem), Enchantment.efficiency, 32767), true)
			.setStackDisplayName(ChatFormatting.RESET + "God Pickaxe");
	}
}