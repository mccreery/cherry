package tk.nukeduck.generation.client.codeblocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockLevel;
import tk.nukeduck.generation.util.NBTConstants.TagType;

public class BlockStack {
	public int x, y;
	public List<ICodeBlock> blocks;

	public BlockStack(int x, int y) {
		this(x, y, new ArrayList<ICodeBlock>());
	}
	public BlockStack(int x, int y, List<ICodeBlock> blocks) {
		this.x = x;
		this.y = y;
		this.blocks = blocks;
	}

	public boolean append(ICodeBlock block) {
		return this.insert(this.blocks.size(), block);
	}
	public boolean insert(int i, ICodeBlock block) {
		if(this.blocks.isEmpty() || i == 0 && this.insertZero(block) || this.blocks.get(i - 1).insertAfter(block)) {
			this.blocks.add(i, block);
			return true;
		}
		return false;
	}

	protected boolean insertZero(ICodeBlock block) {
		return block.getLevel() == BlockLevel.ZERO;
	}

	public void clear() {
		this.blocks.clear();
	}

	public int getWidth() {
		int width = this.blocks.get(0).getWidth();
		for(int i = 1; i < this.blocks.size(); i++) {
			int newWidth = this.blocks.get(i).getWidth();
			if(newWidth > width) width = newWidth;
		}
		return width;
	}
	public int getHeight() {
		int height = 0;
		for(int i = 0; i < this.blocks.size(); i++) {
			height += this.blocks.get(i).getHeight();
		}
		return height;
	}

	public boolean keyTyped(char c, int i) {
		for(ICodeBlock block : this.blocks) {
			if(block.keyTyped(c, i)) return true;
		}
		return false;
	}
	public boolean mouseClicked(int i, int j, int button) {
		for(ICodeBlock block : this.blocks) {
			if(block.mouseClicked(i, j, button)) return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return this.blocks.size() == 0;
	}

	public void render(int x, int y) {
		if(!this.isEmpty()) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0, 0, 500F);

			for(ICodeBlock block : this.blocks) {
				block.revalidate();
				block.render(x, y);
				y += block.getHeight();
			}
			GL11.glPopMatrix();
		}
	}

	public boolean checkValid() {
		if(this.isEmpty()) return false;

		for(ICodeBlock block : this.blocks) {
			if(block.revalidate()) return true;
		}
		return false;
	}

	/** Attempts to pick up blocks from this stack
	 * @param x Stack X position
	 * @param y Stack Y position
	 * @see ICodeBlock#pickUpBlock(int, int, int, int, BlockStack, boolean) */
	public boolean pickUpBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard, boolean duplicate) {
		if(this.isEmpty()) return false;

		//TODO stacks don't currently invalidate and validate
		Iterator<ICodeBlock> it = this.blocks.iterator();
		while(it.hasNext()) {
			ICodeBlock block = it.next();
			int height = block.getHeight();
			if(block.pickUpBlock(x, y, mouseX, mouseY, clipboard, duplicate)) {
				if(clipboard.blocks.get(0) == block) {
					if(!duplicate) it.remove();
					while(it.hasNext()) {
						clipboard.append(it.next());
						if(!duplicate) it.remove();
					}
				}
				//this.invalidate();
				return true;
			}
			y += height;
		}
		return false;
	}

	/** Attempts to place a block into this stack
	 * @param x Stack X position
	 * @param y Stack Y position
	 * @see ICodeBlock#placeBlock(int, int, int, int, BlockStack) */
	public boolean placeBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard) {
		if(this.isEmpty()) return false;

		for(ICodeBlock block : this.blocks) {
			if(block.placeBlock(x, y, mouseX, mouseY, clipboard)) return true;
		}
		return false;
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setShort("XPos", (short) this.x);
		tag.setShort("YPos", (short) this.y);

		NBTTagList list = new NBTTagList();
		for(ICodeBlock block : this.blocks) {
			list.appendTag(block.writeToNBT());
		}
		tag.setTag("Blocks", list);
		return tag;
	}

	public static BlockStack loadFromNBT(NBTTagCompound tag) {
		BlockStack stack = new BlockStack(tag.getShort("XPos") & 0xFFFF, tag.getShort("YPos") & 0xFFFF);
		if(tag.hasKey("Blocks")) {
			NBTTagList list = tag.getTagList("Blocks", TagType.COMPOUND);
			for(int i = 0; i < list.tagCount(); i++) {
				stack.append(ICodeBlock.loadFromNBT(list.getCompoundTagAt(i)));
			}
		}
		return stack;
	}

	public int getLength() {
		int length = 0;
		for(ICodeBlock block : this.blocks) {
			length += block.getBlockCount();
		}
		return length;
	}
}
