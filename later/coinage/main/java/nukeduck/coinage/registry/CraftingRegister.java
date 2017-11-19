package nukeduck.coinage.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nukeduck.coinage.Coinage;

public class CraftingRegister implements IRegister {
	@Override
	public void init() {
		for(Item leather : new Item[] {Items.leather, Items.rabbit_hide}) {
			GameRegistry.addRecipe(new ItemStack(Coinage.instance.itemRegister.coinBag, 1, 0), new Object[] {
				"L#L", "L L", "SCS", 'L', leather, '#', Items.string, 'S', Items.stick, 'C', new ItemStack(Blocks.carpet, 1, 0)
			});
			GameRegistry.addRecipe(new ItemStack(Coinage.instance.itemRegister.coinBagLockable, 1, 0), new Object[] {
				"L#L", "LPL", "ICI", 'L', leather, '#', Items.string, 'P', Coinage.instance.itemRegister.ironPadlock, 'I', Items.iron_ingot, 'C', new ItemStack(Blocks.carpet, 1, 0)
			});
		}
	}
}
