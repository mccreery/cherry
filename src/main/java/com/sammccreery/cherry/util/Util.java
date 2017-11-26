package com.sammccreery.cherry.util;

import java.text.DecimalFormat;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Util {
	public static final Random RANDOM = new Random();

	public static void teleportEffect(World world, double x, double y, double z, int count, float volume) {
		//if(!world.isRemote) return;
		System.out.println(world.isRemote ? "CLIENT" : "SERVER");

		for(int i = 0; i < count; i++) {
			double dx = (RANDOM.nextDouble() - 0.5D) * 0.5D;
			double dz = RANDOM.nextBoolean() ? 0.25D : -0.25D;

			if(RANDOM.nextBoolean()) {
				double temp = dx;
				dx = dz;
				dz = temp;
			}
			world.spawnParticle("portal", x + dx, y, z + dz,
				RANDOM.nextDouble() - 0.5D, RANDOM.nextDouble() - 0.5D, RANDOM.nextDouble() - 0.5D);
		}

		world.playSound(x, y + 0.5D, z, "mob.endermen.portal", 1.0F, 1.0F, true);
	}

	/** @return A string representation of {@code x} with the given number of {@code places} */
	public static String toPlaces(double x, int places) {
		DecimalFormat format = new DecimalFormat();
		format.setMaximumFractionDigits(places);
		return format.format(x);
	}

	/** A version of {@link ItemStack#addEnchantment(net.minecraft.enchantment.Enchantment, int)}
	 * which returns the stack */
	public static ItemStack enchant(ItemStack stack, Enchantment enchantment, int level) {
		stack.addEnchantment(enchantment, level);
		return stack;
	}
}
