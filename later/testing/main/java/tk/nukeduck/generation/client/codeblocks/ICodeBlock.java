package tk.nukeduck.generation.client.codeblocks;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.SimpleIcon;
import tk.nukeduck.generation.client.codeblocks.errors.IError;

public abstract class ICodeBlock extends Gui {
	public static final Minecraft mc = Minecraft.getMinecraft();
	public static final int PADDING = 2;

	private IIcon icon;
	public int x, y;
	protected int width, height;

	public BlockType type;
	public ICodeBlock(BlockType type, int iconIndex) {
		this.type = type;
		this.icon = new SimpleIcon(iconIndex);
		this.invalidate();
	}

	public abstract void render(int x, int y);

	public final IIcon getIcon() {
		return this.icon;
	}

	public abstract boolean populateClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard, boolean duplicate);
	public abstract boolean placeClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard);

	/** @return true to cancel picking up the block. */
	public boolean mouseClicked(int x, int y, int button) {
		return false;
	}
	/** @return true to cancel any other key events. */
	public boolean keyTyped(char c, int i) {
		return false;
	}

	protected boolean isInvalid;

	public final void invalidate() {
		this.isInvalid = true;
	}

	public boolean checkValid() {
		if(this.isInvalid) {
			this.validate();
			this.isInvalid = false;
			return true;
		}
		return false;
	}
	public abstract void validate();

	public int getHeaderWidth() {
		return this.getWidth();
	}
	public int getHeaderHeight() {
		return this.getHeight();
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}

	public abstract void evaluate(World world, int x, int y, int z);
	public void checkErrors(List<IError> errors) {}

	public void drawDefaultRect(int x, int y, int x2, int y2, BlockType type) {
		this.drawRect(x, y, x2, y2, type.getColor());
		this.drawRect(x, y, x2, y + 1, type.getHighlight());
		this.drawRect(x, y2, x2, y2 + 1, 0x33000000);
	}

	public abstract BlockLevel getLevel();

	public enum BlockLevel {
		ZERO, ONE;
	}

	public enum BlockType {
		CONTROL(255, 200, 60),
		COMPARISON(70, 190, 255),
		VARIABLE(175, 85, 225),
		VALUE(240, 45, 30),
		OPERATION(65, 85, 240),
		ACTION(255, 145, 0);

		private byte a, r, g, b;

		BlockType(int r, int g, int b) {
			this(0xFF, r, g, b);
		}
		BlockType(int a, int r, int g, int b) {
			this.a = (byte) a;
			this.r = (byte) r;
			this.g = (byte) g;
			this.b = (byte) b;
		}

		public int getColor() {
			int a = this.a & 0xFF;
			int r = this.r & 0xFF;
			int g = this.g & 0xFF;
			int b = this.b & 0xFF;
			return a << 24 | r << 16 | g << 8 | b;
		}
		public int getHighlight() {
			int fac = 50;
			int a = Math.min(0xFF, (this.a & 0xFF) + fac);
			int r = Math.min(0xFF, (this.r & 0xFF) + fac);
			int g = Math.min(0xFF, (this.g & 0xFF) + fac);
			int b = Math.min(0xFF, (this.b & 0xFF) + fac);
			return a << 24 | r << 16 | g << 8 | b;
		}
	}
}
