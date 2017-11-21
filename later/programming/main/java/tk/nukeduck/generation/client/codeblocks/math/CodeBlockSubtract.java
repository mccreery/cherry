package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockSubtract extends CodeBlockOperation {
	public CodeBlockSubtract() {
		super(Constants.SUB, 33);
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockSubtract());
	}

	@Override
	public int apply(int a, int b) {
		return a - b;
	}

	@Override
	public String getSymbol() {
		return "minus";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockSubtract());
	}
}
