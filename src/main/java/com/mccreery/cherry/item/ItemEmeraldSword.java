package com.mccreery.cherry.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemEmeraldSword extends ItemSword {
	public ItemEmeraldSword(ToolMaterial material) {
		super(material);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List items) {
		ItemStack stack = new ItemStack(item);
		stack.addEnchantment(Enchantment.looting, 2);

		((List<ItemStack>)items).add(stack);
	}
}
