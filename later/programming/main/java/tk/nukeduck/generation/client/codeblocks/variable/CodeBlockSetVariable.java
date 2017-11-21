package tk.nukeduck.generation.client.codeblocks.variable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainer;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBoolean;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.client.codeblocks.data.IString;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockSetVariable extends CodeBlock {
	public CodeBlockContainerTyped<IString> name;
	public CodeBlockContainer value;

	public CodeBlockSetVariable() {
		super(BlockCategory.VARIABLE, Constants.VAR_SET, 64);
		this.headerParts.add(new CodeBlockTranslate("set"));
		this.headerParts.add(this.name = CodeBlockContainerTyped.create(IString.class));
		this.headerParts.add(new CodeBlockTranslate("to"));
		this.headerParts.add(this.value = new CodeBlockContainer() {
			@Override
			public boolean isChildValid(ICodeBlock child) {
				return child instanceof IString
					|| child instanceof IInteger
					|| child instanceof IBoolean;
			}

			@Override
			public ICodeBlock copy() {
				return null;
			}
		});
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockSetVariable block = new CodeBlockSetVariable();
		if(this.name.hasChild()) block.name.setChild(this.name.getChild().copy());
		if(this.value.hasChild()) block.value.setChild(this.value.getChild().copy());
		return block;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.name.hasChild()) length += this.name.getChild().getBlockCount();
		if(this.value.hasChild()) length += this.value.getChild().getBlockCount();
		return length;
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockSetVariable block = new CodeBlockSetVariable();
		if(tag.hasKey("Name")) block.name.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Name")));
		if(tag.hasKey("Value")) block.value.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Value")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.name.hasChild()) tag.setTag("Name", this.name.getChild().writeToNBT());
		if(this.value.hasChild()) tag.setTag("Value", this.value.getChild().writeToNBT());
		return tag;
	}

	@Override
	public String getUnlocalizedName() {
		return "set";
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.name.evaluate(world, x, y, z);
		this.value.evaluate(world, x, y, z);
		if(!this.name.hasChild() || !this.value.hasChild()) return;

		Object value = null;
		if(this.value.getChild() instanceof IString) {
			value = ((IString) this.value.getChild()).getString();
		} else if(this.value.getChild() instanceof IInteger) {
			value = ((IInteger) this.value.getChild()).getInteger();
		} else if(this.value.getChild() instanceof IBoolean) {
			value = ((IBoolean) this.value.getChild()).getBoolean();
		}

		if(value != null) {
			VariableMap.variables.put(this.name.getChildTyped().getString(), value);
		}
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ZERO;
	}
}
