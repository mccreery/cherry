package tk.nukeduck.generation.client;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import tk.nukeduck.generation.block.ContainerProgrammer;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.BlockStack;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.action.CodeBlockChatMessage;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockIf;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockConstant;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockConstant.ConstantType;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockJoinText;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockRawInteger;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockTextBox;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockEqual;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockGreater;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockLesser;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockAbs;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockAdd;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockDivide;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockMultiply;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSqrt;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSquare;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSubtract;

public class GuiProgrammer extends GuiContainer {
	private ContainerProgrammer programmerSlots;

	public List<BlockStack> stacks = new ArrayList<BlockStack>();
	public BlockStack clipboard = new BlockStack(0, 0, new ArrayList<ICodeBlock>());

	private static final ResourceLocation BACKGROUND = new ResourceLocation("alchemy", "textures/gui/programmer.png");
	private static final ResourceLocation TABS =       new ResourceLocation("alchemy", "textures/gui/tabs.png");

	private BlockSlot[] slots = new BlockSlot[20];
	private GuiButton burn;

	public GuiProgrammer(Container container) {
		super(container);
		this.programmerSlots = (ContainerProgrammer) container;

		this.xSize = 251;
		this.ySize = 230;
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);

		//int left = (this.width - this.xSize) / 2 + 174;
		//int top = (this.height - this.ySize) / 2 + 44;
		for(int i = 0; i < this.slots.length; i++) {
			this.slots[i] = new BlockSlot(174 + (i % 4) * 18, 94 + (i / 4) * 18, null);
		}
		this.populateBlocks(BlockCategory.CONTROL);

		this.buttonList.add(this.burn = new GuiButton(0, this.guiLeft + 173, this.guiTop + 36, 72, 20, "Burn"));
		this.burn.enabled = this.programmerSlots.canWrite();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		List<String> errors = new ArrayList<String>();
		for(BlockStack stack : this.stacks) {
			for(ICodeBlock block : stack.blocks) {
				block.checkErrors(errors);
			}
		}

		/*if(errors.size() == 0) {
			for(ICodeBlock block : this.blocks) {
				block.evaluate(mc.theWorld, (int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ);
			}
		} else {*/
			for(String error : errors) {
				System.err.println(error);
			}
		//}

		if(errors.size() == 0 && this.programmerSlots.canWrite()) {
			this.programmerSlots.write();
		}
	}

	public void updateScreen() {
		//text.cursorTick();
	}

	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		for(int i = this.stacks.size() - 1; i >= 0; i--) {
			BlockStack stack = this.stacks.get(i);
			if(stack.keyTyped(p_73869_1_, p_73869_2_)) return;
		}
		super.keyTyped(p_73869_1_, p_73869_2_);
	}

	private float scrollH, scrollV;
	private boolean scrollingH, scrollingV;
	private int selectedCategory;

	private void playClickSound() {
		this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	}

	protected boolean selectTab(int mouseX, int mouseY, int button) {
		if(button != 0 || mouseX < this.guiLeft - 26 || mouseX > this.guiLeft) return false;

		for(int i = 0, y = this.guiTop; i < BlockCategory.values().length; i++, y += 29) {
			if(this.selectedCategory != i && mouseY >= y && mouseY < y + 28) {
				this.selectedCategory = i;
				this.populateBlocks(BlockCategory.values()[i]);
				return true;
			}
		}
		return false;
	}

	/** @return {@code true} if the mouse position is within the bounds of the GUI. */
	private boolean isInBounds(int mouseX, int mouseY) {
		return mouseX >= this.guiLeft && mouseY >= this.guiTop
			&& (mouseX < this.guiLeft + 251 && mouseY < this.guiTop + 190
			|| mouseX < this.guiLeft + 176 && mouseY < this.guiTop + 230);
	}

	protected void mouseClicked(int mouseX, int mouseY, int button) {
		int leftBox = this.guiLeft + 8 - this.scrollX;
		int topBox = this.guiTop + 18 - this.scrollY;

		if(this.selectTab(mouseX, mouseY, button)) {
			this.playClickSound();
			return;
		}

		if(mouseX >= this.guiLeft + 8 && mouseX < this.guiLeft + 152 && mouseY >= this.guiTop + 18 && mouseY < this.guiTop + 114) {
			if(this.clipboard.isEmpty()) {
				for(int i = this.stacks.size() - 1; i >= 0; i--) {
					BlockStack stack = this.stacks.get(i);
					if(stack.mouseClicked(mouseX - leftBox, mouseY - topBox, button)) return;
				}

				for(int i = this.stacks.size() - 1; i >= 0; i--) {
					BlockStack stack = this.stacks.get(i);
					if(stack.pickUpBlock(stack.x, stack.y, mouseX - leftBox, mouseY - topBox, this.clipboard, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
						if(stack.isEmpty()) this.stacks.remove(stack);
						return;
					}
				}
				super.mouseClicked(mouseX, mouseY, button);
			} else {
				for(int i = this.stacks.size() - 1; i >= 0; i--) {
					BlockStack stack = this.stacks.get(i);
					if(stack.placeBlock(stack.x, stack.y, mouseX - leftBox, mouseY - topBox, this.clipboard)) {
						this.clipboard.clear();
						return;
					}
				}
				BlockStack newStack = new BlockStack(mouseX - leftBox, mouseY - topBox);
				for(int j = 0; j < clipboard.blocks.size(); j++) {
					newStack.append(clipboard.blocks.get(j));
				}
				this.clipboard.clear();
				this.stacks.add(newStack);
				this.updateBounds();
				return;
			}
		}

		if(mouseX >= this.guiLeft + 156 && mouseX < this.guiLeft + 168 && mouseY >= this.guiTop + 18 && mouseY < this.guiTop + 114) {
			scrollingV = true;
			this.scrollV = (float) (mouseY - this.guiTop - 18 - 7) / (float) (96 - 15);
			if(this.scrollV < 0.0F) this.scrollV = 0.0F;
			else if(this.scrollV > 1.0F) this.scrollV = 1.0F;

			this.updateScroll();
		} else if(mouseX >= this.guiLeft + 8 && mouseX < this.guiLeft + 150 && mouseY >= this.guiTop + 120 && mouseY < this.guiTop + 132) {
			scrollingH = true;
			this.scrollH = (float) (mouseX - this.guiLeft - 8 - 7) / (float) (142 - 15);
			if(this.scrollH < 0.0F) this.scrollH = 0.0F;
			else if(this.scrollH > 1.0F) this.scrollH = 1.0F;

			this.updateScroll();
		}

		for(BlockSlot slot : this.slots) {
			if(slot.hasBlock() && slot.isMouseOver(mouseX - this.guiLeft, mouseY - this.guiTop)) {
				this.clipboard.append(slot.block.copy());
				break;
			}
		}

		if(this.clipboard.isEmpty()) {
			super.mouseClicked(mouseX, mouseY, button);
		} else if(!this.isInBounds(mouseX, mouseY)) {
			this.clipboard.clear();
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int p_146273_3_, long p_146273_4_) {
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;

		if(this.scrollingV) {
			this.scrollV = (float) (mouseY - top - 18 - 7) / (float) (96 - 15);
			if(this.scrollV < 0.0F) this.scrollV = 0.0F;
			else if(this.scrollV > 1.0F) this.scrollV = 1.0F;

			this.updateScroll();
		} else if(this.scrollingH) {
			this.scrollH = (float) (mouseX - left - 8 - 7) / (float) (142 - 15);
			if(this.scrollH < 0.0F) this.scrollH = 0.0F;
			else if(this.scrollH > 1.0F) this.scrollH = 1.0F;

			this.updateScroll();
		}
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {
		super.mouseMovedOrUp(mouseX, mouseY, which);
		if(which != -1) this.scrollingH = this.scrollingV = false;
	}

	private int minX, minY, maxX, maxY;

	public void updateBounds() {
		this.minX = this.stacks.get(0).x;
		this.minY = this.stacks.get(0).y;
		this.maxX = this.minX + this.stacks.get(0).getWidth();
		this.maxY = this.minY + this.stacks.get(0).getHeight();

		for(int i = 1; i < this.stacks.size(); i++) {
			BlockStack block = this.stacks.get(i);
			if(block.x < this.minX) this.minX = block.x;
			if(block.y < this.minY) this.minY = block.y;
			if(block.x + block.getWidth() > this.maxX) this.maxX = block.x + block.getWidth();
			if(block.y + block.getHeight() > this.maxY) this.maxY = block.y + block.getHeight();
		}

		this.minX -= 10;
		this.minY -= 10;
		this.maxX += 10;
		this.maxY += 10;
		this.maxX -= 142;
		this.maxY -= 96;

		this.updateScroll();
	}

	@Override
	public void handleMouseInput() {
		if(!this.scrollingH && !this.scrollingV) {
			int scroll = Mouse.getEventDWheel();

			if(scroll != 0) {
				this.scrollV -= (float) scroll / 1000.0F;
				if(this.scrollV < 0.0F) this.scrollV = 0.0F;
				if(this.scrollV > 1.0F) this.scrollV = 1.0F;
				this.updateScroll();
			}
		}
		super.handleMouseInput();
	}

	private int scrollX, scrollY;

	public void updateScroll() {
		if(this.maxX <= this.minX) {
			this.scrollX = this.minX;
		} else {
			this.scrollX = Math.round((this.maxX - this.minX) * this.scrollH) + this.minX;
		}
		if(this.maxY <= this.minY) {
			this.scrollY = this.minY;
		} else {
			this.scrollY = Math.round((this.maxY - this.minY) * this.scrollV) + this.minY;
		}
	}

	public void populateBlocks(BlockCategory category) {
		int i;
		for(i = 0; i < this.slots.length && i < category.size(); i++) {
			this.slots[i].block = category.getBlock(i);
		}
		for(; i < this.slots.length; i++) {
			this.slots[i].block = null;
		}
	}

	public void drawScrollbarH(int x, int y, int width, float value) {
		mc.getTextureManager().bindTexture(TABS);
		int scrollAmt = Math.round((width - 15) * value);
		this.drawTexturedModalRect(x + scrollAmt, y, 0, 68, 15, 12);
	}
	public void drawScrollbarV(int x, int y, int height, float value) {
		mc.getTextureManager().bindTexture(TABS);
		int scrollAmt = Math.round((height - 15) * value);
		this.drawTexturedModalRect(x, y + scrollAmt, 0, 80, 12, 15);
	}

	public void drawTab(int x, int y, int index, boolean selected, IIcon icon) {
		mc.getTextureManager().bindTexture(TABS);
		this.drawTexturedModalRect(x, y, index * 32, selected ? 28 : 0, 32, 28);
		mc.getTextureManager().bindTexture(BlockSlot.TEXTURE);
		this.drawTexturedModelRectFromIcon(x + 9, y + 6, icon, 16, 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(this.BACKGROUND);
		mouseX -= this.guiLeft;
		mouseY -= this.guiTop;

		int scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
		//GL11.glEnable(GL11.GL_SCISSOR_TEST);
		//GL11.glScissor(8 * scale, 114 * scale, 142 * scale, 96 * scale);
		GL11.glTranslatef(8 - this.scrollX, 18 - this.scrollY, 0);

		for(BlockStack stack : this.stacks) {
			if(stack.checkValid()) {
				this.updateBounds();
			}
			stack.render(stack.x, stack.y);
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		for(BlockSlot slot : this.slots) {
			slot.render(this.mc, mouseX, mouseY);

			if(slot.hasBlock() && slot.isMouseOver(mouseX, mouseY)) {
				this.currentTooltip = new ArrayList<String>();
				this.currentTooltip.add(slot.getBlock().getLocalizedName());
			}
		}

		this.clipboard.render(mouseX, mouseY);
		if(this.currentTooltip != null) {
			this.drawHoveringText(this.currentTooltip, mouseX, mouseY, this.mc.fontRenderer);
		}

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(!this.clipboard.isEmpty() && !this.isInBounds(mouseX + this.guiLeft, mouseY + this.guiTop)) {
			mc.getTextureManager().bindTexture(TABS);
			this.drawTexturedModalRect(mouseX - 20, mouseY - 20, 0, 95, 16, 16);
		}

		//List<String> nan = new ArrayList<String>();
		//nan.add("Control");
		//this.drawHoveringText(nan, mouseX, mouseY, this.fontRendererObj);

		/*int j = 0;
		for(Object obj : this.inventorySlots.inventorySlots) {
			Slot slot = (Slot) obj;
			this.drawString(this.fontRendererObj, String.valueOf(j++), slot.xDisplayPosition, slot.yDisplayPosition, 0xFFFFFF);
		}*/

		/*for(BlockSlot slot : this.slots) {
			if(slot.isMouseOver(mouseX, mouseY) && slot.block != null) {
				List<String> list = new ArrayList<String>();
				list.add(slot.block.getClass().getSimpleName());
				this.drawHoveringText(list, mouseX, mouseY, this.mc.fontRenderer);
				break;
			}
		}*/
	}

	private List<String> currentTooltip;

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.currentTooltip = null;

		boolean mouseHoverTabs = mouseX >= this.guiLeft - 28 && mouseX < this.guiLeft && mouseY >= this.guiTop;

		for(int i = 0, y = this.guiTop; i < BlockCategory.values().length; i++, y += 29) {
			BlockCategory category = BlockCategory.values()[i];
			boolean selected = i == this.selectedCategory;

			if(selected) this.zLevel = 50;
			this.drawTab(this.guiLeft - 28, y, i == 0 ? 0 : 1, selected, category.getIcon());
			if(selected) this.zLevel = 0;

			if(mouseHoverTabs && mouseY >= y && mouseY < y + 28) {
				this.currentTooltip = category.getTooltip();
			}
		}

		this.mc.getTextureManager().bindTexture(this.BACKGROUND);

		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		this.drawScrollbarH(this.guiLeft + 8, this.guiTop + 120, 142, this.scrollH);
		this.drawScrollbarV(this.guiLeft + 156, this.guiTop + 18, 96, this.scrollV);

		final int color = 4210752; 
		this.fontRendererObj.drawString(I18n.format("container.programmer"), this.guiLeft + 8, this.guiTop + 6, color);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), this.guiLeft + 8, this.guiTop + 136, color);
		this.fontRendererObj.drawString(BlockCategory.values()[this.selectedCategory].getLocalizedName(), this.guiLeft + 174, this.guiTop + 82, color);
	}
}
