package com.sammccreery.cherry.item;

import java.util.List;

import com.sammccreery.cherry.util.Funcs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemEmeraldShovel extends ItemSpade {
	public ItemEmeraldShovel(ToolMaterial material) {
		super(material);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List items) {
		items.add(Funcs.enchant(new ItemStack(item), Enchantment.fortune, 2));
	}
}
