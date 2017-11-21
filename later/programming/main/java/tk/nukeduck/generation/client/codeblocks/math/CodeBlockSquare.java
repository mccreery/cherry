package tk.nukeduck.generation.client.codeblocks.math;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockSquare extends CodeBlockProcess {
	public CodeBlockSquare() {
		super(Constants.SQUARE, 37);
		this.headerParts.add(0, this.headerParts.remove(1));
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockSquare());
	}

	@Override
	public String getProcess() {
		return "square";
	}

	@Override
	public int apply(int input) {
		return input * input;
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockSquare());
	}
}
