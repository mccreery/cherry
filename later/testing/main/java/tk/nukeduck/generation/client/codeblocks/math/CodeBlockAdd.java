package tk.nukeduck.generation.client.codeblocks.math;

import tk.nukeduck.generation.client.codeblocks.CodeBlockText;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockRawInteger;
import tk.nukeduck.generation.client.codeblocks.value.IInteger;

public class CodeBlockAdd extends CodeBlockOperation {
	public CodeBlockAdd() {
		super(255);
	}

	@Override
	public int apply(int a, int b) {
		return a + b;
	}

	@Override
	public String getSymbol() {
		return "plus";
	}
}
