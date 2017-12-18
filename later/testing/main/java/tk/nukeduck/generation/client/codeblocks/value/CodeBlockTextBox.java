package tk.nukeduck.generation.client.codeblocks.value;

import java.util.List;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.GuiTextBox;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;

public class CodeBlockTextBox extends ICodeBlock implements IString {
	public GuiTextBox textbox;

	public CodeBlockTextBox(String text) {
		super(BlockType.VALUE, 17);
		this.textbox = new GuiTextBox(this.mc.fontRenderer, 0, 0, 0, 0, 1024);
		this.textbox.write(text);
	}

	public CharSequence getText() {
		return this.textbox.getText();
	}

	@Override
	public boolean mouseClicked(int x, int y, int button) {
		this.textbox.mouseClicked(x, y, button);
		return this.textbox.hasFocus;
	}

	@Override
	public boolean keyTyped(char c, int i) {
		if(this.textbox.keyTyped(c, i)) {
			this.invalidate();
			return true;
		}
		return false;
	}

	@Override
	public void render(int x, int y) {
		this.drawDefaultRect(x, y, x + this.getWidth(), y + this.getHeight(), type);
		this.drawRect(x + PADDING, y + PADDING, x + this.getWidth() - PADDING, y + this.getHeight() - PADDING, 0xFFFFFFFF);
		this.drawRect(x + PADDING, y + PADDING, x + this.getWidth() - PADDING, y + PADDING + 1, 0xFFCCCCCC);
		//mc.fontRenderer.drawString(this.getText().toString(), x + PADDING * 2, y + PADDING * 2, 0xFF000000);
		this.textbox.setX(x + 2).setY(y + 2).setWidth(this.getWidth() - PADDING * 2).setHeight(mc.fontRenderer.FONT_HEIGHT + PADDING * 2);
		this.textbox.draw();
	}

	@Override
	public void validate() {
		this.width = mc.fontRenderer.getStringWidth(this.getText().toString()) + PADDING * 4;
		this.height = mc.fontRenderer.FONT_HEIGHT + PADDING * 4;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {}

	@Override
	public String getString() {
		return this.textbox.getText().toString();
	}

	@Override
	public boolean populateClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard, boolean duplicate) {
		if(mouseX >= x && mouseY >= y &&
				mouseX < x + this.getWidth() && mouseY < y + this.getHeight()) {
			clipboard.add(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean placeClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard) {
		return false;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
