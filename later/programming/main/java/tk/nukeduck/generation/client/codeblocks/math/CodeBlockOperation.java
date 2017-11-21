package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockLevel;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.client.codeblocks.data.IString;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockCompare;

public abstract class CodeBlockOperation extends CodeBlock implements IInteger, IString {
	public CodeBlockContainerTyped<IInteger> containerA;
	public CodeBlockContainerTyped<IInteger> containerB;
	public int value;

	public CodeBlockOperation(short id, int iconIndex) {
		super(BlockCategory.MATH, id, iconIndex);
		this.headerParts.add(containerA = CodeBlockContainerTyped.create(IInteger.class));
		this.headerParts.add(new CodeBlockTranslate(this.getSymbol()));
		this.headerParts.add(containerB = CodeBlockContainerTyped.create(IInteger.class));
	}

	public <T extends CodeBlockOperation> T copy(T block) {
		if(this.containerA.hasChild()) block.containerA.setChild(this.containerA.getChild().copy());
		if(this.containerB.hasChild()) block.containerB.setChild(this.containerB.getChild().copy());
		return block;
	}

	public boolean hasValues() {
		return this.containerA.getChildTyped() != null &&
			this.containerB.getChildTyped() != null;
	}
	public abstract int apply(int a, int b);
	public abstract String getSymbol();

	public void evaluate(World world, int x, int y, int z) {
		if(!this.hasValues()) {
			this.value = 0;
			return;
		}
		this.containerA.evaluate(world, x, y, z);
		this.containerB.evaluate(world, x, y, z);
		this.value = this.apply(this.containerA.getChildTyped().getInteger(), this.containerB.getChildTyped().getInteger());
	}

	@Override
	public Integer getInteger() {
		return this.value;
	}

	@Override
	public String getString() {
		return String.valueOf(this.getInteger());
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	@Override
	public String getUnlocalizedName() { 
		return "operation." + this.getSymbol();
	}

	public <T extends CodeBlockOperation> T construct(NBTTagCompound tag, T block) {
		if(tag.hasKey("ContainerA")) block.containerA.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("ContainerA")));
		if(tag.hasKey("ContainerB")) block.containerB.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("ContainerB")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.containerA.hasChild()) tag.setTag("ContainerA", this.containerA.getChild().writeToNBT());
		if(this.containerB.hasChild()) tag.setTag("ContainerB", this.containerB.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.containerA.hasChild()) length += this.containerA.getChild().getBlockCount();
		if(this.containerB.hasChild()) length += this.containerB.getChild().getBlockCount();
		return length;
	}
}
