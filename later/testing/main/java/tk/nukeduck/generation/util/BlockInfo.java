package tk.nukeduck.generation.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class BlockInfo {
	private Block block;
	private int metadata;
	private TileEntity tileEntity = null;

	public BlockInfo(Block block) {
		this(block, 0);
	}
	public BlockInfo(Block block, int metadata) {
		this.block = block;
		this.metadata = metadata;
	}
	public BlockInfo(Block block, int metadata, TileEntity tileEntity) {
		this(block, metadata);
		this.tileEntity = tileEntity;
	}

	public Block getBlock() {
		return this.block;
	}
	public int getMetadata() {
		return this.metadata;
	}

	public boolean hasTileEntity() {
		return this.tileEntity != null;
	}
	public TileEntity getTileEntity() {
		return this.tileEntity;
	}
}
