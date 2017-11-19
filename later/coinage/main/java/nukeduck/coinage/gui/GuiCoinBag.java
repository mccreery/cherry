package nukeduck.coinage.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import nukeduck.coinage.Coinage;
import nukeduck.coinage.Constants;
import nukeduck.coinage.inventory.ContainerCoinBag;
import nukeduck.coinage.inventory.InventoryCoinBag;
import nukeduck.coinage.registry.ItemRegister;

public class GuiCoinBag extends GuiContainer {
	private float xSize_lo;
	private float ySize_lo;
	
	private static final ResourceLocation background = new ResourceLocation(Constants.MODID, "textures/gui/coin_bag.png");
	private final InventoryCoinBag inventory;
	
	public GuiCoinBag(ContainerCoinBag containerItem) {
		super(containerItem);
		this.inventory = containerItem.inventory;
		
		this.xSize = 176;
		this.ySize = 151;
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		this.xSize_lo = (float) par1;
		this.ySize_lo = (float) par2;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = I18n.format("menu." + inventory.getName());
		
		this.fontRendererObj.drawString(s, 8, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		RenderHelper.enableGUIStandardItemLighting();
		for(int i = 0; i < 3; i++) {
			this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Coinage.instance.itemRegister.coin, 1, i), 120, 47 - i * 18);
		}
		RenderHelper.disableStandardItemLighting();
		
		if(Minecraft.getMinecraft().thePlayer.getHeldItem().hasTagCompound()) {
			NBTTagCompound compound = Minecraft.getMinecraft().thePlayer.getHeldItem().getTagCompound();
			if(compound.hasKey("CoinCount", 11)) {
				int[] coinCount = compound.getIntArray("CoinCount");
				
				for(int i = 0; i < 3; i++) {
					int val = 0;
					this.fontRendererObj.drawString(String.valueOf(coinCount[i]), 138, 52 - i * 18, 4210752);
				}
			}
		}
		
		/*Container slots = this.inventorySlots;
		for(int i = 0; i < slots.inventorySlots.size(); i++) {
			Slot a = slots.getSlot(i);
			this.fontRendererObj.drawString(String.valueOf(i), a.xDisplayPosition, a.yDisplayPosition, 0xffffff);
		}*/
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}
