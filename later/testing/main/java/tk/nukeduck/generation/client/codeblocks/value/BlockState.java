package tk.nukeduck.generation.client.codeblocks.value;

import net.minecraft.block.Block;

public class BlockState {
	public Block block;
	public int meta;

	public BlockState(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}
}
