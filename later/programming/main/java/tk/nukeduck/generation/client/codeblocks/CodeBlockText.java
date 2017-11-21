package tk.nukeduck.generation.client.codeblocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.Generation;

public class CodeBlockText extends ICodeBlock {
	protected String text;

	public CodeBlockText(String text) {
		super(BlockCategory.VARIABLE, NO_ID, 17);
		this.text = text;
	}

	@Override
	public ICodeBlock copy() {
		return null;
	}

	public String getText() {
		return this.text;
	}

	@Override
	public void render(int x, int y) {
		this.drawString(Generation.mc.fontRenderer, this.getText(), x, y, 0xFFFFFFFF);
	}

	@Override
	public void recalculateSize() {
		this.width = Generation.mc.fontRenderer.getStringWidth(this.getText());
		this.height = Generation.mc.fontRenderer.FONT_HEIGHT;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {}

	@Override
	public boolean pickUpBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard, boolean duplicate) {
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
		return null;
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return null;
	}

	@Override
	public NBTTagCompound serialize() {
		return null;
	}

	@Override
	public int getBlockCount() {
		return 0;
	}
}
