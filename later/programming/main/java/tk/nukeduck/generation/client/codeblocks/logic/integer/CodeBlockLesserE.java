package tk.nukeduck.generation.client.codeblocks.logic.integer;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.CodeBlockText;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IInteger;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockCompare;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockLesserE extends CodeBlockCompare<IInteger, IInteger> {
	public CodeBlockLesserE() {
		super(IInteger.class, IInteger.class, Constants.LESS, 24);
		this.headerParts.add(1, new CodeBlockText("<="));
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockLesserE());
	}

	@Override
	public boolean compare(IInteger a, IInteger b) {
		return a.getInteger() <= b.getInteger();
	}

	@Override
	public String getUnlocalizedName() {
		return "lessEqual";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockLesserE());
	}
}
