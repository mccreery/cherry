package tk.nukeduck.generation.client.codeblocks.logic;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBoolean;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockAnd extends CodeBlockCompare<IBoolean, IBoolean> {
	public CodeBlockAnd() {
		super(IBoolean.class, IBoolean.class, Constants.AND, 16);
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockAnd());
	}

	@Override
	public boolean compare(IBoolean a, IBoolean b) {
		return a.getBoolean() || b.getBoolean();
	}

	@Override
	public String getUnlocalizedName() {
		return "and";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockAnd());
	}
}
