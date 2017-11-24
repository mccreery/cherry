package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.util.ResourceName;
import com.sammccreery.cherry.util.ResourceName.Format;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public final class CherryBlocks {
	private CherryBlocks() {}

	public static Block registerBlock(Block block, ResourceName name) {
		block.setBlockName(name.format(false, Format.HEADLESS));
		block.setBlockTextureName(name.format(true, Format.SNAKE));
		GameRegistry.registerBlock(block, name.format(false, Format.SNAKE));

		return block;
	}
}
