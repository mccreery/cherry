package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainer;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockLevel;
import tk.nukeduck.generation.client.codeblocks.value.IInteger;
import tk.nukeduck.generation.client.codeblocks.value.IString;

public abstract class CodeBlockProcess extends CodeBlock implements IInteger, IString {
	public CodeBlockContainerTyped<IInteger> container;
	protected int value;

	public CodeBlockProcess(int iconIndex) {
		super(BlockType.OPERATION, iconIndex);
		this.headerParts.add(new CodeBlockTranslate(this.getProcess()));
		this.headerParts.add(this.container = CodeBlockContainerTyped.create(IInteger.class));
	}

	public abstract String getProcess();
	public abstract int apply(int input);

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.container.getChild() == null) return;
		this.container.getChild().evaluate(world, x, y, z);
		this.value = this.apply(this.container.get().getInteger());
	}

	@Override
	public Integer getInteger() {
		return this.value;
	}

	@Override
	public String getString() {
		return String.valueOf(this.value);
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
