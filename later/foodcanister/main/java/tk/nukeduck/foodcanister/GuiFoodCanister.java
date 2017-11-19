package tk.nukeduck.foodcanister;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiFoodCanister extends GuiContainer
{
/** x and y size of the inventory window in pixels. Defined as float, passed as int
* These are used for drawing the player model. */
private float xSize_lo;
private float ySize_lo;

/** ResourceLocation takes 2 parameters: ModId, path to texture at the location:
* "src/minecraft/assets/modid/" */
private static final ResourceLocation iconLocation = new ResourceLocation("textures/gui/container/hopper.png");

/** The inventory to render on screen */
private final InventoryFoodCanister inventory;

public GuiFoodCanister(ContainerFoodCanister containerItem)
{
super(containerItem);
this.inventory = containerItem.inventory;
this.xSize = 176;
this.ySize = 133;
}

/**
* Draws the screen and all the components in it.
*/
public void drawScreen(int par1, int par2, float par3)
{
super.drawScreen(par1, par2, par3);
this.xSize_lo = (float)par1;
this.ySize_lo = (float)par2;
}

/**
* Draw the foreground layer for the GuiContainer (everything in front of the items)
*/
protected void drawGuiContainerForegroundLayer(int par1, int par2)
{
String s = this.inventory.getInventoryName();
this.fontRendererObj.drawString(s, 7, 6, 4210752);
this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 98 + 4, 4210752);
}

/**
* Draw the background layer for the GuiContainer (everything behind the items)
*/
protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
{
GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
this.mc.getTextureManager().bindTexture(iconLocation);
int k = (this.width - this.xSize) / 2;
int l = (this.height - this.ySize) / 2;
this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
int i1;
}

// If using 1.7.2 (you can do this in 1.6.4 too, but it wasn't absolutely necessary)
// you can override keyTyped to allow your custom keybinding to close the gui
/*@Override
protected void keyTyped(char c, int keyCode) {
// make sure you call super!!!
super.keyTyped(c, keyCode);
// 1 is the Esc key, and we made our keybinding array public and static so we can access it here
if (c == 1 || keyCode == KeyHandler.keys[KeyHandler.CUSTOM_INV].getKeyCode()) {
mc.thePlayer.closeScreen();
}
}*/
}