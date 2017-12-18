package tk.nukeduck.generation.client.codeblocks.math;

public class CodeBlockSqrt extends CodeBlockProcess {
	public CodeBlockSqrt() {
		super(255);
	}

	@Override
	public String getProcess() {
		return "sqrt";
	}

	@Override
	public int apply(int input) {
		return (int) Math.sqrt(input);
	}
}
