package tk.nukeduck.generation.client.codeblocks.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.Generation;
import tk.nukeduck.generation.client.GuiTextBox;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.BlockStack;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockTextBox extends ICodeBlock implements IString {
	public GuiTextBox textbox;

	public CodeBlockTextBox(String text) {
		super(BlockCategory.DATA, Constants.TEXT_BOX, 17);
		this.textbox = new GuiTextBox(Generation.mc.fontRenderer, 0, 0, 0, 0, 1024);
		this.textbox.write(text);
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockTextBox block = new CodeBlockTextBox("");
		block.textbox.setText(this.textbox.getText());
		return block;
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
		this.drawDefaultRect(x, y, x + this.getWidth(), y + this.getHeight(), this.category);
		this.drawRect(x + PADDING, y + PADDING, x + this.getWidth() - PADDING, y + this.getHeight() - PADDING, 0xFFFFFFFF);
		this.drawRect(x + PADDING, y + PADDING, x + this.getWidth() - PADDING, y + PADDING + 1, 0xFFCCCCCC);
		//mc.fontRenderer.drawString(this.getText().toString(), x + PADDING * 2, y + PADDING * 2, 0xFF000000);
		this.textbox.setX(x + 2).setY(y + 2).setWidth(this.getWidth() - PADDING * 2).setHeight(Generation.mc.fontRenderer.FONT_HEIGHT + PADDING * 2);
		this.textbox.draw();
	}

	@Override
	public void recalculateSize() {
		this.width = Generation.mc.fontRenderer.getStringWidth(this.getText().toString()) + PADDING * 4;
		this.height = Generation.mc.fontRenderer.FONT_HEIGHT + PADDING * 4;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {}

	@Override
	public String getString() {
		return this.textbox.getText().toString();
	}

	@Override
	public boolean pickUpBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard, boolean duplicate) {
		if(mouseX >= x && mouseY >= y &&
				mouseX < x + this.getWidth() && mouseY < y + this.getHeight()) {
			clipboard.append(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean placeBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard) {
		return false;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	@Override
	public String getUnlocalizedName() {
		return "text";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return new CodeBlockTextBox(tag.hasKey("Text") ? tag.getString("Text") : "");
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.getString().length() > 0) tag.setString("Text", this.getString());
		return tag;
	}

	@Override
	public int getBlockCount() {
		return 1;
	}
}
