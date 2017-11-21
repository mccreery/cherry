package tk.nukeduck.lightsaber.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.block.tileentity.ContainerRefillUnit;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import tk.nukeduck.lightsaber.network.IORuleMessage;
import tk.nukeduck.lightsaber.registry.LightsaberBlocks;
import tk.nukeduck.lightsaber.registry.LightsaberItems;
import tk.nukeduck.lightsaber.util.Strings;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.client.FMLClientHandler;

public class GuiRefillUnit extends GuiContainer {
	/** The GUI background image. */
	public static final ResourceLocation background = new ResourceLocation(Strings.MOD_ID, "textures/gui/refill_unit_gui.png");
	/** The tile entity being followed. */
	private TileEntityRefillUnit tileEntity;
	
	/** Constructor.<br/>
	 * Sets size, container and tile entity. */
	public GuiRefillUnit(InventoryPlayer inventory, TileEntityRefillUnit tileEntity) {
		super(new ContainerRefillUnit(inventory, tileEntity));
		this.tileEntity = tileEntity;
		xSize = 176;
		ySize = 166;
	}
	
	/** Localised names of IO modes. */
	private static String[] modeNamesLocal = new String[Strings.ioModes.length];
	
	/** @return How many IO modes are available. */
	public static int getSizeModes() {
		return modeNamesLocal.length;
	}
	
	/** Localised name of Energy Capsule and Lightsaber. */
	public static String energyCapsuleName, lightsaberName;
	
	/** {@code ItemStack} instances for rendering icons. */
	public static ItemStack energyCapsule = null, lightsaber = null;
	/** {@code RenderItem} instance used for rendering item icons on buttons. */
	private static RenderItem ri = new RenderItem();
	
	@Override
	public void initGui() {
		super.initGui();
		
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		
		int l = x - 25; // Left
		int r = x + xSize + 5; // Right
		int t = y + 45; // Top
		int b = t + 25; // Bottom
		
		if(energyCapsule == null) energyCapsule = new ItemStack(LightsaberItems.energyCapsules[1]);
		if(lightsaber == null) lightsaber = new ItemStack(LightsaberItems.lightsabers[Strings.colorIndex(Strings.RED) * 2]);
		
		energyCapsuleName = Strings.translate(LightsaberItems.energyCapsules[0].getUnlocalizedName() + Strings.NAME_SUFFIX);
		lightsaberName = Strings.translate(LightsaberItems.lightsabers[0].getUnlocalizedName() + Strings.NAME_SUFFIX);
		
		for(int i = 0; i < modeNamesLocal.length; i++) {
			modeNamesLocal[i] = Strings.getIOModifier(i) + Strings.translate(Strings.getIOMode(i));
		}
		
		String importRule = ChatFormatting.DARK_GREEN + Strings.translate(Strings.IMPORT_ARROW) + ChatFormatting.RESET + " " + Strings.translate(Strings.IMPORT);
		String exportRule = ChatFormatting.GOLD + Strings.translate(Strings.EXPORT_ARROW) + ChatFormatting.RESET + " " + Strings.translate(Strings.EXPORT);
		
		this.buttonList.add(new GuiButtonImage(0, l, t, 20, 20).setToolTip(new String[] {importRule, ChatFormatting.GRAY + energyCapsuleName, modeNamesLocal[this.tileEntity.ioModes[0]]}).addImage(0, 0).addImage(2, this.tileEntity.ioModes[0]));
		this.buttonList.add(new GuiButtonImage(1, l, b, 20, 20).setToolTip(new String[] {exportRule, ChatFormatting.GRAY + energyCapsuleName, modeNamesLocal[this.tileEntity.ioModes[1]]}).addImage(0, 1).addImage(2, this.tileEntity.ioModes[1]));
		this.buttonList.add(new GuiButtonImage(2, r, t, 20, 20).setToolTip(new String[] {importRule, ChatFormatting.GRAY + lightsaberName, modeNamesLocal[this.tileEntity.ioModes[2]]}).addImage(1, 0).addImage(2, this.tileEntity.ioModes[2]));
		this.buttonList.add(new GuiButtonImage(3, r, b, 20, 20).setToolTip(new String[] {exportRule, ChatFormatting.GRAY + lightsaberName, modeNamesLocal[this.tileEntity.ioModes[3]]}).addImage(1, 1).addImage(2, this.tileEntity.ioModes[3]));
	}
	
	/** Sends IO rule changes on click. */
	@Override
	public void actionPerformed(GuiButton a) {
		Lightsaber.networkWrapper.sendToServer(
				new IORuleMessage(this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, a.id));
		
		int newValue = (this.tileEntity.ioModes[a.id] + 1) % getSizeModes();
		if(a instanceof GuiButtonImage) {
			GuiButtonImage button = (GuiButtonImage) a;
			button.setAt(1, 2, newValue);
			button.setToolTipLine(2, this.modeNamesLocal[newValue]);
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_BLEND);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(background);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		int distance = (int) (((float) this.tileEntity.chargeLevel / (float) this.tileEntity.chargeLevelMax) * 120.0F);
		this.drawTexturedModalRect(x + 28, y + 17, 0, 166, distance, 16);
		if(distance > 0) this.drawTexturedModalRect(x + 27 + distance, y + 18, 120, 167, 1, 14);
		
		double chargeProgress = (double) (this.tileEntity.chargeDelay - this.tileEntity.untilNextCharge) / (this.tileEntity.chargeDelay + 1.0);
		int dist = (int) (chargeProgress * 21);
		if(this.tileEntity.getStackInSlot(1) != null || this.tileEntity.getStackInSlot(2) != null || this.tileEntity.getStackInSlot(3) != null)
			this.drawTexturedModalRect(x + 41, y + 32 + (21 - dist), 176, (20 - dist), 32, dist);
		if(this.tileEntity.getStackInSlot(0) != null)
			this.drawTexturedModalRect(x + 115, y + 34, 208, 0, 18, (int) (chargeProgress * 15));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String mark = Strings.translate(Strings.MARK_PREFIX + this.tileEntity.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord) + Strings.NAME_SUFFIX);
		fontRendererObj.drawString(Strings.translate(LightsaberBlocks.charger.getUnlocalizedName() + Strings.NAME_SUFFIX) + " " + mark, 8, 6, 4210752);
		fontRendererObj.drawString(Strings.translate(Strings.INVENTORY), 8, ySize - 96 + 2, 4210752);
		
		short currentCharge = this.tileEntity.chargeLevel;
		short maxCharge = this.tileEntity.chargeLevelMax;
		
		String e = Strings.translate(Strings.ENERGY_SYMBOL);
		String current = currentCharge + e;
		String max = maxCharge + e;
		
		int distance = (int) (((float) currentCharge / (float) maxCharge) * 120.0F);
		
		glPushMatrix();
		glTranslatef(25 - fontRendererObj.FONT_HEIGHT, 26 + (fontRendererObj.getStringWidth("0" + e) / 2), 0);
		glRotatef(-90, 0, 0, 1);
		fontRendererObj.drawString("0" + e, 0, 0, 4210752);
		glPopMatrix();
		
		int a = fontRendererObj.getStringWidth(current);
		int xOffset = Math.max(a + 7, distance);
		
		int x = 25 + xOffset - a;
		int y = 22;
		
		fontRendererObj.drawString(current, x - 1, y, 0x000000);
		fontRendererObj.drawString(current, x + 1, y, 0x000000);
		fontRendererObj.drawString(current, x, y - 1, 0x000000);
		fontRendererObj.drawString(current, x, y + 1, 0x000000);
		
		fontRendererObj.drawString(current, x, y, 0xffffff);
		
		glPushMatrix(); {
			glTranslatef(160, 26 - (fontRendererObj.getStringWidth(max) / 2), 0);
			glRotatef(90, 0, 0, 1);
			fontRendererObj.drawString(max, 0, 0, 4210752);
		}
		glPopMatrix();
		
		String speed = (this.tileEntity.chargeDelay > 20 ? Strings.translate(Strings.LESS_THAN_ONE) : (int) Math.ceil(20.0 / (this.tileEntity.chargeDelay + 1.0)))
			+ Strings.translate(Strings.TRANSFER_RATE);
		fontRendererObj.drawString(speed, xSize - 6 - fontRendererObj.getStringWidth(speed), ySize - 96 + 2, 4210752);
		
		int xa = (width - xSize) / 2;
		int ya = (height - ySize) / 2;
		
		mc.renderEngine.bindTexture(background);
		
		ScaledResolution scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int sc = scale.getScaleFactor();
		
		for(Object button : buttonList.toArray()) {
			if(button instanceof GuiButtonImage) {
				GuiButtonImage buttonImage = (GuiButtonImage) button;
				
				if(isWithinBounds(buttonImage.xPosition * sc, buttonImage.yPosition * sc, (buttonImage.xPosition + buttonImage.width) * sc, (buttonImage.yPosition + buttonImage.height) * sc, Mouse.getX(), mc.displayHeight - Mouse.getY())) {
					ArrayList<String> texts = new ArrayList<String>();
					for(String s : buttonImage.getToolTip()) texts.add(s);
					
					this.renderToolTip(texts, Mouse.getX() / sc - xa, (mc.displayHeight - Mouse.getY()) / sc - ya);
				}
			}
		}
		
		int x1 = xa - 23, y1 = ya + 25;
		int x2 = xa + xSize + 7;
		
		ri.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), energyCapsule, -23, 25);
		ri.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), lightsaber, xSize + 7, 25);
		
		if(isWithinBounds(x1 * sc, y1 * sc, (x1 + 16) * sc, (y1 + 16) * sc, Mouse.getX(), mc.displayHeight - Mouse.getY())) {
			ArrayList<String> texts = new ArrayList<String>();
			texts.add(energyCapsuleName);
			this.renderToolTip(texts, Mouse.getX() / sc - xa, (mc.displayHeight - Mouse.getY()) / sc - ya);
		} else if(isWithinBounds(x2 * sc, y1 * sc, (x2 + 16) * sc, (y1 + 16) * sc, Mouse.getX(), mc.displayHeight - Mouse.getY())) {
			ArrayList<String> texts = new ArrayList<String>();
			texts.add(lightsaberName);
			this.renderToolTip(texts, Mouse.getX() / sc - xa, (mc.displayHeight - Mouse.getY()) / sc - ya);
		}
	}
	
	/** @param x X position of the bounds.
	 * @param y Y position of the bounds.
	 * @param x1 X position of the right side of the bounds.
	 * @param y1 Y position of the bottom side of the bounds.
	 * @return {@code true} if (i, j) is within the given bounds. */
	public boolean isWithinBounds(int x, int y, int x1, int y1, int i, int j) {
		return i >= x && i <= x1 && j >= y && j <= y1;
	}
	
	/** Render a tooltip with the given text.
	 * @param list The list of stings.
	 * @param x X position.
	 * @param y Y position. */
	protected void renderToolTip(List list, int x, int y) {
		drawHoveringText(list, x, y, fontRendererObj);
	}
}