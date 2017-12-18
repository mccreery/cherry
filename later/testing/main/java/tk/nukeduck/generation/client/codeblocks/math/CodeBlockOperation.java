package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockLevel;
import tk.nukeduck.generation.client.codeblocks.value.IInteger;
import tk.nukeduck.generation.client.codeblocks.value.IString;

public abstract class CodeBlockOperation extends CodeBlock implements IInteger, IString {
	public CodeBlockContainerTyped<IInteger> containerA;
	public CodeBlockContainerTyped<IInteger> containerB;
	public int value;

	public CodeBlockOperation(int iconIndex) {
		super(BlockType.OPERATION, iconIndex);
		this.headerParts.add(containerA = CodeBlockContainerTyped.create(IInteger.class));
		this.headerParts.add(new CodeBlockTranslate(this.getSymbol()));
		this.headerParts.add(containerB = CodeBlockContainerTyped.create(IInteger.class));
	}

	public boolean hasValues() {
		return this.containerA.get() != null &&
			this.containerB.get() != null;
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
		this.value = this.apply(this.containerA.get().getInteger(), this.containerB.get().getInteger());
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
}
