package tk.nukeduck.generation.client.codeblocks.action;

import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockTextBox;
import tk.nukeduck.generation.client.codeblocks.value.IString;

public class CodeBlockChatMessage extends CodeBlock {
	public CodeBlockContainerTyped<IString> textBox;

	public CodeBlockChatMessage() {
		super(BlockType.ACTION, 32);
		this.headerParts.add(new CodeBlockTranslate("send"));
		this.headerParts.add(this.textBox = CodeBlockContainerTyped.create(IString.class));
		this.headerParts.add(new CodeBlockTranslate("toChat"));
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.textBox.get() == null) return;
		this.textBox.evaluate(world, x, y, z);
		mc.thePlayer.addChatMessage(new ChatComponentText(this.textBox.get().getString()));
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ZERO;
	}
}
