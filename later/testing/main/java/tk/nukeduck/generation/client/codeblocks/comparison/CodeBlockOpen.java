package tk.nukeduck.generation.client.codeblocks.comparison;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockLevel;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockType;
import tk.nukeduck.generation.client.codeblocks.errors.IError;
import net.minecraft.world.World;

public abstract class CodeBlockOpen extends CodeBlock {
	public List<ICodeBlock> blockBlocks = new ArrayList<ICodeBlock>();
	protected int headerWidth, headerHeight, blockHeight;

	public CodeBlockOpen(BlockType type, int iconIndex) {
		super(type, iconIndex);
	}

	public CodeBlockOpen addToBlock(ICodeBlock block) {
		this.blockBlocks.add(block);
		return this;
	}

	public void evaluateChildren(World world, int x, int y, int z) {
		for(ICodeBlock child : this.blockBlocks) {
			child.evaluate(world, x, y, z);
		}
	}

	@Override
	public boolean mouseClicked(int x, int y, int button) {
		if(super.mouseClicked(x, y, button)) return true;
		for(ICodeBlock block : this.blockBlocks) {
			if(block.mouseClicked(x, y, button)) return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char c, int i) {
		if(super.keyTyped(c, i)) return true;
		for(ICodeBlock block : this.blockBlocks) {
			if(block.keyTyped(c, i)) return true;
		}
		return false;
	}

	@Override
	public boolean populateClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard, boolean duplicate) {
		int i = x + PADDING, j = y + this.getHeaderHeight();

		Iterator<ICodeBlock> it = this.blockBlocks.iterator();
		while(it.hasNext()) {
			ICodeBlock block = it.next();
			int height = block.getHeight();
			if(block.populateClipboard(i, j, mouseX, mouseY, clipboard, duplicate)) {
				if(clipboard.get(0) == block) {
					if(!duplicate) it.remove();
					while(it.hasNext()) {
						clipboard.add(it.next());
						if(!duplicate) it.remove();
					}
				}
				this.invalidate();
				return true;
			}
			j += height;
		}
		if(super.populateClipboard(x, y, mouseX, mouseY, clipboard, duplicate)) return true;

		int k = j + this.blockHeight;
		if(mouseX >= x && (
				(mouseX < i && mouseY >= j && mouseY < k) ||
				(mouseX < x + this.getWidth() && mouseY >= k && mouseY < k + PADDING)
			)) {
			clipboard.add(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean placeClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard) {
		if(super.placeClipboard(x, y, mouseX, mouseY, clipboard)) return true;
		int i = x + PADDING;
		int j = y + this.getHeaderHeight();
		for(ICodeBlock block : this.blockBlocks) {
			if(block.placeClipboard(i, j, mouseX, mouseY, clipboard)) return true;
			j += block.getHeight();
		}

		if(clipboard.get(0).getLevel() == BlockLevel.ZERO) {
			if(mouseX >= x && mouseX < x + this.getHeaderWidth() && mouseY >= y + this.getHeaderHeight() / 2 && mouseY < y + this.getHeaderHeight()) {
				this.blockBlocks.addAll(0, clipboard);
				this.invalidate();
				return true;
			}

			j = y + this.getHeaderHeight();
			for(int in = 0; in < this.blockBlocks.size(); in++) {
				ICodeBlock block = this.blockBlocks.get(in);
				if(mouseX >= i && mouseX < i + block.getWidth() &&
						mouseY >= j + block.getHeight() / 2 && mouseY < (j += block.getHeight())) {
					this.blockBlocks.addAll(in + 1, clipboard);
					this.invalidate();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void checkErrors(List<IError> errors) {
		super.checkErrors(errors);
		for(ICodeBlock block : this.blockBlocks) {
			block.checkErrors(errors);
		}
	}

	@Override
	public int getHeaderWidth() {
		return this.headerWidth;
	}

	@Override
	public int getHeaderHeight() {
		return this.headerHeight;
	}

	@Override
	public void render(int x, int y) {
		super.render(x, y);
		int a = y + this.getHeaderHeight();
		for(ICodeBlock block : this.blockBlocks) {
			block.render(x + PADDING, a);
			a += block.getHeight();
		}
		this.drawRect(x, y + this.getHeaderHeight(), x + PADDING, y + this.getHeaderHeight() + this.blockHeight, this.type.getColor()); // LHS
		this.drawRect(x, y + this.getHeaderHeight() + this.blockHeight, x + this.getHeaderWidth(), y + this.getHeight(), this.type.getColor()); // Bottom
		this.drawRect(x + PADDING, y + this.getHeaderHeight() + this.blockHeight, x + this.getHeaderWidth(), y + this.getHeaderHeight() + this.blockHeight + 1, this.type.getHighlight()); // Bottom highlight
		this.drawRect(x + PADDING, y + this.getHeaderHeight(), x + PADDING + 1, y + this.getHeaderHeight() + this.blockHeight, 0x55000000); // Shadow
	}

	@Override
	public boolean checkValid() {
		for(ICodeBlock block : this.blockBlocks) {
			if(block.checkValid()) {
				this.invalidate();
			}
		}
		return super.checkValid();
	}

	@Override
	public void validate() {
		super.validate();
		this.headerHeight = this.height;
		this.headerWidth = this.width;

		this.blockHeight = 0;
		for(ICodeBlock block : this.blockBlocks) {
			int width = PADDING + block.getWidth();
			if(width > this.width) this.width = width;
			this.blockHeight += block.getHeight();
		}

		this.height += this.blockHeight + PADDING * 4;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ZERO;
	}
}
