package tk.nukeduck.generation.client.codeblocks.variable;

import java.util.List;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockRawInteger;
import tk.nukeduck.generation.client.codeblocks.value.IInteger;
import tk.nukeduck.generation.client.codeblocks.value.variable.CodeBlockVariable;

public class CodeBlockVariableSetterInteger extends CodeBlockVariableSetter implements IInteger {
	public CodeBlockContainerTyped<IInteger> value;

	public CodeBlockVariableSetterInteger() {
		super(255);
		this.headerParts.add(this.value = CodeBlockContainerTyped.create(IInteger.class));
	}

	@Override
	public void set(String key) {
		CodeBlockVariable.set(key, this.value.get().getInteger());
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.value.evaluate(world, x, y, z);
		super.evaluate(world, x, y, z);
	}

	@Override
	public Integer getInteger() {
		return (Integer) CodeBlockVariable.get(this.key.get().getKey());
	}
}
