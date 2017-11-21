package tk.nukeduck.lightsaber.client.renderer;

import static org.lwjgl.opengl.GL11.*;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.util.Strings;

public class EntityLightFX extends EntityFX {
	private static final ResourceLocation particleTexture = new ResourceLocation(Strings.MOD_ID, "textures/particle/light2.png");
	private Entity entity;
	private float[] color;
	
	/** Constructor.<br/>
	 * Sets world, position and colour. */
	public EntityLightFX(World world, Entity entity, double x, double y, double z, float[] color) {
		super(world, x, y, z);
		this.setMaxAge(30);
		this.setVelocity(
			(Lightsaber.random.nextDouble() - 0.5) / 20,
			(Lightsaber.random.nextDouble() - 0.5) / 20,
			(Lightsaber.random.nextDouble() - 0.5) / 20
		);
		this.setSize(0.5F, 0.5F);
		this.entity = entity;
		this.color = color;
	}
	
	/** Sets the ticks before this entity will die.
	 * @return This entity. */
	public EntityLightFX setMaxAge(int max) {
		this.particleMaxAge = max;
		return this;
	}
	
	/** Sets the initial particle gravity of this entity.
	 * @return This entity. */
	public EntityLightFX setGravity(float grav) {
		this.particleGravity = grav;
		return this;
	}
	
	/** Sets the scale of this entity.
	 * @return This entity. */
	public EntityLightFX setScale(float scale) {
		this.particleScale = scale;
		return this;
	}
	
	@Override
	public void renderParticle(Tessellator tessellator, float partialTick, float par3, float par4, float par5, float par6, float par7) {
		Lightsaber.mc.getTextureManager().bindTexture(particleTexture);
		glDepthMask(false);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER, 0.003921569F);
		
		glColor4f(color[0], color[1], color[2], 0.5F - ((float) this.particleAge / (float) this.particleMaxAge) * 0.5F);
		
		tessellator.startDrawingQuads();
		
		tessellator.setBrightness(getBrightnessForRender(partialTick));
		float scale = particleScale / 10F;
		float x = (float) (prevPosX + (posX - prevPosX) * partialTick - interpPosX);
		float y = (float) (prevPosY + (posY - prevPosY) * partialTick - interpPosY);
		float z = (float) (prevPosZ + (posZ - prevPosZ) * partialTick - interpPosZ);
		
		tessellator.addVertexWithUV(x - par3 * scale - par6 * scale, y - par4 * scale, z - par5 * scale - par7 * scale, 0, 0);
		tessellator.addVertexWithUV(x - par3 * scale + par6 * scale, y + par4 * scale, z - par5 * scale + par7 * scale, 1, 0);
		tessellator.addVertexWithUV(x + par3 * scale + par6 * scale, y + par4 * scale, z + par5 * scale + par7 * scale, 1, 1);
		tessellator.addVertexWithUV(x + par3 * scale - par6 * scale, y - par4 * scale, z + par5 * scale - par7 * scale, 0, 1);
		
		tessellator.draw();
		
		glDisable(GL_BLEND);
		glDepthMask(true);
		glAlphaFunc(GL_GREATER, 0.1F);
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
}