package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockMultiply extends CodeBlockOperation {
	public CodeBlockMultiply() {
		super(Constants.MULT, 34);
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockMultiply());
	}

	@Override
	public int apply(int a, int b) {
		return a * b;
	}

	@Override
	public String getSymbol() {
		return "multiply";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockMultiply());
	}
}
