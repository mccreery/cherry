package tk.nukeduck.generation.client.codeblocks.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockConstant extends CodeBlock implements IInteger, IString {
	private ConstantType constant;
	int value;

	public CodeBlockConstant(ConstantType constant) {
		super(BlockCategory.DATA, Constants.CONSTANT, 255);
		this.constant = constant;
		this.headerParts.add(new CodeBlockTranslate("block." + this.getUnlocalizedName()));
	}

	@Override
	public ICodeBlock copy() {
		return new CodeBlockConstant(this.constant);
	}

	@Override
	protected void populateCategory() {
		for(ConstantType type : ConstantType.values()) {
			this.category.addBlock(new CodeBlockConstant(type));
		}
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		switch(this.constant) {
			case X:
				this.value = x;
				break;
			case Y:
				this.value = y;
				break;
			case Z:
				this.value = z;
				break;
			case BIOME:
				this.value = world.getBiomeGenForCoords(x, z).biomeID;
				break;
		}
	}

	@Override
	public Integer getInteger() {
		return this.value;
	}

	@Override
	public String getString() {
		return String.valueOf(this.getInteger());
	}

	public enum ConstantType {
		X(0, "x"), Y(1, "y"), Z(2, "z"),
		BIOME(3, "biome");

		public static final ConstantType[] ID_MAPPING = new ConstantType[values().length];
		private final String name;
		private final byte id;

		ConstantType(int id, String name) {
			this.id = (byte) id;
			this.name = name;
		}

		public String getUnlocalizedName() {
			return "constant." + this.name;
		}
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	@Override
	public String getUnlocalizedName() {
		return this.constant.getUnlocalizedName();
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return new CodeBlockConstant(ConstantType.ID_MAPPING[tag.getByte("ConstantID")]);
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setByte("ConstantID", this.constant.id);
		return tag;
	}

	@Override
	public int getBlockCount() {
		return 1;
	}
}
