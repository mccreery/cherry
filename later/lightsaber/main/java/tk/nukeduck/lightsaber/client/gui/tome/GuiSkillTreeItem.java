package tk.nukeduck.lightsaber.client.gui.tome;

import static org.lwjgl.opengl.GL11.glColor3f;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.Gui;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.entity.skills.ForceSkill;
import tk.nukeduck.lightsaber.util.Constants;

public class GuiSkillTreeItem extends Gui {
	/** Whether or not this item should show as gained. */
	private boolean isGained = false;
	/** Constant values for spacing out items in the grid. */
	public static int xSpacing = 29, ySpacing = 37, yOffset = 19;
	
	/** This item's parent. */
	public GuiSkillTreeItem parent = null;
	/** This item's children. */
	public List<GuiSkillTreeItem> children = new ArrayList<GuiSkillTreeItem>();
	
	/** The skill this item activates. */
	private ForceSkill skill;
	/** The X and Y coordinates on the grid to render this item at. */
	public int x, y;
	/** The icon index of this item. */
	private int iconIndex;
	
	/** Constructor.
	 * @param skill The skill this item activates.
	 * @param x The X coordinate for the grid.
	 * @param y The Y coordinate for the grid.
	 * @param iconIndex The index of the icon of this item in the texture. */
	public GuiSkillTreeItem(ForceSkill skill, int x, int y, int iconIndex) {
		this.skill = skill;
		this.x = x;
		this.y = y;
		this.iconIndex = iconIndex;
	}
	
	/** @return The skill this item activates. */
	public ForceSkill getSkill() {
		return this.skill;
	}
	
	/** @returns This item's icon index. */
	public int getIconIndex() {
		return this.iconIndex;
	}
	
	/** Sets the parent of this item to the given item.
	 * @param parent The item to set as parent.
	 * @return This item. */
	public GuiSkillTreeItem setParent(GuiSkillTreeItem parent) {
		this.parent = parent;
		parent.children.add(this);
		return this;
	}
	
	/** Recursively copies this item and its parents, then returns the copy.
	 * @return The copied item. */
	public GuiSkillTreeItem copyUp() {
		GuiSkillTreeItem copy = new GuiSkillTreeItem(this.skill, this.x, this.y, this.iconIndex);
		if(this.parent != null) copy.parent = this.parent.copyUp();
		return copy;
	}
	
	/** Sets this item as gained (only visually).
	 * @return This item. */
	public GuiSkillTreeItem setGained() {return setIsGained(true);}
	/** Sets this item as gained or not gained (only visually).
	 * @param gained Whether or not the item should be gained.
	 * @return This item. */
	public GuiSkillTreeItem setIsGained(Boolean gained) {
		this.isGained = gained;
		return this;
	}
	
	/** @return Whether or not this item is gained. */
	public boolean getIsGained() {
		return this.isGained;
	}
	
	/** @return The X position of this item in screen space. */
	public int getX() {
		return this.x * xSpacing;
	}
	
	/** @return The Y position of this item in screen space. */
	public int getY() {
		return y * ySpacing + ((x % 2) * yOffset);
	}
	
	/** Renders the item on the screen.
	 * @param a Scroll X coordinate.
	 * @param b Scroll Y coordinate. */
	public void render(int a, int b) {
		xSpacing = 30;
		ySpacing = 38;
		yOffset = 19;
		
		int w = 224;
		int h = 153;
		
		int currentX = x * xSpacing - a;
		int currentY = y * ySpacing + ((x % 2) * yOffset) - b;
		
		int dist = distanceFromLastGained(this);
		float brightness = 1.0F - dist * 0.3F;
		glColor3f(brightness, brightness, brightness);
		
		int xCutoff = currentX + 16 < 0 ? 16 : 0;
		int yCutoff = currentY + 16 < 0 ? 16 : 0;
		
		int xCutoffRight = currentX + 16 > w ? 16 : 0;
		int yCutoffDown = currentY + 16 > h ? 16 : 0;
		
		this.drawTexturedModalRect(currentX + xCutoff				, currentY + yCutoff				, 0 + xCutoff									, (this.getIsGained() ? 185 : 217) + yCutoff			, 32 - xCutoff - xCutoffRight, 32 - yCutoff - yCutoffDown);
		this.drawTexturedModalRect(currentX + 8 + xCutoff / 2		, currentY + 8 + yCutoff / 2		, 32 + (getIndex() % 5) * 16 + xCutoff / 2		, 185 + yCutoff / 2		, 16 - (xCutoff + xCutoffRight) / 2, 16 - (yCutoff + yCutoffDown) / 2);
		this.drawIcon(dist, currentX, xCutoff, xCutoffRight, currentY, yCutoff, yCutoffDown);
	}
	
	/** Render the item's icon.
	 * @param dist Distance from last gained item.
	 * @param currentX Current X coordinate.
	 * @param xCutoff Cutoff X value in the corners of the window.
	 * @param xCutoffRight Cutoff X value in the corners of the window.
	 * @param currentY Current Y coordinate.
	 * @param yCutoff Cutoff Y value in the corners of the window.
	 * @param yCutoffDown Cutoff Y value in the corners of the window. */
	public void drawIcon(int dist, int currentX, int xCutoff, int xCutoffRight, int currentY, int yCutoff, int yCutoffDown) {
		int iX = 0;
		if(dist < 2) {
			iX = this.getIconIndex();
		}
		this.drawTexturedModalRect(currentX + 4 + xCutoff / 4 * 3	, currentY + 4 + yCutoff / 4 * 3	, 32 + (iX % 9) * 24 + xCutoff / 4 * 3				, 201 + yCutoff / 4 * 3 + 24 * (int) (iX / 9)			, 24 - (xCutoff + xCutoffRight) / 4 * 3, 24 - (yCutoff + yCutoffDown) / 4 * 3);
	}
	
	/** Checks whether a point is within the bounds given.
	 * @param a X coordinate.
	 * @param b Y coordinate.
	 * @param width Width of this item.
	 * @param height Height of this item.
	 * @param scrollX Scroll X.
	 * @param scrollY Scroll Y. */
	public boolean isWithin(int a, int b, int width, int height, int scrollX, int scrollY) {
		int currentX = x * xSpacing + 16 - scrollX;
		int currentY = y * ySpacing + 16 + ((x % 2) * yOffset) - scrollY;
		
		return currentX >= a && currentX <= a + width
			&& currentY >= b && currentY <= a + height;
	}
	
	/** Checks whether a point is within this item's bounds.
	 * @param a X coordinate.
	 * @param b Y coordinate. */
	public boolean isOver(int a, int b) {
		int currentX = x * xSpacing;
		int currentY = y * ySpacing + ((x % 2) * yOffset);
		
		return a >= currentX && a <= currentX + 32
			&& b >= currentY && b <= currentY + 32;
	}
	
	/** Gets how many places up the tree there are between the given item and its first gained parent.
	 * @param item The item to start with.
	 * @return The distance. */
	public int distanceFromLastGained(GuiSkillTreeItem item) {
		GuiSkillTreeItem checking = item;
		int i = 0;
		while(!checking.getIsGained()) {
			if(checking.parent == null) return 5;
			checking = checking.parent;
			i++;
		}
		return i;
		//
		/*for(int i = index; i >= 0; i--) {
			if(tree[i].getIsGained()) {
				return index - i;
			}
		}
		return 5;*/
	}
	
	/** @return the index in the tree of this item. */
	public int getIndex() {
		GuiSkillTreeItem a = this.copyUp();
		int i = 0;
		while(a.parent != null) {
			a = a.parent;
			i++;
		}
		return i;
	}
}