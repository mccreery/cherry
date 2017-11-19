package nukeduck.functionaldecor.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMicrophone extends ModelBase {
	private final ModelRenderer base, arms, mic;

	public ModelMicrophone() {
		this.textureWidth = 32;
		this.textureHeight = 16;

		this.base = new ModelRenderer(this, 0, 0);
		this.base.addBox(5.5F, 14.0F, 5.5F, 5, 2, 5);

		this.arms = new ModelRenderer(this, 0, 7);
		this.arms.addBox(6.0F, 9.0F, 6.0F, 1, 5, 4);
		this.arms.addBox(9.0F, 9.0F, 6.0F, 1, 5, 4);

		this.mic = new ModelRenderer(this, 20, 0);
		this.mic.setRotationPoint(8.0F, 11.0F, 8.0F);

		this.mic.addBox(-1.5F, -6.0F, -1.5F, 3, 7, 3);
	}

	public void setRotationAngle(byte rotation) {
		this.mic.rotateAngleX = rotation * 0.2F;
	}

	public void render(Entity entity, float a, float b, float c, float d, float e, float f) {
		this.base.render(f);
		this.arms.render(f);
		this.mic.render(f);
	}
}
