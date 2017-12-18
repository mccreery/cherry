package tk.nukeduck.generation.client.codeblocks.value;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;

public class CodeBlockJoinText extends CodeBlock implements IString {
	public CodeBlockContainerTyped<IString> containerA = CodeBlockContainerTyped.create(IString.class);
	public CodeBlockContainerTyped<IString> containerB = CodeBlockContainerTyped.create(IString.class);

	private String value;

	public CodeBlockJoinText() {
		super(BlockType.OPERATION, 255);
		this.headerParts.add(new CodeBlockTranslate("join"));
		this.headerParts.add(containerA);
		this.headerParts.add(containerB);
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(containerA.get() == null || containerB.get() == null) return;
		this.containerA.evaluate(world, x, y, z);
		this.containerB.evaluate(world, x, y, z);
		this.value = containerA.get().getString() + containerB.get().getString();
	}

	@Override
	public String getString() {
		return this.value;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
