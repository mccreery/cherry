package tk.nukeduck.generation.client.codeblocks.action;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import tk.nukeduck.generation.Generation;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IString;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockChatMessage extends CodeBlock {
	public CodeBlockContainerTyped<IString> container;

	public CodeBlockChatMessage() {
		super(BlockCategory.ACTION, Constants.CHAT, 32);
		this.headerParts.add(new CodeBlockTranslate("send"));
		this.headerParts.add(this.container = CodeBlockContainerTyped.create(IString.class));
		this.headerParts.add(new CodeBlockTranslate("toChat"));
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		if(this.container.getChildTyped() == null) return;
		this.container.evaluate(world, x, y, z);
		Generation.mc.thePlayer.addChatMessage(new ChatComponentText(this.container.getChildTyped().getString())); // TODO make it possible to send a message to everyone, or to one player
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ZERO;
	}

	@Override
	public String getUnlocalizedName() {
		return "chat";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockChatMessage block = new CodeBlockChatMessage();
		if(tag.hasKey("Child")) this.container.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Child")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.container.hasChild()) tag.setTag("Child", this.container.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.container.hasChild()) length += this.container.getChild().getBlockCount();
		return length;
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockChatMessage block = new CodeBlockChatMessage();
		if(this.container.hasChild()) block.container.setChild(this.container.getChild().copy());
		return block;
	}
}
