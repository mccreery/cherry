package tk.nukeduck.generation.client.codeblocks.variable;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockLevel;
import tk.nukeduck.generation.client.codeblocks.value.variable.CodeBlockVariable;

public abstract class CodeBlockVariableSetter extends CodeBlock {
	public CodeBlockContainerTyped<CodeBlockVariable> key;

	public CodeBlockVariableSetter(int iconIndex) {
		super(BlockType.VARIABLE, iconIndex);
		this.headerParts.add(new CodeBlockTranslate("set"));
		this.headerParts.add(key = CodeBlockContainerTyped.create(CodeBlockVariable.class));
		this.headerParts.add(new CodeBlockTranslate("to"));
	}

	public abstract void set(String key);

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.set(key.get().getKey());
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ZERO;
	}
}
