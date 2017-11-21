package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockAdd extends CodeBlockOperation {
	public CodeBlockAdd() {
		super(Constants.ADD, 32);
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockAdd());
	}

	@Override
	public int apply(int a, int b) {
		return a + b;
	}

	@Override
	public String getSymbol() {
		return "plus";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockAdd());
	}
}
