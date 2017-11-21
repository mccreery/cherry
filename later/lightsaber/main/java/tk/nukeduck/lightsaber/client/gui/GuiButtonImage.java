package tk.nukeduck.lightsaber.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;

public class GuiButtonImage extends GuiButton {
	/** Images to render onto this button, in order. */
	private ArrayList<int[]> images = new ArrayList<int[]>();
	/** Lines of tooltip to render when hovered. */
	private String[] toolTip = {};
	
	/** Constructor.<br/>
	 * Sets the ID and X/Y coordinates.
	 * @param id The ID to set.
	 * @param x The X coordinate to set.
	 * @param y The Y coordinate to set. */
	public GuiButtonImage(int id, int x, int y) {
		super(id, x, y, "");
	}
	
	/** Constructor.<br/>
	 * Sets the ID, X/Y coordinates and size.
	 * @param id The ID to set.
	 * @param x The X coordinate to set.
	 * @param y The Y coordinate to set.
	 * @param width The width to set.
	 * @param height The height to set. */
	public GuiButtonImage(int id, int x, int y, int width, int height) {
		super(id, x, y, width, height, "");
	}
	
	/** Sets the tooltip.
	 * @param s Tooltip to set.
	 * @return This button. */
	public GuiButtonImage setToolTip(String[] s) {
		this.toolTip = s;
		return this;
	}
	
	/** Sets one line of the tooltip.
	 * @param i Line index to set.
	 * @param s Text to set. */
	public void setToolTipLine(int i, String s) {
		if(i > 0 && i < this.toolTip.length) this.toolTip[i] = s;
	}
	
	/** @param i The line index to return.
	 * @return The line of the tooltip at the specified index. */
	public String getToolTipLine(int i) {
		if(i > 0 && i < this.toolTip.length) return this.toolTip[i];
		return null;
	}
	
	/** @return The tooltip. */
	public String[] getToolTip() {
		return this.toolTip;
	}
	
	/** Adds an image to those to render onto this button.
	 * @param x Image X index.
	 * @param y Image Y index.
	 * @return This button. */
	public GuiButtonImage addImage(int x, int y) {
		images.add(new int[] {x, y});
		return this;
	}
	
	/** Removes all images. */
	public void clearImages() {
		images.clear();
	}
	
	/** Removes the image at the specified index.
	 * @param i The index to remove at. */
	public void removeAt(int i) {
		images.remove(i);
	}
	
	/** Removes any image at the given coordinates.
	 * @param x Image X index.
	 * @param y Image Y index. */
	public void remove(int x, int y) {
		images.remove(new int[] {x, y});
	}
	
	/** Sets the image at the specified index to the given coordinates.
	 * @param i The index to set.
	 * @param x Image X index.
	 * @param y Image y index. */
	public void setAt(int i, int x, int y) {
		images.set(i, new int[] {x, y});
	}
	
	/** @return The images this button will render. */
	public ArrayList<int[]> getImages() {
		return images;
	}
	
	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		super.drawButton(mc, i, j);
		GL11.glColor3f(1, 1, 1);
		if(this.visible) {
			mc.renderEngine.bindTexture(GuiRefillUnit.background);
			for(int[] pos : this.getImages()) {
				GL11.glTranslatef(0, 0, -0.001F);
				this.drawTexturedModalRect(
					this.xPosition + this.width / 2 - 8,
					this.yPosition + this.height / 2 - 8,
					176 + 16 * pos[0], 20 + 16 * pos[1],
					16, 16);
			}
		}
	}
}