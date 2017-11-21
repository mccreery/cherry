package tk.nukeduck.lightsaber.client.gui.tome;

import tk.nukeduck.lightsaber.entity.skills.ForceSkill;

public class GuiSkillTreeItemSpeed extends GuiSkillTreeItem {
	int count = 1;
	
	/** @param count How many speed symbols to display.
	 * @see GuiSkillTreeItem#GuiSkillTreeItem(ForceSkill, int, int, int) */
	public GuiSkillTreeItemSpeed(ForceSkill skill, int x, int y, int iconIndex, int count) {
		super(skill, x, y, iconIndex);
		this.count = count;
	}
	
	@Override
	public void drawIcon(int dist, int currentX, int xCutoff, int xCutoffRight, int currentY, int yCutoff, int yCutoffDown) {
		switch(count) {
			case 2:
				super.drawIcon(dist, currentX - 2, xCutoff, xCutoffRight, currentY, yCutoff, yCutoffDown);
				super.drawIcon(dist, currentX + 2, xCutoff, xCutoffRight, currentY, yCutoff, yCutoffDown);
				break;
			case 3:
				super.drawIcon(dist, currentX - 3, xCutoff, xCutoffRight, currentY, yCutoff, yCutoffDown);
				super.drawIcon(dist, currentX, xCutoff, xCutoffRight, currentY, yCutoff, yCutoffDown);
				super.drawIcon(dist, currentX + 3, xCutoff, xCutoffRight, currentY, yCutoff, yCutoffDown);
				break;
			default:
				super.drawIcon(dist, currentX, xCutoff, xCutoffRight, currentY, yCutoff, yCutoffDown);
		}
	}
}