package tk.nukeduck.generation.client.codeblocks.logic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBoolean;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockNot extends CodeBlock implements IBoolean {
	public CodeBlockContainerTyped<IBoolean> container;
	public boolean value;

	public CodeBlockNot() {
		super(BlockCategory.LOGIC, Constants.NOT, 18);
		this.headerParts.add(container = CodeBlockContainerTyped.create(IBoolean.class));
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockNot block = new CodeBlockNot();
		if(this.container.hasChild()) block.container.setChild(this.container.getChild().copy());
		return block;
	}

	@Override
	public boolean getBoolean() {
		return this.value;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.value = this.container.getChildTyped() != null;
		this.container.evaluate(world, x, y, z);
		this.value &= !this.container.getChildTyped().getBoolean();
	}

	@Override
	public String getUnlocalizedName() {
		return "not";
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockNot block = new CodeBlockNot();
		if(tag.hasKey("Container")) this.container.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Container")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.container.hasChild()) tag.setTag("Container", this.container.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.container.hasChild()) length += this.container.getChild().getBlockCount();
		return length;
	}
}
