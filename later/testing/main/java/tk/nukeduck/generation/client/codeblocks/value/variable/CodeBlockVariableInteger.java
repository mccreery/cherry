package tk.nukeduck.generation.client.codeblocks.value.variable;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.value.IInteger;

public class CodeBlockVariableInteger extends CodeBlockVariable implements IInteger {
	public int value;

	public CodeBlockVariableInteger(String key) {
		super(key);
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.value = (Integer) get(this.key.getText().toString());
	}

	@Override
	public Integer getInteger() {
		return this.value;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
