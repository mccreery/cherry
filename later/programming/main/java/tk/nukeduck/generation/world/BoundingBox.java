package tk.nukeduck.generation.world;

public class BoundingBox {
	public static final BoundingBox EMPTY = new BoundingBox(0, 0, 0, 0, 0, 0);

	private int x, y, z, width, height, depth;

	public BoundingBox(int width, int height, int depth) {
		this(0, 0, 0, width, height, depth);
	}
	public BoundingBox(int x, int y, int z, int width, int height, int depth) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	@Override
	public BoundingBox clone() {
		return new BoundingBox(this.x, this.y, this.z, this.width, this.height, this.depth);
	}

	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public int getZ() {return this.z;}
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	public int getDepth() {return this.depth;}
	public int getX2() {return this.getX() + this.getWidth();}
	public int getY2() {return this.getY() + this.getHeight();}
	public int getZ2() {return this.getZ() + this.getDepth();}

	public BoundingBox setX(int x) {
		this.x = x;
		return this;
	}
	public BoundingBox setY(int y) {
		this.y = y;
		return this;
	}
	public BoundingBox setZ(int z) {
		this.z = z;
		return this;
	}
	public BoundingBox setWidth(int width) {
		this.width = width;
		return this;
	}
	public BoundingBox setHeight(int height) {
		this.height = height;
		return this;
	}
	public BoundingBox setDepth(int depth) {
		this.depth = depth;
		return this;
	}
	public BoundingBox setX2(int x2) {
		this.setWidth(x2 - this.getX());
		return this;
	}
	public BoundingBox setY2(int y2) {
		this.setHeight(y2 - this.getY());
		return this;
	}
	public BoundingBox setZ2(int z2) {
		this.setDepth(z2 - this.getZ());
		return this;
	}

	/** Asserts that each second coordinate is greater than each first,
	 * by correcting any axes that are in the wrong order. */
	private void correctDirections() {
		if(this.getX() > this.getX2()) {
			int temp = this.getX();
			this.setX(this.getX2());
			this.setX2(temp);
		}
		if(this.getY() > this.getY2()) {
			int temp = this.getY();
			this.setY(this.getY2());
			this.setY2(temp);
		}
		if(this.getZ() > this.getZ2()) {
			int temp = this.getZ();
			this.setZ(this.getZ2());
			this.setZ2(temp);
		}
	}

	public BoundingBox offset(int x, int y, int z) {
		return new BoundingBox(this.getX() + x, this.getY() + y, this.getZ() + z, this.getWidth(), this.getHeight(), this.getDepth());
	}
}
