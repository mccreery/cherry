package tk.nukeduck.lightsaber.client.gui.tome;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.entity.skills.ForceSkill;
import tk.nukeduck.lightsaber.network.BuySkillMessage;
import tk.nukeduck.lightsaber.network.IORuleMessage;
import tk.nukeduck.lightsaber.network.SetSelectedMessage;
import tk.nukeduck.lightsaber.util.Constants;
import tk.nukeduck.lightsaber.util.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import static org.lwjgl.opengl.GL11.*;

public class GuiSkillTree extends GuiScreen {
	/*private static ResourceLocation[] backgroundsSky = {
		new ResourceLocation(Strings.MOD_ID, "textures/gui/skill_tree_gui_background.png"),
		new ResourceLocation(Strings.MOD_ID, "textures/gui/skill_tree_gui_background_2.png"),
		new ResourceLocation(Strings.MOD_ID, "textures/gui/skill_tree_gui_background_3.png")
	};*/
	/** The image used for the starry background. */
	private static ResourceLocation space = new ResourceLocation(Strings.MOD_ID, "textures/gui/skill_tree_gui_background_normal.png");
	/** The image used for the border, bottom bar and icons. */
	private static ResourceLocation background = new ResourceLocation(Strings.MOD_ID, "textures/gui/skill_tree_gui.png");
	
	private GuiSkillTreeItem meditation = new GuiSkillTreeItem(ForceSkill.MEDITATION, 3, 1, 2);
	private GuiSkillTreeItem focus = new GuiSkillTreeItem(ForceSkill.FOCUS, 0, 0, 1),
		control = new GuiSkillTreeItem(ForceSkill.CONTROL_I, 0, 1, 3),
			forceSpeed = new GuiSkillTreeItemSpeed(ForceSkill.SPEED_I, -1, 2, 5, 1),
				forceSpeedII = new GuiSkillTreeItemSpeed(ForceSkill.SPEED_II, -2, 2, 5, 2),
					forceSpeedIII = new GuiSkillTreeItemSpeed(ForceSkill.SPEED_III, -2, 3, 5, 3),
			controlII = new GuiSkillTreeItem(ForceSkill.CONTROL_II, 0, 2, 4),
				forceCloak = new GuiSkillTreeItem(ForceSkill.CLOAK, 0, 3, 6),
				forceBody = new GuiSkillTreeItem(ForceSkill.BODY, 1, 2, 7),
		forceSense = new GuiSkillTreeItem(ForceSkill.SENSE, -1, 0, 15),
			forceSight = new GuiSkillTreeItem(ForceSkill.SIGHT, -2, 0, 16),
			auraSense = new GuiSkillTreeItem(ForceSkill.AURA, -1, -1, 17),
		alter = new GuiSkillTreeItem(ForceSkill.ALTER, 1, -1, 9),
			telekinesis = new GuiSkillTreeItem(ForceSkill.TELEKINESIS, 2, -1, 10),
				forceLeap = new GuiSkillTreeItem(ForceSkill.LEAP, 2, -2, 11),
				forcePush = new GuiSkillTreeItem(ForceSkill.PUSH, 3, -2, 12),
					forcePull = new GuiSkillTreeItem(ForceSkill.PULL, 4, -1, 13),
					forceBlast = new GuiSkillTreeItem(ForceSkill.BLAST, 5, -2, 14),
			combustion = new GuiSkillTreeItem(ForceSkill.FIRE, 1, 0, 8);
	
	/** All registered skill tree items. */
	public ArrayList<GuiSkillTreeItem> items = new ArrayList<GuiSkillTreeItem>();
	/** The current progress to display. */
	public byte progressCurrent;
	/** The current points to display. */
	public short points;
	/** The current selected skills to display. */
	public ForceSkill[] selected;
	
	/** Minimum and maximum values for scrolling bounds. */
	int minX, maxX, minY, maxY;
	/** The width and height of the GUI. */
	int xSize, ySize;
	
	/** The player being tracked. */
	EntityPlayer player;
	
	/** Constructor.<br/>
	 * Registers items, sets size, player and other values and calculates scroll bounds. */
	public GuiSkillTree(EntityPlayer player) {
		registerItems(focus, meditation, control, forceSpeed, forceSpeedII,
			forceSpeedIII, controlII, forceCloak, forceBody, forceSense,
			forceSight, auraSense, alter, telekinesis, forceLeap,
			forcePush, forcePull, forceBlast, combustion);
		
		this.xSize = 256;
		this.ySize = 185;
		this.player = player;
		
		this.minX = Integer.MAX_VALUE;
		this.maxX = -Integer.MAX_VALUE;
		this.minY = Integer.MAX_VALUE;
		this.maxY = -Integer.MAX_VALUE;
		
		ExtendedPropertiesForceSkills prop = ExtendedPropertiesForceSkills.get(player);
		this.progressCurrent = prop.progressCurrent;
		this.points = prop.points;
		this.selected = prop.selected;
		
		for(GuiSkillTreeItem item : items) {
			if(item.getX() + 16 < minX) minX = item.getX() + 16;
			if(item.getY() + 16 < minY) minY = item.getY() + 16;
			if(item.getX() + 16 > maxX) maxX = item.getX() + 16;
			if(item.getY() + 16 > maxY) maxY = item.getY() + 16;
			
			item.setIsGained(prop.skillsAttained.get(item.getSkill()));
		}
		
		if(this.scrollX == Integer.MAX_VALUE && this.scrollY == Integer.MAX_VALUE) {
			this.scrollX = 96;
			this.scrollY = 60;
		}
	}
	
	/** Activates a skill (only visually).
	 * @param skill The skill to activate. */
	public void enable(ForceSkill skill) {
		GuiSkillTreeItem item = getItemFromSkill(skill);
		if(item != null) item.setGained();
	}
	
	/** Sets the value of points (only visually).
	 * @param points The point value to set. */
	public void updatePoints(short points) {
		this.points = points;
	}
	
	/** Select a skill (only visually).
	 * @param select The skill to select. */
	public void updateSelected(int i, ForceSkill select) {
		this.selected[i] = select;
	}
	
	/**	@param skill The skill to find.
	 * @return The item for the specified skill. */
	public GuiSkillTreeItem getItemFromSkill(ForceSkill skill) {
		for(GuiSkillTreeItem item : this.items) {
			if(item.getSkill() == skill) {
				return item;
			}
		}
		return null;
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	/** Register the given items.
	 * @param list The items to register. */
	public void registerItems(GuiSkillTreeItem... list) {
		for(GuiSkillTreeItem item : list) {
			items.add(item);
			
			ForceSkill parentSkill = item.getSkill().getParent();
			if(parentSkill != null) {
				item.setParent(getItemFromSkill(parentSkill));
			}
		}
	}
	
	/** Holds whether or not the mouse is currently held down. */
	boolean mouseDown = false;
	/** Holds whether or not the mouse was inside the window when it was pressed down. */
	boolean inArea = false;
	/** Rolling starting position for the mouse before it has been dragged. */
	int mouseXStart, mouseYStart;
	/** Current X and Y scrolling. */
	private static int scrollX = Integer.MAX_VALUE, scrollY = Integer.MAX_VALUE;
	
	public void drawScreen(int x, int y, float p_73863_3_) {
		int[] scrollBounds = {
			(width - xSize) / 2 + 16,
			(height - ySize) / 2 + 16,
			(width - xSize) / 2 + 16 + 224,
			(height - ySize) / 2 + 16 + 153
		};
		
		if(Mouse.isButtonDown(0)) {
			if(!mouseDown) {
				mouseDown = true;
				mouseXStart = x;
				mouseYStart = y;
				inArea = x >= scrollBounds[0] && x < scrollBounds[2]
					&& y >= scrollBounds[1] && y < scrollBounds[3];
			} else if(inArea) {
				scrollX += x - mouseXStart;
				scrollX = Math.min(maxX + 48, Math.max(minX - 48, scrollX));
				mouseXStart = x;
				scrollY += y - mouseYStart;
				scrollY = Math.min(maxY + 48, Math.max(minY - 48, scrollY));
				mouseYStart = y;
			}
		} else if(mouseDown) {
			mouseDown = false;
		}
		
		/*if(mouseDown) {
			scrollX += x - mouseXStart;
			scrollX = Math.min(maxX + 48, Math.max(minX - 48, scrollX));
			mouseXStart = x;
			scrollY += y - mouseYStart;
			scrollY = Math.min(maxY + 48, Math.max(minY - 48, scrollY));
			mouseYStart = y;
			
			if(!Mouse.isButtonDown(0)) {
				mouseDown = false;
			}
		} else if(x >= scrollBounds[0]
				&& x <= scrollBounds[2]
				&& y >= scrollBounds[1]
				&& y <= scrollBounds[3]
				&& Mouse.isButtonDown(0) && inArea) {
			mouseDown = true;
			mouseXStart = x;
			mouseYStart = y;
		}*/
		
		this.drawDefaultBackground();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(space);
		this.drawTexturedModalRect((width - xSize) / 2 + 16, (height - ySize) / 2 + 16, -scrollX, -scrollY, 224, 153);
		
		Lightsaber.mc.getTextureManager().bindTexture(background);
		glEnable(GL_BLEND);
		
		glPushMatrix();
		glTranslatef(scrollBounds[0], scrollBounds[1], 0);
		for(GuiSkillTreeItem i : items) {
			if(i.isWithin(-16, -16, 256, 185, -scrollX, -scrollY)) {
				i.render(-scrollX, -scrollY);
			}
		}
		
		for(GuiSkillTreeItem i : items) {
			if(i.isWithin(-16, -16, 256, 185, -scrollX, -scrollY)) {
				String text = ChatFormatting.BOLD + "";
				for(int u = 0; u < Constants.MAX_SELECTED; u++) {
					if(this.selected[u] == i.getSkill()) {
						text += (text.length() > 2 ? "," : "") + (u + 1);
					}
				}
				int xSpacing = 30;
				int ySpacing = 38;
				int yOffset = 19;
				
				int currentX = i.getX() + scrollX;
				int currentY = i.getY() + scrollY;
				
				this.drawString(Lightsaber.mc.fontRenderer, text, currentX + 32 - Lightsaber.mc.fontRenderer.getStringWidth(text), currentY + 32 - Lightsaber.mc.fontRenderer.FONT_HEIGHT, 0xffffff);
			}
		}
		glPopMatrix();
		
		glColor3f(1, 1, 1);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);
		
		//
		this.drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
		//
		
		GuiTomeLore.lore.render((width - xSize) / 2 + 10, (height - ySize) / 2 - 21, false);
		GuiTomeLore.skills.render((width - xSize) / 2 + 75, (height - ySize) / 2 - 21, true);
		
		if(GuiTomeLore.lore.isClicked((width - xSize) / 2 + 10, (height - ySize) / 2 - 21, x, y)) {
			mc.displayGuiScreen(new GuiTomeLore(this.player));
			Lightsaber.mc.thePlayer.playSound("random.click", 1, 1);
		}
		
		this.drawTexturedModalRect((width - xSize) / 2 + 15, (height - ySize) / 2 + 173, 0, 249, Math.round(((float) this.progressCurrent / (float) Constants.MAX_PROGRESS) * 182F), 5);
		this.fontRendererObj.drawString(Strings.formatCounter(points, Strings.POINTS), (width - xSize) / 2 + 200, (height - ySize) / 2 + 172, Constants.GUI_TEXT_COLOR);
		
		//this.fontRendererObj.drawString(Strings.translate(Strings.SKILLS_MENU), (width - xSize) / 2 + 15, (height - ySize) / 2 + 5, Constants.GUI_TEXT_COLOR);
		
		for(GuiSkillTreeItem i : this.items) {
			if(x >= scrollBounds[0] && x <= scrollBounds[2] && y >= scrollBounds[1] && y <= scrollBounds[3] && i.isOver(x - (width - xSize) / 2 - scrollX - 16, y - (height - ySize) / 2 - scrollY - 16)) {
				ArrayList<String> s = new ArrayList<String>();
				int dist = i.distanceFromLastGained(i);
				s.add(Strings.garble(i.getSkill().getName(), dist - 1));
				
				if(dist > 1) {
					s.add(Strings.sequenceCodes(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC) + Strings.translate(Strings.UNKNOWN));
				} else {
					if(dist == 1) {
						s.add(ChatFormatting.DARK_RED + Strings.translate(Strings.COSTS) + Strings.formatCounter(i.getSkill().getCost(), Strings.POINTS));
					} else {
						s.add(ChatFormatting.DARK_GREEN + Strings.translate(Strings.BOUGHT));
					}
					
					for(String line : i.getSkill().getDescription()) {
						s.add(Strings.sequenceCodes(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC) + line);
					}
				}
				if(i.parent != null && dist < 3) {
					s.add(ChatFormatting.DARK_AQUA + Strings.translate(Strings.REQUIRES_SKILL) + i.parent.getSkill().getName());
				}
				this.drawHoveringText(s, x, y, Minecraft.getMinecraft().fontRenderer);
				
				if(Mouse.isButtonDown(0)) {
					Lightsaber.networkWrapper.sendToServer(new BuySkillMessage(i.getSkill().getId()));
				}
				
				int[] keys = {Keyboard.KEY_1, Keyboard.KEY_2, Keyboard.KEY_3, Keyboard.KEY_4, Keyboard.KEY_5, Keyboard.KEY_6, Keyboard.KEY_7, Keyboard.KEY_8, Keyboard.KEY_9, Keyboard.KEY_0};
				for(int u = 0; u < Constants.MAX_SELECTED; u++) {
					if(Keyboard.isKeyDown(keys[u])) {
						Lightsaber.networkWrapper.sendToServer(new SetSelectedMessage(u, i.getSkill().getId()));
					}
				}
				break;
			}
		}
		
		glDisable(GL_BLEND);
	}
}