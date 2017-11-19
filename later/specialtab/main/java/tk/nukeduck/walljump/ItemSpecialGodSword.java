package tk.nukeduck.walljump;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSpecialGodSword extends ItemSpecial {
	public ItemSpecialGodSword() {
		super(Items.diamond_sword);
	}
	
	@Override
	public ItemStack item() {
		ItemStack sword = addEnchantment(new ItemStack(this.baseItem), Enchantment.sharpness, 32767)
			.setStackDisplayName(ChatFormatting.RESET + "God Sword");
		NBTTagCompound nbt = sword.getTagCompound();
		nbt.setByte("Unbreakable", (byte) 1);
		return sword;
	}
}