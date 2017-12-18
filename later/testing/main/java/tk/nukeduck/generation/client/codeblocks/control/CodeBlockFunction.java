package tk.nukeduck.generation.client.codeblocks.control;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.comparison.CodeBlockOpen;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockTextBox;

public class CodeBlockFunction extends CodeBlockOpen {
	private CodeBlockTextBox name;
	public int x, y;

	public CharSequence getName() {
		return this.name.getText();
	}

	public CodeBlockFunction(String name, int x, int y) {
		super(BlockType.CONTROL, 255);
		this.headerParts.add(new CodeBlockTranslate("function"));
		this.headerParts.add(this.name = new CodeBlockTextBox(name));
		this.x = x;
		this.y = y;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.evaluateChildren(world, x, y, z);
	}

	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}
