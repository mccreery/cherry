package tk.nukeduck.generation.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tk.nukeduck.generation.util.BlockInfo;
import tk.nukeduck.generation.util.BlockPos;
import tk.nukeduck.generation.world.loot.ILootGenerator;
import tk.nukeduck.generation.world.placer.IBlockPlacer;
import tk.nukeduck.generation.world.structure.IReplaceTest;

public class WorldUtils {
	/** Same as {@link #getReplaceFactor(World, BoundingBox, IReplaceTest)}, but {@link #REPLACE_TEST_DEFAULT} is used. */
	public static float getReplaceFactor(World world, BoundingBox boundingBox) {
		return getReplaceFactor(world, boundingBox, REPLACE_TEST_DEFAULT);
	}
	/** @param world The {@link World} instance to test on
	 * @param boundingBox A bounding box representing the area
	 * @return The fraction of the total area within this bounding box that is replaceable */
	public static float getReplaceFactor(World world, BoundingBox boundingBox, IReplaceTest replaceTest) {
		int replaceable = 0;
		for(int x = boundingBox.getX(); x < boundingBox.getX2(); x++) {
			for(int y = boundingBox.getY(); y < boundingBox.getY2(); y++) {
				for(int z = boundingBox.getZ(); z < boundingBox.getZ2(); z++) {
					if(replaceTest.canReplace(world, x, y, z)) replaceable++;
				}
			}
		}
		return (float) replaceable / (float) (boundingBox.getWidth() * boundingBox.getHeight() * boundingBox.getDepth());
	}

	public static final IReplaceTest REPLACE_TEST_DEFAULT = new IReplaceTest() {
		@Override
		public boolean canReplace(World world, int x, int y, int z) {
			return world.getBlock(x, y, z).isReplaceable(world, x, y, z);
		}
	};

	public static boolean hasFloor(World world, BoundingBox boundingBox, int maxDistance) {
		boolean isTouchingFloor = false;
		for(int x = boundingBox.getX(); x < boundingBox.getX2(); x++) {
			for(int z = boundingBox.getZ(); z < boundingBox.getZ2(); z++) {
				int y = boundingBox.getY();
				while(world.getBlock(x, --y, z).isReplaceable(world, x, y, z)) {
					if(y < boundingBox.getY() - maxDistance) {
						return false;
					}
				}
				if(y == boundingBox.getY() - 1) isTouchingFloor = true;
			}
		}
		return /*isTouchingFloor*/true;
	}

	public static void fillFloor(World world, BoundingBox boundingBox, IBlockPlacer placer, Random random, GenFlag... flags) {
		fillFloor(world, boundingBox, placer, random, GenFlag.getInt(flags));
	}
	public static void fillFloor(World world, BoundingBox boundingBox, IBlockPlacer placer, Random random, int flags) {
		if(GenFlag.has(flags, GenFlag.HOLLOW)) {
			for(int x = boundingBox.getX(); x < boundingBox.getX2(); x++) {
				for(int z = boundingBox.getZ(); z < boundingBox.getZ2(); z++) {
					placer.setBlock(world, x, boundingBox.getY() - 1, z, random);
				}
			}

			for(int x = boundingBox.getX(); x < boundingBox.getX2(); x++) {
				int y = boundingBox.getY() - 1;
				int z = boundingBox.getZ();
				while(world.getBlock(x, --y, z).isReplaceable(world, x, y, z)) {
					placer.setBlock(world, x, y, z, random);
				}
				y = boundingBox.getY() - 1;
				z += boundingBox.getDepth() - 1;
				while(world.getBlock(x, --y, z).isReplaceable(world, x, y, z)) {
					placer.setBlock(world, x, y, z, random);
				}
			}
			for(int z = boundingBox.getZ() + 1; z < boundingBox.getZ2() - 1; z++) {
				int x = boundingBox.getX();
				int y = boundingBox.getY() - 1;
				while(world.getBlock(x, --y, z).isReplaceable(world, x, y, z)) {
					placer.setBlock(world, x, y, z, random);
				}
				x += boundingBox.getWidth() - 1;
				y = boundingBox.getY() - 1;
				while(world.getBlock(x, --y, z).isReplaceable(world, x, y, z)) {
					placer.setBlock(world, x, y, z, random);
				}
			}
		} else {
			for(int x = boundingBox.getX(); x < boundingBox.getX2(); x++) {
				for(int z = boundingBox.getZ(); z < boundingBox.getZ2(); z++) {
					int y = boundingBox.getY();
					while(world.getBlock(x, --y, z).isReplaceable(world, x, y, z)) {
						placer.setBlock(world, x, y, z, random);
					}
				}
			}
		}
	}

	public static void fill(World world, BoundingBox boundingBox, IBlockPlacer placer, Random random, GenFlag... flags) {
		fill(world, boundingBox, placer, random, GenFlag.getInt(flags));
	}
	public static void fill(World world, BoundingBox boundingBox, IBlockPlacer placer, Random random, int flags) {
		if(GenFlag.has(flags, GenFlag.HOLLOW)) {
			for(int y = boundingBox.getY(); y < boundingBox.getY2(); y++) {
				for(int z = boundingBox.getZ(); z < boundingBox.getZ2(); z++) {
					placer.setBlock(world, boundingBox.getX(), y, z, random);
					placer.setBlock(world, boundingBox.getX2() - 1, y, z, random);
				}
			}
			if(!GenFlag.has(flags, GenFlag.HOLLOW_ENDS)) {
				for(int x = boundingBox.getX() + 1; x < boundingBox.getX2() - 1; x++) {
					for(int z = boundingBox.getZ() + 1; z < boundingBox.getZ2() - 1; z++) {
						placer.setBlock(world, x, boundingBox.getY(), z, random);
						placer.setBlock(world, x, boundingBox.getY2() - 1, z, random);
					}
				}
			}
			for(int x = boundingBox.getX() + 1; x < boundingBox.getX2() - 1; x++) {
				for(int y = boundingBox.getY(); y < boundingBox.getY2(); y++) {
					placer.setBlock(world, x, y, boundingBox.getZ(), random);
					placer.setBlock(world, x, y, boundingBox.getZ2() - 1, random);
				}
			}
		} else {
			for(int x = boundingBox.getX(); x < boundingBox.getX2(); x++) {
				for(int y = boundingBox.getY(); y < boundingBox.getY2(); y++) {
					for(int z = boundingBox.getZ(); z < boundingBox.getZ2(); z++) {
						placer.setBlock(world, x, y, z, random);
					}
				}
			}
		}
	}

	private static final HashMap<BlockPos, ArrayList<BlockPos>> ellipsoidCache = new HashMap<BlockPos, ArrayList<BlockPos>>(),
		ellipsoidCacheHollow = new HashMap<BlockPos, ArrayList<BlockPos>>();

	public static ArrayList<BlockPos> getCachedSphere(BlockPos size, boolean hollow) {
		if(hollow) {
			for(BlockPos pos : ellipsoidCacheHollow.keySet()) {
				if(pos.equals(size)) return ellipsoidCacheHollow.get(pos);
			}
		} else {
			for(BlockPos pos : ellipsoidCache.keySet()) {
				if(pos.equals(size)) return ellipsoidCache.get(pos);
			}
		}
		return null;
	}

	public static void fillEllipsoid(World world, BoundingBox boundingBox, IBlockPlacer placer, Random random, GenFlag... flags) {
		fillEllipsoid(world, boundingBox, placer, random, GenFlag.getInt(flags));
	}
	public static void fillEllipsoid(World world, BoundingBox boundingBox, IBlockPlacer placer, Random random, int flags) {
		boolean hollow = GenFlag.has(flags, GenFlag.HOLLOW);
		BlockPos size = new BlockPos(boundingBox.getWidth(), boundingBox.getHeight(), boundingBox.getDepth());
		ArrayList<BlockPos> toPlace = getCachedSphere(size, hollow);

		if(toPlace == null) {
			float radiusX = (float) boundingBox.getWidth() / 2.0F;
			float radiusY = (float) boundingBox.getHeight() / 2.0F;
			float radiusZ = (float) boundingBox.getDepth() / 2.0F;
			float radiusXS = radiusX * radiusX;
			float radiusYS = radiusY * radiusY;
			float radiusZS = radiusZ * radiusZ;

			radiusX -= 0.5F;
			radiusY -= 0.5F;
			radiusZ -= 0.5F;

			boolean[][][] temp = new boolean[boundingBox.getWidth()][boundingBox.getHeight()][boundingBox.getDepth()];

			for(int x = 0; x < temp.length; x++) {
				for(int y = 0; y < temp[0].length; y++) {
					for(int z = 0; z < temp[0][0].length; z++) {
						//System.out.println(String.format("X: %d Y: %d Z: %d", x, y, z));
						float distX = (float) x - radiusX;
						distX *= distX;
						float distY = (float) y - radiusY;
						distY *= distY;
						float distZ = (float) z - radiusZ;
						distZ *= distZ;

						if(distX / radiusXS + distY / radiusYS + distZ / radiusZS <= 1.0F) {
							temp[x][y][z] = true; //temp.add(new BlockPos(x, y, z));
						}
					}
				}
			}

			if(hollow) {
				toPlace = new ArrayList<BlockPos>();
				for(int x = 0; x < temp.length; x++) {
					for(int y = 0; y < temp[0].length; y++) {
						for(int z = 0; z < temp[0][0].length; z++) {
							if(isOnEdge(temp, x, y, z)) toPlace.add(new BlockPos(x, y, z));
						}
					}
				}
				ellipsoidCacheHollow.put(size, toPlace);
			} else {
				toPlace = new ArrayList<BlockPos>();
				for(int x = 0; x < temp.length; x++) {
					for(int y = 0; y < temp[0].length; y++) {
						for(int z = 0; z < temp[0][0].length; z++) {
							if(temp[x][y][z]) toPlace.add(new BlockPos(x, y, z));
						}
					}
				}
				ellipsoidCache.put(size, toPlace);
			}
		}

		for(BlockPos pos : toPlace) {
			placer.setBlock(world, boundingBox.getX() + pos.x, boundingBox.getY() + pos.y, boundingBox.getZ() + pos.z, random);
		}
	}

	public static boolean isOnEdge(boolean[][][] array, int x, int y, int z) {
		if(!array[x][y][z])
			return false;
		if(x == 0 || y == 0 || z == 0 || x == array.length - 1 || y == array[0].length - 1 || z == array[0][0].length - 1)
			return true;

		return !(array[x - 1][y][z]
			&& array[x + 1][y][z]
			&& array[x][y - 1][z]
			&& array[x][y + 1][z]
			&& array[x][y][z - 1]
			&& array[x][y][z + 1]);
	}

	private static HashMap<BlockPos, ArrayList<BlockPos>> ellipseCache = new HashMap<BlockPos, ArrayList<BlockPos>>();
	private static HashMap<BlockPos, ArrayList<BlockPos>> ellipseCacheHollow = new HashMap<BlockPos, ArrayList<BlockPos>>();

	public static ArrayList<BlockPos> getCachedEllipse(BlockPos size, boolean hollow) {
		if(hollow) {
			for(BlockPos pos : ellipseCacheHollow.keySet()) {
				if(pos.equals(size)) return ellipseCacheHollow.get(pos);
			}
		} else {
			for(BlockPos pos : ellipseCache.keySet()) {
				if(pos.equals(size)) return ellipseCache.get(pos);
			}
		}
		return null;
	}

	public static void fillCylinder(World world, BoundingBox boundingBox, IBlockPlacer placer, Random random, GenFlag... flags) {
		fillCylinder(world, boundingBox, placer, random, GenFlag.getInt(flags));
	}
	public static void fillCylinder(World world, BoundingBox boundingBox, IBlockPlacer placer, Random random, int flags) {
		boolean hollow = GenFlag.has(flags, GenFlag.HOLLOW);
		boolean hollowEnds = GenFlag.has(flags, GenFlag.HOLLOW_ENDS);

		if(hollow && !hollowEnds) {
			BoundingBox bottom = new BoundingBox(boundingBox.getX(), boundingBox.getY(), boundingBox.getZ(), boundingBox.getWidth(), 1, boundingBox.getDepth());
			BoundingBox top = new BoundingBox(boundingBox.getX(), boundingBox.getY2() - 1, boundingBox.getZ(), boundingBox.getWidth(), 1, boundingBox.getDepth());
			fillCylinder(world, bottom, placer, random);
			fillCylinder(world, top, placer, random);

			BoundingBox body = new BoundingBox(boundingBox.getX(), boundingBox.getY() + 1, boundingBox.getZ(), boundingBox.getWidth(), boundingBox.getHeight() - 2, boundingBox.getDepth());
			fillCylinder(world, body, placer, random, GenFlag.HOLLOW, GenFlag.HOLLOW_ENDS);
			return;
		}

		BlockPos size = new BlockPos(boundingBox.getWidth(), 1, boundingBox.getDepth());
		ArrayList<BlockPos> toPlace = getCachedSphere(size, hollow);

		if(toPlace == null) {
			float radiusX = (float) boundingBox.getWidth() / 2.0F;
			float radiusZ = (float) boundingBox.getDepth() / 2.0F;

			ArrayList<BlockPos> temp = new ArrayList<BlockPos>();

			for(int x = 0; x < boundingBox.getWidth(); x++) {
				for(int z = 0; z < boundingBox.getDepth(); z++) {
					//System.out.println(String.format("X: %d Y: %d Z: %d", x, y, z));
					float distX = (float) x + 0.5F - radiusX;
					distX *= distX;
					float distZ = (float) z + 0.5F - radiusZ;
					distZ *= distZ;

					if(distX / (radiusX * radiusX) + distZ / (radiusZ * radiusZ) <= 1.0F) {
						temp.add(new BlockPos(x, 0, z));
					}
				}
			}

			if(hollow) {
				ellipseCache.put(size, temp);
				toPlace = (ArrayList<BlockPos>) temp.clone();

				Iterator<BlockPos> it = toPlace.iterator();
				while(it.hasNext()) {
					BlockPos pos = it.next();
					if(temp.contains(pos.decreaseX())
							&& temp.contains(pos.increaseX())
							&& temp.contains(pos.decreaseZ())
							&& temp.contains(pos.increaseZ())) {
						it.remove();
					}
				}
				ellipseCacheHollow.put(size, toPlace);
			} else {
				toPlace = temp;
				ellipseCache.put(size, temp);
			}
		}

		for(BlockPos pos : toPlace) {
			for(int y = boundingBox.getY(); y < boundingBox.getY2(); y++) {
				placer.setBlock(world, boundingBox.getX() + pos.x, y, boundingBox.getZ() + pos.z, random);
			}
		}
	}

	private static BlockInfo[][][] clipboard = null;

	public static void cut(World world, BoundingBox boundingBox) {
		copy(world, boundingBox, true);
	}
	public static void copy(World world, BoundingBox boundingBox) {
		copy(world, boundingBox, false);
	}

	private static void copy(World world, BoundingBox boundingBox, boolean cut) {
		clipboard = new BlockInfo[boundingBox.getWidth()][boundingBox.getHeight()][boundingBox.getDepth()];
		BlockPos origin = new BlockPos(boundingBox.getX(), boundingBox.getY(), boundingBox.getZ());

		for(int x = 0; x < boundingBox.getWidth(); x++) {
			for(int y = 0; y < boundingBox.getHeight(); y++) {
				for(int z = 0; z < boundingBox.getDepth(); z++) {
					int x2 = origin.x + x;
					int y2 = origin.y + y;
					int z2 = origin.z + z;
					clipboard[x][y][z] = new BlockInfo(world.getBlock(x2, y2, z2), world.getBlockMetadata(x2, y2, z2));

					if(cut) world.setBlockToAir(x2, y2, z2);
				}
			}
		}
	}

	public static int paste(World world, BlockPos pos) {
		if(clipboard == null) return 0;
		for(int x = 0; x < clipboard.length; x++) {
			for(int y = 0; y < clipboard[0].length; y++) {
				for(int z = 0; z < clipboard[0][0].length; z++) {
					BlockInfo info = clipboard[x][y][z];
					world.setBlock(pos.x + x, pos.y + y, pos.z + z, info.getBlock(), info.getMetadata(), 2);
				}
			}
		}
		return clipboard.length * clipboard[0].length * clipboard[0][0].length;
	}

	public static void rotate(World world, BoundingBox boundingBox, GenFlag rotateFlag) {
		rotate(world, boundingBox, rotateFlag.getRotX(), rotateFlag.getRotY(), rotateFlag.getRotZ());
	}
	public static void rotate(World world, BoundingBox boundingBox, double rotateX, double rotateY, double rotateZ) {
		double sinX = quickSin(rotateX);
		double sinY = quickSin(rotateY);
		double sinZ = quickSin(rotateZ);

		double cosX = quickCos(rotateX);
		double cosY = quickCos(rotateY);
		double cosZ = quickCos(rotateZ);

		double[][] matrix = {
				{cosY * cosZ, cosZ * sinX * sinY - cosX * sinZ, cosX * cosZ * sinY + sinX * sinZ},
				{cosY * sinZ, cosX * cosZ + sinX * sinY * sinZ, -cosZ * sinX + cosX * sinY * sinZ},
				{-sinY, cosY * sinX, cosX * cosY}
		};

		BlockInfo[][][] transformedStates = new BlockInfo[boundingBox.getWidth()][boundingBox.getHeight()][boundingBox.getDepth()];
		for(int x = 0; x < boundingBox.getWidth(); x++) {
			for(int y = 0; y < boundingBox.getHeight(); y++) {
				for(int z = 0; z < boundingBox.getDepth(); z++) {
					BlockInfo state = new BlockInfo(
						world.getBlock(boundingBox.getX() + x, boundingBox.getY() + y, boundingBox.getZ() + z),
						world.getBlockMetadata(boundingBox.getX() + x, boundingBox.getY() + y, boundingBox.getZ() + z));

					double[][] pos = {
							{x + 0.5},
							{y + 0.5},
							{z + 0.5}
					};

					double[][] multiplied = matrixMultiply(matrix, pos);
					int xT = (int) Math.round(multiplied[0][0] - 0.5);
					xT %= boundingBox.getWidth();
					if(xT < 0) xT += boundingBox.getWidth();

					int yT = (int) Math.round(multiplied[1][0] - 0.5);
					yT %= boundingBox.getHeight();
					if(yT < 0) yT += boundingBox.getHeight();

					int zT = (int) Math.round(multiplied[2][0] - 0.5);
					zT %= boundingBox.getDepth();
					if(zT < 0) zT += boundingBox.getDepth();

					transformedStates[xT][yT][zT] = state;
				}
			}
		}

		for(int x = 0; x < boundingBox.getWidth(); x++) {
			for(int y = 0; y < boundingBox.getHeight(); y++) {
				for(int z = 0; z < boundingBox.getDepth(); z++) {
					BlockInfo info = transformedStates[x][y][z];
					if(info != null && info.getBlock() != null) {
						world.setBlock(boundingBox.getX() + x, boundingBox.getY() + y, boundingBox.getZ() + z, info.getBlock(), info.getMetadata(), 2);
					}
				}
			}
		}
	}

	private static final double[] sinTable = new double[8];
	public static double quickSin(double angle) {
		return sinTable[(int) Math.round(angle / 360.0 * sinTable.length) % sinTable.length];
	}

	private static final double[] cosTable = new double[8];
	public static double quickCos(double angle) {
		return cosTable[(int) Math.round(angle / 360.0 * cosTable.length) % cosTable.length];
	}

	static {
		for(int i = 0; i < sinTable.length; i++) {
			sinTable[i] = Math.sin(Math.toRadians((double) i * (360.0 / (double) sinTable.length)));
		}
		for(int i = 0; i < cosTable.length; i++) {
			cosTable[i] = Math.cos(Math.toRadians((double) i * (360.0 / (double) cosTable.length)));
		}
	}

	public static double[][] matrixMultiply(double[][] m1, double[][] m2) {
		int m1ColLength = m1[0].length; // m1 columns length
		int m2RowLength = m2.length;    // m2 rows length
		if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
		int mRRowLength = m1.length;    // m result rows length
		int mRColLength = m2[0].length; // m result columns length
		double[][] mResult = new double[mRRowLength][mRColLength];
		for(int i = 0; i < mRRowLength; i++) {         // rows from m1
			for(int j = 0; j < mRColLength; j++) {     // columns from m2
				for(int k = 0; k < m1ColLength; k++) { // columns from m1
					mResult[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return mResult;
	}

	/** Same as {@link #setContainer(World, int, int, int, BlockContainer, int, ItemStack[], Random, boolean, SlotChecker)}, but {@link SlotChecker#DEFAULT} is always used.
	 * @see #setContainer(World, int, int, int, BlockContainer, int, ItemStack[], Random, boolean, SlotChecker) */
	public static boolean setContainer(World world, int x, int y, int z, BlockContainer container, int metadata, ILootGenerator items, Random random, boolean checkSlots) {
		return setContainer(world, x, y, z, container, metadata, items, random, checkSlots, SlotChecker.DEFAULT);
	}
	/** Places a container in the world and randomly distributes loot inside it.
	 * @param world The world to place the container in
	 * @param x The X coordinate in the world
	 * @param y The Y coordinate in the world
	 * @param z The z coordinate in the world
	 * @param container The block to place
	 * @param metadata The metadata to set
	 * @param items An array of items to be randomly spread throughout the container.<br/>
	 * Higher-priority items (those with fewer possible slots) should be first.
	 * @param random An instance of {@link Random} to be used in item placement
	 * @param checkSlots {@code true} if items should only be placed in valid slots
	 * @param checker A {@link SlotChecker} used to check that an item can be placed in a slot
	 * @return {@code true} if the block was placed and populated correctly */
	public static boolean setContainer(World world, int x, int y, int z, BlockContainer container, int metadata, ILootGenerator items, Random random, boolean checkSlots, SlotChecker checker) {
		if(!world.setBlock(x, y, z, container)) return false;
		if(!world.setBlockMetadataWithNotify(x, y, z, metadata, 2)) return false;

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity == null || !(tileEntity instanceof IInventory)) {
			return false;
		}
		return populateContainer((IInventory) tileEntity, items.generate(world, new BlockPos(x, y, z), (IInventory) tileEntity, random), random, checkSlots, checker);
	}

	/** Same as {@link #populateContainer(IInventory, ItemStack[], Random, boolean, SlotChecker)}, but {@link SlotChecker#DEFAULT} is always used.
	 * @see #populateContainer(IInventory, ItemStack[], Random, boolean, SlotChecker) */
	public static boolean populateContainer(IInventory inventory, ItemStack[] items, Random random, boolean checkSlots) {
		return populateContainer(inventory, items, random, checkSlots, SlotChecker.DEFAULT);
	}
	/** Randomly distributes loot inside a container.
	 * @param inventory The inventory of the container
	 * @param items An array of items to be randomly spread throughout the container
	 * @param random An instance of {@link Random} to be used in item placement
	 * @param checkSlots {@code true} if items should only be placed in valid slots
	 * @param checker A {@link SlotChecker} used to check that an item can be placed in a slot
	 * @return {@code true} if the inventory has been correctly populated */
	public static boolean populateContainer(IInventory inventory, ItemStack[] items, Random random, boolean checkSlots, SlotChecker checker) {
		if(items.length > inventory.getSizeInventory()) return false; // There aren't enough slots in the container
		for(int i = 0; i < items.length; i++) {
			int slot;

			if(checkSlots) {
				ArrayList<Integer> validSlots = new ArrayList<Integer>();
				for(int j = 0; j < inventory.getSizeInventory(); j++) {
					if(inventory.getStackInSlot(j) == null && checker.isValid(inventory, j, items[i])) validSlots.add(j);
				}
				if(validSlots.size() == 0) {
					return false; // There aren't any slots to put this item in
				}

				slot = validSlots.get(random.nextInt(validSlots.size()));
			} else {
				while(inventory.getStackInSlot(slot = random.nextInt(inventory.getSizeInventory())) != null);
			}
			inventory.setInventorySlotContents(slot, items[i]);
		}
		return true;
	}

	public static interface SlotChecker {
		public static final SlotChecker DEFAULT = new SlotChecker() {
			@Override
			public boolean isValid(IInventory inventory, int slot, ItemStack stack) {
				return inventory.isItemValidForSlot(slot, stack);
			}
		};

		/** Verifies that a slot is valid for an item stack.
		 * @param inventory The inventory to test against
		 * @param slot The slot index to test against
		 * @param stack The item stack being tested
		 * @return {@code true} if the slot can be filled with the specified stack */
		public boolean isValid(IInventory inventory, int slot, ItemStack stack);
	}

	public static class SlotCheckerWhitelist implements SlotChecker {
		private int[] validSlots;
		public SlotCheckerWhitelist(int... validSlots) {
			this.validSlots = validSlots;
		}

		@Override
		public boolean isValid(IInventory inventory, int slot, ItemStack stack) {
			for(int slot2 : validSlots) {
				if(slot == slot2) return SlotChecker.DEFAULT.isValid(inventory, slot, stack);
			}
			return false;
		}
	}

	public static class SlotCheckerBlacklist implements SlotChecker {
		private int[] invalidSlots;
		public SlotCheckerBlacklist(int... invalidSlots) {
			this.invalidSlots = invalidSlots;
		}

		@Override
		public boolean isValid(IInventory inventory, int slot, ItemStack stack) {
			for(int slot2 : invalidSlots) {
				if(slot == slot2) return false;
			}
			return SlotChecker.DEFAULT.isValid(inventory, slot, stack);
		}
	}
}
