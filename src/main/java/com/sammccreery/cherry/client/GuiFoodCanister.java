package com.sammccreery.cherry.client;

import org.lwjgl.opengl.GL11;

import com.sammccreery.cherry.inventory.ContainerFoodCanister;
import com.sammccreery.cherry.inventory.InventoryFoodCanister;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiFoodCanister extends GuiContainer {
	private static final ResourceLocation BACKGROUND = new ResourceLocation("textures/gui/container/hopper.png");
	private final InventoryFoodCanister inventory;

	public GuiFoodCanister(ContainerFoodCanister containerItem) {
		super(containerItem);
		this.inventory = containerItem.inventory;
		this.xSize = 176;
		this.ySize = 133;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.inventory.getInventoryName();
		this.fontRendererObj.drawString(s, 7, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 98 + 4, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(BACKGROUND);

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
