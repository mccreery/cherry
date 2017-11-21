package tk.nukeduck.generation.client.codeblocks.logic.integer;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.CodeBlockText;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockAnd;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockCompare;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockGreaterE extends CodeBlockCompare<IInteger, IInteger> {
	public CodeBlockGreaterE() {
		super(IInteger.class, IInteger.class, Constants.GREATER, 23);
		this.headerParts.add(1, new CodeBlockText(">="));
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockGreaterE());
	}

	@Override
	public boolean compare(IInteger a, IInteger b) {
		return a.getInteger() >= b.getInteger();
	}

	@Override
	public String getUnlocalizedName() {
		return "greaterEqual";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockGreaterE());
	}
}
