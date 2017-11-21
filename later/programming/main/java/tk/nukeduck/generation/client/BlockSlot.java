package tk.nukeduck.generation.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;

public class BlockSlot extends Gui {
	public static final ResourceLocation TEXTURE = new ResourceLocation("alchemy", "textures/gui/blocks.png");
	public static final int SLOT_SIZE = 16;

	protected ICodeBlock block;
	protected int x, y;

	public BlockSlot(int x, int y, ICodeBlock block) {
		this.x = x;
		this.y = y;
		this.block = block;
	}

	public ICodeBlock getBlock() {
		return this.block;
	}
	public boolean hasBlock() {
		return this.block != null;
	}

	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}

	public void render(Minecraft mc, int mouseX, int mouseY) {
		if(this.block != null) {
			mc.getTextureManager().bindTexture(TEXTURE);
			IIcon icon = this.getBlock().getIcon();
			this.drawTexturedModelRectFromIcon(this.getX(), this.getY(), icon, 16, 16);
		}

		if(this.isMouseOver(mouseX, mouseY)) {
			this.drawRect(this.getX(), this.getY(), this.getX() + 16, this.getY() + 16, -2130706433);
		}
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return mouseX >= this.getX() && mouseX < this.getX() + SLOT_SIZE
			&& mouseY >= this.getY() && mouseY < this.getY() + SLOT_SIZE;
	}
}
