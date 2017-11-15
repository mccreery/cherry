package com.sammccreery.cherry.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemEmeraldHoe extends ItemHoe {
	public ItemEmeraldHoe(ToolMaterial material) {
		super(material);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List items) {
		ItemStack stack = new ItemStack(item);
		stack.addEnchantment(Enchantment.fortune, 2);

		((List<ItemStack>)items).add(stack);
	}
}
