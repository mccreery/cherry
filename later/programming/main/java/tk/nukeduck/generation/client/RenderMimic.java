package tk.nukeduck.generation.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import tk.nukeduck.generation.entity.EntityMimic;

public class RenderMimic extends RenderLiving {
	public static final ResourceLocation TEXTURE = new ResourceLocation("alchemy", "textures/models/mimic.png");

	public RenderMimic(ModelBase model, float f) {
		super(model, f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	@Override
	public void doRender(EntityLiving entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		super.doRender(entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
