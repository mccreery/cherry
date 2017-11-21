package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockAbs extends CodeBlockProcess {
	public CodeBlockAbs() {
		super(Constants.ABS, 41);
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockAbs());
	}

	@Override
	public String getProcess() {
		return "abs";
	}

	@Override
	public int apply(int input) {
		return input < 0 ? -input : input;
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockAbs());
	}
}
