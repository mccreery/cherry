package com.sammccreery.cherry.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockSnowDoor extends BlockDoor {
	public BlockSnowDoor() {
		super(Material.craftedSnow);
		setStepSound(Block.soundTypeSnow);
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public String getItemIconName() {
		return getTextureName();
	}
}
