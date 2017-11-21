package tk.nukeduck.alchemy;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLightningBolt;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import cpw.mods.fml.client.FMLClientHandler;

public class AlchemyPotRenderer extends TileEntitySpecialRenderer {
	private final ModelAlchemyPot model;
	private static final ResourceLocation texture = new ResourceLocation("alchemy", "textures/blocks/alchemy_pot.png");
	private static final ResourceLocation liquid = new ResourceLocation("alchemy", "textures/blocks/liquid.png");
	
	public AlchemyPotRenderer() {
		this.model = new ModelAlchemyPot();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix(); {
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotatef(180, 0, 0, 1);
			
			this.bindTexture(texture);
			
			GL11.glPushMatrix(); {
				model.renderModel(0.0625F);
			}
			GL11.glPopMatrix();
			
			GL11.glEnable(GL11.GL_BLEND);
		}
		GL11.glPopMatrix();
		
		GL11.glPushMatrix(); {
			GL11.glTranslatef((float) x, (float) y, (float) z);
			this.bindTexture(liquid);
			
			GL11.glPushMatrix(); {
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glColor3f(0.4F, 0.9F, 0.4F);
				Tessellator t = Tessellator.instance;
				t.startDrawingQuads(); {
					t.addVertexWithUV(0.1875, 7.0 / 16.0, 0.8125, 0.0, 0.02564102564102564102564102564103);
					t.addVertexWithUV(0.8125, 7.0 / 16.0, 0.8125, 1.0, 0.02564102564102564102564102564103);
					t.addVertexWithUV(0.8125, 7.0 / 16.0, 0.1875, 1.0, 0.0);
					t.addVertexWithUV(0.1875, 7.0 / 16.0, 0.1875, 0.0, 0.0);
				}
				t.draw();
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
}