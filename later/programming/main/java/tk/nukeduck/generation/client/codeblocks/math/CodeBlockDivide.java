package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockDivide extends CodeBlockOperation {
	public CodeBlockDivide() {
		super(Constants.DIV, 35);
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockDivide());
	}

	@Override
	public int apply(int a, int b) {
		return a / b;
	}

	@Override
	public String getSymbol() {
		return "divide";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockDivide());
	}
}
