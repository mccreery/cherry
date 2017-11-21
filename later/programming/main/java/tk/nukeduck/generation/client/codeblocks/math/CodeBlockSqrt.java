package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockSqrt extends CodeBlockProcess {
	public CodeBlockSqrt() {
		super(Constants.SQRT, 39);
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockSqrt());
	}

	@Override
	public String getProcess() {
		return "sqrt";
	}

	@Override
	public int apply(int input) {
		return (int) Math.sqrt(input);
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockSqrt());
	}
}
