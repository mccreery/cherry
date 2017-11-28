package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.block.Block;
import com.sammccreery.cherry.util.UniversalName;
import com.sammccreery.cherry.util.UniversalName.Format;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public final class CherryBlocks extends Registry<net.minecraft.block.Block> {
	@Override
	public net.minecraft.block.Block registerLocal(net.minecraft.block.Block block, UniversalName name) {
		block.setBlockName(name.format(false, Format.HEADLESS));
		block.setBlockTextureName(name.format(true, Format.SNAKE));
		GameRegistry.registerBlock(block, name.format(false, Format.SNAKE));

		return block;
	}

	public static final Block wideBrick = new Block(Material.rock);
	public static final Block fancyBrick = new Block(Material.rock);

	@Override
	public void init() {
		wideBrick.setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypePiston).setCreativeTab(CreativeTabs.tabBlock);
		registerLocal(wideBrick, new UniversalName("wide_brick"));

		fancyBrick.setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypePiston).setCreativeTab(CreativeTabs.tabBlock);
		registerLocal(fancyBrick, new UniversalName("fancy_brick"));
	}
}
