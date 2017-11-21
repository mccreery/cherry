package tk.nukeduck.generation.client.codeblocks;

import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

public class CodeBlockTranslate extends CodeBlockText {
	public CodeBlockTranslate(String text) {
		super(text);
	}

	@Override
	public String getText() {
		return I18n.format("code." + this.text, new Object[0]);
	}
}
