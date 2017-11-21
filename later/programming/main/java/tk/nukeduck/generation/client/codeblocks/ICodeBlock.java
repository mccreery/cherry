package tk.nukeduck.generation.client.codeblocks;

import java.util.List;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.SimpleIcon;
import tk.nukeduck.generation.client.codeblocks.action.CodeBlockChatMessage;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockFunction;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockIf;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockLoop;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockReturn;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockWhile;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockConstant;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockConstant.ConstantType;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockTextBox;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockAnd;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockNot;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockOr;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockEqual;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockGreater;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockGreaterE;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockLesser;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockLesserE;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockNEqual;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockAbs;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockAdd;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockDivide;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockMultiply;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockPower;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSqrt;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSquare;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSubtract;
import tk.nukeduck.generation.client.codeblocks.variable.CodeBlockGetVariable;
import tk.nukeduck.generation.client.codeblocks.variable.CodeBlockSetVariable;

public abstract class ICodeBlock extends Gui {
	public static final int PADDING = 2;
	public static final String ID_KEY = "Id";
	public static final String TAG_KEY = "Tag";

	public static ICodeBlock lastReturn = null;

	private IIcon icon;
	public int x, y;
	protected int width, height;

	public abstract ICodeBlock copy();

	public static final short NO_ID = (short) 65535;
	private static final ICodeBlock[] ID_MAPPING = new ICodeBlock[65536];

	public static final void register(ICodeBlock block) {
		ID_MAPPING[block.id & 0xFFFF] = block;
		block.populateCategory();
	}

	private boolean visible = true;
	public ICodeBlock setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	protected void populateCategory() {
		if(this.visible) {
			this.category.addBlock(this);
		}
	}

	public static final void registerBlocks() {
		register(new CodeBlockFunction());
		register(new CodeBlockIf());
		register(new CodeBlockLoop());
		register(new CodeBlockWhile());
		register(new CodeBlockReturn());

		register(new CodeBlockAnd());
		register(new CodeBlockOr());
		register(new CodeBlockNot());
		register(new CodeBlockEqual());
		register(new CodeBlockNEqual());
		register(new CodeBlockGreater());
		register(new CodeBlockLesser());
		register(new CodeBlockGreaterE());
		register(new CodeBlockLesserE());

		register(new CodeBlockAdd());
		register(new CodeBlockSubtract());
		register(new CodeBlockMultiply());
		register(new CodeBlockDivide());
		register(new CodeBlockSquare());
		register(new CodeBlockPower());
		register(new CodeBlockSqrt());
		register(new CodeBlockAbs());

		register(new CodeBlockTextBox(""));
		register(new CodeBlockConstant(ConstantType.BIOME));
		register(new CodeBlockBlock(Blocks.grass, 0));

		register(new CodeBlockSetVariable());
		register(new CodeBlockGetVariable());

		register(new CodeBlockChatMessage());

		BlockCategory.CONTROL .setIcon(new CodeBlockFunction()            .getIcon());
		BlockCategory.LOGIC   .setIcon(new CodeBlockAnd()                 .getIcon());
		BlockCategory.MATH    .setIcon(new CodeBlockAdd()                 .getIcon());
		BlockCategory.DATA    .setIcon(new CodeBlockBlock(Blocks.grass, 0).getIcon());
		BlockCategory.VARIABLE.setIcon(new CodeBlockSetVariable()         .getIcon());
		BlockCategory.ACTION  .setIcon(new CodeBlockChatMessage()         .getIcon());
	}

	private final short id;
	public short getId() {
		return this.id;
	}

	/** Counts how many blocks this block counts as.<br/>
	 * Returning 0 means the block itself will not be counted.
	 * @return The amount of blocks that are contained within this one, including itself. */
	// TODO this can also probably be changed to work with a list of containers
	public abstract int getBlockCount();
	public BlockCategory category;

	public ICodeBlock(BlockCategory category, short id, int iconIndex) {
		this.id = id;
		this.category = category;
		this.icon = new SimpleIcon(iconIndex);
		this.invalidate();
	}

	public abstract void render(int x, int y);

	public final IIcon getIcon() {
		return this.icon;
	}

	// TODO consider providing implementations to auto-RW containers and just allow overriding
	/** Creates a new instance of this block from the given NBT.<br/>
	 * Does not include block ID.
	 * @param tag The NBT compound to load from
	 * @see #loadFromNBT(NBTTagCompound) */
	public abstract ICodeBlock construct(NBTTagCompound tag);
	/** Writes block-specific data to an NBT tag.<br/>
	 * Does not include block ID.
	 * @return The written NBT
	 * @see #writeToNBT() */
	public abstract NBTTagCompound serialize();

	/** Creates a new block from the given NBT.<br/>
	 * Includes block ID.
	 * @param tag The NBT compound to load from
	 * @see #construct(NBTTagCompound) */
	public static final ICodeBlock loadFromNBT(NBTTagCompound tag) throws IllegalArgumentException {
		if(!tag.hasKey(ID_KEY, 2)) throw new IllegalArgumentException("ID not found");
		if(!tag.hasKey(TAG_KEY, 10)) throw new IllegalArgumentException("Tag not found");

		short id = tag.getShort(ID_KEY);
		return ID_MAPPING[id & 0xFFFF].construct(tag.getCompoundTag(TAG_KEY));
	}

	/** Writes block data to an NBT tag.<br/>
	 * Includes block ID.
	 * @return The written NBT
	 * @see #serialize() */
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setShort(ID_KEY, this.id);
		compound.setTag(TAG_KEY, this.serialize());
		return compound;
	}

	public abstract String getUnlocalizedName();
	public final String getLocalizedName() {
		return I18n.format("code.block." + this.getUnlocalizedName());
	}

	/** Attempts to pick up blocks, either this block, children or several blocks
	 * @param x Block X position
	 * @param y Block Y position
	 * @param mouseX The X position of the mouse click
	 * @param mouseY The Y position of the mouse click
	 * @param clipboard The block clipboard to be added to
	 * @param duplicate {@code true} to not remove the original block
	 * @return {@code true} if a block has been added to the clipboard after clicking */
	public abstract boolean pickUpBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard, boolean duplicate);
	/** Attempts to place a block as a child of this block
	 * @param x Block X position
	 * @param y Block Y position
	 * @param mouseX The X position of the mouse click
	 * @param mouseY The Y position of the mouse click
	 * @param clipboard The block clipboard to place from
	 * @return {@code true} if a block has been added to the clipboard after clicking */
	public abstract boolean placeBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard);

	/** @return true to cancel picking up the block. */
	public boolean mouseClicked(int x, int y, int button) {
		return false;
	}
	/** @return true to cancel any other key events. */
	public boolean keyTyped(char c, int i) {
		return false;
	}

	protected boolean isInvalid;

	public final void invalidate() {
		this.isInvalid = true;
	}

	/** Validates a block, if it's currently invalid.
	 * @return {@code true} if the block was invalid, and has been validated */
	public boolean revalidate() {
		if(this.isInvalid) {
			this.recalculateSize();
			this.isInvalid = false;
			return true;
		}
		return false;
	}
	public abstract void recalculateSize();

	public int getHeaderWidth() {
		return this.getWidth();
	}
	public int getHeaderHeight() {
		return this.getHeight();
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}

	public abstract void evaluate(World world, int x, int y, int z);
	public void checkErrors(List<String> errors) {}

	public void drawDefaultRect(int x, int y, int x2, int y2, BlockCategory category) {
		this.drawRect(x, y, x2, y2, category.color);
		this.drawRect(x, y, x2, y + 1, category.highlight);
		this.drawRect(x, y2, x2, y2 + 1, 0x33000000);
	}

	public enum BlockLevel {
		ZERO, ONE;
	}
	public abstract BlockLevel getLevel();

	public boolean insertAfter(ICodeBlock block) {
		return block.getLevel() == BlockLevel.ZERO;
	}
}
