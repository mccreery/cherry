package tk.nukeduck.generation.client.codeblocks.logic;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBoolean;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockOr extends CodeBlockCompare<IBoolean, IBoolean> {
	public CodeBlockOr() {
		super(IBoolean.class, IBoolean.class, Constants.OR, 17);
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockOr());
	}

	@Override
	public boolean compare(IBoolean a, IBoolean b) {
		return a.getBoolean() && b.getBoolean();
	}

	@Override
	public String getUnlocalizedName() {
		return "or";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockOr());
	}
}
