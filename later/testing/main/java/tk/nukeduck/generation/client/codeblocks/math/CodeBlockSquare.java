package tk.nukeduck.generation.client.codeblocks.math;

public class CodeBlockSquare extends CodeBlockProcess {
	public CodeBlockSquare() {
		super(255);
		this.headerParts.add(0, this.headerParts.remove(1));
	}

	@Override
	public String getProcess() {
		return "square";
	}

	@Override
	public int apply(int input) {
		return input * input;
	}
}
