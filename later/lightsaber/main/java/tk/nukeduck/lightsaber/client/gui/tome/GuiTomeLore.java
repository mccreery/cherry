package tk.nukeduck.lightsaber.client.gui.tome;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.util.Constants;
import tk.nukeduck.lightsaber.util.Strings;

public class GuiTomeLore extends GuiScreen {
	public static final GuiTab lore = new GuiTab("forceTome.tab.lore");
	public static final GuiTab skills = new GuiTab("forceTome.tab.skills");
	
	private static ResourceLocation background = new ResourceLocation(Strings.MOD_ID, "textures/gui/tome_gui.png");
	
	/** The width and height of the GUI. */
	int xSize, ySize;
	/** The player being tracked. */
	EntityPlayer player;
	/** Current page, shown on the left side. */
	private static int currentSpread = 1;
	
	/** Constructor.<br/>
	 * Sets size and player. */
	public GuiTomeLore(EntityPlayer player) {
		this.xSize = 256;
		this.ySize = 185;
		this.player = player;
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	boolean press = false;
	
	public void drawScreen(int x, int y, float p_73863_3_) {
		this.drawDefaultBackground();
		
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);
		this.drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
		
		this.drawTexturedModalRect(xStart + 24, yStart + 156, 18 - (currentSpread == 1 ? 18 : 0), 211, 18, 10);
		this.drawTexturedModalRect(xStart + xSize - 24 - 18, yStart + 156, 18 - (currentSpread == Constants.TOME_PAGE_COUNT - 1 ? 18 : 0), 201, 18, 10);
		
		if(Mouse.isButtonDown(0)) {
			if(!press) {
				if(x >= xStart + 24 && x <= xStart + 24 + 18 && y >= yStart + 156 && y <= yStart + 156 + 10) {
					if(currentSpread != 1) {
						currentSpread -= 2;
						Lightsaber.mc.thePlayer.playSound("random.click", 1, 1);
					} else {
						Lightsaber.mc.thePlayer.playSound("random.click", 0.5F, 0.5F);
					}
				}
				if(x >= xStart + xSize - 24 - 18 && x <= xStart + xSize - 24 - 18 + 18 && y >= yStart + 156 && y <= yStart + 156 + 10) {
					if(currentSpread != Constants.TOME_PAGE_COUNT - 1) {
						currentSpread += 2;
						Lightsaber.mc.thePlayer.playSound("random.click", 1, 1);
					} else {
						Lightsaber.mc.thePlayer.playSound("random.click", 0.5F, 0.5F);
					}
				}
				press = true;
			}
		} else {
			press = false;
		}
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor3f(0, 0, 0);
		this.drawTexturedModalRect(xStart + 60, yStart + ySize - 32, (16 * (currentSpread - 1)), 185, 16, 16);
		this.drawTexturedModalRect(xStart + xSize - 76, yStart + ySize - 32, (16 * currentSpread), 185, 16, 16);
		
		int textStartX = (width - xSize) / 2 + 18;
		int textStartY = (height - ySize) / 2 + 20;
		
		for(int i = currentSpread; i < currentSpread + 2; i++) {
			ArrayList<String> page = Strings.formatLinesWidth(this.fontRendererObj, Strings.translate(Strings.TOME_LORE + i), 100);
			String title = Strings.translate(Strings.TOME_LORE + i + Strings.TOME_TITLE_SUFFIX);
			if(title.length() > 0) {
				page.add(0, "");
				page.add(0, title);
			}
			
			int a = 0;
			for(String s : page) {
				if(s.startsWith("\\c")) {
					s = s.replace("\\c", "");
					this.fontRendererObj.drawString(s, textStartX + 50 - this.fontRendererObj.getStringWidth(s) / 2, textStartY + (a * this.fontRendererObj.FONT_HEIGHT), 0x000000);
				} else {
					this.fontRendererObj.drawString(s, textStartX, textStartY + (a * this.fontRendererObj.FONT_HEIGHT), 0x000000);
				}
				a++;
			}
			textStartX += 120;
		}
		
		lore.render((width - xSize) / 2 + 10, (height - ySize) / 2 - 21, true);
		skills.render((width - xSize) / 2 + 75, (height - ySize) / 2 - 21, false);
		
		if(skills.isClicked((width - xSize) / 2 + 75, (height - ySize) / 2 - 21, x, y)) {
			mc.displayGuiScreen(new GuiSkillTree(this.player));
			Lightsaber.mc.thePlayer.playSound("random.click", 1, 1);
		}
	}
}