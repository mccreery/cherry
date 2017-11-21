package tk.nukeduck.generation.client.codeblocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockCompare;
import tk.nukeduck.generation.util.IError;

public abstract class CodeBlock extends ICodeBlock {
	public List<ICodeBlock> headerParts = new ArrayList<ICodeBlock>();

	public CodeBlock(BlockCategory type, short id, int iconIndex) {
		super(type, id, iconIndex);
	}

	public CodeBlock addHeaderPart(ICodeBlock block) {
		this.headerParts.add(block);
		this.invalidate();
		return this;
	}

	@Override
	public boolean mouseClicked(int x, int y, int button) {
		for(ICodeBlock block : this.headerParts) {
			if(block.mouseClicked(x, y, button)) return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char c, int i) {
		for(ICodeBlock block : this.headerParts) {
			if(block.keyTyped(c, i)) return true;
		}
		return false;
	}

	@Override
	public void checkErrors(List<IError> errors) {
		for(ICodeBlock block : this.headerParts) {
			block.checkErrors(errors);
		}
	}

	@Override
	public void render(int x, int y) {
		this.drawDefaultRect(x, y, x + this.getHeaderWidth(), y + this.getHeaderHeight(), this.category);
		int a = PADDING;
		for(ICodeBlock block : this.headerParts) {
			block.render(x + a, y + (this.getHeaderHeight() - block.getHeight()) / 2);
			a += block.getWidth() + PADDING;
			//System.out.println(block.getClass().getSimpleName() + block.getHeight());
		}
	}

	@Override
	public boolean revalidate() {
		for(ICodeBlock block : this.headerParts) {
			if(block.revalidate()) {
				this.invalidate();
			}
		}
		return super.revalidate();
	}

	@Override
	public void recalculateSize() {
		this.width = PADDING;
		this.height = 0;

		for(ICodeBlock block : this.headerParts) {
			this.width += block.getWidth() + PADDING;
			int height = block.getHeight();
			if(height > this.height) this.height = height;
		}
		this.height += PADDING * 2;
	}

	@Override
	public boolean pickUpBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard, boolean duplicate) {
		int a = PADDING;
		for(ICodeBlock block : this.headerParts) {
			if(block.pickUpBlock(x + a, y + (this.getHeaderHeight() - block.getHeight()) / 2, mouseX, mouseY, clipboard, duplicate)) {
				return true;
			}
			a += block.getWidth() + PADDING;
		}
		if(mouseX >= x && mouseY >= y && mouseX < x + this.getHeaderWidth() && mouseY < y + this.getHeaderHeight()) {
			clipboard.append(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean placeBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard) {
		int a = PADDING;
		for(ICodeBlock block : this.headerParts) {
			if(block.placeBlock(x + a, y + (this.getHeaderHeight() - block.getHeight()) / 2, mouseX, mouseY, clipboard)) {
				return true;
			}
			a += block.getWidth() + PADDING;
		}
		return false;
	}
}
