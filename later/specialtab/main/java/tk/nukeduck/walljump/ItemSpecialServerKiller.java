package tk.nukeduck.walljump;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class ItemSpecialServerKiller extends ItemSpecial {
	public ItemSpecialServerKiller(Item item) {
		super(item);
	}
	
	@Override
	public ItemStack item() {
		ItemStack serverKiller = new ItemStack(this.baseItem);
		serverKiller.setStackDisplayName(ChatFormatting.RESET + "" + ChatFormatting.BOLD + "" + ChatFormatting.DARK_RED + "/!\\ Server Killer /!\\");
		addEnchantment(serverKiller, Enchantment.looting, 32767);
		addEnchantment(serverKiller, Enchantment.sharpness, 32767);
		
		NBTTagCompound nbt10 = serverKiller.getTagCompound();
		NBTTagCompound lore = nbt10.getCompoundTag("display");
		
		NBTTagList loreList = new NBTTagList();
		loreList.appendTag(new NBTTagString(ChatFormatting.RESET + "" + ChatFormatting.RED + "Kill a mob with this to"));
		loreList.appendTag(new NBTTagString(ChatFormatting.RESET + "" + ChatFormatting.RED + "destroy the server with lag."));
		lore.setTag("Lore", loreList);
		nbt10.setTag("display", lore);
		serverKiller.setTagCompound(nbt10);
		
		return serverKiller;
	}
}