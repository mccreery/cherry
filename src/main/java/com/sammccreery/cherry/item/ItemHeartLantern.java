package com.sammccreery.cherry.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class ItemHeartLantern extends ItemBlock {
	public ItemHeartLantern(Block block) {
		super(block);
		setMaxStackSize(16);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
}
