package nukeduck.functionaldecor.client.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import nukeduck.functionaldecor.FunctionalDecor;
import nukeduck.functionaldecor.block.TileEntityDecor;

public class RendererAlarmClock extends DecorRenderer {
	private final ModelBase model = new ModelBase() {
		private final ModelRenderer box;

		{
			this.textureWidth = 64;
			this.textureHeight = 32;
			this.box = new ModelRenderer(this, 0, 0);
			this.box.addBox(3.0F, 12.0F, 3.0F, 10, 4, 10, 0.0f);
			this.box.addBox(4.0F, 11.5F, 4.0F, 1, 1, 1, 0.0F);
			this.box.addBox(6.0F, 11.5F, 4.0F, 1, 1, 1, 0.0F);
			this.box.addBox(4.0F, 11.75F, 6.0F, 3, 1, 2, 0.0F);
		}

		public void render(Entity entity, float a, float b, float c, float d, float e, float f) {
			this.box.render(f);
		}
	};

	private final ResourceLocation[] textures = new ResourceLocation[] {
		new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/alarm_clock_red.png"),
		new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/alarm_clock_green.png"),
		new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/alarm_clock_blue.png")
	};

	public ResourceLocation getTexture(TileEntityDecor tileEntity) {
		if(tileEntity == null) return this.textures[0];
		byte type = tileEntity.getCustomData().getByte("Type");

		return this.textures[type < this.textures.length ? type : 0];
	}

	private static final float SCREEN_SCALE = 1.0F / 48.0F;

	@Override
	public void render(TileEntityDecor tileEntity, float partialTicks) {
		World world = tileEntity.getWorldObj();
		boolean isSurface = world.provider.isSurfaceWorld();

		int time = (int) ((world.getWorldTime() + 6000L) % 24000L);
		int hour = time / 1000;
		int minute = (int) ((time % 1000) * 0.06);

		Tessellator tes = Tessellator.instance;
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTexture(tileEntity));

		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();

		GL11.glTranslatef(0.75F, 0.0625F, 0.1875F);
		GL11.glScalef(SCREEN_SCALE, SCREEN_SCALE, SCREEN_SCALE);

		tes.startDrawingQuads();
		double u = 0.375 * (minute & 1);
		tes.addVertexWithUV(0, 0, -0.03125, u, 0.71875);
		tes.addVertexWithUV(-24.0, 0, -0.03125, u + 0.375, 0.71875);
		tes.addVertexWithUV(-24.0, 9.0, -0.03125, u + 0.375, 0.4375);
		tes.addVertexWithUV(0, 9.0, -0.03125, u, 0.4375);
		tes.draw();

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

		// Draw time
		if(isSurface || (minute & 1) == 1) {
			double uHourT, uHourU, uMinuteT, uMinuteU;
			if(isSurface) {
				uHourT = hour / 10 * 0.0625;
				uHourU = hour % 10 * 0.0625;
				uMinuteT = minute / 10 * 0.0625;
				uMinuteU = minute % 10 * 0.0625;
			} else {
				uHourT = uHourU = uMinuteT = uMinuteU = 0.5;
			}

			tes.startDrawingQuads();

			tes.addVertexWithUV(-1.0, 1.0, -0.0625F, uHourT, 0.9375);
			tes.addVertexWithUV(-5.0, 1.0, -0.0625F, uHourT + 0.0625, 0.9375);
			tes.addVertexWithUV(-5.0, 8.0, -0.0625F, uHourT + 0.0625, 0.71875);
			tes.addVertexWithUV(-1.0, 8.0, -0.0625F, uHourT, 0.71875);

			tes.addVertexWithUV(-6.0, 1.0, -0.0625F, uHourU, 0.9375);
			tes.addVertexWithUV(-10.0, 1.0, -0.0625F, uHourU + 0.0625, 0.9375);
			tes.addVertexWithUV(-10.0, 8.0, -0.0625F, uHourU + 0.0625, 0.71875);
			tes.addVertexWithUV(-6.0, 8.0, -0.0625F, uHourU, 0.71875);

			tes.addVertexWithUV(-13.0, 1.0, -0.0625F, uMinuteT, 0.9375);
			tes.addVertexWithUV(-17.0, 1.0, -0.0625F, uMinuteT + 0.0625, 0.9375);
			tes.addVertexWithUV(-17.0, 8.0, -0.0625F, uMinuteT + 0.0625, 0.71875);
			tes.addVertexWithUV(-13.0, 8.0, -0.0625F, uMinuteT, 0.71875);

			tes.addVertexWithUV(-18.0, 1.0, -0.0625F, uMinuteU, 0.9375);
			tes.addVertexWithUV(-22.0, 1.0, -0.0625F, uMinuteU + 0.0625, 0.9375);
			tes.addVertexWithUV(-22.0, 8.0, -0.0625F, uMinuteU + 0.0625, 0.71875);
			tes.addVertexWithUV(-18.0, 8.0, -0.0625F, uMinuteU, 0.71875);

			tes.draw();
		}

		GL11.glDisable(GL11.GL_BLEND);
	}
}
