package tk.nukeduck.generation.client.codeblocks.math;

public class CodeBlockSubtract extends CodeBlockOperation {
	public CodeBlockSubtract() {
		super(255);
	}

	@Override
	public int apply(int a, int b) {
		return a - b;
	}

	@Override
	public String getSymbol() {
		return "minus";
	}
}
