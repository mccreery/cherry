package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.client.codeblocks.data.IString;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockCompare;

public abstract class CodeBlockProcess extends CodeBlock implements IInteger/*, IString*/ {
	public CodeBlockContainerTyped<IInteger> container;
	protected int value;

	public CodeBlockProcess(short id, int iconIndex) {
		super(BlockCategory.MATH, id, iconIndex);
		this.headerParts.add(new CodeBlockTranslate(this.getProcess()));
		this.headerParts.add(this.container = CodeBlockContainerTyped.create(IInteger.class));
	}

	public <T extends CodeBlockProcess> T copy(T block) {
		if(this.container.hasChild()) block.container.setChild(this.container.getChild().copy());
		return block;
	}

	public abstract String getProcess();
	public abstract int apply(int input);

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.container.getChild() == null) return;
		this.container.getChild().evaluate(world, x, y, z);
		this.value = this.apply(this.container.getChildTyped().getInteger());
	}

	@Override
	public Integer getInteger() {
		return this.value;
	}

	/*@Override
	public String getString() {
		return String.valueOf(this.value);
	}*/

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	@Override
	public String getUnlocalizedName() { 
		return "process";
	}

	public <T extends CodeBlockProcess> T construct(NBTTagCompound tag, T block) {
		if(tag.hasKey("Child")) block.container.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Child")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.container.hasChild()) tag.setTag("Child", this.container.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.container.hasChild()) length += this.container.getChild().getBlockCount();
		return length;
	}
}
