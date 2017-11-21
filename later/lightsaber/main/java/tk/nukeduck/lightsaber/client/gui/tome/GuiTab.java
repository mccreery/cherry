package tk.nukeduck.lightsaber.client.gui.tome;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.util.Constants;
import tk.nukeduck.lightsaber.util.Strings;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class GuiTab extends Gui {
	private static ResourceLocation background = new ResourceLocation(Strings.MOD_ID, "textures/gui/tabs.png");
	private String text;
	
	public GuiTab(String text) {
		this.text = text;
	}
	
	/** Draws the tab at the specified coordinates.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @param enabled Whether or not this tab is the current one. */
	public void render(int x, int y, boolean enabled) {
		GL11.glColor4f(1, 1, 1, 1);
		Lightsaber.mc.getTextureManager().bindTexture(background);
		
		int uOffset = enabled ? 0 : 64;
		this.drawTexturedModalRect(x, y, uOffset, 0, 64, 24);
		Lightsaber.mc.fontRenderer.drawString(Strings.translate(text), x + 10, y + 9, Constants.GUI_TEXT_COLOR);
	}
	
	/** @return Whether or not the mouse is down given the coordinates.
	 * @param x X coordinate of the tab.
	 * @param y Y coordinate of the tab.
	 * @param mouseX Mouse X position.
	 * @param mouseY Mouse Y position. */
	public boolean isClicked(int x, int y, int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + 64
			&& mouseY >= y && mouseY <= y + 24
			&& Mouse.isButtonDown(0);
	}
}