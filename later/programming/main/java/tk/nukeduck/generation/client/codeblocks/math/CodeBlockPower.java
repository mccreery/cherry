package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockPowerContainer;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockPower extends CodeBlock implements IInteger {
	public CodeBlockContainerTyped<IInteger> container;
	public CodeBlockPowerContainer power;
	protected int value;

	public CodeBlockPower() {
		super(BlockCategory.MATH, Constants.POWER, 36);
		this.headerParts.add(this.container = CodeBlockContainerTyped.create(IInteger.class));
		this.headerParts.add(this.power = new CodeBlockPowerContainer());
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockPower block = new CodeBlockPower();
		if(this.container.hasChild()) block.container.setChild(this.container.getChild().copy());
		if(this.power.hasChild()) block.power.setChild(this.power.getChild().copy());
		return block;
	}

	@Override
	public String getUnlocalizedName() {
		return "power";
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.container.getChild() == null || this.power.getChild() == null) return;
		this.container.getChild().evaluate(world, x, y, z);
		this.power.getChild().evaluate(world, x, y, z);

		this.value = 1;
		int base = this.container.getChildTyped().getInteger();
		for(int exp = this.power.getChildTyped().getInteger(); exp > 0; exp >>= 1) {
			if((exp & 1) == 1) this.value *= base;
			base *= base;
		}
	}

	@Override
	public Integer getInteger() {
		return this.value;
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockPower block = new CodeBlockPower();
		if(tag.hasKey("Child")) block.container.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Child")));
		if(tag.hasKey("Power")) block.power.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Power")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.container.hasChild()) tag.setTag("Child", this.container.getChild().writeToNBT());
		if(this.power.hasChild()) tag.setTag("Power", this.power.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.container.hasChild()) length += this.container.getChild().getBlockCount();
		if(this.power.hasChild()) length += this.power.getChild().getBlockCount();
		return length;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
