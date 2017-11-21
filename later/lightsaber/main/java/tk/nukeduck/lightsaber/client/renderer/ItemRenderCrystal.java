package tk.nukeduck.lightsaber.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import tk.nukeduck.lightsaber.item.ItemBlockCrystal;

public class ItemRenderCrystal implements IItemRenderer {
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}
	
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		ItemRenderer.renderItemIn2D(Tessellator.instance, itemStack.getIconIndex().getMinU(), itemStack.getIconIndex().getMinV(), itemStack.getIconIndex().getMaxU(), itemStack.getIconIndex().getMaxV(), itemStack.getIconIndex().getIconWidth(), itemStack.getIconIndex().getIconWidth(), 0.0625F);
	}
}