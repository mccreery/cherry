package tk.nukeduck.generation.client.codeblocks.control;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBoolean;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockOpen;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockLoop extends CodeBlockOpen {
	public CodeBlockContainerTyped<IInteger> iterations;

	public CodeBlockLoop() {
		super(BlockCategory.CONTROL, Constants.LOOP, 2);
		this.headerParts.add(new CodeBlockTranslate("loop"));
		this.headerParts.add(iterations = CodeBlockContainerTyped.create(IInteger.class));
		this.headerParts.add(new CodeBlockTranslate("times"));
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockLoop block = new CodeBlockLoop();
		if(this.iterations.hasChild()) block.iterations.setChild(this.iterations.getChild().copy());
		for(ICodeBlock block2 : this.blockBlocks) {
			block.addToBlock(block2.copy());
		}
		return block;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(iterations.getChildTyped() == null) return;
		iterations.evaluate(world, x, y, z);
		for(int i = 0; i < iterations.getChildTyped().getInteger(); i++) {
			this.evaluateChildren(world, x, y, z);
		}
	}

	@Override
	public String getUnlocalizedName() {
		return "loop";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockLoop block = super.construct(tag, new CodeBlockLoop());
		if(tag.hasKey("Child")) block.iterations.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Child")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = super.serialize();
		if(this.iterations.hasChild()) tag.setTag("Child", this.iterations.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = super.getBlockCount();
		if(this.iterations.hasChild()) length += this.iterations.getChild().getBlockCount();
		return length;
	}
}
