package tk.nukeduck.generation.world.loot;

import java.util.Random;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tk.nukeduck.generation.util.BlockPos;

public interface ILootGenerator {
	/** Generates an array of item stacks to be added to the given inventory.
	 * @param random A random number generator instance to use in generation
	 * @return An array of item stacks which have been generated */
	public ItemStack[] generate(World world, BlockPos pos, IInventory container, Random random);

	/** @return {@code true} if the given inventory is valid for this generator */
	public boolean isValidContainer(IInventory container);
}
