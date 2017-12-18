package tk.nukeduck.generation.client.codeblocks.comparison.integer;

import tk.nukeduck.generation.client.codeblocks.CodeBlockText;
import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockCompare;
import tk.nukeduck.generation.client.codeblocks.value.IInteger;

public class CodeBlockEqualTo extends CodeBlockCompare<IInteger, IInteger> {
	public CodeBlockEqualTo() {
		super(IInteger.class, IInteger.class, 2);
		this.headerParts.add(1, new CodeBlockText("="));
	}

	@Override
	public boolean compare(IInteger a, IInteger b) {
		return a.getInteger() == b.getInteger();
	}
}
