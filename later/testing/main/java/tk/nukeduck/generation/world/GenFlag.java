package tk.nukeduck.generation.world;

import net.minecraftforge.common.util.ForgeDirection;

public enum GenFlag {
	HOLLOW,
	HOLLOW_ENDS,
	FACE_XNEG(-90, 0, 0),
	FACE_XPOS(90, 0, 0),
	FACE_YNEG(180, 0, 0),
	FACE_YPOS(0, 0, 0),
	FACE_ZNEG(0, 0, -90),
	FACE_ZPOS(0, 0, 90);

	private double rotX = 0, rotY = 0, rotZ = 0;

	private GenFlag() {}
	private GenFlag(double rotX, double rotY, double rotZ) {
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

	public double getRotX() {return this.rotX;}
	public double getRotY() {return this.rotY;}
	public double getRotZ() {return this.rotZ;}

	public int getFlag() {
		return 1 << this.ordinal();
	}

	public static int getInt(GenFlag... flags) {
		int x = 0;
		for(GenFlag flag : flags) {
			x |= flag.getFlag();
		}
		return x;
	}

	public static boolean has(int flags, GenFlag flag) {
		int bit = flag.getFlag();
		return (flags & bit) == bit;
	}

	public static GenFlag getDirection(ForgeDirection direction) {
		switch(direction) {
		case WEST:
			return FACE_XNEG;
		case EAST:
			return FACE_XPOS;
		case DOWN:
			return FACE_YNEG;
		case UP:
			return FACE_YPOS;
		case NORTH:
			return FACE_ZNEG;
		case SOUTH:
			return FACE_ZPOS;
		default:
			return FACE_YPOS;
		}
	}
}
