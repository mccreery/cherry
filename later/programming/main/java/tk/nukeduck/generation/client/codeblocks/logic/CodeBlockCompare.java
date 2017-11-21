package tk.nukeduck.generation.client.codeblocks.logic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.BlockStack;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBoolean;

public abstract class CodeBlockCompare<A, B> extends CodeBlock implements IBoolean {
	public CodeBlockContainerTyped<A> containerA;
	public CodeBlockContainerTyped<B> containerB;
	public boolean value;

	public CodeBlockCompare(Class<A> a, Class<B> b, short id, int iconIndex) {
		super(BlockCategory.LOGIC, id, iconIndex);
		this.headerParts.add(containerA = CodeBlockContainerTyped.create(a));
		this.headerParts.add(containerB = CodeBlockContainerTyped.create(b));
	}

	public <T extends CodeBlockCompare> T copy(T block) {
		if(this.containerA.hasChild()) block.containerA.setChild(this.containerA.getChild().copy());
		if(this.containerB.hasChild()) block.containerB.setChild(this.containerB.getChild().copy());
		return block;
	}

	public boolean hasValues() {
		return this.containerA.getChildTyped() != null &&
			this.containerB.getChildTyped() != null;
	}
	public abstract boolean compare(A a, B b);

	@Override
	public boolean getBoolean() {
		return this.value;
	}

	public void evaluate(World world, int x, int y, int z) {
		this.value = this.hasValues();
		this.containerA.evaluate(world, x, y, z);
		this.containerB.evaluate(world, x, y, z);
		this.value &= this.compare(this.containerA.getChildTyped(), this.containerB.getChildTyped());
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	public <T extends CodeBlockCompare> T construct(NBTTagCompound tag, T block) {
		if(tag.hasKey("ContainerA")) block.containerA.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("ContainerA")));
		if(tag.hasKey("ContainerB")) block.containerB.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("ContainerB")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.containerA.hasChild()) tag.setTag("ComtainerA", this.containerA.getChild().writeToNBT());
		if(this.containerB.hasChild()) tag.setTag("ComtainerB", this.containerB.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = 0;
		if(this.containerA.hasChild()) length += this.containerA.getChild().getBlockCount();
		if(this.containerB.hasChild()) length += this.containerB.getChild().getBlockCount();
		return length;
	}
}
