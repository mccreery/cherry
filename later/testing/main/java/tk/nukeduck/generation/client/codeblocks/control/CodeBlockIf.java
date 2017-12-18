package tk.nukeduck.generation.client.codeblocks.control;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockOpen;
import tk.nukeduck.generation.client.codeblocks.value.IBoolean;

public class CodeBlockIf extends CodeBlockOpen {
	public CodeBlockContainerTyped<IBoolean> container = CodeBlockContainerTyped.create(IBoolean.class);

	public CodeBlockIf() {
		super(BlockType.CONTROL, 33);
		this.headerParts.add(new CodeBlockTranslate("if"));
		this.headerParts.add(container);
		this.headerParts.add(new CodeBlockTranslate("then"));
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.container.get() == null) return;
		this.container.evaluate(world, x, y, z);

		if(this.container.get().getBoolean()) {
			this.evaluateChildren(world, x, y, z);
		}
	}
}
