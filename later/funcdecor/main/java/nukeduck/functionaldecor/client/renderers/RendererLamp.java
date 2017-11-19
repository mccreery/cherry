package nukeduck.functionaldecor.client.renderers;

import static org.lwjgl.opengl.GL11.glRotatef;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import nukeduck.functionaldecor.FunctionalDecor;
import nukeduck.functionaldecor.block.TileEntityDecor;

public class RendererLamp extends DecorRenderer {
	private final ModelBase model = new ModelBase() {
		private final ModelRenderer base, base2, stem, box;

		{
			this.textureWidth = 32;
			this.textureHeight = 32;

			this.base = new ModelRenderer(this, 0, 0);
			this.base2 = new ModelRenderer(this, 0, 24);
			this.stem = new ModelRenderer(this, 16, 0);
			this.box = new ModelRenderer(this, 0, 8);
			this.base.addBox(6.0F, 12.0F, 6.0F, 4, 4, 4);
			this.base2.addBox(5.0F, 15.0F, 5.0F, 6, 1, 6);
			this.stem.addBox(7.0F, 8.0F, 7.0F, 2, 4, 2);
			this.box.addBox(4.0F, 2.0F, 4.0F, 8, 8, 8);
		}

		public void render(Entity entity, float a, float b, float c, float d, float e, float f) {
			this.base.render(f);
			this.base2.render(f);
			this.stem.render(f);
			this.box.render(f);
		}
	};

	private final ResourceLocation texture = new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/lamp.png");
	private final ResourceLocation bulb = new ResourceLocation(FunctionalDecor.MODID, "textures/misc/bright.png");

	@Override
	public void render(TileEntityDecor tileEntity, float partialTicks) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Entity camera = Minecraft.getMinecraft().thePlayer;

		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 10/16F, 0.5F);
		glRotatef(-camera.rotationYaw + tileEntity.blockMetadata / 16.0F * 360.0F, 0.0F, 1.0F, 0.0F);
		glRotatef(camera.rotationPitch, 1.0F, 0.0F, 0.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(this.bulb);
		GL11.glDisable(GL11.GL_LIGHTING);

		Tessellator tes = Tessellator.instance;
		tes.startDrawingQuads();

		tes.addVertexWithUV(-0.25, -0.25, 0.0, 0.0, 1.0);
		tes.addVertexWithUV(-0.25, 0.25, 0.0, 0.0, 0.0);
		tes.addVertexWithUV(0.25, 0.25, 0.0, 1.0, 0.0);
		tes.addVertexWithUV(0.25, -0.25, 0.0, 1.0, 1.0);

		tes.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

		Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		
		GL11.glDisable(GL11.GL_BLEND);
	}
}
