package tk.nukeduck.lightsaber.client.renderer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ENABLE_BIT;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityCrystal;
import tk.nukeduck.lightsaber.client.ClientEvents;
import tk.nukeduck.lightsaber.util.Strings;

public class CrystalRenderer extends TileEntitySpecialRenderer {
	/** The crystal model to render. */
	public static final ModelCrystal model = new ModelCrystal();
	/** The texture of the model. */
	private static final ResourceLocation texture = new ResourceLocation(Strings.MOD_ID, "textures/models/blocks/crystal.png");
	
	/** Array of RGB codes for the different colours of crystal. */
	public static final float[][] colors = {
		{0, 0, 1}, // Blue
		{1, 0, 0}, // Red
		{0, 1, 0}, // Green
		{1, 0, 1}, // Purple
		{1, 1, 0}, // Yellow
		{0.1F, 0.1F, 0.1F}, // Black
		{1, 0.5F, 0}, // Orange
		{1, 1, 1} // White
	};
	
	public CrystalRenderer() {}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		TileEntityCrystal te = ((TileEntityCrystal) tileentity);
		float[] components = colors[tileentity.getBlockMetadata()];
		
		glPushMatrix(); {
			glPushAttrib(GL_ENABLE_BIT); {
				glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				glDepthMask(true);
				
				float val = (float) Math.sin(ClientEvents.tickCounter) / 4F + 1F;
				
				int x = te.xCoord;
				int y = te.yCoord;
				int z = te.zCoord;
				World world = te.getWorldObj();
				
				glTranslated(d0 + 0.5, d1 + 0.5, d2 + 0.5);
				
				if(world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH)) {
					glRotatef(90, 1, 0, 0);
				} else if(world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH)) {
					glRotatef(-90, 1, 0, 0);
				} else if(world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.EAST)) {
					glRotatef(-90, 0, 0, 1);
				} else if(world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.WEST)) {
					glRotatef(90, 0, 0, 1);
				} else if(world.getBlock(x, y + 1, z).isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN) && !world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) {
					glRotatef(180, 1, 0, 0);
				}
				
				double offsetX = (double) te.getOffsetX() / 512.0;
				double offsetZ = (double) te.getOffsetZ() / 512.0;
				
				glTranslated(offsetX, 1, offsetZ);
				glRotatef(te.getRotation() / 255F * 360F, 0, 1, 0);
				
				glRotatef(180, 0F, 0F, 1F);
				
				this.bindTexture(texture);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 0);
				
				glPolygonMode(GL_FRONT, GL_LINE);
				glColor4f(components[0] + 0.5F, components[1] + 0.5F, components[2] + 0.5F, 1);
				glLineWidth(4);
				model.render(null, 0, 0, 0, 0, 0, 0.0625F, te.getDensity());
				glPolygonMode(GL_FRONT, GL_FILL);
				
				glColor4f(components[0] * val, components[1] * val, components[2] * val, 0.6F);
				model.render(null, 0, 0, 0, 0, 0, 0.0625F, te.getDensity());
				glTranslatef(0, 1.4F, 0);
				glScalef(1.2F, 1.2F, 1.2F);
				glTranslatef(0, -1.4F, 0);
				glColor4f(components[0] * val, components[1] * val, components[2] * val, 0.4F);
				model.render(null, 0, 0, 0, 0, 0, 0.0625F, te.getDensity());
				
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, OpenGlHelper.lastBrightnessX, OpenGlHelper.lastBrightnessY);
			}
			glPopAttrib();
		}
		glPopMatrix();
	}
}