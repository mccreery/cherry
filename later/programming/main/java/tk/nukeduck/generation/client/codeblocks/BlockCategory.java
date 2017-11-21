package tk.nukeduck.generation.client.codeblocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IIcon;
import tk.nukeduck.generation.util.ColorUtils;

public enum BlockCategory {
	CONTROL("control", 0, 0xFFC83C),
	LOGIC("logic", 1, 0x46BEFF),
	MATH("math", 2, 0x4155F0),
	DATA("data", 3, 0xF02D1E),
	VARIABLE("variable", 4, 0xAF55E1),
	ACTION("action", 5, 0xFF9100);

	private String name;
	public final byte id;
	private final List<ICodeBlock> blocks = new ArrayList<ICodeBlock>();

	public final void addBlock(ICodeBlock block) {
		this.blocks.add(block);
	}
	public int size() {
		return this.blocks.size();
	}
	public ICodeBlock getBlock(int i) {
		return this.blocks.get(i);
	}

	public final int color, highlight;

	private IIcon icon = null;
	public void setIcon(IIcon icon) {
		this.icon = icon;
	}
	public IIcon getIcon() {
		return this.icon;
	}

	BlockCategory(String name, int id, int color) {
		this.name = name;
		this.id = (byte) id;

		this.color = 0xFF000000 | color;
		this.highlight = ColorUtils.addColor(this.color, 50);
	}

	public String getUnlocalizedName() {
		return this.name;
	}
	public final String getLocalizedName() {
		return I18n.format("category." + this.getUnlocalizedName());
	}

	public final List<String> getTooltip() {
		List<String> list = new ArrayList<String>();
		list.add(this.getLocalizedName());
		return list;
	}
}
