package tk.nukeduck.generation.client.codeblocks.math;

public class CodeBlockAbs extends CodeBlockProcess {
	public CodeBlockAbs() {
		super(255);
	}

	@Override
	public String getProcess() {
		return "abs";
	}

	@Override
	public int apply(int input) {
		return input < 0 ? -input : input;
	}
}
