package com.sammccreery.cherry.util;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Funcs {
	public static final Random RANDOM = new Random();

	public static void spawnParticlesAt(World world, double x, double y, double z, int amount, float volume) {
		if(world.isRemote) {
			for (int l = 0; l < amount; ++l) {
				//double d6 = (double)((float)x + RANDOM.nextFloat());
				double d1 = (double)((float)y + RANDOM.nextFloat());
				//d6 = (double)((float)z + RANDOM.nextFloat());
				double d3 = 0.0D;
				double d4 = 0.0D;
				double d5 = 0.0D;
				int i1 = RANDOM.nextInt(2) * 2 - 1;
				int j1 = RANDOM.nextInt(2) * 2 - 1;
				d3 = ((double)RANDOM.nextFloat() - 0.5D) * 0.125D;
				d4 = ((double)RANDOM.nextFloat() - 0.5D) * 0.125D;
				d5 = ((double)RANDOM.nextFloat() - 0.5D) * 0.125D;
				double d2 = (double)z + 0.5D + 0.25D * (double)j1;
				d5 = (double)(RANDOM.nextFloat() * 1.0F * (float)j1);
				double d0 = (double)x + 0.5D + 0.25D * (double)i1;
				d3 = (double)(RANDOM.nextFloat() * 1.0F * (float)i1);
				world.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
			}
		}
	}

	/** A version of {@link ItemStack#addEnchantment(net.minecraft.enchantment.Enchantment, int)}
	 * which returns the stack */
	public static ItemStack enchant(ItemStack stack, Enchantment enchantment, int level) {
		stack.addEnchantment(enchantment, level);
		return stack;
	}
}
