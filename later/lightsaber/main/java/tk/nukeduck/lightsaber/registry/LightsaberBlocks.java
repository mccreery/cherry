package tk.nukeduck.lightsaber.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tk.nukeduck.lightsaber.block.BlockCrystal;
import tk.nukeduck.lightsaber.block.BlockRefillUnit;
import tk.nukeduck.lightsaber.item.ItemBlockCrystal;
import tk.nukeduck.lightsaber.item.ItemBlockRefillUnit;
import cpw.mods.fml.common.registry.GameRegistry;

public class LightsaberBlocks {
	public static Block charger, crystal;
	
	/** Registers all the blocks in the mod. */
	public static void registerBlocks() {
		charger = new BlockRefillUnit(Material.rock).setBlockName("refillUnit");
		GameRegistry.registerBlock(charger, ItemBlockRefillUnit.class, "refill_unit");
		crystal = new BlockCrystal().setBlockName("crystal");
		GameRegistry.registerBlock(crystal, ItemBlockCrystal.class, "crystal");
	}
}