package com.sammccreery.cherry.util;

import java.text.DecimalFormat;

public final class MathUtil {
	private MathUtil() {}

	/** @return {@code x} formatted to {@code places} places */
	public static String formatPlaces(double x, int places) {
		DecimalFormat format = new DecimalFormat();
		format.setMaximumFractionDigits(places);
		return format.format(x);
	}

	/** @return A smoothly distributed value using an inverse smoothstep
	 * @param x The index parameter to the function, clamped to [0,1]
	 * @param mode The point where the function is densest (lowest gradient)
	 * @param spread The range of the function +- from {@code mode} */
	public static float distributeSmooth(float x, float mode, float spread) {
		x = x < 0 ? 0 : (x > 1 ? 1 : x); // Clamp x to [0,1]

		x = x*(x*(2*x - 3) + 2); // Inverse smoothstep y = 2x^3 - 3x^2 + 2x
		return (mode - spread) + 2*spread * x; // Denormalize
	}
}
