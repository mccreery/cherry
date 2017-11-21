package tk.nukeduck.lightsaber.client.renderer;

import static org.lwjgl.opengl.GL11.GL_ALL_ATTRIB_BITS;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glRotatef;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import tk.nukeduck.lightsaber.client.gui.GuiButtonImage;
import tk.nukeduck.lightsaber.client.gui.GuiRefillUnit;
import tk.nukeduck.lightsaber.item.ItemChargeable;
import tk.nukeduck.lightsaber.item.ItemEnergyCapsule;
import tk.nukeduck.lightsaber.item.ItemLightsaber;
import tk.nukeduck.lightsaber.registry.LightsaberItems;
import tk.nukeduck.lightsaber.util.Constants;
import tk.nukeduck.lightsaber.util.Strings;

public class RefillUnitRenderer extends TileEntitySpecialRenderer {
	/** The model of the block. */
	private static final ModelRefillUnit model = new ModelRefillUnit();
	/** The texture of the model. */
	private static final ResourceLocation texture = new ResourceLocation(Strings.MOD_ID, "textures/models/blocks/refill_unit.png");
	/** Alternate activated model texture. */
	private static final ResourceLocation textureActivated = new ResourceLocation(Strings.MOD_ID, "textures/models/blocks/refill_unit_on.png");
	
	public RefillUnitRenderer() {}
	
	public void renderTileEntityAt(TileEntity tileEntity, double d0, double d1, double d2, float f) {
		boolean hasLightsaber = ((TileEntityRefillUnit) tileEntity).getStackInSlot(0) != null;
		
		glEnable(GL_LIGHTING);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glPushMatrix(); {
			glTranslated(d0 + 0.5D, d1 + 1.5D, d2 + 0.5D);
			glRotatef(180, 0F, 0F, 1F);
			
			glRotatef(tileEntity.getBlockMetadata() * 90, 0F, 1F, 0F);
			
			if(hasLightsaber) {
				ItemRenderLightsaber.instance.renderItemForCharger(IItemRenderer.ItemRenderType.ENTITY, ((TileEntityRefillUnit) tileEntity).getStackInSlot(0), -((TileEntityRefillUnit) tileEntity).rotationAmount / 2);
			}
			
			this.bindTexture(hasLightsaber ? textureActivated : texture);
			model.render(null, 0, 0, 0, 0, 0, 0.0625F, ((TileEntityRefillUnit) tileEntity).rotationAmount); // 1/16
			
			// SCREEN RENDERING
			
			glPushAttrib(GL_ALL_ATTRIB_BITS);
			glPushMatrix();
				ModelBox cube = (ModelBox) model.screen.cubeList.get(0);
				
				glDisable(GL_LIGHTING);
				
				float i = 0.0625F;
				float j = 1f / 128f;
				
				GL11.glTranslatef(model.screen.offsetX, model.screen.offsetY, model.screen.offsetZ);
				
				GL11.glPushMatrix();
				GL11.glTranslatef(model.screen.rotationPointX * i, model.screen.rotationPointY * i, model.screen.rotationPointZ * i);
				
				GL11.glRotatef(model.screen.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(model.screen.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(model.screen.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				
				TileEntityRefillUnit unit = ((TileEntityRefillUnit) tileEntity);
				
				glTranslatef(0, -i * 4, -Constants.INCREMENT_DIST);
				glScalef(j, j, j);
				
				int screenWidth = (int) ((cube.posX2 - cube.posX1) * (i / j));
				int screenHeight = (int) ((cube.posY2 - cube.posY1) * (i / j));
				
				glPushMatrix(); {
					this.bindTexture(hasLightsaber ? textureActivated : texture);
					glScalef(0.5F, 0.25F, 1);
					new Gui().drawTexturedModalRect(0, 0, 144, 128, 112, 128);
				}
				glPopMatrix();
				
				glTranslatef(0, 0, -Constants.INCREMENT_DIST);
				
				String x = unit.chargeLevel + Strings.translate(Strings.OUT_OF) + unit.chargeLevelMax + Strings.translate(Strings.ENERGY_SYMBOL);
				Lightsaber.mc.fontRenderer.drawString(x, (screenWidth - Lightsaber.mc.fontRenderer.getStringWidth(x)) / 2, screenHeight / 2 - Lightsaber.mc.fontRenderer.FONT_HEIGHT - 2, 0xffffff/*0xffffff*/);
				
				int count = 0;
				for(int q = 0; q < 4; q++) {
					ItemStack item = ((TileEntityRefillUnit) tileEntity).getStackInSlot(q);
					if(item != null) {
						if(item.getItem() instanceof ItemEnergyCapsule) {
							ItemEnergyCapsule capsuleItem = (ItemEnergyCapsule) item.getItem();
							if(!capsuleItem.isEmpty(item)) count++;
						} else if(item.getItem() instanceof ItemLightsaber) {
							ItemLightsaber saberItem = (ItemLightsaber) item.getItem();
							if(!saberItem.isFull(item)) count--;
						}
					}
				}
				String y = (count > 0 ? ChatFormatting.GREEN + "+" : count < 0 ? ChatFormatting.RED + "" : ChatFormatting.DARK_AQUA + "") + count;
				
				Lightsaber.mc.fontRenderer.drawString(y, (screenWidth - Lightsaber.mc.fontRenderer.getStringWidth(y)) / 2, screenHeight / 2 + 2, 0xffffff);
				GL11.glPopMatrix();
				
				GL11.glTranslatef(-model.screen.offsetX, -model.screen.offsetY, -model.screen.offsetZ);
			glPopMatrix();
			glPopAttrib();
		}
		glPopMatrix();
		
		if(hasLightsaber) {
			glDisable(GL_TEXTURE_2D);
			glDisable(GL_LIGHTING);
			
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			float x1 = Lightsaber.random.nextFloat() / 5 + 0.4F;
			float z1 = Lightsaber.random.nextFloat() / 5 + 0.2F;
			float x2 = Lightsaber.random.nextFloat() / 5 + 0.4F;
			float z2 = Lightsaber.random.nextFloat() / 5 + 0.6F;
			float x3 = Lightsaber.random.nextFloat() / 5 + 0.2F;
			float z3 = Lightsaber.random.nextFloat() / 5 + 0.4F;
			float x4 = Lightsaber.random.nextFloat() / 5 + 0.6F;
			float z4 = Lightsaber.random.nextFloat() / 5 + 0.4F;
			
			glColor4f(0.7f, 0.9f, 1.0f, 0.5f);
			glLineWidth(8);
			
			glPushMatrix(); {
				glTranslated(d0, d1, d2);
				glTranslatef(0.5F, 0F, 0.5F);
				glRotatef(-((TileEntityRefillUnit) tileEntity).rotationAmount, 0, 1, 0);
				glTranslatef(-0.5F, 0F, -0.5F);
				
				glBegin(GL_LINES); {
					glVertex3f(0.5F, 0.65f, 0.2F);
					glVertex3f(x1, 0.95f, z1);
					
					glVertex3f(x1, 0.95f, z1);
					glVertex3f(0.5F + Lightsaber.random.nextFloat() * 0.1f - 0.05F, 1.25f, 0.4F + Lightsaber.random.nextFloat() * 0.1f - 0.05F);
					//--
					glVertex3f(0.5f, 0.65f, 0.8f);
					glVertex3f(x2, 0.95f, z2);
					
					glVertex3f(x2, 0.95f, z2);
					glVertex3f(0.5f + Lightsaber.random.nextFloat() * 0.1f - 0.05F, 1.25f, 0.6f + Lightsaber.random.nextFloat() * 0.1f - 0.05F);
					//--
					glVertex3f(0.2f, 0.65f, 0.5f);
					glVertex3f(x3, 0.95f, z3);
					
					glVertex3f(x3, 0.95f, z3);
					glVertex3f(0.4f + Lightsaber.random.nextFloat() * 0.1f - 0.05F, 1.25f, 0.5f + Lightsaber.random.nextFloat() * 0.1f - 0.05F);
					//--
					glVertex3f(0.8f, 0.65f, 0.5f);
					glVertex3f(x4, 0.95f, z4);
					
					glVertex3f(x4, 0.95f, z4);
					glVertex3f(0.6f + Lightsaber.random.nextFloat() * 0.1f - 0.05F, 1.25f, 0.5f + Lightsaber.random.nextFloat() * 0.1f - 0.05F);
				}
				glEnd();
			}
			glPopMatrix();
			
			/*float r = 0.05F;
			float[][] points = {
				{-r, 0, -r},
				{-r, 1, -r},
				{r, 1, -r},
				{r, 0, -r},
				
				{r, 0, -r},
				{r, 1, -r},
				{r, 1, r},
				{r, 0, r},
				
				{r, 0, r},
				{r, 1, r},
				{-r, 1, r},
				{-r, 0, r},
				
				{-r, 0, r},
				{-r, 1, r},
				{-r, 1, -r},
				{-r, 0, -r}
			};
			
			glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
			
			glPushMatrix();
				glTranslatef(0.5F, 0.0F, 0.5F);
				glRotatef(((TileEntityRefillUnit) tileentity).rotationAmount, 0, 1, 0);
				glBegin(GL_QUADS);
				for(float[] point : points) {
					glVertex3f(point[0], point[1], point[2]);
				}
				glEnd();
			GL11.glPopMatrix();*/
				
			glDisable(GL_BLEND);
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_LIGHTING);
		}
	}
}