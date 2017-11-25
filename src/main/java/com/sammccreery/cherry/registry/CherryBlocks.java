package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.util.UniversalName;
import com.sammccreery.cherry.util.UniversalName.Format;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public final class CherryBlocks extends Registry<Block> {
	@Override
	public Block registerLocal(Block block, UniversalName name) {
		block.setBlockName(name.format(false, Format.HEADLESS));
		block.setBlockTextureName(name.format(true, Format.SNAKE));
		GameRegistry.registerBlock(block, name.format(false, Format.SNAKE));

		return block;
	}

	@Override
	public void init() {}
}
