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

public class CodeBlockWhile extends CodeBlockOpen {
	public CodeBlockContainerTyped<IBoolean> container = CodeBlockContainerTyped.create(IBoolean.class);

	public CodeBlockWhile() {
		super(BlockCategory.CONTROL, Constants.WHILE, 3);
		this.headerParts.add(new CodeBlockTranslate("while"));
		this.headerParts.add(container);
		this.headerParts.add(new CodeBlockTranslate("do"));
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockWhile block = new CodeBlockWhile();
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
		boolean flag = this.container.getChildTyped().getBoolean();

		while(flag) {
			this.evaluateChildren(world, x, y, z);

			this.container.evaluate(world, x, y, z);
			flag = this.container.getChildTyped().getBoolean();
		}
	}

	@Override
	public String getUnlocalizedName() {
		return "while";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockWhile block = super.construct(tag, new CodeBlockWhile());
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
