package tk.nukeduck.generation.client.codeblocks.comparison;

import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.value.IBlockState;

public class CodeBlockBlockEqual extends CodeBlockCompare<IBlockState, IBlockState> {
	public CodeBlockBlockEqual() {
		super(IBlockState.class, IBlockState.class, 255);
		this.headerParts.add(0, new CodeBlockTranslate("block"));
		this.headerParts.add(2, new CodeBlockTranslate("is"));
	}

	@Override
	public boolean compare(IBlockState a, IBlockState b) {
		return a.getBlockState().block == b.getBlockState().block &&
			a.getBlockState().meta == b.getBlockState().meta;
	}
}
