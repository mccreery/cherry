package tk.nukeduck.generation.client;

import net.minecraft.util.IIcon;

public class SimpleIcon implements IIcon {
	private int iconIndex;
	float minU, minV, maxU, maxV;

	protected static final float F = 0.0625F;

	public SimpleIcon(int iconIndex) {
		this.iconIndex = iconIndex;
		this.minU = F * (iconIndex % 16);
		this.minV = F * (iconIndex / 16);
		this.maxU = this.minU + F;
		this.maxV = this.minV + F;
	}

	@Override
	public int getIconWidth() {
		return 16;
	}
	@Override
	public int getIconHeight() {
		return 16;
	}

	@Override
	public float getMinU() {
		return this.minU;
	}
	@Override
	public float getMinV() {
		return this.minV;
	}

	@Override
	public float getMaxU() {
		return this.maxU;
	}
	@Override
	public float getMaxV() {
		return this.maxV;
	}

	@Override
	public float getInterpolatedU(double fac) {
		return this.getMinU() + F * (float) (fac / 16.0);
	}
	@Override
	public float getInterpolatedV(double fac) {
		return this.getMinV() + F * (float) (fac / 16.0);
	}

	@Override
	public String getIconName() {
		return "_" + this.iconIndex;
	}
	
}
