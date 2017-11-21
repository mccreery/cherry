package tk.nukeduck.generation.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;

public class FuncUtils {
	public static final NBTTagCompound enforceTag(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		return stack.getTagCompound();
	}

	public static final void addEnchantment(ItemStack stack, Enchantment effect, short level) {
		NBTTagCompound tag = enforceTag(stack);

		if(!tag.hasKey("ench", 9)) {
			tag.setTag("ench", new NBTTagList());
		}
		NBTTagList enchantments = tag.getTagList("ench", 10);

		NBTTagCompound enchantment = new NBTTagCompound();
		enchantment.setShort("id", (short) effect.effectId);
		enchantment.setShort("lvl", level);
		enchantments.appendTag(enchantment);
	}

	public static short pack(BlockCategory category, int id) {
		return (short) (category.id << 8 | id & 0xFF);
	}
}
