package tk.nukeduck.hearts.registry;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class HeartsCrafting {
	public static void init() {
		GameRegistry.addShapelessRecipe(new ItemStack(HeartsItems.shard, 3), HeartsBlocks.crystal);
		GameRegistry.addRecipe(new ItemStack(HeartsBlocks.lantern), new Object[] {
			"GCG", "GHG", "GGG", 'G', Blocks.glass, 'C', Blocks.hardened_clay, 'H', HeartsBlocks.crystal
		});
		GameRegistry.addRecipe(new ItemStack(HeartsBlocks.lantern), new Object[] {
			"GCG", "GHG", "GGG", 'G', Blocks.glass, 'C', Blocks.stained_hardened_clay, 'H', HeartsBlocks.crystal
		});
	}
}
