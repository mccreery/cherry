package tk.nukeduck.generation.world.loot;

import java.util.Random;

import net.minecraft.item.ItemStack;
import tk.nukeduck.generation.world.loot.parse.generator.GeneratorStatic;
import tk.nukeduck.generation.world.loot.parse.generator.IGenerator;

public class RangedItemStack {
	private ItemStack stack;
	private IGenerator sizeGenerator;

	public RangedItemStack(ItemStack stack) {
		this(stack, new GeneratorStatic(new Double(stack.stackSize)));
	}
	public RangedItemStack(ItemStack stack, IGenerator sizeGenerator) {
		this.stack = stack;
		this.sizeGenerator = sizeGenerator;
	}

	public ItemStack get(Random random) {
		ItemStack stack = this.stack.copy();
		stack.stackSize = this.sizeGenerator.next(random);
		return stack;
	}
}
