package tk.nukeduck.generation.client.codeblocks.comparison;

import java.util.List;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainer;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockLevel;
import tk.nukeduck.generation.client.codeblocks.value.IBoolean;

public abstract class CodeBlockCompare<A, B> extends CodeBlock implements IBoolean {
	public CodeBlockContainerTyped<A> containerA;
	public CodeBlockContainerTyped<B> containerB;
	public boolean value;

	public CodeBlockCompare(Class<A> a, Class<B> b, int iconIndex) {
		super(BlockType.COMPARISON, iconIndex);
		this.headerParts.add(containerA = CodeBlockContainerTyped.create(a));
		this.headerParts.add(containerB = CodeBlockContainerTyped.create(b));
	}

	@Override
	public boolean placeClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard) {
		System.out.println("COMPARISON {");
		boolean a = super.placeClipboard(x, y, mouseX, mouseY, clipboard);
		System.out.println("}");
		return a;
	}

	public boolean hasValues() {
		return this.containerA.get() != null &&
			this.containerB.get() != null;
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
		this.value &= this.compare(this.containerA.get(), this.containerB.get());
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
