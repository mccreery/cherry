package com.sammccreery.cherry.util;

import java.text.DecimalFormat;
import java.util.Random;

import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

	public static float distributeSmooth(float mode, float spread, float x) {
		// Inverted smoothstep
		if(x < 0) x = 0;
		else if(x > 1) x = 1;

		x = x*(x*(2*x - 3) + 2);
		return (mode - spread) + 2*spread * x;
	}

	/** @return {@code true} if the liquid at the specified position should not spread horizontally */
	public static boolean disableSpread(BlockDynamicLiquid block, World world, int x, int y, int z) {
		return block == Blocks.flowing_water && world.getBlock(x, y - 1, z) == Blocks.sponge;
	}

	/** @return {@code true} if using the player's held item should take priority
	 * over passing the click through to the pointed block.<br>
	 * This usually means the player is sneaking */
	public static boolean isUseForced(EntityPlayer player, World world, int x, int y, int z) {
		return player.isSneaking()
			&& player.getHeldItem() != null
			&& !player.getHeldItem().getItem().doesSneakBypassUse(world, x, y, z, player);
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
