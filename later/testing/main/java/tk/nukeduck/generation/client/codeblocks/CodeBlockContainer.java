package tk.nukeduck.generation.client.codeblocks;

import java.util.List;

import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockCompare;
import tk.nukeduck.generation.client.codeblocks.errors.IError;
import net.minecraft.world.World;

public abstract class CodeBlockContainer extends ICodeBlock {
	protected ICodeBlock child = null;
	private int cachedWidth, cachedHeight;

	public CodeBlockContainer() {
		super(BlockType.VALUE, 255);
	}
	public CodeBlockContainer setChild(ICodeBlock block) {
		if(!this.isChildValid(block)) throw new IllegalArgumentException("Invalid child for container set");
		this.child = block;
		return this;
	}
	public ICodeBlock getChild() {
		return this.child;
	}

	@Override
	public boolean mouseClicked(int x, int y, int button) {
		return child != null && child.mouseClicked(x, y, button);
	}

	@Override
	public boolean keyTyped(char c, int i) {
		return child != null && child.keyTyped(c, i);
	}

	public abstract boolean isChildValid(ICodeBlock child);

	@Override
	public void checkErrors(List<IError> errors) {
		if(this.child == null) {
			errors.add(new IError() {
				@Override
				public String getDescription() {
					return "Containers must have blocks inside them";
				}
			});
		} else {
			this.child.checkErrors(errors);
		}
	}

	@Override
	public void render(int x, int y) {
		if(this.child == null) {
			this.drawRect(x, y, x + this.getWidth(), y + this.getHeight(), 0xFFFFFFFF);
			this.drawRect(x, y, x + this.getWidth(), y + 1, 0xFFCCCCCC);
		} else {
			this.child.render(x, y);
		}
	}

	@Override
	public boolean checkValid() {
		if(this.child != null) {
			if(this.child.checkValid()) {
				this.invalidate();
			}
		}
		return super.checkValid();
	}

	@Override
	public void validate() {
		if(this.child == null) {
			this.width = 30;
			this.height = 10;
		} else {
			this.width = this.child.getWidth();
			this.height = this.child.getHeight();
		}
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(child != null) this.child.evaluate(world, x, y, z);
	}

	@Override
	public boolean populateClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard, boolean duplicate) {
		if(this.child != null) {
			if(this.child.populateClipboard(x, y, mouseX, mouseY, clipboard, duplicate)) {
				if(!duplicate && clipboard.get(0) == this.child) {
					this.child = null;
					this.invalidate();
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean placeClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard) {
		//System.out.println(mouseX + ", " + mouseY + " : " + x + ", " + y + ", " + this.getWidth() + ", " + this.getHeight());
		if(mouseX >= x && mouseY >= y && mouseX < x + this.getWidth() && mouseY < y + this.getHeight() &&
				clipboard.size() == 1 && this.child == null && this.isChildValid(clipboard.get(0))) {
			this.child = clipboard.get(0);
			this.invalidate();
			return true;
		}
		if(this.child != null) {
			return this.child.placeClipboard(x, y, mouseX, mouseY, clipboard);
		}
		return false;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
