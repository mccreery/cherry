package tk.nukeduck.generation.world.loot;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import tk.nukeduck.generation.util.BlockPos;
import tk.nukeduck.generation.util.WeightedRandom;
import tk.nukeduck.generation.world.loot.parse.generator.GeneratorRange;

public class LootGeneratorDungeon implements ILootGenerator {
	private WeightedRandom<RangedItemStack> stacks;

	public LootGeneratorDungeon(Random random) {
		this.stacks = new WeightedRandom<RangedItemStack>() {
			public WeightedRandom set(ItemStack stack, float weight) {
				return set(new RangedItemStack(stack), weight);
			}
		}
			.set(new RangedItemStack(new ItemStack(Items.gunpowder), new GeneratorRange(1.0, 4.0)), 10)
			.set(new RangedItemStack(new ItemStack(Items.redstone), new GeneratorRange(1.0, 4.0)), 10)
			.set(new RangedItemStack(new ItemStack(Items.string), new GeneratorRange(1.0, 4.0)), 10)
			.set(new RangedItemStack(new ItemStack(Items.wheat), new GeneratorRange(1.0, 4.0)), 10)
			.set(new RangedItemStack(new ItemStack(Items.iron_ingot), new GeneratorRange(1.0, 4.0)), 10)
			.set(new RangedItemStack(new ItemStack(Items.bread)), 10)
			.set(new RangedItemStack(new ItemStack(Items.bucket)), 10)
			.set(new RangedItemStack(new ItemStack(Items.name_tag)), 10)
			.set(new RangedItemStack(new ItemStack(Items.saddle)), 10)
			.set(new RangedItemStack(new ItemStack(Items.iron_horse_armor)), 5)
			.set(new RangedItemStack(new ItemStack(Items.record_13)), 4)
			.set(new RangedItemStack(new ItemStack(Items.record_cat)), 4)
			.set(new RangedItemStack(new ItemStack(Items.golden_horse_armor)), 2)
			.set(new RangedItemStack(new ItemStack(Items.enchanted_book)) {
				@Override
				public ItemStack get(Random random) {
					ItemStack stack = super.get(random);
					stack.addEnchantment(Enchantment.efficiency, 5);
					return stack;
				}
			}, 1)
			.set(new RangedItemStack(new ItemStack(Items.diamond_horse_armor)), 1);
	}

	@Override
	public ItemStack[] generate(World world, BlockPos pos, IInventory inventory, Random random) {
		ItemStack[] loot = new ItemStack[8];
		for(int i = 0; i < loot.length; i++) {
			loot[i] = stacks.next(random).get(random);
		}
		return loot;
	}

	@Override
	public boolean isValidContainer(IInventory container) {
		return container instanceof TileEntityChest;
	}
}
