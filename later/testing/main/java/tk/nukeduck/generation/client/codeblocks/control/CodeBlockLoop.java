package tk.nukeduck.generation.client.codeblocks.control;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockOpen;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockRawInteger;
import tk.nukeduck.generation.client.codeblocks.value.IInteger;

public class CodeBlockLoop extends CodeBlockOpen {
	public CodeBlockContainerTyped<IInteger> iterations;

	public CodeBlockLoop() {
		super(BlockType.CONTROL, 34);
		this.headerParts.add(new CodeBlockTranslate("loop"));
		this.headerParts.add(iterations = CodeBlockContainerTyped.create(IInteger.class));
		this.headerParts.add(new CodeBlockTranslate("times"));
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(iterations.get() == null) return;
		iterations.evaluate(world, x, y, z);
		for(int i = 0; i < iterations.get().getInteger(); i++) {
			this.evaluateChildren(world, x, y, z);
		}
	}
}
