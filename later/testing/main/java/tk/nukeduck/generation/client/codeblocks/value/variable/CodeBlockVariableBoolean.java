package tk.nukeduck.generation.client.codeblocks.value.variable;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.value.IBoolean;

public class CodeBlockVariableBoolean extends CodeBlockVariable implements IBoolean {
	private boolean value;

	public CodeBlockVariableBoolean(String key) {
		super(key);
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.value = (Boolean) get(this.key.getText().toString());
	}

	@Override
	public boolean getBoolean() {
		return this.value;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
