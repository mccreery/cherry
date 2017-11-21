package tk.nukeduck.generation.client.codeblocks.control;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBoolean;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockOpen;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockIf extends CodeBlockOpen {
	public CodeBlockContainerTyped<IBoolean> container = CodeBlockContainerTyped.create(IBoolean.class);

	public CodeBlockIf() {
		super(BlockCategory.CONTROL, Constants.IF, 1);
		this.headerParts.add(new CodeBlockTranslate("if"));
		this.headerParts.add(container);
		this.headerParts.add(new CodeBlockTranslate("then"));
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockIf block = new CodeBlockIf();
		if(this.container.hasChild()) block.container.setChild(this.container.getChild().copy());
		for(ICodeBlock block2 : this.blockBlocks) {
			block.addToBlock(block2.copy());
		}
		return block;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.container.getChildTyped() == null) return;
		this.container.evaluate(world, x, y, z);

		if(this.container.getChildTyped().getBoolean()) {
			this.evaluateChildren(world, x, y, z);
		}
	}

	@Override
	public String getUnlocalizedName() {
		return "if";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockIf block = super.construct(tag, new CodeBlockIf());
		if(tag.hasKey("Child")) block.container.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Child")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = super.serialize();
		if(this.container.hasChild()) tag.setTag("Child", this.container.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = super.getBlockCount();
		if(this.container.hasChild()) length += this.container.getChild().getBlockCount();
		return length;
	}
}
