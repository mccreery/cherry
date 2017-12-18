package tk.nukeduck.generation.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import tk.nukeduck.generation.block.ContainerProgrammer;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.action.CodeBlockChatMessage;
import tk.nukeduck.generation.client.codeblocks.comparison.integer.CodeBlockEqualTo;
import tk.nukeduck.generation.client.codeblocks.comparison.integer.CodeBlockGreaterThan;
import tk.nukeduck.generation.client.codeblocks.comparison.integer.CodeBlockLessThan;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockFunction;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockIf;
import tk.nukeduck.generation.client.codeblocks.errors.IError;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockAbs;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockAdd;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockDivide;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockMultiply;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSqrt;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSquare;
import tk.nukeduck.generation.client.codeblocks.math.CodeBlockSubtract;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockConstant;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockConstant.ConstantType;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockJoinText;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockRawInteger;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockTextBox;

public class GuiProgrammer extends GuiContainer {
	//public GuiTextBox text;
	public List<CodeBlockFunction> blocks = new ArrayList<CodeBlockFunction>();
	public List<ICodeBlock> clipboard = new ArrayList<ICodeBlock>();

	private static final ResourceLocation BACKGROUND = new ResourceLocation("alchemy", "textures/gui/programmer.png");
	private static final ResourceLocation TABS = new ResourceLocation("alchemy", "textures/gui/tabs.png");

	private BlockSlot[] slots = new BlockSlot[20];

	//private int top, left;

	public GuiProgrammer(Container container) {
		super(container);
		this.xSize = 256;
		this.ySize = 219;
		//text = new GuiTextBox(this.fontRendererObj, 5, 5, 200, 20, 50);
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);

		//int left = (this.width - this.xSize) / 2 + 174;
		//int top = (this.height - this.ySize) / 2 + 44;
		for(int i = 0; i < this.slots.length; i++) {
			slots[i] = new BlockSlot(174 + (i % 4) * 18, 44 + (i / 4) * 18, null);
		}
		this.populateBlocks(BlockCategory.COMPARISON);

		int a = 5, i = 0;
		for(GuiButton button : new GuiButton[] {
			new GuiButton(0, 0, 0, 60, 20, "Run"),
			new GuiButton(0, 0, 0, 20, 20, "X"),
			new GuiButton(0, 0, 0, 20, 20, "Y"),
			new GuiButton(0, 0, 0, 20, 20, "Z"),
			new GuiButton(0, 0, 0, 20, 20, "+"),
			new GuiButton(0, 0, 0, 20, 20, "-"),
			new GuiButton(0, 0, 0, 20, 20, "*"),
			new GuiButton(0, 0, 0, 20, 20, "/"),
			new GuiButton(0, 0, 0, 40, 20, "Text"),
			new GuiButton(0, 0, 0, 40, 20, "Join"),
			new GuiButton(0, 0, 0, 40, 20, "Sqrt"),
			new GuiButton(0, 0, 0, 40, 20, "Abs"),
			new GuiButton(0, 0, 0, 40, 20, "Sqr"),
			new GuiButton(0, 0, 0, 20, 20, "If"),
			new GuiButton(0, 0, 0, 40, 20, "Chat"),
			new GuiButton(0, 0, 0, 20, 20, "<"),
			new GuiButton(0, 0, 0, 20, 20, ">"),
			new GuiButton(0, 0, 0, 20, 20, "="),
			new GuiButton(0, 0, 0, 20, 20, "#")
		}) {
			button.xPosition = a;
			button.yPosition = 5;
			this.buttonList.add(button);
			a += button.width + 5;
			button.id = i++;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch(button.id) {
			case 0:
				List<IError> errors = new ArrayList<IError>();
				for(ICodeBlock block : this.blocks) {
					block.checkErrors(errors);
				}
				if(errors.size() == 0) {
					for(ICodeBlock block : this.blocks) {
						block.evaluate(mc.theWorld, (int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ);
					}
				} else {
					for(IError error : errors) {
						System.err.println(error.getDescription());
					}
				}
				break;
			case 1:
				this.clipboard.add(new CodeBlockConstant(ConstantType.X));
				break;
			case 2:
				this.clipboard.add(new CodeBlockConstant(ConstantType.Y));
				break;
			case 3:
				this.clipboard.add(new CodeBlockConstant(ConstantType.Z));
				break;
			case 4:
				this.clipboard.add(new CodeBlockAdd());
				break;
			case 5:
				this.clipboard.add(new CodeBlockSubtract());
				break;
			case 6:
				this.clipboard.add(new CodeBlockMultiply());
				break;
			case 7:
				this.clipboard.add(new CodeBlockDivide());
				break;
			case 8:
				this.clipboard.add(new CodeBlockTextBox(""));
				break;
			case 9:
				this.clipboard.add(new CodeBlockJoinText());
				break;
			case 10:
				this.clipboard.add(new CodeBlockSqrt());
				break;
			case 11:
				this.clipboard.add(new CodeBlockAbs());
				break;
			case 12:
				this.clipboard.add(new CodeBlockSquare());
				break;
			case 13:
				this.clipboard.add(new CodeBlockIf());
				break;
			case 14:
				this.clipboard.add(new CodeBlockChatMessage());
				break;
			case 15:
				this.clipboard.add(new CodeBlockLessThan());
				break;
			case 16:
				this.clipboard.add(new CodeBlockGreaterThan());
				break;
			case 17:
				this.clipboard.add(new CodeBlockEqualTo());
				break;
			case 18:
				this.clipboard.add(new CodeBlockRawInteger("0"));
				break;
		}
	}

	public void updateScreen() {
		//text.cursorTick();
	}

	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		for(ICodeBlock block : this.blocks) {
			if(block.keyTyped(p_73869_1_, p_73869_2_)) return;
		}
		super.keyTyped(p_73869_1_, p_73869_2_);
	}

	private float scrollH, scrollV;
	private boolean scrollingH, scrollingV;

	protected void mouseClicked(int mouseX, int mouseY, int button) {
		int leftBox = this.guiLeft + 8 - this.scrollX;
		int topBox = this.guiTop + 18 - this.scrollY;

		if(mouseX >= this.guiLeft + 8 && mouseX < this.guiLeft + 152 && mouseY >= this.guiTop + 18 && mouseY < this.guiTop + 114) {
			if(this.clipboard.size() == 0) {
				for(ICodeBlock block : this.blocks) {
					if(block.mouseClicked(mouseX - leftBox, mouseY - topBox, button)) return;
				}
				int i = 0;
				for(CodeBlockFunction block : this.blocks) {
					if(block.populateClipboard(block.getX(), block.getY(), mouseX - leftBox, mouseY - topBox, this.clipboard, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) return;
				}
				super.mouseClicked(mouseX, mouseY, button);
			} else {
				int i = 0;
				for(CodeBlockFunction block : this.blocks) {
					if(block.placeClipboard(block.getX(), block.getY(), mouseX - leftBox, mouseY - topBox, this.clipboard)) {
						this.clipboard.clear();
						return;
					}
				}
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
				this.clipboard.add(slot.block);
				break;
			}
		}

		if(this.clipboard.size() == 0) {
			super.mouseClicked(mouseX, mouseY, button);
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
		this.minX = this.blocks.get(0).getX();
		this.minY = this.blocks.get(0).getY();
		this.maxX = this.minX + this.blocks.get(0).getWidth();
		this.maxY = this.minY + this.blocks.get(0).getHeight();

		for(int i = 1; i < this.blocks.size(); i++) {
			CodeBlockFunction block = this.blocks.get(i);
			if(block.getX() < this.minX) this.minX = block.getX();
			if(block.getY() < this.minY) this.minY = block.getY();
			if(block.getX() + block.getWidth() > this.maxX) this.maxX = block.getX() + block.getWidth();
			if(block.getY() + block.getHeight() > this.maxY) this.maxY = block.getX() + block.getHeight();
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
		for(int i = 0; i < this.slots.length; i++) {
			this.slots[i].block = i < category.blocks.length ? category.blocks[i] : null;
		}
	}

	public void drawScrollbarH(int x, int y, int width, float value) {
		int scrollAmt = Math.round((width - 15) * value);
		this.drawTexturedModalRect(x + scrollAmt, y, 24, 241, 15, 12);
	}

	public void drawScrollbarV(int x, int y, int height, float value) {
		int scrollAmt = Math.round((height - 15) * value);
		this.drawTexturedModalRect(x, y + scrollAmt, 0, 241, 12, 15);
	}

	public void drawTab(int x, int y, int index, boolean selected, ICodeBlock block) {
		mc.getTextureManager().bindTexture(TABS);
		this.drawTexturedModalRect(x, y, index * 32, selected ? 28 : 0, 32, 28);
		mc.getTextureManager().bindTexture(BlockSlot.TEXTURE);
		this.drawTexturedModelRectFromIcon(x + 9, y + 6, block.getIcon(), 16, 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int count = BlockCategory.values().length;
		for(int i = 0, y = 0; i < count; i++, y += 29) {
			boolean selected = i == 0;
			if(selected) this.zLevel++;
			this.drawTab(-28, y, i == 0 ? 0 : i == count - 1 ? 2 : 1, selected, BlockCategory.values()[i].getDisplayBlock());
			if(selected) this.zLevel--;
		}

		this.mc.getTextureManager().bindTexture(this.BACKGROUND);
		mouseX -= this.guiLeft;
		mouseY -= this.guiTop;
		//int left = (this.width - this.xSize) / 2;
		//int top = (this.height - this.ySize) / 2;

		int scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(8 * scale, 114 * scale, 142 * scale, 96 * scale);
		GL11.glTranslatef(8 - this.scrollX, 18 - this.scrollY, 0);

		for(CodeBlockFunction block : this.blocks) {
			if(block.checkValid()) {
				this.updateBounds();
			}
			block.render(block.getX(), block.getY());
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();

		for(BlockSlot slot : this.slots) {
			slot.render(this.mc, mouseX, mouseY);
		}

		if(this.clipboard.size() > 0) {
			int currentY = mouseY;
			for(ICodeBlock block : this.clipboard) {
				block.checkValid();
				block.render(mouseX, currentY);
				currentY += block.getHeight();
			}
		}

		this.mc.getTextureManager().bindTexture(this.BACKGROUND);
		for(int j = 0; j < 4; j++) {
			int x = 174 + j * 18;
			this.drawTexturedModalRect(x, 137, 228, 157, 16, 16);
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

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.drawWorldBackground(0);

		this.zLevel = 1;
		this.mc.getTextureManager().bindTexture(this.BACKGROUND);
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
		this.drawScrollbarH(left + 8, top + 120, 142, this.scrollH);
		this.drawScrollbarV(left + 156, top + 18, 96, this.scrollV);

		this.fontRendererObj.drawString(I18n.format("container.programmer"), left + 8, top + 6, 4210752);
	}
}
