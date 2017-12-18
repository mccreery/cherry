package tk.nukeduck.generation.client.codeblocks;

import java.util.List;

import net.minecraft.world.World;

public class CodeBlockText extends ICodeBlock {
	protected String text;

	public CodeBlockText(String text) {
		super(BlockType.VALUE, 17);
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	@Override
	public void render(int x, int y) {
		this.drawString(mc.fontRenderer, this.getText(), x, y, 0xFFFFFFFF);
	}

	@Override
	public void validate() {
		this.width = mc.fontRenderer.getStringWidth(this.getText());
		this.height = mc.fontRenderer.FONT_HEIGHT;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {}

	@Override
	public boolean populateClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard, boolean duplicate) {
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
