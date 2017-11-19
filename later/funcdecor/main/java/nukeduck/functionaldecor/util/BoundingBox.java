package nukeduck.functionaldecor.util;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;

public final class BoundingBox {
	public static final BoundingBox EMPTY = new BoundingBox(0, 0, 0);
	public static final BoundingBox ONES = new BoundingBox(1, 1, 1);

	public float xMin, yMin, zMin, xMax, yMax, zMax;

	public BoundingBox(Block block) {
		this((float) block.getBlockBoundsMinX(), (float) block.getBlockBoundsMinY(), (float) block.getBlockBoundsMinZ(),
				(float) block.getBlockBoundsMaxX(), (float) block.getBlockBoundsMaxY(), (float) block.getBlockBoundsMaxZ());
	}
	public BoundingBox(float width, float height, float depth) {
		this(0, 0, 0, width, height, depth);
	}
	public BoundingBox(float xMin, float yMin, float zMin, float xMax, float yMax, float zMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = zMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = zMax;
	}
	public BoundingBox(BoundingBox origin) {
		this(origin.xMin, origin.yMin, origin.zMin,
				origin.xMax, origin.yMax, origin.zMax);
	}

	public float getWidth() {return this.xMax - this.xMin;}
	public float getHeight() {return this.yMax - this.yMin;}
	public float getDepth() {return this.zMax - this.zMin;}
	public float getArea() {return this.getWidth() * this.getHeight() * this.getDepth();}

	public BoundingBox join(BoundingBox... bbs) {
		for(BoundingBox bb : bbs) {
			if(bb.xMin < this.xMin) this.xMin = bb.xMin;
			if(bb.yMin < this.yMin) this.yMin = bb.yMin;
			if(bb.zMin < this.zMin) this.zMin = bb.zMin;
			if(bb.xMax > this.xMax) this.xMax = bb.xMax;
			if(bb.yMax > this.yMax) this.yMax = bb.yMax;
			if(bb.zMax > this.zMax) this.zMax = bb.zMax;
		}
		return this;
	}
	public static final BoundingBox _join(BoundingBox... bbs) {
		BoundingBox out = new BoundingBox(bbs[0]);
		return out.join(bbs);
	}

	public BoundingBox clamp(BoundingBox bounds) {
		if(this.xMin < bounds.xMin) this.xMin = bounds.xMin;
		if(this.yMin < bounds.yMin) this.yMin = bounds.yMin;
		if(this.zMin < bounds.zMin) this.zMin = bounds.zMin;
		if(this.xMax > bounds.xMax) this.xMax = bounds.xMax;
		if(this.yMax > bounds.yMax) this.yMax = bounds.yMax;
		if(this.zMax > bounds.zMax) this.zMax = bounds.zMax;
		return this;
	}
	public static final BoundingBox clamp(BoundingBox base, BoundingBox bounds) {
		BoundingBox out = new BoundingBox(base);
		return out.clamp(bounds);
	}

	public BoundingBox scale(float fac) {
		return this.scale(0, 0, 0, fac);
	}
	public BoundingBox scale(float xCenter, float yCenter, float zCenter, float fac) {
		return this.scale(xCenter, yCenter, zCenter, fac, fac, fac);
	}
	public BoundingBox scale(float xCenter, float yCenter, float zCenter, float x, float y, float z) {
		this.translate(-xCenter, -yCenter, -zCenter);
		this.xMin *= x;
		this.yMin *= y;
		this.zMin *= z;
		this.xMax *= x;
		this.yMax *= y;
		this.zMax *= z;
		this.translate(xCenter, yCenter, zCenter);
		return this;
	}
	public static final BoundingBox scale(BoundingBox base, float fac) {
		return scale(base, 0, 0, 0, fac);
	}
	public static final BoundingBox scale(BoundingBox base, float xCenter, float yCenter, float zCenter, float fac) {
		return scale(base, xCenter, yCenter, zCenter, fac, fac, fac);
	}
	public static final BoundingBox scale(BoundingBox base, float xCenter, float yCenter, float zCenter, float x, float y, float z) {
		BoundingBox out = new BoundingBox(base);
		return out.scale(xCenter, yCenter, zCenter, x, y, z);
	}

	public BoundingBox rotateY(float xCenter, float zCenter, float angle) {
		angle = (float) Math.toRadians(angle);
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		float xTemp, zTemp, xMin, zMin, xMax, zMax;

		this.translate(-xCenter, 0, -zCenter);

		xTemp = cos * this.xMin - sin * this.zMin;
		zTemp = sin * this.xMin + cos * this.zMin;
		xMin = xMax = xTemp;
		zMin = zMax = zTemp;

		xTemp = cos * this.xMin - sin * this.zMax;
		zTemp = sin * this.xMin + cos * this.zMax;
		if(xTemp < xMin) xMin = xTemp;
		if(zTemp < zMin) zMin = zTemp;
		if(xTemp > xMax) xMax = xTemp;
		if(zTemp > zMax) zMax = zTemp;

		xTemp = cos * this.xMax - sin * this.zMax;
		zTemp = sin * this.xMax + cos * this.zMax;
		if(xTemp < xMin) xMin = xTemp;
		if(zTemp < zMin) zMin = zTemp;
		if(xTemp > xMax) xMax = xTemp;
		if(zTemp > zMax) zMax = zTemp;

		xTemp = cos * this.xMax - sin * this.zMin;
		zTemp = sin * this.xMax + cos * this.zMin;
		if(xTemp < xMin) xMin = xTemp;
		if(zTemp < zMin) zMin = zTemp;
		if(xTemp > xMax) xMax = xTemp;
		if(zTemp > zMax) zMax = zTemp;

		this.xMin = xMin;
		this.zMin = zMin;
		this.xMax = xMax;
		this.zMax = zMax;

		this.translate(xCenter, 0, zCenter);
		return this;
	}
	public static final BoundingBox rotateY(BoundingBox base, float xCenter, float zCenter, float angle) {
		BoundingBox out = new BoundingBox(base);
		return out.rotateY(xCenter, zCenter, angle);
	}

	public BoundingBox translate(float x, float y, float z) {
		this.xMin += x;
		this.yMin += y;
		this.zMin += z;
		this.xMax += x;
		this.yMax += y;
		this.zMax += z;
		return this;
	}
	public static final BoundingBox translate(BoundingBox base, float x, float y, float z) {
		BoundingBox out = new BoundingBox(base);
		return base.translate(x, y, z);
	}

	public void applyTo(Block block) {
		block.setBlockBounds(this.xMin, this.yMin, this.zMin,
				this.xMax, this.yMax, this.zMax);
	}

	public AxisAlignedBB toAABB() {
		return AxisAlignedBB.getBoundingBox(this.xMin, this.yMin, this.zMin, this.xMax, this.yMax, this.zMax);
	}
}
