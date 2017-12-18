package tk.nukeduck.generation.client.codeblocks.comparison.integer;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlockText;
import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockCompare;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockRawInteger;
import tk.nukeduck.generation.client.codeblocks.value.IInteger;

public class CodeBlockGreaterThan extends CodeBlockCompare<IInteger, IInteger> {
	public CodeBlockGreaterThan() {
		super(IInteger.class, IInteger.class, 0);
		this.headerParts.add(1, new CodeBlockText(">"));
	}

	@Override
	public boolean compare(IInteger a, IInteger b) {
		return a.getInteger() > b.getInteger();
	}
}
