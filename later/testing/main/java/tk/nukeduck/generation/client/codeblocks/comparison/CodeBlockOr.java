package tk.nukeduck.generation.client.codeblocks.comparison;

import tk.nukeduck.generation.client.codeblocks.value.IBoolean;

public class CodeBlockOr extends CodeBlockCompare<IBoolean, IBoolean> {
	public CodeBlockOr() {
		super(IBoolean.class, IBoolean.class, 255);
	}

	@Override
	public boolean compare(IBoolean a, IBoolean b) {
		return a.getBoolean() && b.getBoolean();
	}
}
