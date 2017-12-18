package tk.nukeduck.generation.client.codeblocks;

import java.util.Iterator;

import net.minecraft.client.resources.I18n;
import tk.nukeduck.generation.client.codeblocks.action.CodeBlockChatMessage;
import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockAnd;
import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockOr;
import tk.nukeduck.generation.client.codeblocks.comparison.integer.CodeBlockEqualTo;
import tk.nukeduck.generation.client.codeblocks.comparison.integer.CodeBlockGreaterThan;
import tk.nukeduck.generation.client.codeblocks.comparison.integer.CodeBlockLessThan;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockIf;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockLoop;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockWhile;

public enum BlockCategory {
	ACTION("action",
			new CodeBlockChatMessage()),
	COMPARISON("comparison",
			new CodeBlockEqualTo(),
			new CodeBlockGreaterThan(),
			new CodeBlockLessThan(),
			new CodeBlockAnd(),
			new CodeBlockOr()),
	CONTROL("control",
			new CodeBlockIf(),
			new CodeBlockLoop(),
			new CodeBlockWhile());

	private String name;
	private ICodeBlock displayBlock;
	public ICodeBlock[] blocks;

	BlockCategory(String name, ICodeBlock displayBlock, ICodeBlock... blocks) {
		this.name = name;
		this.displayBlock = displayBlock;
		this.blocks = blocks;
	}

	public String getUnlocalizedName() {
		return this.name;
	}
	public String getLocalizedName() {
		return I18n.format("category." + this.getUnlocalizedName());
	}

	public ICodeBlock getDisplayBlock() {
		return this.displayBlock;
	}
}
