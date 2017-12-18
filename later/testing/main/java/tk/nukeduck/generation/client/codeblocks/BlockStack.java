package tk.nukeduck.generation.client.codeblocks;

import java.util.ArrayList;
import java.util.List;

public class BlockStack {
	public int x, y;
	public List<ICodeBlock> blocks = new ArrayList<ICodeBlock>();

	public BlockStack(int x, int y, List<ICodeBlock> blocks) {
		this.x = x;
		this.y = y;
		this.blocks = blocks;
	}
}
