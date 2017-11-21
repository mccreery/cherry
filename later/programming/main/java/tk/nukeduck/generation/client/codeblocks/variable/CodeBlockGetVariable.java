package tk.nukeduck.generation.client.codeblocks.variable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBoolean;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.client.codeblocks.data.IString;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockGetVariable extends CodeBlock implements IString, IInteger, IBoolean {
	public CodeBlockContainerTyped<IString> name;
	private Object value;

	public CodeBlockGetVariable() {
		super(BlockCategory.VARIABLE, Constants.VAR_GET, 65);
		this.addHeaderPart(this.name = CodeBlockContainerTyped.create(IString.class));
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockGetVariable block = new CodeBlockGetVariable();
		if(this.name.hasChild()) block.name.setChild(this.name.getChild().copy());
		return block;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.name.hasChild()) length += this.name.getChild().getBlockCount();
		return length;
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockGetVariable block = new CodeBlockGetVariable();
		if(tag.hasKey("Name")) block.name.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Name")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.name.hasChild()) tag.setTag("Name", this.name.getChild().writeToNBT());
		return tag;
	}

	@Override
	public String getUnlocalizedName() {
		return "get";
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.name.evaluate(world, x, y, z);
		this.value = this.name.hasChild() ? VariableMap.variables.get(this.name.getChildTyped().getString()) : null;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ZERO;
	}

	// Attempts to convert to different types, to reduce errors/misunderstandings

	@Override
	public boolean getBoolean() {
		if(this.value == null) throw new RuntimeException("");
		if(this.value instanceof Boolean) {
			return (Boolean) this.value;
		} else if(this.value instanceof Integer) {
			return ((Integer) this.value) != 0;
		} else if(this.value instanceof String) {
			return !((String) this.value).equals("");
		}
		throw new RuntimeException();
	}

	@Override
	public Integer getInteger() {
		if(this.value instanceof Boolean) {
			return ((Boolean) this.value) ? 1 : 0;
		} else if(this.value instanceof Integer) {
			return (Integer) this.value;
		} else if(this.value instanceof String) {
			throw new RuntimeException("Attempted to use a string as an integer");
		}
		throw new RuntimeException();
	}

	@Override
	public String getString() {
		if(this.value instanceof Boolean) {
			return ((Boolean) this.value) ? "true" : "false";
		} else if(this.value instanceof Integer) {
			return String.valueOf((Integer) this.value);
		} else if(this.value instanceof String) {
			return (String) this.value;
		}
		throw new RuntimeException();
	}
}
