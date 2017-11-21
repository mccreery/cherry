package tk.nukeduck.generation.util;

public class ColorUtils {
	public static final int SHADOW = 0x33000000;

	public static final int packColor(int r, int g, int b) {
		return packColor(255, r, g, b);
	}
	public static final int packColor(int a, int r, int g, int b) {
		return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
	}
	public static final int addColor(int color, int fac) {
		int out = 0;

		for(int i = 24; i >= 0; i -= 8) {
			int component = (color >> i) & 0xFF; // Grab the component
			component += fac;
			out |= (component > 255 ? 255 : component) << i;
		}
		return out;
	}
}
