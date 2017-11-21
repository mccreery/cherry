package tk.nukeduck.generation.util;

public class BlockPos {
	public int x, y, z;

	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockPos increaseX() {return this.increaseX(1);}
	public BlockPos increaseX(int n) {
		return new BlockPos(this.x + n, this.y, this.z);
	}

	public BlockPos decreaseX() {return this.decreaseX(1);}
	public BlockPos decreaseX(int n) {
		return new BlockPos(this.x - n, this.y, this.z);
	}

	public BlockPos increaseY() {return this.increaseY(1);}
	public BlockPos increaseY(int n) {
		return new BlockPos(this.x, this.y + n, this.z);
	}

	public BlockPos decreaseY() {return this.decreaseY(1);}
	public BlockPos decreaseY(int n) {
		return new BlockPos(this.x, this.y - n, this.z);
	}

	public BlockPos increaseZ() {return this.increaseZ(1);}
	public BlockPos increaseZ(int n) {
		return new BlockPos(this.x, this.y, this.z + n);
	}

	public BlockPos decreaseZ() {return this.decreaseZ(1);}
	public BlockPos decreaseZ(int n) {
		return new BlockPos(this.x, this.y, this.z - n);
	}

	@Override
	public boolean equals(Object object) {
		if(!(object instanceof BlockPos)) return false;
		BlockPos pos = (BlockPos) object;
		return this.x == pos.x && this.y == pos.y && this.z == pos.z;
	}
}
