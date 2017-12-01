package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.block.BlockHeartCrystal;
import com.sammccreery.cherry.block.BlockLantern;
import com.sammccreery.cherry.block.CBlock;
import com.sammccreery.cherry.item.ItemHeartCrystal;
import com.sammccreery.cherry.item.ItemHeartLantern;
import com.sammccreery.cherry.util.Name;
import com.sammccreery.cherry.util.Name.Format;
import com.sammccreery.cherry.util.Names;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public final class CherryBlocks extends Registry<Block> {
	@Override
	public Block registerLocal(Block block, Name name) {
		applyNames(block, name);
		GameRegistry.registerBlock(block, name.format(Format.SNAKE, false));
		return block;
	}

	public static Block applyNames(Block block, Name name) {
		block.setBlockName(name.format(Format.HEADLESS, false));
		block.setBlockTextureName(name.format(Format.SNAKE, true));
		return block;
	}

	public static final Block wideBrick = new CBlock(Material.rock);
	public static final Block fancyBrick = new CBlock(Material.rock);
	public static final Block heartCrystal = new BlockHeartCrystal();
	public static final Block heartLantern = new BlockLantern();

	@Override
	public void init() {
		wideBrick.setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypePiston).setCreativeTab(CreativeTabs.tabBlock);
		registerLocal(wideBrick, Names.WIDE_BRICK);
		fancyBrick.setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypePiston).setCreativeTab(CreativeTabs.tabBlock);
		registerLocal(fancyBrick, Names.FANCY_BRICK);

		// Special ItemBlocks
		applyNames(heartLantern, Names.HEART_LANTERN);
		GameRegistry.registerBlock(heartLantern, ItemHeartLantern.class, Names.HEART_LANTERN.format(Format.SNAKE, false));

		applyNames(heartCrystal, Names.HEART_CRYSTAL);
		GameRegistry.registerBlock(heartCrystal, ItemHeartCrystal.class, Names.HEART_CRYSTAL.format(Format.SNAKE, false));
	}
}
