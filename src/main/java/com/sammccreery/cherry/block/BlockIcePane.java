package com.sammccreery.cherry.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockIcePane extends BlockPane {
	public BlockIcePane() {
		super("", "", Material.ice, false);
	}

	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		blockIcon = Blocks.ice.getIcon(0, 0);
	}

	@Override
	public IIcon func_150097_e() {
		return getIcon(0, 0);
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}
}
