package com.sammccreery.cherry.item;

import java.util.List;

import com.sammccreery.cherry.util.StackUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemEmeraldAxe extends ItemAxe {
	public ItemEmeraldAxe(ToolMaterial material) {
		super(material);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List items) {
		items.add(StackUtil.enchant(new ItemStack(item), Enchantment.fortune, 2));
	}
}
