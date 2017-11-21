package tk.nukeduck.generation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import tk.nukeduck.generation.client.codeblocks.BlockStack;

public class BlockProgram {
	public static final String BLOCKS_KEY = "Blocks";
	public static final String POINTER_KEY = "Pointer";

	public final List<BlockStack> stacks = new ArrayList<BlockStack>();
	public short start, length;

	public BlockProgram(BlockStack... blocks) {
		this.addStacks(blocks);
	}
	public BlockProgram addStacks(BlockStack... stacks) {
		for(BlockStack stack : stacks) {
			this.stacks.add(stack);
		}
		return this;
	}

	public boolean writeToNBT(NBTTagCompound tag) {
		tag.setShort(POINTER_KEY, this.start);

		NBTTagList blocks = new NBTTagList();
		for(BlockStack stack : this.stacks) {
			blocks.appendTag(stack.writeToNBT());
		}
		tag.setTag(BLOCKS_KEY, blocks);
		return true;
	}

	public boolean readFromNBT(NBTTagCompound tag) {
		if(!tag.hasKey(BLOCKS_KEY, 9) || !tag.hasKey(POINTER_KEY, 2)) return false;

		this.start = tag.getShort(POINTER_KEY);
		NBTTagList blocks = tag.getTagList(BLOCKS_KEY, 10);

		this.length = 0;
		for(int i = 0; i < blocks.tagCount(); i++) {
			BlockStack stack;
			if((stack = BlockStack.loadFromNBT(blocks.getCompoundTagAt(i))) == null) {
				return false;
			}
			this.length += stack.getLength();
			this.addStacks(stack);
		}

		return true;
	}
}
