package tk.nukeduck.generation.client.codeblocks.control;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockReturn extends CodeBlock {
	public CodeBlockContainerTyped<IInteger> value;

	public CodeBlockReturn() {
		super(BlockCategory.CONTROL, Constants.RETURN, 4);
		this.headerParts.add(new CodeBlockTranslate("return"));
		this.headerParts.add(this.value = CodeBlockContainerTyped.create(IInteger.class));
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockReturn block = new CodeBlockReturn();
		if(this.value.hasChild()) block.value.setChild(this.value.getChild().copy());
		return block;
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockReturn block = new CodeBlockReturn();
		if(tag.hasKey("Child")) block.value.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Child")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.value.hasChild()) tag.setTag("Child", this.value.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.value.hasChild()) length += this.value.getChild().getBlockCount();
		return length;
	}

	@Override
	public String getUnlocalizedName() {
		return "return";
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.value.hasChild()) {
			this.value.getChild().evaluate(world, x, y, z);
			ICodeBlock.lastReturn = this.value.getChild();
		}
		ICodeBlock.lastReturn = null;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ZERO;
	}
}
