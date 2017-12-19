package com.sammccreery.cherry.client;

import org.lwjgl.opengl.GL11;

import com.sammccreery.cherry.Cherry;
import com.sammccreery.cherry.Config;
import com.sammccreery.cherry.block.TileEntityHeartCrystal;
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

public class HeartCrystalRenderer extends TileEntitySpecialRenderer {
	private EntityItem item;
	protected static final ResourceLocation oldTexture = new ResourceLocation(Cherry.MODID, "textures/models/" + Name.HEART_CRYSTAL.format(Format.SNAKE, false) + ".png");
	private ModelHeart model;

	public HeartCrystalRenderer() {
		this.item = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, new ItemStack(CherryBlocks.heartCrystal));
		this.item.hoverStart = 0.0F;
		this.model = new ModelHeart();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		if(!(tileentity instanceof TileEntityHeartCrystal)) return;
		TileEntityHeartCrystal te = (TileEntityHeartCrystal) tileentity;

		GL11.glPushMatrix(); {
			if(Config.getOldModel()) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
				GL11.glTranslatef((float) d0 + 0.5f, (float) d1 + 1.5f, (float) d2 + 0.5f);
				GL11.glRotatef(180, 0, 0, 1);
				GL11.glRotatef(te.rotation, 0, 1, 0);

				this.bindTexture(oldTexture);
				model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

				GL11.glDisable(GL11.GL_BLEND);
			} else {
				GL11.glTranslated(d0 + 0.5, d1 + 0.175, d2 + 0.5);
				GL11.glRotatef(te.rotation, 0, 1, 0);
				GL11.glTranslatef(0, 0, 0.03125f);
				GL11.glScalef(1.75f, 1.75f, 7f);
	
				boolean disableCull = !Minecraft.getMinecraft().gameSettings.fancyGraphics;
				if(disableCull) GL11.glDisable(GL11.GL_CULL_FACE);
	
				RenderItem.renderInFrame = true;
				RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderItem.renderInFrame = false;
	
				if(disableCull) GL11.glEnable(GL11.GL_CULL_FACE);
			}
		}
		GL11.glPopMatrix();
	}
}
