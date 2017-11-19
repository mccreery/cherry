package nukeduck.coinage.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class LayerPocketItems implements LayerRenderer {
	private static final ItemStack potion = new ItemStack(Items.potionitem, 1, 8229);
	
	@Override
	public void doRenderLayer(EntityLivingBase entity, float p_177141_2_, float p_177141_3_, float partialTicks,
			float p_177141_5_, float p_177141_6_, float p_177141_7_, float last) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.26f, 0.5f, 0);
		GlStateManager.rotate(90, 0, 1, 0);
		GlStateManager.rotate(180, 0, 0, 1);
		GlStateManager.rotate(10, 1, 0, 0);
		GlStateManager.scale(0.5, 0.5, 0.5);
		
		float par1 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
		float par2 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
		float rotFinal = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		GlStateManager.rotate(rotFinal * 50, 0, 0, 1);
		GlStateManager.translate(0, -1.0f, 0);
		
		Minecraft.getMinecraft().getItemRenderer().renderItem(entity, potion, TransformType.NONE);
		GlStateManager.popMatrix();
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
