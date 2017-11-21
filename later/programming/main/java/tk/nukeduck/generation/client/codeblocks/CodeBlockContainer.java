package tk.nukeduck.generation.client.codeblocks;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.util.IError;

public abstract class CodeBlockContainer extends ICodeBlock {
	protected ICodeBlock child;
	private int cachedWidth, cachedHeight;

	public CodeBlockContainer() {
		super(BlockCategory.VARIABLE, NO_ID, 255);
	}
	public CodeBlockContainer setChild(ICodeBlock block) {
		if(!this.isChildValid(block)) throw new IllegalArgumentException("Invalid child for container set");
		this.child = block;
		return this;
	}
	public ICodeBlock getChild() {
		return this.child;
	}
	public boolean hasChild() {
		return this.child != null;
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
	public boolean revalidate() {
		if(this.child != null) {
			if(this.child.revalidate()) {
				this.invalidate();
			}
		}
		return super.revalidate();
	}

	@Override
	public void recalculateSize() {
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
	public boolean pickUpBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard, boolean duplicate) {
		if(this.child != null) {
			if(this.child.pickUpBlock(x, y, mouseX, mouseY, clipboard, duplicate)) {
				if(!duplicate && clipboard.blocks.get(0) == this.child) {
					this.child = null;
					this.invalidate();
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean placeBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard) {
		//System.out.println(mouseX + ", " + mouseY + " : " + x + ", " + y + ", " + this.getWidth() + ", " + this.getHeight());
		if(mouseX >= x && mouseY >= y && mouseX < x + this.getWidth() && mouseY < y + this.getHeight() &&
				clipboard.blocks.size() == 1 && this.child == null && this.isChildValid(clipboard.blocks.get(0))) {
			this.child = clipboard.blocks.get(0);
			this.invalidate();
			return true;
		}
		if(this.child != null) {
			return this.child.placeBlock(x, y, mouseX, mouseY, clipboard);
		}
		return false;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	@Override
	public NBTTagCompound serialize() {return null;}
	@Override
	public ICodeBlock construct(NBTTagCompound tag) {return null;}
	@Override
	public int getBlockCount() {return 0;}

	@Override
	public String getUnlocalizedName() {
		return null;
	}
}
