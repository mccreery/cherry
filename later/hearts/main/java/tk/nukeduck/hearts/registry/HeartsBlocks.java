package tk.nukeduck.hearts.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import tk.nukeduck.hearts.block.BlockHeartCrystal;
import tk.nukeduck.hearts.block.BlockLantern;
import tk.nukeduck.hearts.block.ItemBlock16;
import tk.nukeduck.hearts.item.ItemHeartCrystal;
import cpw.mods.fml.common.registry.GameRegistry;

public class HeartsBlocks {
	public static BlockHeartCrystal crystal;
	public static BlockLantern lantern;

	public static final void init() {
		crystal = new BlockHeartCrystal();
		crystal.setBlockName("heartCrystal").setHardness(1.5F).setResistance(0.5F).setHarvestLevel("pickaxe", 2);
		crystal.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(crystal, ItemHeartCrystal.class, "heart_crystal");
		
		lantern = new BlockLantern(Material.glass);
		lantern.setBlockName("heartLantern").setHardness(0.2F).setResistance(0.2F).setStepSound(Block.soundTypeGlass).setHarvestLevel("pickaxe", 0);
		lantern.setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(lantern, ItemBlock16.class, "heart_lantern");
	}
}
