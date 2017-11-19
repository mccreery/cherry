package tk.nukeduck.walljump;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemSpecialFastBoots extends ItemSpecial {
	public ItemSpecialFastBoots() {
		super(Items.diamond_boots);
	}
	
	@Override
	public ItemStack item() {
		ItemStack fastBoots = new ItemStack(this.baseItem);
		addModifier(fastBoots, "generic.movementSpeed", "Speed Boost", 2, 0);
		fastBoots.setStackDisplayName(ChatFormatting.RESET + "Super-Speed Boots");
		return fastBoots;
	}
}