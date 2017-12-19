package com.sammccreery.cherry.client;

import org.lwjgl.opengl.GL11;

import com.sammccreery.cherry.Cherry;
import com.sammccreery.cherry.Config;
import com.sammccreery.cherry.registry.CherryBlocks;
import com.sammccreery.cherry.util.Name;
import com.sammccreery.cherry.util.Name.Format;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class LanternRenderer extends TileEntitySpecialRenderer {
	private ResourceLocation texture = new ResourceLocation(Cherry.MODID, "textures/models/" + Name.HEART_LANTERN.format(Format.SNAKE, false) + ".png");
	private ModelLantern model;
	private ModelHeart heartModel;
	private EntityItem item;

	public LanternRenderer() {
		this.model = new ModelLantern();
		this.heartModel = new ModelHeart();
		this.item = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, new ItemStack(CherryBlocks.heartCrystal));
		this.item.hoverStart = 0.0F;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
		int metadata = tileEntity.getBlockMetadata();

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glRotatef(90 * (metadata % 4), 0, 1, 0);

		if(Config.getOldModel()) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);

			GL11.glTranslated(0, -0.85, 0);
			GL11.glScaled(0.4, 0.4, 0.4);
			GL11.glRotatef(180, 0, 0, 1);

			this.bindTexture(HeartCrystalRenderer.oldTexture);
			heartModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		} else {
			GL11.glPushMatrix();
			GL11.glTranslated(0.0, -1.45, 0.03);
			GL11.glScaled(0.7, 0.7, 5);
			boolean disableCull = !Minecraft.getMinecraft().gameSettings.fancyGraphics;
			if(disableCull) GL11.glDisable(GL11.GL_CULL_FACE);
	
			RenderItem.renderInFrame = true;
			RenderManager.instance.renderEntityWithPosYaw(this.item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;
	
			if(disableCull) GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPopMatrix();
		}

		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
		model.render(metadata, 0.0625F);
		
		GL11.glPopMatrix();
	}
}
