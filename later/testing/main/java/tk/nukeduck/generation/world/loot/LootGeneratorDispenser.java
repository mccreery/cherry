package tk.nukeduck.generation.world.loot;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.world.World;
import tk.nukeduck.generation.util.BlockPos;
import tk.nukeduck.generation.util.WeightedRandom;

public class LootGeneratorDispenser implements ILootGenerator {
	public LootGeneratorDispenser() {
		this.itemRandomiser = new WeightedRandom<Item>()
			.set(Items.arrow, 1.0F)
			.set(Items.fire_charge, 0.1F);
	}

	public WeightedRandom<Item> itemRandomiser;

	@Override
	public ItemStack[] generate(World world, BlockPos pos, IInventory inventory, Random random) {
		ItemStack[] stacks = new ItemStack[random.nextInt(9)];
		for(int i = 0; i < stacks.length; i++) {
			stacks[i] = new ItemStack(this.itemRandomiser.next(random), 1 + random.nextInt(64), 0);
		}
		return stacks;
	}

	@Override
	public boolean isValidContainer(IInventory container) {
		return container instanceof TileEntityDispenser;
	}
}
