package tk.nukeduck.generation.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FuncUtils {
	public static void addEnchantment(ItemStack stack, Enchantment enchantment, short level) {
		if(stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}

		if(!stack.stackTagCompound.hasKey("ench", 9)) {
			stack.stackTagCompound.setTag("ench", new NBTTagList());
		}

		NBTTagList nbttaglist = stack.stackTagCompound.getTagList("ench", 10);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setShort("id", (short) enchantment.effectId);
		nbttagcompound.setShort("lvl", level);
		nbttaglist.appendTag(nbttagcompound);
	}
}
