package tk.nukeduck.magma;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBN extends Block {
	public BlockBN(Material mat) {
		super(mat);
		this.setCreativeTab(CreativeTabs.tabBlock);
		// This class only exists because the Block(Material) constructor is protected
	}
}