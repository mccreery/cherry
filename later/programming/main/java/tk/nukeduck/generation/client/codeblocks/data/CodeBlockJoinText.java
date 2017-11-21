package tk.nukeduck.generation.client.codeblocks.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockJoinText extends CodeBlock implements IString {
	public CodeBlockContainerTyped<IString> containerA = CodeBlockContainerTyped.create(IString.class);
	public CodeBlockContainerTyped<IString> containerB = CodeBlockContainerTyped.create(IString.class);

	private String value;

	public CodeBlockJoinText() {
		super(BlockCategory.VARIABLE, Constants.JOIN_TEXT, 255);
		this.headerParts.add(new CodeBlockTranslate("join"));
		this.headerParts.add(containerA);
		this.headerParts.add(containerB);
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockJoinText block = new CodeBlockJoinText();
		if(this.containerA.hasChild()) block.containerA.setChild(this.containerA.getChild().copy());
		if(this.containerB.hasChild()) block.containerB.setChild(this.containerB.getChild().copy());
		return block;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(containerA.getChildTyped() == null || containerB.getChildTyped() == null) return;
		this.containerA.evaluate(world, x, y, z);
		this.containerB.evaluate(world, x, y, z);
		this.value = containerA.getChildTyped().getString() + containerB.getChildTyped().getString();
	}

	@Override
	public String getString() {
		return this.value;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	@Override
	public String getUnlocalizedName() {
		return "join";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockJoinText block = new CodeBlockJoinText();
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
