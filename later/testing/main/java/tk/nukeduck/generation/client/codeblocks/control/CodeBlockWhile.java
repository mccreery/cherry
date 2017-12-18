package tk.nukeduck.generation.client.codeblocks.control;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockOpen;
import tk.nukeduck.generation.client.codeblocks.value.IBoolean;

public class CodeBlockWhile extends CodeBlockOpen {
	public CodeBlockContainerTyped<IBoolean> container = CodeBlockContainerTyped.create(IBoolean.class);

	public CodeBlockWhile() {
		super(BlockType.CONTROL, 35);
		this.headerParts.add(new CodeBlockTranslate("while"));
		this.headerParts.add(container);
		this.headerParts.add(new CodeBlockTranslate("do"));
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.container.get() == null) return;

		this.container.evaluate(world, x, y, z);
		boolean flag = this.container.get().getBoolean();

		while(flag) {
			this.evaluateChildren(world, x, y, z);

			this.container.evaluate(world, x, y, z);
			flag = this.container.get().getBoolean();
		}
	}
}
